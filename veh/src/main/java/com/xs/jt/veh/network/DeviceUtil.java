package com.xs.jt.veh.network;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class DeviceUtil {

	public static String send(String ip, Integer port, String param) {
		
		String context =null;

		try {
			Socket socket = new Socket(ip, port);
			socket.setSoTimeout(5539900);
			OutputStream out = socket.getOutputStream();

			byte[] date = param.getBytes();
			
			out.flush();
			
			byte[] buffer = new byte[1024];
			int len = -1;
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			InputStream in = socket.getInputStream();
			while ((len = in.read(buffer, 0, buffer.length)) > 0) {
				bout.write(buffer, 0, len);
			}
			in.close();
			bout.flush();
			bout.close();

			byte[] rdata = bout.toByteArray();
			context =new String(rdata);
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return context;
	}

}
