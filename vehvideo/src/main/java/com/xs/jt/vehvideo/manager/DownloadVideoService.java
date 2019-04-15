package com.xs.jt.vehvideo.manager;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.vehvideo.entity.VideoInfo;
import com.xs.jt.vehvideo.util.ClientDemo;
import com.xs.jt.vehvideo.util.FileCopyUtils;
import com.xs.jt.vehvideo.util.FileUtil;
import com.xs.jt.vehvideo.util.HCNetSDK;
import com.xs.jt.vehvideo.util.HCNetSDK.NET_DVR_TIME;

@Service
@Scope("prototype")
public class DownloadVideoService implements ChannelAwareMessageListener {

	static ConcurrentHashMap<String,HCNetSDK> hCNetSDKMap = new ConcurrentHashMap<String,HCNetSDK>();
	public static ConcurrentHashMap<String,Integer> countMap = new ConcurrentHashMap<String,Integer>();
	static ConcurrentHashMap<String,NativeLong> userIdMap = new ConcurrentHashMap<String,NativeLong>();
	//static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	//private HCNetSDK hCNetSDK;
	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息

	// 视频下载地址
	@Value("${video.downloadpath}")
	private String downLoadPath;

	@Autowired
	private IVideoInfoManager videoInfoManager;

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;

	protected static Log log = LogFactory.getLog(DownloadVideoService.class);
	
	SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
	
	public HCNetSDK getHcNetSdk(String ip) throws IOException {
		if(!hCNetSDKMap.containsKey(ip)) {
			FileCopyUtils.copyDir(ClientDemo.EXTEND_PATH+"\\hk", ClientDemo.EXTEND_PATH+"\\"+ip);
			HCNetSDK INSTANCE = (HCNetSDK) Native.loadLibrary(ClientDemo.EXTEND_PATH+"\\"+ip+"\\HCNetSDK.dll",
		            HCNetSDK.class);
			CameraInit(INSTANCE);
			hCNetSDKMap.put(ip, INSTANCE);
		}
		log.info("###############################################"+hCNetSDKMap);
		return hCNetSDKMap.get(ip);
	}

	@Override
	@Transactional(noRollbackFor=Exception.class)
	public void onMessage(Message message, Channel channel) throws Exception {
		byte[] body = message.getBody();
		VideoInfo info = null;
		try {
			info = JSONObject.parseObject(new String(body), VideoInfo.class);
			NativeLong lUserID = new NativeLong(-1);
			// 初始化
			HCNetSDK hCNetSDK = this.getHcNetSdk(info.getIp());
			if(!userIdMap.contains(info.getIp())) {
			// 注册
			lUserID = register(lUserID, info.getUserName(), info.getPassword(), info.getIp(),
					Integer.parseInt(info.getPort()),hCNetSDK);
			userIdMap.put(info.getIp(), lUserID);
			}else {
				lUserID = userIdMap.get(info.getIp());
			}
			
			
			Date kssj=info.getKssj();
			Date jssj =info.getJssj();
			
			if(jssj.getTime()-kssj.getTime()>=(1000*60*10)) {
				kssj=new Date(jssj.getTime()-(1000*60*10));
			}
			
			// 按时间下载
			NET_DVR_TIME lpStartTime = new HCNetSDK.NET_DVR_TIME();
			convert(kssj, lpStartTime);
			NET_DVR_TIME lpStopTime = new HCNetSDK.NET_DVR_TIME();
			convert(jssj, lpStopTime);
			// 下载后保存到PC机的文件路径，需为绝对路径（包括文件名）
			String fileName = info.getJylsh() + "_" + info.getJyxm() + "_" + info.getJycs() + "_" + ".mp4";
			
			String path =downLoadPath+sdf.format(info.getJssj())+"\\"+info.getJyjgbh();
			FileUtil.createDirectory(path);
			String saveFile = path +"/"+ fileName;
			long channelno = getChannelNumber(Long.valueOf(info.getChannelno()), lUserID) + 1;
			log.info("-----------------------------------ip:"+info.getIp()+"  sdk:"+hCNetSDK+"  hashcode:"+hCNetSDK.hashCode());
			downLoad(lUserID, new NativeLong(channelno), lpStartTime, lpStopTime, saveFile,hCNetSDK,info.getIp());
			// 修改状态为已下载
			info.setVideoName(fileName);
			info.setZt(VideoInfo.ZT_YXZ);
			info.setVideoSize(FileUtil.getFileSize(saveFile));
			log.info("*************************************************************************save info");
			this.videoInfoManager.save(info);
		} catch (Exception e) {
			// 下載失敗
			info.setTaskCount((info.getTaskCount() == null ? 0 : info.getTaskCount()) + 1);
			info.setZt(VideoInfo.ZT_XZSB);
			info.setReason(e.toString());
			log.error("下载视频失败！", e);
			this.videoInfoManager.save(info);
		}
	}

