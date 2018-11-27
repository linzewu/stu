package com.xs.jt.cms.manager.impl;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.cms.manager.IGongGaoImageManager;
import com.xs.jt.cms.zhpt.dao.GongGaoImageRepository;
import com.xs.jt.cms.zhpt.dao.PDAServiceRepository;
@Service
public class GongGaoImageManagerImpl implements IGongGaoImageManager {
	
	@Autowired
	private PDAServiceRepository pDAServiceRepository;
	
	@Autowired
	private GongGaoImageRepository gongGaoImageRepository;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;

	@Override
	public List<Map<String,String>> getGongGaoImagesByBh(String bh) {
		List<Map<String,String>> zpList=new ArrayList<Map<String,String>>();
		List<String> imageBH = pDAServiceRepository.getZPBHList(bh);
		byte[] b = null;
		Blob blob = null;
		if(imageBH!=null){
			List<Blob> list = gongGaoImageRepository.getGongGaoImageList(imageBH);
			for(int i=0;i<list.size();i++){
				blob =list.get(i);
				b=blobToBytes(blob);
				//String strImage = Base64Utils.encodeToString(b);
				//String strImage = encoder.encode(b);
				String strImage =createImage(bh+"_"+i,b);		
				Map<String,String> map = new HashMap<String,String>();
				map.put("url", strImage);
				zpList.add(map);
				
			}
		}
		
		return zpList;
	}
	
	private String createImage(String bh,byte[] b) {
		String imagePath = "\\ggimage\\" + bh + ".jpg";
		try {
			//判断是否存在目录，不存在就创建
			File pathFile = new File(cacheDir+"\\ggimage");
			if(!pathFile.exists()) {
				pathFile.mkdir();
			}
			//生成图片
			File imgFile = new File(cacheDir + imagePath);
			if (!imgFile.exists()) {

				ByteArrayInputStream bais = new ByteArrayInputStream(b);
				BufferedImage bi1 = ImageIO.read(bais);
				File w2 = new File(cacheDir + imagePath);// 可以是jpg,png格式

				ImageIO.write(bi1, "jpg", w2);

			}
		} catch (IOException e) {
			throw new ApplicationException("生成公告图片异常", e);
		}
		
		return imagePath;
	}
	
	private static byte[] blobToBytes(Blob blob) {
		BufferedInputStream is = null;
		byte[] bytes = null;
		try {
			is = new BufferedInputStream(blob.getBinaryStream());
			bytes = new byte[(int) blob.length()];
			int len = bytes.length;
			int offset = 0;
			int read = 0;
			while (offset < len
					&& (read = is.read(bytes, offset, len - offset)) >= 0) {
				offset += read;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bytes;
	}

}
