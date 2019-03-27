package com.xs.jt.cmsvideo.webservice;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.entity.VideoPara;
import com.xs.jt.cmsvideo.manager.IVideoConfigManager;
import com.xs.jt.cmsvideo.manager.IVideoInfoManager;
import com.xs.jt.cmsvideo.manager.IVideoParaManager;
import com.xs.jt.cmsvideo.util.XmlMapAdapter;

@WebService
public class VideoWebServiceImpl implements IVideoWebService {
	
	protected static Log log = LogFactory.getLog(VideoWebServiceImpl.class);
	
	@Autowired
	private IVideoParaManager videoParaManager;
	@Autowired
	private IVideoInfoManager videoInfoManager;	
	@Autowired
	private IVideoConfigManager videoConfigManager;

	@Override
	public Map<String, String> setVideoPara(VideoPara videopara) {
		
		log.info("begin==setVideoPara");
		log.info("param："+JSONObject.toJSONString(videopara));
		
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			if (StringUtils.isEmpty(videopara.getCylsh()) || StringUtils.isEmpty(videopara.getCyqxh())
					|| StringUtils.isEmpty(videopara.getCyqtd()) || StringUtils.isEmpty(videopara.getCllx())
					|| StringUtils.isEmpty(videopara.getCysj()) || StringUtils.isEmpty(videopara.getCycs())) {
				map.put("code", "-1001");
				map.put("message", "数据项内容不完整");
			} else {
				videoParaManager.addVideoPara(videopara);
				List<VideoConfig> configList = videoConfigManager.getVideoConfigByCyqxhAndCyqtd(videopara.getCyqxh(),
						videopara.getCyqtd());
				VideoInfo info = null;
				//查验开始
				if("0".equals(videopara.getCllx())) {
					for(VideoConfig config:configList) {
						info = new VideoInfo();
						info.setClsbdh(videopara.getClsbdh());
						info.setCyqtd(videopara.getCyqtd());
						info.setCyqxh(videopara.getCyqxh());
						info.setHphm(videopara.getHphm());
						info.setHpzl(videopara.getHpzl());
						info.setJycs(Integer.parseInt(videopara.getCycs()));
						info.setVideoBegin(df.parse(videopara.getCysj()));
						info.setZt(VideoInfo.ZT_WXZ);
						info.setLsh(videopara.getCylsh());
						info.setConfigId(config.getId());
						info.setDeviceName(config.getDeviceName());
						info.setJyjgbh(config.getJyjgbh());
						this.videoInfoManager.save(info);
					}
				}else {
					//查验结束
					List<VideoInfo> infoList = this.videoInfoManager.getVideoInfoByLshAndJycs(videopara.getCylsh(), Integer.parseInt(videopara.getCycs()));
					for(VideoInfo vinfo:infoList) {
						vinfo.setVideoEnd(df.parse(videopara.getCysj()));
						this.videoInfoManager.save(vinfo);
					}
				}
				
				map.put("code", "1");
				map.put("message", "数据写入成功");
			}
		} catch (Exception e) {
			map.put("code", "$E");
			map.put("message", e.toString());
		}
		return map;
	}

}
