package com.xs.jt.base.module.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class ComputerInfoUtil {

	public static void main(String[] args) {

		Properties p = System.getProperties();// 获取当前的系统属性

		System.out.println("操作系统：" + p.getProperty("sun.desktop"));
		System.out.print("CPU个数:");// Runtime.getRuntime()获取当前运行时的实例
		System.out.println(Runtime.getRuntime().availableProcessors());// availableProcessors()获取当前电脑CPU数量

		System.out.println("操作系统的名称：" + p.getProperty("os.name"));
		System.out.println("操作系统的构架：" + p.getProperty("os.arch"));
		System.out.println("操作系统的版本：" + p.getProperty("os.version"));


	}

	private static String toHexString(int i) {

		String str = Integer.toHexString((int) (i & 0xff));
		if (str.length() == 1) {
			str = "0" + str;
		}
		return str;
	}

	public static List<String> getIpAndMac() {
		List<String> list =new ArrayList<String>();
		try {
			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
			while (en.hasMoreElements()) {
				NetworkInterface intf = (NetworkInterface) en.nextElement();
				Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses();
				while (enumIpAddr.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress()
							&& inetAddress.isSiteLocalAddress()) {
						byte[] macAddr = intf.getHardwareAddress();
						String mac="";
						for (byte b : macAddr) {
							mac+=toHexString(b) + "-";
						}
						list.add(inetAddress.getHostAddress().toString()+","+mac);
					}
				}
			}
		} catch (SocketException e) {
		}
		return list;
	}

	public static Map getComputerInfo(){
		
		Properties p = System.getProperties();
		Map computer=new HashMap();
		computer.put("czxt", p.getProperty("sun.desktop"));
		computer.put("cpugs",Runtime.getRuntime().availableProcessors());
		computer.put("czxtmc",p.getProperty("os.name"));
		computer.put("czxtjg",p.getProperty("os.arch"));
		computer.put("czxtbb",p.getProperty("os.version"));
		List<String> ips = getIpAndMac();
		String ipmac="";
		for(String ip:ips){
			ipmac +=ip+";";
		}
		computer.put("ipmac",ipmac);
		return computer;
	}
}