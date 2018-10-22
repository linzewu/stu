package com.xs.jt.cms.manager.impl;

import java.io.BufferedInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cms.manager.IGongGaoImageManager;
import com.xs.jt.cms.zhpt.dao.GongGaoImageRepository;
import com.xs.jt.cms.zhpt.dao.PDAServiceRepository;
@Service
public class GongGaoImageManagerImpl implements IGongGaoImageManager {
	
	@Autowired
	private PDAServiceRepository pDAServiceRepository;
	
	@Autowired
	private GongGaoImageRepository gongGaoImageRepository;

	@Override
	public List getGongGaoImagesByBh(String bh) {
		List zpList=new ArrayList();
		List<String> imageBH = pDAServiceRepository.getZPBHList(bh);
		byte[] b = null;
		Blob blob = null;
		//BASE64Encoder encoder=new BASE64Encoder();
		if(imageBH!=null){
			List list = gongGaoImageRepository.getGongGaoImageList(imageBH);
			for(int i=0;i<list.size();i++){
				blob =(Blob)list.get(0);
				b=blobToBytes(blob);
				//String strImage = encoder.encode(b);
				//zpList.add(strImage);
				
			}
		}
		
		return zpList;
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
