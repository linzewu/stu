package com.xs.jt.veh.network;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.VehCheckLogin;


public class TakePicture implements Runnable {
	
	private static Log logger = LogFactory.getLog(TakePicture.class);
	
	private Integer yc;
	
	public Integer getYc() {
		return yc;
	}
	public void setYc(Integer yc) {
		this.yc = yc;
	}
	public static void createNew(VehCheckLogin vehCheckLogin, String jyxm) {
		TakePicture tp = new TakePicture(vehCheckLogin.getJylsh(), vehCheckLogin.getJcxdh(), vehCheckLogin.getJycs(),
				vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), jyxm, 0);
		tp.setYc(0);
		Thread t=new Thread(tp);
		t.start();
	}
	public static void createNew(VehCheckLogin vehCheckLogin, String jyxm,Integer yc) {
		TakePicture tp = new TakePicture(vehCheckLogin.getJylsh(), vehCheckLogin.getJcxdh(), vehCheckLogin.getJycs(),
				vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), jyxm, 0);
		tp.setYc(yc);
		Thread t=new Thread(tp);
		t.start();
		
	}
	
	public static void createNew(VehCheckLogin vehCheckLogin, String jyxm,Integer yc,String zpzl) {
		TakePicture tp = new TakePicture(vehCheckLogin.getJylsh(), vehCheckLogin.getJcxdh(), vehCheckLogin.getJycs(),
				vehCheckLogin.getHphm(), vehCheckLogin.getHpzl(), vehCheckLogin.getClsbdh(), jyxm, 0,zpzl);
		tp.setYc(yc);
		Thread t=new Thread(tp);
		t.start();
	}


	private String ml;

	private TakePicture(String jylsh, String jcxdh, Integer jycs, String hphm, String hpzl, String clsbdh, String jyxm,
			Integer jyzt) {
		ml = jylsh + "," + jcxdh + "," + jycs + "," + hphm + ","+hpzl+"," + clsbdh + "," + jyxm + "," + jyzt + ",by1,by2";
		logger.info("拍照命令="+ml);
	}
	
	private TakePicture(String jylsh, String jcxdh, Integer jycs, String hphm, String hpzl, String clsbdh, String jyxm,
			Integer jyzt,String zpzl) {
		ml = jylsh + "," + jcxdh + "," + jycs + "," + hphm + ","+hpzl+"," + clsbdh + "," + jyxm + "," + jyzt + ","+zpzl+",by2";
		logger.info("拍照命令="+ml);
	}

	@Override
	public void run() {
		Socket client = null;
		PrintStream out = null;
		try {
			Thread.sleep(yc);
			client = new Socket("127.0.0.1", 8000);
			client.setSoTimeout(500);
			out = new PrintStream(client.getOutputStream());
			System.out.println("发送拍照命令："+ml);
			out.print(ml);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
