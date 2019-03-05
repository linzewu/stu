package com.xs.jt.cmsvideo.job;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sun.jna.NativeLong;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.manager.IVideoConfigManager;
import com.xs.jt.cmsvideo.manager.IVideoInfoManager;
import com.xs.jt.cmsvideo.util.ConvertVideo;
import com.xs.jt.cmsvideo.util.FtpUtil;
import com.xs.jt.cmsvideo.util.HCNetSDK;
import com.xs.jt.cmsvideo.util.HCNetSDK.NET_DVR_TIME;

@Component("videoListJob")
public class VideoListJob {

	protected static Log log = LogFactory.getLog(VideoListJob.class);
	@Autowired
	private IVideoConfigManager videoConfigManager;
	@Autowired
	private IVideoInfoManager videoInfoManager;

	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息
	// public NativeLong lUserID = new NativeLong(-1);// 用户句柄

	// 视频下载地址
	static String downLoadPath = "E:\\";

	// 转码后视频存放地址
	static String convertOutPath = "E:\\vod\\";

	// 上传ftp地址
	static String  ftpPath = "svn/CMSVideo/video/";

	/**
	 * 定时下载视频
	 * 
	 * @throws InterruptedException
	 */
	//@Scheduled(cron = "0 0/1 * * * ? ")
	public void downLoadVideo() throws InterruptedException {
		log.info("***************downLoadVideo begin*********************");
		String jyjgbh = "3200000195";// *********************
		String jcxdh = "1";// *********************
		String jyxm = "B1";// *********************
		List<VideoConfig> configList = videoConfigManager.getVideoConfigByJyjgbhAndJcxdhAndJyxm(jyjgbh, jcxdh, jyxm);
		for (VideoConfig vc : configList) {
			NativeLong lUserID = new NativeLong(-1);
			// 初始化
			CameraInit();
			// 注册
			lUserID = register(lUserID, vc.getUserName(), vc.getPassword(), vc.getIp(), Integer.parseInt(vc.getPort()));
			// 按时间下载
			NET_DVR_TIME lpStartTime = null;// *********************
			NET_DVR_TIME lpStopTime = null;// *********************
			// 下载后保存到PC机的文件路径，需为绝对路径（包括文件名）
			String saveFile = "";// *********************
			long channel = vc.getChannel();
			downLoad(lUserID, new NativeLong(channel), lpStartTime, lpStopTime, saveFile);

		}
		log.info("***************downLoadVideo end*********************");
	}

	@Scheduled(cron = "0 0/1 * * * ? ")
	public void convertVideoAndUpload() {
		log.info("***************convertVideoAndUpload begin*********************");
		// 查询所有已下载数据
		List<VideoInfo> infoList = videoInfoManager.getVideoInfoByZt(VideoInfo.ZT_YXZ, 3);

		// 转为H264
		for (VideoInfo vi : infoList) {
			// 转码中
			vi.setTaskCount(0);
			vi.setZt(VideoInfo.ZT_ZMZ);
			vi.setReason("");
			updateStatus(vi);
			ConvertVideo.getPath(downLoadPath + vi.getVideoName(), convertOutPath,
					vi.getVideoName().substring(0, vi.getVideoName().indexOf(".")));

			if (!ConvertVideo.checkfile(downLoadPath + vi.getVideoName())) {
				log.info("convertVideoAndUpload: " + downLoadPath + vi.getVideoName() + " is not file");
				continue;
			}
			try {
				if (ConvertVideo.process()) {
					// 转码成功
					vi.setTaskCount(0);
					vi.setZt(VideoInfo.ZT_ZMWC);
					vi.setReason("");
					updateStatus(vi);
					
					//上传
					String ftpHost = "192.168.0.105";
			        String ftpUserName = "test";
			        String ftpPassword = "0901";
			        int ftpPort = 21;
			        
			        String localPath = convertOutPath+vi.getVideoName();
			        String fileName = vi.getVideoName();

			        //上传一个文件
			        try{
			        	vi.setTaskCount(0);
						vi.setZt(VideoInfo.ZT_SCZ);
						vi.setReason("");
						updateStatus(vi);
			            FileInputStream in=new FileInputStream(new File(localPath));
			            boolean uploadFlag = FtpUtil.uploadFile(ftpHost, ftpUserName, ftpPassword, ftpPort, ftpPath, fileName,in);
			            if(uploadFlag) {
			            	vi.setTaskCount(0);
							vi.setZt(VideoInfo.ZT_JS);
							vi.setReason("");
							updateStatus(vi);
			            }
			            System.out.println(uploadFlag);
			        } catch (IOException e){
			        	vi.setTaskCount((vi.getTaskCount() == null?0:vi.getTaskCount())+1);
						vi.setZt(VideoInfo.ZT_ZHSB);
						vi.setReason(e.getMessage());
						updateStatus(vi);
						continue;
			        }
					
				}
			} catch (Exception e) {
				//转码失败
				vi.setTaskCount((vi.getTaskCount() == null?0:vi.getTaskCount())+1);
				vi.setZt(VideoInfo.ZT_ZHSB);
				vi.setReason(e.getMessage());
				updateStatus(vi);
				continue;
			}
		}

		log.info("***************convertVideoAndUpload end*********************");
	}

