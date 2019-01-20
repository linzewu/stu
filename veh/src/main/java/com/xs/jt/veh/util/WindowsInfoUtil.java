package com.xs.jt.veh.util;

import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import com.sun.management.OperatingSystemMXBean;

public class WindowsInfoUtil {
	private static final int CPUTIME = 500;
	private static final int PERCENT = 100;
	private static final int FAULTLENGTH = 10;

	/*public static void main(String[] args) {
		 //System.out.println(getCpuRatioForWindows());
		//System.out.println(getMemery());
		System.out.println(getDisk());

		Properties p = System.getProperties();// 获取当前的系统属性
		p.list(System.out);// 将属性列表输出

		System.out.println("操作系统：" + p.getProperty("sun.desktop"));
		System.out.print("CPU个数:");// Runtime.getRuntime()获取当前运行时的实例
		System.out.println(Runtime.getRuntime().availableProcessors());// availableProcessors()获取当前电脑CPU数量
		System.out.print("虚拟机内存总量:");
		System.out.println(Runtime.getRuntime().totalMemory());// totalMemory()获取java虚拟机中的内存总量
		System.out.print("虚拟机空闲内存量:");
		System.out.println(Runtime.getRuntime().freeMemory());// freeMemory()获取java虚拟机中的空闲内存量
		System.out.print("虚拟机使用最大内存量:");
		System.out.println(Runtime.getRuntime().maxMemory());// maxMemory()获取java虚拟机试图使用的最大内存量

		try {
			System.out.println(getLocalInetMac());
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/

	// 获取内存使用率
	public static String getMemery() {
		OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		// 总的物理内存+虚拟内存
		long totalvirtualMemory = osmxb.getTotalSwapSpaceSize();
		// 剩余的物理内存
		long freePhysicalMemorySize = osmxb.getFreePhysicalMemorySize();
		Double compare = (Double) (1 - freePhysicalMemorySize * 1.0 / totalvirtualMemory) * 100;
		String str = "内存已使用:" + compare.intValue() + "%";
		return str;
	}

	// 获取文件系统使用率
	public static List<String> getDisk() {
		// 操作系统
		List<String> list = new ArrayList<String>();
		for (char c = 'A'; c <= 'Z'; c++) {
			String dirName = c + ":/";
			File win = new File(dirName);
			if (win.exists()) {
				long total = (long) win.getTotalSpace();
				long free = (long) win.getFreeSpace();
				Double compare = (Double) (1 - free * 1.0 / total) * 100;
				String str = c + ":盘  已使用 " + compare.intValue() + "%";
				list.add(str);
			}
		}
		return list;
	}

	// 获得cpu使用率
	public static String getCpuRatioForWindows() {
		try {
			/*
			 * String procCmd = System.getenv("windir") +
			 * "process get Caption,CommandLine,KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount"
			 * ;
			 */

