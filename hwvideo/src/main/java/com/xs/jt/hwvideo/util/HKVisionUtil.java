package com.xs.jt.hwvideo.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jna.NativeLong;
import com.xs.jt.hwvideo.util.HCNetSDK.NET_DVR_TIME;

@Component
public class HKVisionUtil {
	
	@Value("${hk.ip}")
	private String ip;
	
	@Value("${hk.port}")
	private int port;
	
	@Value("${hk.userName}")
	private String userName;
	
	@Value("${hk.password}")
	private String password;
	
	@Value("${hk.channel}")
	private long channel;
	
	@Value("${hk.picPath}")
	private String picPath;
	
	
	
	@Value("${hk1.ip}")
	private String devIp;
	
	@Value("${hk1.port}")
	private int devPort;
	
	@Value("${hk1.userName}")
	private String devUserName;
	
	@Value("${hk1.password}")
	private String devPassword;
	
	@Value("${hk1.channel}")
	private long devChannel;

	
	
	
	protected static Log log = LogFactory.getLog(HKVisionUtil.class);
	
	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	
	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息
	
	public void cameraInit() {
		// 初始化
		boolean initSuc = hCNetSDK.NET_DVR_Init();
		if (initSuc != true) {
			log.info("初始化失败");
		} else {
			log.info("初始化成功");
		}
	}

	// 注册
	public NativeLong register( String username, String password, String m_sDeviceIP, int iPort) throws Exception {

		iPort = 8000;
		// 注册
		m_strDeviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		// int iPort = 8000;
		log.info("注册，设备IP：" + m_sDeviceIP);
		NativeLong lUserID = hCNetSDK.NET_DVR_Login_V30(m_sDeviceIP, (short) iPort, username, password, m_strDeviceInfo);

		long userID = lUserID.longValue();
		if (userID == -1) {
			log.info("注册失败");
			throw new Exception("注册失败");
		} else {
			log.info("注册成功,lUserID:" + userID);
		}
		return lUserID;
	}
	
	public void taskPicture(String recordId) throws Exception {
		// 初始化
		cameraInit();
		NativeLong lUserID = register(devUserName,devPassword,devIp,devPort);
		try {
			NativeLong lChannel =new NativeLong(devChannel);
			HCNetSDK.NET_DVR_JPEGPARA jpgparam =new HCNetSDK.NET_DVR_JPEGPARA();
			jpgparam.wPicQuality=1;
			jpgparam.wPicSize=5;
			boolean flag = hCNetSDK.NET_DVR_CaptureJPEGPicture(lUserID, lChannel, jpgparam, picPath+"\\"+recordId+".jpg");
			if(!flag) {
				log.error("拍照失败:"+hCNetSDK.NET_DVR_GetLastError() );
				throw new Exception("拍照失");
			}
		}finally {
			// 注销用户
			hCNetSDK.NET_DVR_Logout(lUserID);
			// 释放SDK资源
			hCNetSDK.NET_DVR_Cleanup();
		}
	}
	
	
	public void downLoad(NET_DVR_TIME lpStartTime, NET_DVR_TIME lpStopTime,String saveFile) throws Exception {
		cameraInit();
		NativeLong lUserID = register(userName,password,ip,port);
		try {
			FileUtil.createDirectory(picPath+"\\temp\\");
			NativeLong lChannel =new NativeLong(channel);
			// 指定下载的文件
			NativeLong tRet = hCNetSDK.NET_DVR_GetFileByTime(lUserID, lChannel, lpStartTime, lpStopTime, picPath+"\\temp\\"+saveFile+".mp4");
			int tError = hCNetSDK.NET_DVR_GetLastError();
			if (tRet.longValue() == -1) {
				log.error("NET_DVR_GetFileByTime fail,channel:" + lChannel + "error code:" + tError);
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
			
			boolean status = ConvertVideo.processMp4(picPath+"\\temp\\"+saveFile+".mp4",saveFile+".mp4",picPath);
			
			if(status) {
				File file = new File(picPath+"\\temp\\"+saveFile+".mp4");
				file.delete();
			}
			
		} finally {
			// 注销用户
			hCNetSDK.NET_DVR_Logout(lUserID);
			// 释放SDK资源
			hCNetSDK.NET_DVR_Cleanup();
		}
	}

}
