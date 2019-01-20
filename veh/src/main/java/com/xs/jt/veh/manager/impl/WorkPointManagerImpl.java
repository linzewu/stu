package com.xs.jt.veh.manager.impl;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Message;
import com.xs.jt.veh.dao.WorkPointRepository;
import com.xs.jt.veh.entity.WorkPoint;
import com.xs.jt.veh.manager.IWorkPointManager;

@Service("workPointManager")
public class WorkPointManagerImpl implements IWorkPointManager {

	@Autowired
	private WorkPointRepository workPointRepository;

	@Autowired
	private ServletContext servletContext;

	@Override
	public List<WorkPoint> getWorkPointsByJcxdh(Integer jcxdh) {
		return workPointRepository.findWorkPointByJcxdh(jcxdh);
	}

	@Override
	public List<WorkPoint> getWorkPoints() {
		return workPointRepository.getWorkPoints();
	}

	@Override
	public WorkPoint saveWorkPoint(WorkPoint workPoint) {
		return workPointRepository.save(workPoint);
	}

	@Override
	public void deleteWorkPoint(WorkPoint workPoint) {
		workPointRepository.delete(workPoint);
	}

	@Override
	public Message startWorkpoint(Integer id) {
		WorkPoint workPoint = this.workPointRepository.findById(id).get();
		Message message = new Message();
		/**
		 * WorkPointThread workPointThread = (WorkPointThread)
		 * servletContext.getAttribute(workPoint.getThreadKey());
		 * 
		 * if (workPointThread == null) { WebApplicationContext wac =
		 * WebApplicationContextUtils.getWebApplicationContext(servletContext);
		 * workPointThread = wac.getBean(WorkPointThread.class);
		 * workPoint.setGwzt(WorkPoint.GWZT_QY);
		 * this.hibernateTemplate.update(workPoint); this.hibernateTemplate.flush();
		 * this.hibernateTemplate.clear(); workPointThread.setWorkPoint(workPoint);
		 * workPointThread.start();
		 * servletContext.setAttribute(workPoint.getThreadKey(), workPointThread);
		 * 
		 * message.setState(Message.STATE_SUCCESS); message.setMessage("启动成功");
		 * 
		 * logger.info("executor.getActiveCount() :" + executor.getActiveCount() +
		 * "启动成功");
		 * 
		 * logger.info("getMaxPoolSize:" + executor.getMaxPoolSize() + "启动成功");
		 * 
		 * logger.info("getCorePoolSize:" + executor.getCorePoolSize() + "启动成功");
		 * 
		 * logger.info("getKeepAliveSeconds:" + executor.getKeepAliveSeconds() +
		 * "启动成功");
		 * 
		 * } else { message.setMessage("该工位已启动"); }
		 **/
		return message;
	}

	@Override
	public Message stopWorkpoint(Integer id) throws InterruptedException {
		WorkPoint workPoint = this.workPointRepository.findById(id).get();
		Message message = new Message();
		/**
		 * WorkPointThread workPointThread = (WorkPointThread)
		 * this.servletContext.getAttribute(workPoint.getThreadKey()); if
		 * (workPointThread == null) { message.setState(Message.STATE_SUCCESS);
		 * message.setMessage("工位已停止"); return message; } workPointThread.interrupt();
		 * for (int i = 0; i < 30; i++) { Thread.sleep(100); if
		 * (!workPointThread.isActive()) { workPoint.setGwzt(WorkPoint.GWZT_TY);
		 * this.hibernateTemplate.update(workPoint);
		 * this.servletContext.removeAttribute(workPoint.getThreadKey());
		 * message.setState(Message.STATE_SUCCESS); message.setMessage("工位停止成功"); return
		 * message; } }
		 **/
		message.setMessage("工位停止失败！");
		return message;
	}

	@Override
	public Message reStartWorkpoint(Integer id) throws InterruptedException {
		WorkPoint workPoint = this.workPointRepository.findById(id).get();
		Message message = new Message();
		/**
		 * WorkPointThread workPointThread = (WorkPointThread)
		 * this.servletContext.getAttribute(workPoint.getThreadKey()); if
		 * (workPointThread != null) { workPointThread.interrupt(); for (int i = 0; i <
		 * 30; i++) { Thread.sleep(100); if (!workPointThread.isActive()) { //
		 * workPointThread.start();
		 * servletContext.removeAttribute(workPoint.getThreadKey()); startWorkpoint(id);
		 * message.setState(Message.STATE_SUCCESS); message.setMessage("工位重启成功"); return
		 * message; } } } else { return startWorkpoint(id); }
		 **/
		message.setMessage("工位停止失败！");
		return message;
	}

}
