package com.xs.jt.cmsvideo.job;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.sun.jna.NativeLong;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.cmsvideo.config.RabbitConfig;
import com.xs.jt.cmsvideo.entity.FtpConfig;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.entity.VideoWarn;
import com.xs.jt.cmsvideo.manager.IFtpConfigManager;
import com.xs.jt.cmsvideo.manager.IVideoConfigManager;
import com.xs.jt.cmsvideo.manager.IVideoInfoManager;
import com.xs.jt.cmsvideo.manager.IVideoWarnManager;
import com.xs.jt.cmsvideo.util.FileUtil;
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
	@Autowired
	private IFtpConfigManager ftpConfigManager;
	@Autowired
	private IVideoWarnManager videoWarnManager;

	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;

	static HCNetSDK hCNetSDK = HCNetSDK.INSTANCE;
	HCNetSDK.NET_DVR_DEVICEINFO_V30 m_strDeviceInfo;// 设备信息

	// public NativeLong lUserID = new NativeLong(-1);// 用户句柄

	// 视频下载地址
	@Value("${video.downloadpath}")
	private String downLoadPath;

	// 转码后视频存放地址
	@Value("${video.convertoutpath}")
	private String convertOutPath;

	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Resource(name="simpleMessageListenerContainerMap")
	private Map<String,SimpleMessageListenerContainer>  simpleMessageListenerContainerMap;
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Resource(name = "connectionFactory")
	private ConnectionFactory connectionFactory;
	

	/**
	 * 定时下载视频
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0/5 * * * * ? ")
	public void sendDownLoadVideoMessage() throws InterruptedException {
		log.info("***************downLoadVideo begin*********************");
		// 目录不存在则创建目录
		FileUtil.createDirectory(downLoadPath);
		FileUtil.createDirectory(convertOutPath);

		List<VideoInfo> noDownLoadList = this.videoInfoManager.getVideoInfosNoDownLoad(VideoInfo.ZT_WXZ,
				getMaxTaskCou());

		if (CollectionUtils.isEmpty(noDownLoadList)) {
			return;
		}

		for (VideoInfo info : noDownLoadList) {
			String queryName = getQueryName(info);
			if(StringUtils.isEmpty(queryName)) {
				return;
			}
			initQuery(queryName);
			rabbitTemplate.convertAndSend(queryName, JSONObject.toJSONString(info));
			info.setZt(VideoInfo.ZT_XZZ);
			this.updateStatus(info);
		}
		log.info("***************downLoadVideo end*********************");
	}
	
	private String getQueryName(VideoInfo info) {
		VideoConfig vc = videoConfigManager.getVideoConfigById(info.getConfigId());
		if(StringUtils.isEmpty(vc.getIp())) {
			return null;
		}
		String queryName ="jy_"+vc.getIp()+"_Query";
		return queryName;
	}
	
	private void initQuery(String queryName) {
		Properties properties = rabbitAdmin.getQueueProperties(queryName);
		if(properties==null) {
			rabbitAdmin.declareQueue(new Queue(queryName));
		}
		if(simpleMessageListenerContainerMap.get(queryName)==null) {
			SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
			 container.setQueueNames(queryName);
			 container.setExposeListenerChannel(true);
			 container.setPrefetchCount(1);//设置每个消费者获取的最大的消息数量
			 container.setConcurrentConsumers(4);//消费者个数
			 //container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式为手工确认
			 container.setMessageListener(new MessageListener() {
				@Override
				public void onMessage(Message message) {
					byte[] body = message.getBody();
					VideoInfo info = null;
					try {
						info = JSONObject.parseObject(new String(body,"UTF-8"), VideoInfo.class);
//						List<VideoConfig> configList = videoConfigManager.getVideoConfigByCyqxhAndCyqtd(info.getCyqxh(),
//								info.getCyqtd());
						VideoConfig vc = videoConfigManager.getVideoConfigById(info.getConfigId());
						if (vc == null) {
							// 下載失敗
							info.setTaskCount((info.getTaskCount() == null ? 0 : info.getTaskCount()) + 1);
							info.setZt(info.getTaskCount() == getMaxTaskCou() ? VideoInfo.ZT_XZSB : VideoInfo.ZT_WXZ);
							info.setReason("无法找到该查验通道视频配置信息！");
							return;
						}
						NativeLong lUserID = new NativeLong(-1);
						// 初始化
						CameraInit();
						int hdport = 8000;
						if (!StringUtils.isEmpty(vc.getHdPort())) {
							hdport = Integer.parseInt(vc.getHdPort());
						}
						// 注册
						lUserID = register(lUserID, vc.getUserName(), vc.getPassword(), vc.getIp(), hdport);
						// 按时间下载
						NET_DVR_TIME lpStartTime = new HCNetSDK.NET_DVR_TIME();
						convert(info.getVideoBegin(), lpStartTime);
						NET_DVR_TIME lpStopTime = new HCNetSDK.NET_DVR_TIME();
						convert(info.getVideoEnd(), lpStopTime);
						// 下载后保存到PC机的文件路径，需为绝对路径（包括文件名）
						String fileName = info.getCyqxh() + info.getCyqtd() + "_" + info.getConfigId() + "_" + info.getJycs() + "_"
								+ info.getLsh() + ".mp4";
						String saveFile = downLoadPath + fileName;
						long channel = getChannelNumber(vc.getChannel(), lUserID);

						downLoad(lUserID, new NativeLong(channel), lpStartTime, lpStopTime, saveFile);

						// 修改状态为已下载
						info.setVideoName(fileName);
						info.setZt(VideoInfo.ZT_YXZ);
						info.setTaskCount(0);
						updateStatus(info);
					} catch (Exception e) {
						// 下載失敗
						info.setTaskCount((info.getTaskCount() == null ? 0 : info.getTaskCount()) + 1);
						info.setZt(info.getTaskCount() == getMaxTaskCou() ? VideoInfo.ZT_XZSB : VideoInfo.ZT_WXZ);
						info.setReason(e.toString());
						log.error("下载视频失败！", e);
						updateStatus(info);
					}

				}
			});//监听处理类
			 container.start();
			 simpleMessageListenerContainerMap.put(queryName,container);
		}
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

	@Scheduled(cron = "0/5 * * * * ? ")
	public void sendUploadVideoMessage() {
		// 查询所有已下载数据
		List<VideoInfo> uploadList = videoInfoManager.getVideoInfoByZt(VideoInfo.ZT_YXZ, getMaxTaskCou());
		if (CollectionUtils.isEmpty(uploadList)) {
			return;
		}

		for (VideoInfo info : uploadList) {
			rabbitTemplate.convertAndSend(RabbitConfig.QUEUE_VIDEO_UPLOAD, JSONObject.toJSONString(info));
			info.setZt(VideoInfo.ZT_ZMSCZ);
			this.updateStatus(info);
		}
	}

	@RabbitListener(queues = RabbitConfig.QUEUE_VIDEO_UPLOAD, containerFactory = "videoContainerFactory")
	public void convertVideoAndUpload(String message) {
		log.info("***************convertVideoAndUpload begin*********************");
		VideoInfo vi = JSONObject.parseObject(message, VideoInfo.class);
		
		try {
			String createDate = vi.getCreateTime().substring(0, 10);
			String[] dateArr = createDate.split("-");
			String ftpPath = (dateArr[0]+dateArr[1])+"/"+(dateArr[2]);
			FtpConfig ftpConfig = ftpConfigManager.getFtpConfigByJyjgbh(vi.getJyjgbh());
			if(ftpConfig == null) {
				throw new Exception("检验机构编号： "+vi.getJyjgbh()+" 获取不到视频Ftp地址！");
			}
			// 上传
			String localPath = downLoadPath + vi.getVideoName();
			String fileName = vi.getVideoName();
			File localFile = new File(localPath);

			// 上传一个文件
			FileInputStream in = new FileInputStream(localFile);
			boolean uploadFlag = FtpUtil.uploadFile(ftpConfig.getFtpHost(), ftpConfig.getFtpUserName(), ftpConfig.getFtpPassword(), ftpConfig.getFtpPort(), ftpPath, fileName, in);

			if (uploadFlag) {
				// 上传成功
				vi.setVideoSize(localFile.length());
				vi.setTaskCount(0);
				vi.setZt(VideoInfo.ZT_JS);
				vi.setReason("");
				updateStatus(vi);
				// 删除转码后文件
				FileUtil.deleteFile(downLoadPath + vi.getVideoName());
				
				//视频预警
				VideoEarlyWarning(vi);
			} else {
				throw new Exception("上传失败！");
			}

		} catch (Exception e) {
			// 转码失败
			vi.setTaskCount((vi.getTaskCount() == null ? 0 : vi.getTaskCount()) + 1);
			vi.setZt(vi.getTaskCount() == getMaxTaskCou() ? VideoInfo.ZT_ZMSCSB : VideoInfo.ZT_YXZ);
			vi.setReason(e.getMessage());
			updateStatus(vi);
			log.error("上传失败 ", e);
		}

		log.info("***************convertVideoAndUpload end*********************");
	}
	
	public void VideoEarlyWarning(VideoInfo vi) {
		
		List<BaseParams> bps = baseParamsManager.getBaseParamsByType("spyj");
		for(BaseParams bp:bps) {
			VideoWarn warn = null;
			//视频时间低于秒数
			if(VideoWarn.SPYJ_SJDY.equals(bp.getParamValue())) {
				//视频时间长度
				long time = (vi.getVideoEnd().getTime()-vi.getVideoBegin().getTime())/1000;
				if(time < Long.parseLong(bp.getMemo())) {
					warn = new VideoWarn();
					warn.setRemark("视频时间:"+time+"秒,小于阈值"+bp.getMemo()+"秒预警");
				}
				
			}else if(VideoWarn.SPYJ_SJDY.equals(bp.getParamValue())) {
				//视频时间高于秒数
				//视频时间长度
				long time = (vi.getVideoEnd().getTime()-vi.getVideoBegin().getTime())/1000;
				if(time > Long.parseLong(bp.getMemo())) {
					warn = new VideoWarn();
					warn.setRemark("视频时间:"+time+"秒,大于阈值"+bp.getMemo()+"秒预警");
				}
			}else if(VideoWarn.SPYJ_DXDY.equals(bp.getParamValue())) {
				//视频大小低于兆数
				Double videoSize = (double) (vi.getVideoSize()/1024/1024);
				if(videoSize < Double.parseDouble(bp.getMemo())) {
					warn = new VideoWarn();
					warn.setRemark("视频大小:"+videoSize+"兆,小于阈值"+bp.getMemo()+"兆预警");
				}
			}else if(VideoWarn.SPYJ_DXGY.equals(bp.getParamValue())) {
				//视频大小高于兆数
				Double videoSize = (double) (vi.getVideoSize()/1024/1024);
				if(videoSize > Double.parseDouble(bp.getMemo())) {
					warn = new VideoWarn();
					warn.setRemark("视频大小:"+videoSize+"兆,大于阈值"+bp.getMemo()+"兆预警");
				}
			}
			if(warn != null) {
				warn.setJyjgbh(vi.getJyjgbh());
				warn.setDeviceName(vi.getDeviceName());
				warn.setLsh(vi.getLsh());
				warn.setJycs(vi.getJycs());
				warn.setVid(vi.getId());
				warn.setType(Integer.parseInt(bp.getParamValue()));
				this.videoWarnManager.save(warn);
			}
		}
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
	public NativeLong register(NativeLong lUserID, String username, String password, String m_sDeviceIP, int iPort) throws Exception {
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
			log.info("注册失败");
			throw new Exception("注册失败");
		} else {
			log.info("注册成功,lUserID:" + userID);
		}
		return lUserID;
	}

	public void downLoad(NativeLong lUserID, NativeLong lChannel, NET_DVR_TIME lpStartTime, NET_DVR_TIME lpStopTime,
			String saveFile) throws Exception {
		try {
			log.info("lUserID:"+lUserID+" lChannel:"+lChannel+" lpStartTime:"+lpStartTime+" lpStopTime:"+lpStopTime+" saveFile:"+saveFile);
			// 指定下载的文件
			NativeLong tRet = hCNetSDK.NET_DVR_GetFileByTime(lUserID, lChannel, lpStartTime, lpStopTime, saveFile);
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
		} finally {
			// 注销用户
			hCNetSDK.NET_DVR_Logout(lUserID);
			// 释放SDK资源
			hCNetSDK.NET_DVR_Cleanup();
		}
	}

	private void updateStatus(VideoInfo info) {
		this.videoInfoManager.save(info);
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

	private int getMaxTaskCou() {
		int maxCou = 3;
		BaseParams bp = this.baseParamsManager.getBaseParam("taskExecCount", "定时任务允许最多失败次数");
		if (bp != null) {
			maxCou = Integer.parseInt(bp.getParamValue());
		}

		return maxCou;
	}

}
