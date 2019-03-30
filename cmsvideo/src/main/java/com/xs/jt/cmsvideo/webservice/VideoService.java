package com.xs.jt.cmsvideo.webservice;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axiom.om.OMElement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.xs.jt.cmsvideo.entity.VideoConfig;
import com.xs.jt.cmsvideo.entity.VideoInfo;
import com.xs.jt.cmsvideo.entity.VideoPara;
import com.xs.jt.cmsvideo.manager.IVideoConfigManager;
import com.xs.jt.cmsvideo.manager.IVideoInfoManager;
import com.xs.jt.cmsvideo.manager.IVideoParaManager;


@Service
public class VideoService {
	
	@Autowired
	private IVideoInfoManager videoInfoManager;	
	
	@Autowired
	private IVideoParaManager videoParaManager;
	
	@Autowired
	private IVideoConfigManager videoConfigManager;

	protected static Log log = LogFactory.getLog(VideoService.class);
	public String setVideoPara(OMElement doc) throws DocumentException {
		log.info("测试axis2webservice:" + doc.getText());
		
		Document document = DocumentHelper.parseText(doc.getText());
		
		Element rootElement = document.getRootElement();
		
		Element videoparaElement = rootElement.element("videopara");
		
		Map<String,Object> param =new HashMap<String,Object>();
		
		for(Object o:videoparaElement.elements()) {
			Element element =(Element)o;
			String key = element.getName();
			String value = element.getText();
			param.put(key, value);
		}
		
		JSONObject jo =new JSONObject(param);
		VideoPara videopara = jo.toJavaObject(VideoPara.class);
		Document retDoc = DocumentHelper.createDocument();
		Element retElement= retDoc.addElement("root");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		try {
			if (StringUtils.isEmpty(videopara.getCylsh()) || StringUtils.isEmpty(videopara.getCyqxh())
					|| StringUtils.isEmpty(videopara.getCyqtd()) || StringUtils.isEmpty(videopara.getCllx())
					|| StringUtils.isEmpty(videopara.getCysj()) || StringUtils.isEmpty(videopara.getCycs())) {
				retElement.addElement("code").setText("-1001");
				retElement.addElement("message").setText("数据项内容不完整");
			} else {
				videoParaManager.addVideoPara(videopara);
				List<VideoConfig> configList = videoConfigManager.getVideoConfigByCyqxhAndCyqtd(videopara.getCyqxh(),
						videopara.getCyqtd());
				VideoInfo info = null;
				
				//查验结束
				List<VideoInfo> infoList = this.videoInfoManager.getVideoInfoByLshAndJycs(videopara.getCylsh(), Integer.parseInt(videopara.getCycs()));
				if(CollectionUtils.isEmpty(infoList)) {
					info = new VideoInfo();
				}else {
					info = infoList.get(0);
				}
				//查验开始
				if("0".equals(videopara.getCllx())) {
					for(VideoConfig config:configList) {
						info.setClsbdh(videopara.getClsbdh());
						info.setCyqtd(videopara.getCyqtd());
						info.setCyqxh(videopara.getCyqxh());
						info.setHphm(videopara.getHphm());
						info.setHpzl(videopara.getHpzl());
						info.setJycs(Integer.parseInt(videopara.getCycs()));
						info.setVideoBegin(df.parse(videopara.getCysj()));
						info.setZt(VideoInfo.ZT_WXZ);
						info.setLsh(videopara.getCylsh());
						info.setSfzmhm(videopara.getSfzmhm());
						info.setConfigId(config.getId());
						info.setDeviceName(config.getDeviceName());
						info.setJyjgbh(config.getJyjgbh());
						this.videoInfoManager.save(info);
					}
				}else {
					for(VideoInfo vinfo:infoList) {
						vinfo.setVideoEnd(df.parse(videopara.getCysj()));
						this.videoInfoManager.save(vinfo);
					}
				}
				retElement.addElement("code").setText("1");
				retElement.addElement("message").setText( "数据写入成功");
			}
		} catch (Exception e) {
			retElement.addElement("code").setText("$E");
			retElement.addElement("message").setText(e.toString());
		}
		return retDoc.asXML();
	}
}