			String procCmd = System.getenv("windir") + "\\system32\\wbem\\wmic.exe process get Caption,CommandLine,"
					+ "KernelModeTime,ReadOperationCount,ThreadCount,UserModeTime,WriteOperationCount";
			// 取进程信息
			long[] c0 = readCpu(Runtime.getRuntime().exec(procCmd));
			Thread.sleep(CPUTIME);
			long[] c1 = readCpu(Runtime.getRuntime().exec(procCmd));
			if (c0 != null && c1 != null) {
				long idletime = c1[0] - c0[0];
				long busytime = c1[1] - c0[1];
				return "CPU使用率:" + Double.valueOf(PERCENT * (busytime) * 1.0 / (busytime + idletime)).intValue() + "%";
			} else {
				return "CPU使用率:" + 0 + "%";
			}
		} catch (Exception ex) {
			// ex.printStackTrace();
			return "获取CUP使用率出错";
		}
	}

	// 读取cpu相关信息
	private static long[] readCpu(final Process proc) {
		long[] retn = new long[2];
		try {
			proc.getOutputStream().close();
			InputStreamReader ir = new InputStreamReader(proc.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line = input.readLine();
			if (line == null || line.length() < FAULTLENGTH) {
				return null;
			}
			int capidx = line.indexOf("Caption");
			int cmdidx = line.indexOf("CommandLine");
			int rocidx = line.indexOf("ReadOperationCount");
			int umtidx = line.indexOf("UserModeTime");
			int kmtidx = line.indexOf("KernelModeTime");
			int wocidx = line.indexOf("WriteOperationCount");
			long idletime = 0;
			long kneltime = 0;
			long usertime = 0;
			while ((line = input.readLine()) != null) {
				if (line.length() < wocidx) {
					continue;
				}
				// 字段出现顺序：Caption,CommandLine,KernelModeTime,ReadOperationCount,
				// ThreadCount,UserModeTime,WriteOperation
				String caption = substring(line, capidx, cmdidx - 1).trim();
				String cmd = substring(line, cmdidx, kmtidx - 1).trim();
				if (cmd.indexOf("wmic.exe") >= 0) {
					continue;
				}
				String s1 = substring(line, kmtidx, rocidx - 1).trim();
				String s2 = substring(line, umtidx, wocidx - 1).trim();
				if (caption.equals("System Idle Process") || caption.equals("System")) {
					if (s1.length() > 0)
						idletime += Long.valueOf(s1).longValue();
					if (s2.length() > 0)
						idletime += Long.valueOf(s2).longValue();
					continue;
				}
				if (s1.length() > 0)
					kneltime += Long.valueOf(s1).longValue();
				if (s2.length() > 0)
					usertime += Long.valueOf(s2).longValue();
			}
			retn[0] = idletime;
			retn[1] = kneltime + usertime;
			return retn;
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				proc.getInputStream().close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 由于String.subString对汉字处理存在问题（把一个汉字视为一个字节)，因此在 包含汉字的字符串时存在隐患，现调整如下：
	 * 
	 * @param src
	 *            要截取的字符串
	 * @param start_idx
	 *            开始坐标（包括该坐标)
	 * @param end_idx
	 *            截止坐标（包括该坐标）
	 * @return
	 */
	private static String substring(String src, int start_idx, int end_idx) {
		byte[] b = src.getBytes();
		String tgt = "";
		for (int i = start_idx; i <= end_idx; i++) {
			tgt += (char) b[i];
		}
		return tgt;
	}

	/**
	 * 获取本地IP列表（针对多网卡情况）
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static List<Map<String, Object>> getLocalInetMac() throws SocketException {

		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

		Map<String, Object> ipMacInfo = null;
		Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

		while (networkInterfaces.hasMoreElements()) {
			NetworkInterface networkInterface = networkInterfaces.nextElement();

			Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();

			while (inetAddresses.hasMoreElements()) {
				InetAddress inetAddress = inetAddresses.nextElement();
				ipMacInfo = pickInetAddress(inetAddress, networkInterface);
				if (ipMacInfo != null) {
					datas.add(ipMacInfo);
				}
			}
		}
		return datas;
	}

	private static Map<String, Object> pickInetAddress(InetAddress inetAddress, NetworkInterface ni) {
		try {
			String name = ni.getDisplayName();
			//JOptionPane.showMessageDialog(null, "显示名称："+name,"info", JOptionPane.INFORMATION_MESSAGE); 
			if (name.contains("Adapter") || name.contains("Virtual") || name.contains("VMnet")) {
				return null;
			}
			if (ni.isVirtual() || !ni.isUp() || !ni.supportsMulticast()) {
				return null;
			}
			

			if (!inetAddress.isLoopbackAddress()) {
				//JOptionPane.showMessageDialog(null, "真实IP地址","info", JOptionPane.INFORMATION_MESSAGE); 
				Formatter formatter = new Formatter();
				String sMAC = null;
				byte[] macBuf = ni.getHardwareAddress();
				for (int i = 0; i < macBuf.length; i++) {
					sMAC = formatter
							.format(Locale.getDefault(), "%02X%s", macBuf[i], (i < macBuf.length - 1) ? "-" : "")
							.toString();
				}
				formatter.close();
				Map<String, Object> ipMacInfo = new HashMap<String, Object>();
				ipMacInfo.put("ip", inetAddress.getHostAddress()); // ip地址
				ipMacInfo.put("ipnet", inetAddressTypeName(inetAddress)); // 网络类型
				ipMacInfo.put("mac", sMAC); // mac 地址
				ipMacInfo.put("network-arch", ni.getDisplayName()); // 网卡名称
				return ipMacInfo;
			}

		} catch (SocketException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String inetAddressTypeName(InetAddress inetAddress) {
		return (inetAddress instanceof Inet4Address) ? "ipv4" : "ipv6";
	}

}