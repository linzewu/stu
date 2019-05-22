package com.xs.jt.hwvideo.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.xs.jt.hwvideo.dao.VehInfoRepository;
import com.xs.jt.hwvideo.entity.VehInfo;


@Service
public class VehInfoManager {
	
	@Autowired
	private VehInfoRepository vehInfoRepository;
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private WeighManager weighManager;
	
	@Value("${hk.picPath}")
	private String picPath;
	
	public VehInfo getVehInfoOfcc(String hphm) {
		return vehInfoRepository.getVehInfoOfcc(hphm);
	} 
	
	
	public VehInfo getVehInfoByHPHM(String hphm) {
		em.clear();
		Query query = em.createQuery("from VehInfo where hphm=:hphm and status!=3", VehInfo.class).setParameter("hphm", hphm);
		VehInfo vehInfo =(VehInfo)query.getSingleResult();
		return vehInfo;
		
	} 
	
	public List<VehInfo> getOutVehInfos() {
		return vehInfoRepository.getOutVehInfos();
	}
	
	public List<VehInfo> getCompleteData(VehInfo vehInfo){
		em.clear();
		String sql ="from VehInfo where status=3";
		Map<String,Object> param=new HashMap<String,Object>();
		if(vehInfo!=null) {
			if(!StringUtils.isEmpty(vehInfo.getHphm())) {
				sql+=" and hphm=:hphm";
				param.put("hphm", vehInfo.getHphm());
			}
		}
		Query query = em.createQuery(sql,VehInfo.class);
		
		for(String key: param.keySet()) {
			query.setParameter(key, param.get(key));
		}
		List<VehInfo> datas =(List<VehInfo>)query.getResultList();
		return datas;
	 }
	
	
	public void save(VehInfo vehInfo) {
		vehInfoRepository.save(vehInfo);
	}
	
	@Scheduled(cron = "0/10 * * * * ? ")
	public void downLoadInVideo() throws Exception {
		em.clear();
		String sql ="from VehInfo where status=3 and inVideoStatus=0 and jcjssj<DATEADD(n,2,GETDATE())";
		Query query = em.createQuery(sql,VehInfo.class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<VehInfo> datas =(List<VehInfo>)query.getResultList();
		if(!CollectionUtils.isEmpty(datas)) {
			VehInfo data =datas.get(0);
			weighManager.dowloandVideo(data.getInRecordId(), data.getJckssj(), data.getJcjssj());
			data.setInVideoStatus(1);
			vehInfoRepository.save(data);
			weighManager.uploadFile(picPath, data.getInRecordId());
			weighManager.uploadPhoto(picPath, data.getInRecordId());
		}
		
	}
	
	@Scheduled(cron = "0/10 * * * * ? ")
	public void downLoadOutVideo() throws Exception {
		em.clear();
		String sql ="from VehInfo where status=3 and outVideoStatus=0 and ccjssj<DATEADD(n,2,GETDATE())";
		Query query = em.createQuery(sql,VehInfo.class);
		query.setFirstResult(0);
		query.setMaxResults(1);
		List<VehInfo> datas =(List<VehInfo>)query.getResultList();
		if(!CollectionUtils.isEmpty(datas)) {
			VehInfo data =datas.get(0);
			weighManager.dowloandVideo(data.getOutRecordId(), data.getCckssj(), data.getCcjssj());
			data.setOutVideoStatus(1);
			vehInfoRepository.save(data);
			weighManager.uploadFile(picPath, data.getOutRecordId());
			weighManager.uploadPhoto(picPath, data.getOutRecordId());
		}
	}
	
	


}