	public void CameraInit() {
		// 初始化
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc != true) {
			log.info("初始化失败");
		} else {
			log.info("初始化成功");
		}
	}

	// 注册
	public NativeLong register(NativeLong lUserID, String username, String password, String m_sDeviceIP, int iPort) {
		// ****** 注册之前先注销已注册的用户,预览情况下不可注销
//		if (lUserID.longValue() > -1) {
//			// 先注销
//			hCNetSDK.NET_DVR_Logout(lUserID);
//			lUserID = new NativeLong(-1);
//		}

		// 注册
		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		// int iPort = 8000;
		log.info("注册，设备IP：" + m_sDeviceIP);
		lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) iPort, username, password, m_strDeviceInfo);

		long userID = lUserID.longValue();
		if (userID == -1) {
			log.info("注册失败");
		} else {
			log.info("注册成功,lUserID:" + userID);
		}
		return lUserID;
	}

	public void downLoad(NativeLong lUserID, NativeLong lChannel, NET_DVR_TIME lpStartTime, NET_DVR_TIME lpStopTime,
			String saveFile) throws InterruptedException {
		// 指定下载的文件
		NativeLong tRet = hCNetSDK.NET_DVR_GetFileByTime(lUserID, lChannel, lpStartTime, lpStopTime, saveFile);
		int tError = hCNetSDK.NET_DVR_GetLastError();
		if (tRet.longValue() == -1) {
			log.info("NET_DVR_GetFileByTime fail,channel:" + lChannel + "error code:" + tError);
		}
		// 开启下载
		boolean flagPlay = hCNetSDK.NET_DVR_PlayBackControl(tRet, hCNetSDK.NET_DVR_PLAYSTART, 0, null);
		tError = hCNetSDK.NET_DVR_GetLastError();
		if (!flagPlay) {
			log.info("NET_DVR_PlayBackControl() failed ,ErrorCode:" + tError);
		}

		// 获取下载进度
		int nPos = 0;
		for (nPos = 0; nPos < 100 && nPos >= 0;) {
			Thread.sleep(2000); // millisecond
			nPos = hCNetSDK.NET_DVR_GetDownloadPos(tRet);
			log.info("channel:" + lChannel + ",  is downloading... " + nPos); // 下载进度
		}
		// 注销用户
		hCNetSDK.NET_DVR_Logout(lUserID);
		// 释放SDK资源
		hCNetSDK.NET_DVR_Cleanup();
	}

	private void updateStatus(VideoInfo info) {
		this.videoInfoManager.save(info);
	}

}
