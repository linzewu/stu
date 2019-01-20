package com.xs.jt.veh.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.dao.CheckQueueRepository;
import com.xs.jt.veh.dao.FlowRepository;
import com.xs.jt.veh.dao.VehCheckLoginRepository;
import com.xs.jt.veh.dao.VehCheckProcessRepository;
import com.xs.jt.veh.entity.CheckQueue;
import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.entity.VehCheckLogin;
import com.xs.jt.veh.entity.VehCheckProcess;
import com.xs.jt.veh.entity.WorkPoint;
import com.xs.jt.veh.manager.ICheckQueueManager;

@Service("checkQueueManager")
public class CheckQueueManagerImpl implements ICheckQueueManager {
	
	@Autowired
	private CheckQueueRepository checkQueueRepository;
	@Autowired
	private VehCheckLoginRepository vehCheckLoginRepository;
	@Autowired
	private VehCheckProcessRepository vehCheckProcessRepository;
	@Autowired
	private FlowRepository flowRepository;

	@Override
	public synchronized Integer getPdxh(String jcxdh, Integer gwsx) {
		
		Integer xh = checkQueueRepository.getPdxh(Integer.parseInt(jcxdh), gwsx);
		//没有排队则为1
		
		if(xh==null){
			return 1;
		}else{
			return ((Integer)xh)+1;
		}
	}

	@Override
	public Message upLine(String jylsh, Integer jycs) {
		Message message = new Message();
		List<VehCheckLogin> list = vehCheckLoginRepository.findByJylshAndJycs(jylsh, jycs);

		List<VehCheckProcess> process = vehCheckProcessRepository.getVehCheckProcesByJylshAndJycs(jylsh, jycs);
		

		if (list == null || list.isEmpty()) {
			message.setMessage("无法查询到流水号：" + jylsh + " 检验次数：" + jycs);
			message.setState(Message.STATE_ERROR);
			return message;
		} else if (process == null || list.isEmpty()) {
			message.setMessage("该流水号：" + jylsh + " 检验次数：" + jycs+" 没有存在检验项目");
			message.setState(Message.STATE_ERROR);
			return message;
		} else {
			VehCheckLogin vcl = list.get(0);
			
			List<Flow> flows = flowRepository.getFlowByJcxdh(Integer.parseInt(vcl.getJcxdh()));
			
			if(flows==null||flows.isEmpty()){
				message.setMessage("无法获取检测线代号："+vcl.getJcxdh()+"的检测流程");
				message.setState(Message.STATE_ERROR);
				return message;
			}
			
			Flow flow = flows.get(0);
			
			List<WorkPoint> wps = null;
			
			if(wps==null||wps.isEmpty()){
				message.setMessage("无法获取检测工位，请联系管理员");
				message.setState(Message.STATE_ERROR);
				return message;
			}
			
			//获取第一工位数据
			WorkPoint wp = wps.get(0);
			
			CheckQueue cq=new CheckQueue();
			
			cq.setGwsx(wp.getSort());
			
			cq.setJcxdh(Integer.parseInt(vcl.getJcxdh()));
			
			cq.setHphm(vcl.getHphm());
			
			cq.setJycs(jycs);
			
			cq.setJylsh(jylsh);
			
			cq.setPdxh(getPdxh(vcl.getJcxdh(), wp.getSort()));
			
			this.checkQueueRepository.save(cq);
			
			message.setMessage("上线排队成功，请等待");
			
			message.setState(Message.STATE_SUCCESS);
			 
			return message;
		}
	}

}
