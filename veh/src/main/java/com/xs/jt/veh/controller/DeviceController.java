package com.xs.jt.veh.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TooManyListenersException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.xs.jt.base.module.annotation.Modular;
import com.xs.jt.base.module.annotation.UserOperation;
import com.xs.jt.base.module.common.ResultHandler;
import com.xs.jt.base.module.common.SystemException;
import com.xs.jt.base.module.util.PageInfo;
import com.xs.jt.veh.entity.Device;
import com.xs.jt.veh.entity.VehFlow;
import com.xs.jt.veh.manager.IDeviceManager;
import com.xs.jt.veh.network.DeviceBrakRoller;
import com.xs.jt.veh.network.DeviceDisplay;
import com.xs.jt.veh.network.DeviceLight;
import com.xs.jt.veh.network.DeviceSideslip;
import com.xs.jt.veh.network.DeviceSignal;
import com.xs.jt.veh.network.DeviceSpeed;
import com.xs.jt.veh.network.DeviceWeigh;
import com.xs.jt.veh.network.SimpleRead;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;

@Controller
@RequestMapping(value = "/device")
@SuppressWarnings("rawtypes")
@Modular(modelCode = "Device", modelName = "设备管理", isEmpowered = false)
public class DeviceController {

	@Resource(name = "deviceManager")
	private IDeviceManager deviceManager;

	@Resource(name = "taskExecutor")
	private ThreadPoolTaskExecutor executor;

	@Autowired
	private ServletContext servletContext;

	@UserOperation(code = "getDevices", name = "查询设备")
	@RequestMapping(value = "getDevices", method = RequestMethod.POST)
	public @ResponseBody Map getDevices(PageInfo pageInfo) {
		Map json = ResultHandler.toMyJSON(deviceManager.getDevices(), 0);
		return json;
	}

	@UserOperation(code = "getDevices", name = "查询设备")
	@RequestMapping(value = "getDeviceByJcxxh", method = RequestMethod.POST)
	public @ResponseBody Map getDeviceByJcxxh(@RequestParam Integer jcxdh) {
		Map json = ResultHandler.toMyJSON(deviceManager.getDevices(jcxdh), 0);
		return json;
	}

	@UserOperation(code = "getDevices", name = "查询设备")
	@RequestMapping(value = "getDeviceDisplay", method = RequestMethod.POST)
	public @ResponseBody Map getDeviceDisplay(@RequestParam Integer jcxxh) {
		Map json = ResultHandler.toMyJSON(deviceManager.getDevicesDisplay(jcxxh), 0);
		return json;
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "createLinkDevice", method = RequestMethod.POST)
	public @ResponseBody Map createLinkDevice() throws SystemException {
		this.deviceManager.createLinkDevice();
		return ResultHandler.toSuccessJSON("保存成功！");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Map saveDevice(Device device, BindingResult result) {
		Device d = this.deviceManager.saveDevice(device);
		return ResultHandler.resultHandle(result, d, "保存成功");

	}

	@UserOperation(code = "deleteDevice", name = "删除设备")
	@RequestMapping(value = "delete", method = RequestMethod.POST)
	public @ResponseBody void delete(Device device) {
		this.deviceManager.deleteDevice(device);
	}

	/**
	 * 获取串口列表
	 * 
	 * @return
	 */
	@UserOperation(code = "getDevices", name = "查询设备")
	@RequestMapping(value = "getComList", method = RequestMethod.POST)
	public @ResponseBody List getComList() {
		System.out.println("开始获取COM口");
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		System.out.println("已获取COM口");
		List<Map> datas = new ArrayList<Map>();
		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", portId.getName());
			data.put("text", portId.getName());
			datas.add(data);
		}
		System.out.println("成功返回");
		return datas;
	}

