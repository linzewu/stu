package com.xs.jt.base.module.common;

import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.hibernate.engine.jdbc.SerializableBlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.util.ServletContextPropertyUtils;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.Node;
import com.aspose.words.NodeCollection;
import com.aspose.words.NodeType;
import com.aspose.words.SaveFormat;
import com.aspose.words.Shape;
import com.xs.jt.base.module.entity.BaseParams;

public class Sql2WordUtil {
	
	static Logger logger = LoggerFactory.getLogger(Sql2WordUtil.class);
	
	public static Document sql2WordUtil(final String template,final String sql, JdbcTemplate jdbcTemplate,Map<String,List<BaseParams>> bpsMap) throws Exception{
		Map<String,Object> data = jdbcTemplate.queryForMap(sql);
		Document doc=null;
		if(data!=null) {
			 doc = createTemplate(template,data,bpsMap);
		}
		return doc;
	}
	
	public static Map<String,Object> sql2MapUtil(final String sql,JdbcTemplate jdbcTemplate) throws Exception{
		Map<String,Object> data = jdbcTemplate.queryForMap(sql);
		return data;
	}
	
	public static Document map2WordUtil(final String template,Map<String,Object> data,Map<String,List<BaseParams>> bpsMap) throws Exception{
		
		Document doc=null;
		if(data!=null) {
			 doc = createTemplate(template,data,bpsMap);
		}
		return doc;
	}
	
	public static String toCase(Document doc,String paht,String fileName) throws Exception{
		
		if(doc!=null) {
			ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
			iso.setPrettyFormat(true);
			iso.setUseAntiAliasing(true);
			iso.setJpegQuality(80);
			doc.save(paht+fileName,iso);
			return fileName;
		}else {
			return null;
		}
	}
	
	
	
	public static Document createTemplate(String template,Map<String, Object> data,Map<String,List<BaseParams>> bpsMap) throws Exception {
	
		InputStream wordTemplate = Sql2WordUtil.class.getClassLoader().getResourceAsStream(template);
		Document doc = new Document(wordTemplate);
		NodeCollection shapeCollection = doc.getChildNodes(NodeType.SHAPE, true);// 查询文档中所有wmf图片
		Node[] shapes = shapeCollection.toArray();// 序列化
		
		for(Node node:shapes) {
			Shape shape = (Shape) node;
			com.aspose.words.ImageData i = shape.getImageData();// 获得图片数据
			Object imgObj=data.get(shape.getAlternativeText()); 
			if(imgObj==null) {
				continue;
			}
			if(imgObj instanceof Proxy) {
				SerializableBlobProxy proxy = (SerializableBlobProxy) Proxy.getInvocationHandler(imgObj);
				Blob blob =proxy.getWrappedBlob();
				if (blob!=null) {// 如果shape类型是ole类型
					InputStream inStream = blob.getBinaryStream();
					i.setImage(inStream);
				}
			}
			
			if(imgObj instanceof InputStream) {
				InputStream inStream =(InputStream)imgObj;
				i.setImage(inStream);
			}
			
		}
		
		// 填充文字
		if (data != null) {
			
			String[] fieldNames =  doc.getMailMerge().getFieldNames();
			Object[] fieldValues = new Object[fieldNames.length];
			int i=0;
			for(String fieldName:fieldNames) {
				if(fieldName.indexOf("CK##")==0) {
					String[] temp = fieldName.split("##");
					String value=temp[1];
					String key =temp[2].toLowerCase();
					fieldValues[i]=(String)data.get(key);
					if(value.equals(fieldValues[i])) {
						fieldValues[i]="☑";
					}else {
						fieldValues[i]="□";
					}
				}else if(bpsMap!=null&&bpsMap.containsKey(fieldName.toLowerCase())){
					
					fieldValues[i] = translateParamVlaue(data.get(fieldName),bpsMap.get(fieldName.toLowerCase()));
					
				}else {
					fieldValues[i] = translateMapValue(data, fieldName.toLowerCase());
				}
				i++;
			}
			
			// 合并模版，相当于页面的渲染
			doc.getMailMerge().execute(fieldNames, fieldValues);
			
			
		}
		return doc;
		
	}
	
	
	private static Object translateParamVlaue(Object object, List<BaseParams> list) {
		
		if(object!=null) {
			for(BaseParams param : list) {
				if(param.getParamValue().equals(object.toString())) {
					return param.getParamName();
				}
			}
		}
		
		return object;
	}

	public static String sql2WordUtilCase(String template, String sql, JdbcTemplate jdbcTemplate,String fileName) throws Exception {
		Document doc = sql2WordUtil(template, sql, jdbcTemplate,null);
		
		if(doc!=null) {
			ImageSaveOptions iso = new ImageSaveOptions(SaveFormat.JPEG);
			iso.setPrettyFormat(true);
			iso.setUseAntiAliasing(true);
			iso.setJpegQuality(80);
			doc.save(getCacheDir()+fileName,iso);
			return fileName;
		}else {
			return null;
		}
		
		
	}
	
	public static String getCacheDir() {
		String path = Sql2WordUtil.class.getClassLoader().getResource("").getPath().toString();
		String temp = path.split("WEB-INF")[0];
		
		return temp+"images/cache/";
		
	}

	public static String getStringDate(Date date,int type){
		return type==0?new SimpleDateFormat("yyyy年MM月dd日").format(date):new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
	}
	
	public static String translateMapValue(Map<String,Object> map,String key){
		
		if(map.get(key) instanceof BigDecimal ){
			BigDecimal bg = (BigDecimal) map.get(key);
			return bg.toString();
		}
		if(map.get(key) instanceof Date ){
			if(key.indexOf("PSSJ")==0){
				return getStringDate((Date) map.get(key),1);
			}
			return getStringDate((Date) map.get(key),0);
		}
		if(map.get(key) instanceof Character){
			return ((Character)map.get(key)).toString();
		}
		if(map.get(key) instanceof Integer){
			return map.get(key).toString();
		}
		return (String) map.get(key);
	}
}
