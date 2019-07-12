package com.xs.jt.srms.manager;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import com.xs.jt.srms.vehimg.entity.VehImg;

public interface IVehImgManager {
	
	public List<VehImg> getVehImgByLsh(String lsh);
	
	public void updateVehImgZpztOfzplx(String lsh,String zplx);
	
	public void saveVehImg(VehImg vehImg);
	
	public VehImg getImgById(Integer id);
	
	public Map<String, Object> saveImg(VehImg vehimg,String base64Img);
	
	public List<VehImg> getVehImgByLshOfzc(String lsh);
	
	public List getYwListByClsbdh(String clsbdh);
	
	public String buildImgById(Integer id);
	
	public void uploadVehImg(VehImg vehImg) throws Exception;
	
	public List<Map<String,Object>> getVehImgMapByLsh(String lsh);
	
	public void deleteVehImgByLsh(String lsh);
	
	public void deleteVehImgById(Integer id);

}