	/**
	 * 启动信号 开关
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws NoSuchPortException
	 * @throws TooManyListenersException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceSignalStart", method = RequestMethod.POST)
	public @ResponseBody Map deviceSignalStart(Integer id) throws NoSuchPortException, TooManyListenersException,
			PortInUseException, UnsupportedCommOperationException, IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {

		Device device = this.deviceManager.getDevice(id);
		DeviceSignal deviceSignal = (DeviceSignal) this.getSimpleRead(id);
		if (deviceSignal == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			deviceSignal = (DeviceSignal) context.getBean("deviceSignal");
			deviceSignal.setDevice(device);
			servletContext.setAttribute(device.getThredKey(), deviceSignal);
		}
		if (!deviceSignal.isOpen()) {
			deviceSignal.open();
		}
		if (!deviceSignal.isRun()) {
			this.executor.execute(deviceSignal);
			deviceSignal.setRun(true);
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	/**
	 * 关闭设备
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceStop", method = RequestMethod.POST)
	public @ResponseBody Map deviceStop(Integer id) throws IOException, InterruptedException {

		SimpleRead simpleread = (SimpleRead) this.getSimpleRead(id);

		if (simpleread == null) {
			return ResultHandler.toSuccessJSON("设备未启动");
		}
		if (simpleread.isOpen()) {
			simpleread.close();
		}
		return ResultHandler.toSuccessJSON("设备已停止");
	}

	/**
	 * 停止所有设备
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "stopAllDevice", method = RequestMethod.POST)
	public @ResponseBody Map stopAllDevice() throws IOException, InterruptedException {
		List<Device> devices = this.deviceManager.getDevices();
		for (Device device : devices) {
			SimpleRead sr = (SimpleRead) servletContext.getAttribute(device.getThredKey());
			if (sr != null && sr.isOpen()) {
				sr.close();
			}
		}

		return ResultHandler.toSuccessJSON("所有设备停止成功");
	}

	/**
	 * 获取串口状态
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@UserOperation(code = "getDevices", name = "查询设备")
	@RequestMapping(value = "getState", method = RequestMethod.POST)
	public @ResponseBody Map getDeeviceState(Integer id) {
		SimpleRead sr = this.getSimpleRead(id);
		Map<String, Object> data = new HashMap<String, Object>();
		if (sr == null) {
			data.put("isOpen", false);
			data.put("isRun", false);
		} else {
			data.put("isOpen", sr.isOpen());
			data.put("isRun", sr.isRun());
		}
		return data;
	}

	/**
	 * 获取报文 测试用
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@UserOperation(code = "getDevices", name = "查询设备")
	@RequestMapping(value = "getRtx", method = RequestMethod.POST)
	public @ResponseBody String getRtx(Integer id) {
		DeviceSignal sr = (DeviceSignal) this.getSimpleRead(id);
		if (sr == null) {
			return "";
		} else {
			return sr.getRtx();
		}
	}

	/**
	 * 显示屏发送信息
	 * 
	 * @param request
	 * @param id
	 * @param ph
	 * @param message
	 * @return
	 * @throws IOException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "sendMessageDisplay", method = RequestMethod.POST)
	public @ResponseBody Map sendMessageDisplay(Integer id, Integer ph, String message) throws IOException {
		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();

		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}
		DeviceDisplay sr = (DeviceDisplay) this.getSimpleRead(id);

		if (sr == null || !sr.isOpen()) {
			return ResultHandler.toSuccessJSON("设备端口未打开");
		}
		sr.sendMessage(message, ph);
		return ResultHandler.toSuccessJSON("发送成功");
	}

	/**
	 * 启动显示屏
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws NoSuchPortException
	 * @throws TooManyListenersException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceDisplayStart", method = RequestMethod.POST)
	public @ResponseBody Map deviceDisplayStart(Integer id)
			throws IOException, NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();

		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}

		SimpleRead sr = this.getSimpleRead(id);
		if (sr == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DeviceDisplay dd = (DeviceDisplay) context.getBean("deviceDisplay");
			dd.setDevice(device);
			dd.open();
			servletContext.setAttribute(device.getThredKey(), dd);
		} else {
			if (!sr.isOpen()) {
				sr.open();
			}
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceLightStart", method = RequestMethod.POST)
	public @ResponseBody Map deviceLightStart(Integer id)
			throws IOException, NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();

		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}
		SimpleRead sr = this.getSimpleRead(id);
		if (sr == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DeviceLight dl = (DeviceLight) context.getBean("deviceLight");
			dl.setDevice(device);
			dl.open();
			servletContext.setAttribute(device.getThredKey(), dl);
		} else {
			if (!sr.isOpen()) {
				sr.open();
			}
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceLightSetting", method = RequestMethod.POST)
	public @ResponseBody Map deviceLightSetting(Integer id) throws Exception {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toMyJSON(300, "设备信息不完整！");
		}

		DeviceLight deviceLight = (DeviceLight) this.getSimpleRead(id);
		if (deviceLight == null) {
			return ResultHandler.toMyJSON(300, "灯光设备串口未启动");
		} else {
			deviceLight.sysSetting();
			return ResultHandler.toMyJSON(200, "设置成功");
		}
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceLightTest", method = RequestMethod.POST)
	public @ResponseBody Map deviceLightTest(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException,
			InterruptedException, SystemException, InvocationTargetException, NoSuchMethodException {
		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}

		final DeviceLight deviceLight = (DeviceLight) this.getSimpleRead(id);

		if (deviceLight == null) {
			return ResultHandler.toMyJSON(300, "设备未启动");
		}

		/*
		 * final Map<String, String> setting = deviceLight.createSettingData(); final
		 * String clzd = (String) deviceLight.getQtxxObject().get("t-czd"); final String
		 * clyd = (String) deviceLight.getQtxxObject().get("t-cyd");
		 */

		/*
		 * // 执行线程 executor.execute(new Runnable() { public void run() { try {
		 * deviceLight.startCheck(setting, clzd, clyd); } catch (IOException |
		 * InterruptedException e) { e.printStackTrace(); } } });
		 */

