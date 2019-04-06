package com.xs.jt.vehvideo.job;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.vehvideo.entity.VideoInfo;
import com.xs.jt.vehvideo.manager.DownloadVideoService;
import com.xs.jt.vehvideo.manager.IVideoInfoManager;
import com.xs.jt.vehvideo.util.FileUtil;

@Service
public class ScanVehVideoInfoJob {
	
	protected static Log log = LogFactory.getLog(ScanVehVideoInfoJob.class);
	
	// 视频下载地址
	@Value("${video.downloadpath}")
	private String downLoadPath;
	
	@Autowired
	private IVideoInfoManager videoInfoManager;
	
	@Resource(name = "baseParamsManager")
	private IBaseParamsManager baseParamsManager;
	
	@Resource(name = "connectionFactory")
	private ConnectionFactory connectionFactory;
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	@Autowired
	private DownloadVideoService downloadVideoService;
	
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	@Resource(name="simpleMessageListenerContainerMap")
	private Map<String,SimpleMessageListenerContainer>  simpleMessageListenerContainerMap;
	
	
	
	/**
	 * 定时下载视频
	 * 
	 * @throws InterruptedException
	 */
	@Scheduled(cron = "0/5 * * * * ? ")
	public void sendDownLoadVideoMessage() throws InterruptedException {
		
		VideoInfo videoInfo=new VideoInfo();
		
		videoInfo.setZt(VideoInfo.ZT_WXZ);
		videoInfo.setTaskCount(getMaxTaskCou());
		
		Pageable pageable =new QPageRequest(0, 100);
		Page<VideoInfo> page = this.videoInfoManager.getVideoInfos(pageable,videoInfo);
		
		 List<VideoInfo> videInfos=page.getContent();
		if (CollectionUtils.isEmpty(videInfos)) {
			return;
		}
		for (VideoInfo info : videInfos) {
			String queryName = getQueryName(info);
			if(StringUtils.isEmpty(queryName)) {
				return;
			}
			initQuery(queryName);
			rabbitTemplate.convertAndSend(queryName, JSONObject.toJSONString(info));
			info.setZt(VideoInfo.ZT_XZZ);
			this.updateStatus(info);
		}
	}
	
	private String getQueryName(VideoInfo info) {
		String ip = info.getIp();
		if(StringUtils.isEmpty(ip)) {
			return null;
		}
		String queryName ="jy_"+ip+"_Query";
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
			 container.setConcurrentConsumers(3);//消费者个数
			 //container.setAcknowledgeMode(AcknowledgeMode.MANUAL);//设置确认模式为手工确认
			 container.setMessageListener(downloadVideoService);//监听处理类
			 container.start();
			 simpleMessageListenerContainerMap.put(queryName,container);
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
	
	
	private void updateStatus(VideoInfo info) {
		this.videoInfoManager.save(info);
	}

}
