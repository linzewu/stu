package com.xs.jt.srms.manager.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import com.xs.jt.base.module.common.ApplicationException;
import com.xs.jt.base.module.common.Constant;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.srms.manager.IVehImgManager;
import com.xs.jt.srms.vehimg.dao.VehImgRepository;
import com.xs.jt.srms.vehimg.entity.VehImg;
@Service
public class VehImgManagerImpl implements IVehImgManager {
	
	private static Logger logger = LoggerFactory.getLogger(VehImgManagerImpl.class);
	@Autowired
	private VehImgRepository vehImgRepository;
	
	@Value("${stu.cache.dir}")
	private String cacheDir;

	@Override
	public List<VehImg> getVehImgByLsh(String lsh) {
		List<VehImg> list = vehImgRepository.getVehImgByLsh(lsh);
		for (VehImg vehImg:list) {
			
			String imagePath = "\\vehimg\\" + vehImg.getId() + ".jpg";
			try {
				//判断是否存在目录，不存在就创建
				File pathFile = new File(cacheDir+"\\vehimg");
				if(!pathFile.exists()) {
					pathFile.mkdir();
				}
				//生成图片
				File imgFile = new File(cacheDir + imagePath);
				if (!imgFile.exists()) {
	
					ByteArrayInputStream bais = new ByteArrayInputStream(vehImg.getZp());
					BufferedImage bi1 = ImageIO.read(bais);
					File w2 = new File(cacheDir + imagePath);// 可以是jpg,png格式
	
					ImageIO.write(bi1, "jpg", w2);
	
				}
			} catch (IOException e) {
				throw new ApplicationException("生成图片异常", e);
			}
		}
		return list;
	}

	@Override
	public void updateVehImgZpztOfzplx(String lsh, String zplx) {
		List<VehImg> imgs = this.vehImgRepository.getVehImgByLshAndZplx(lsh, zplx);
		for (VehImg img : imgs) {
			img.setZpzt("1");
			this.vehImgRepository.save(img);
		}
		
	}

	@Override
	public void saveVehImg(VehImg vehImg) {
		this.vehImgRepository.save(vehImg);
		
	}

	@Override
	public VehImg getImgById(Integer id) {
		Optional<VehImg> opt = this.vehImgRepository.findById(id);
		
		if ((!opt.isPresent())) {
			return null;
		}
		return opt.get();
	}

	@Override
	public Map<String, Object> saveImg(VehImg vehimg, String base64Img) {
		if(base64Img==null||base64Img.equals("")){
			return ResultHandler.toErrorJSON("图片不能为空");
		}else{
			this.updateVehImgZpztOfzplx(vehimg.getLsh(), vehimg.getZplx());
			byte[] zp = Base64Utils.decodeFromString(base64Img.replace("\r\n", ""));
			vehimg.setZp(zp);
			vehimg.setPssj(new Date());
			vehimg.setZpzt("0");
			this.saveVehImg(vehimg);
			return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "保存成功", vehimg);
		}
	}

	@Override
	public List<VehImg> getVehImgByLshOfzc(String lsh) {
		return vehImgRepository.getVehImgByLshOfzc(lsh);
	}

	@Override
	public List getYwListByClsbdh(String clsbdh) {
		return vehImgRepository.getYwListByClsbdh(clsbdh);
	}

	@Override
	public String buildImgById(Integer id) {
		VehImg photo = this.vehImgRepository.findById(id).get();
		String imagePath = "\\vehimg\\" + id + ".jpg";
		try {
			//判断是否存在目录，不存在就创建
			File pathFile = new File(cacheDir+"\\vehimg");
			if(!pathFile.exists()) {
				pathFile.mkdir();
			}
			//生成图片
			File imgFile = new File(cacheDir + imagePath);
			if (!imgFile.exists()) {

				ByteArrayInputStream bais = new ByteArrayInputStream(photo.getZp());
				BufferedImage bi1 = ImageIO.read(bais);
				File w2 = new File(cacheDir + imagePath);// 可以是jpg,png格式
	
				ImageIO.write(bi1, "jpg", w2);

			}
		} catch (IOException e) {
			throw new ApplicationException("生成图片异常", e);
		}
		return imagePath;
	}

	@Override
	public void uploadVehImg(VehImg vehImg) throws Exception {
		try {
			this.updateVehImgZpztOfzplx(vehImg.getLsh(), vehImg.getZplx());
			
			vehImg.setZp(vehImg.getRemoteFile().getBytes());
			//vehImg.setZp(image2byte("C:\\Users\\lin\\Desktop\\image\\7.jpg"));
			vehImg.setPssj(new Date());
			vehImg.setZpzt("0");
			this.saveVehImg(vehImg);
		}catch(Exception e) {
			logger.error("上传图片异常",e);
			throw new  ApplicationException("上传图片异常",e);
		}
	//	return ResultHandler.toMyJSON(Constant.ConstantState.STATE_SUCCESS, "保存成功", vehImg);
		
	}

	@Override
	public List<Map<String, Object>> getVehImgMapByLsh(String lsh) {
		return this.vehImgRepository.getVehImgMapByLsh(lsh);
	}

	
	@Override
	public void deleteVehImgByLsh(String lsh) {
		vehImgRepository.deleteVehImgByLsh(lsh);		
	}
	
	@Override
	public void deleteVehImgById(Integer id) {
		vehImgRepository.deleteById(id);		
	}
	
//	public byte[] image2byte(String path){
//	     byte[] data = null;
//	      FileImageInputStream input = null;
//	      try {
//	        input = new FileImageInputStream(new File(path));
//	        ByteArrayOutputStream output = new ByteArrayOutputStream();
//	        byte[] buf = new byte[1024];
//	        int numBytesRead = 0;
//	       while ((numBytesRead = input.read(buf)) != -1) {
//	       output.write(buf, 0, numBytesRead);
//	       }
//	       data = output.toByteArray();
//	       output.close();
//	       input.close();
//	     }
//	     catch (FileNotFoundException ex1) {
//	       ex1.printStackTrace();
//	     }
//	     catch (IOException ex1) {
//	       ex1.printStackTrace();
//	     }
//	     return data;
//	   }

	

}