		return ResultHandler.toSuccessJSON("灯光测试命令发送成功");
	}

	/**
	 * 启动制动设备
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws NoSuchPortException
	 * @throws TooManyListenersException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "brakRollerStart", method = RequestMethod.POST)
	public @ResponseBody Map brakRollerStart(Integer id)
			throws IOException, NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}
		SimpleRead sr = this.getSimpleRead(id);
		if (sr == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DeviceBrakRoller dl = (DeviceBrakRoller) context.getBean("deviceBrakRoller");
			dl.setDevice(device);
			dl.open();
			servletContext.setAttribute(device.getThredKey(), dl);
		} else {
			if (!sr.isOpen()) {
				sr.open();
			}
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	/**
	 * 制动设备测试
	 * 
	 * @param id
	 * @return
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "brakRollerTest", method = RequestMethod.POST)
	public @ResponseBody Map brakRollerTest(Integer id) {
		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}

		final DeviceBrakRoller deviceBrakRoller = (DeviceBrakRoller) this.getSimpleRead(id);

		// 执行线程
		executor.execute(new Runnable() {
			public void run() {
				try {
					VehFlow vheFlow = new VehFlow();
					deviceBrakRoller.startCheck(null, vheFlow, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return ResultHandler.toSuccessJSON("制动设备测试命令已经发送");
	}

	/**
	 * 速度设备启动
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws NoSuchPortException
	 * @throws TooManyListenersException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "speedStart", method = RequestMethod.POST)
	public @ResponseBody Map speedStart(Integer id)
			throws IOException, NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}
		SimpleRead sr = this.getSimpleRead(id);
		if (sr == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DeviceSpeed dl = (DeviceSpeed) context.getBean("deviceSpeed");
			dl.setDevice(device);
			dl.open();
			servletContext.setAttribute(device.getThredKey(), dl);
		} else {
			if (!sr.isOpen()) {
				sr.open();
			}
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "deviceSpeedTest", method = RequestMethod.POST)
	public @ResponseBody Map deviceSpeedTest(Integer id) {
		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}

		final DeviceSpeed deviceSpeed = (DeviceSpeed) this.getSimpleRead(id);

		// 执行线程
		executor.execute(new Runnable() {
			public void run() {
				try {
					// deviceSpeed.startCheck(new List(), null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return ResultHandler.toSuccessJSON("速度测试命令已经发送");
	}

	/**
	 * 侧滑设备
	 * 
	 * @param id
	 * @return
	 * @throws IOException
	 * @throws NoSuchPortException
	 * @throws TooManyListenersException
	 * @throws PortInUseException
	 * @throws UnsupportedCommOperationException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 */
	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "sideslipStart", method = RequestMethod.POST)
	public @ResponseBody Map sideslipStart(Integer id)
			throws IOException, NoSuchPortException, TooManyListenersException, PortInUseException,
			UnsupportedCommOperationException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}
		SimpleRead sr = this.getSimpleRead(id);
		if (sr == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DeviceSideslip dl = (DeviceSideslip) context.getBean("deviceSideslip");
			dl.setDevice(device);
			dl.open();
			servletContext.setAttribute(device.getThredKey(), dl);
		} else {
			if (!sr.isOpen()) {
				sr.open();
			}
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "sideslipTest", method = RequestMethod.POST)
	public @ResponseBody Map sideslipTest(Integer id) {
		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}

		final DeviceSideslip deviceSideslip = (DeviceSideslip) this.getSimpleRead(id);

		// 执行线程
		executor.execute(new Runnable() {
			public void run() {
				try {

					VehFlow vehFlow = new VehFlow();
					deviceSideslip.startCheck(null, vehFlow, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return ResultHandler.toSuccessJSON("侧滑命令已经发送");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "weighStart", method = RequestMethod.POST)
	public @ResponseBody Map weighStart(Integer id)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchPortException,
			PortInUseException, IOException, UnsupportedCommOperationException, TooManyListenersException {

		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}
		SimpleRead sr = this.getSimpleRead(id);
		if (sr == null) {
			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			DeviceWeigh dl = (DeviceWeigh) context.getBean("deviceWeigh");
			dl.setDevice(device);
			dl.open();
			servletContext.setAttribute(device.getThredKey(), dl);
		} else {
			if (!sr.isOpen()) {
				sr.open();
			}
		}
		return ResultHandler.toSuccessJSON("启动成功");
	}

	@UserOperation(code = "saveDevice", name = "新增修改设备")
	@RequestMapping(value = "weighTest", method = RequestMethod.POST)
	public @ResponseBody Map weighTest(Integer id) {
		Device device = this.deviceManager.getDevice(id);
		String qtxx = device.getQtxx();
		if (qtxx == null || qtxx.trim().equals("")) {
			return ResultHandler.toSuccessJSON("设备信息不完整！");
		}

		final DeviceWeigh deviceWeigh = (DeviceWeigh) this.getSimpleRead(id);

		// 执行线程
		executor.execute(new Runnable() {
			public void run() {
				try {
					VehFlow vehflow = new VehFlow();
					deviceWeigh.startCheck(null, vehflow, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		return ResultHandler.toSuccessJSON("称重测试命令已经发送");
	}

	private SimpleRead getSimpleRead(Integer id) {
		Device device = this.deviceManager.getDevice(id);
		SimpleRead sr = (SimpleRead) servletContext.getAttribute(device.getThredKey());
		return sr;
	}

}