	public void CameraInit(HCNetSDK hCNetSDK) {
		// 初始化
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc != true) {
			log.debug("初始化失败");
		} else {
			log.debug("初始化成功");
		}
	}

	public NativeLong register(NativeLong lUserID, String username, String password, String m_sDeviceIP, int iPort,HCNetSDK hCNetSDK)
			throws Exception {
		// ****** 注册之前先注销已注册的用户,预览情况下不可注销
//		if (lUserID.longValue() > -1) {
//			// 先注销
//			hCNetSDK.NET_DVR_Logout(lUserID);
//			lUserID = new NativeLong(-1);
//		}
		iPort = 8000;
		// 注册
		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		// int iPort = 8000;
		log.info("注册，设备IP：" + m_sDeviceIP);
		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) iPort, username, password, m_strDeviceInfo);

		long userID = lUserID.longValue();
		if (userID == -1) {
			log.error("注册失败:"+m_sDeviceIP+" countMap:"+countMap);
			throw new Exception("注册失败");
		} else {
			/////
			log.info("before "+countMap);
			if(!countMap.containsKey(m_sDeviceIP)) {
				countMap.put(m_sDeviceIP, 1);
			}else {
				int b = countMap.get(m_sDeviceIP)+1;
				countMap.replace(m_sDeviceIP, b);
			}
			log.info("after "+countMap);
			/////
			log.info("注册成功,IP:"+m_sDeviceIP+" lUserID:" + userID);
		}
		return lUserID;
	}

	private void convert(Date date, NET_DVR_TIME lpStartTime) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		lpStartTime.dwYear = now.get(Calendar.YEAR);
		lpStartTime.dwMonth = (now.get(Calendar.MONTH) + 1);
		lpStartTime.dwDay = now.get(Calendar.DAY_OF_MONTH);
		lpStartTime.dwHour = now.get(Calendar.HOUR_OF_DAY);
		lpStartTime.dwMinute = now.get(Calendar.MINUTE);
		lpStartTime.dwSecond = now.get(Calendar.SECOND);
	}

	/**
	 * 获取设备通道
	 */
	public int getChannelNumber(long channel, NativeLong lUserID) {
		int iChannelNum = -1;
		int iIPChanStart = 0;

		if (m_strDeviceInfo.byIPChanNum >= 64) {
			iIPChanStart = 0;
		} else {
			iIPChanStart = 32;
		}

		if (channel >= m_strDeviceInfo.byChanNum) {
			iChannelNum = (int) (channel - m_strDeviceInfo.byChanNum + iIPChanStart);
		}
		return iChannelNum;
	}

	public void downLoad(NativeLong lUserID, NativeLong lChannel, NET_DVR_TIME lpStartTime, NET_DVR_TIME lpStopTime,
			String saveFile,HCNetSDK hCNetSDK,String m_sDeviceIP) throws Exception {
		try {
			log.info("lUserID:" + lUserID + " lChannel:" + lChannel + " lpStartTime:" + lpStartTime + " lpStopTime:"
					+ lpStopTime + " saveFile:" + saveFile);
			// 指定下载的文件
			NativeLong tRet = hCNetSDK.NET_DVR_GetFileByTime(lUserID, lChannel, lpStartTime, lpStopTime, saveFile);
			int tError = hCNetSDK.NET_DVR_GetLastError();
			if (tRet.longValue() == -1) {
				log.error("NET_DVR_GetFileByTime fail,channel:" + lChannel + "error code:" + tError+" userId:"+lUserID+" m_sDeviceIP:"+m_sDeviceIP+" countMap:"+countMap);
				throw new Exception("NET_DVR_GetFileByTime fail,channel:" + lChannel + "error code:" + tError);
			}

			hCNetSDK.NET_DVR_PlayBackControl(tRet, hCNetSDK.NET_DVR_SET_TRANS_TYPE, hCNetSDK.NET_DVR_SET_TRANS_TYPE_MP4,
					null);
			// 开启下载
			boolean flagPlay = hCNetSDK.NET_DVR_PlayBackControl(tRet, hCNetSDK.NET_DVR_PLAYSTART, 0, null);
			tError = hCNetSDK.NET_DVR_GetLastError();
			if (!flagPlay) {
				log.error("NET_DVR_PlayBackControl() failed ,ErrorCode:" + tError);
				throw new Exception("NET_DVR_PlayBackControl() failed ,ErrorCode:" + tError);
			}

			// 获取下载进度
			int nPos = 0;
			for (nPos = 0; nPos < 100 && nPos >= 0;) {
				Thread.sleep(2000); // millisecond
				nPos = hCNetSDK.NET_DVR_GetDownloadPos(tRet);
				log.info("channel:" + lChannel + ",  is downloading... " + nPos); // 下载进度
			}
		} finally {
			log.info("logout before "+countMap);
			int b = countMap.get(m_sDeviceIP)-1;
			countMap.replace(m_sDeviceIP, b);
			log.info("logout after "+countMap);
			// 注销用户
			//hCNetSDK.NET_DVR_Logout(lUserID);
			// 释放SDK资源
			//hCNetSDK.NET_DVR_Cleanup();
		}
	}

	

	private int getMaxTaskCou() {
		int maxCou = 3;
		BaseParams bp = this.baseParamsManager.getBaseParam("taskExecCount", "定时任务允许最多失败次数");
		if (bp != null) {
			maxCou = Integer.parseInt(bp.getParamValue());
		}
		return maxCou;
	}

}
