package com.xs.jt.veh.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;

public class IamgeBase64Cash {
	
	public static final Integer DEFAULT_BUFFER_SIZE=1024;
	
	private static IamgeBase64Cash iamgeBase64Cash;
	
	private IamgeBase64Cash(){}
	
	private static String path;
	
	public static IamgeBase64Cash getInstance(){
		
		if(iamgeBase64Cash==null){
			iamgeBase64Cash=new IamgeBase64Cash();
			path=iamgeBase64Cash.getClass().getClassLoader().getResource("/").getFile();
			path=path.split("WEB-INF")[0]+"cache/";
		}
		
		return iamgeBase64Cash;
	}
	
	public  void cashIamge(byte[] res, String id) throws IOException {
		
		FileOutputStream fos=null;
		
		try {
			
			File distFile = new File(path+id+".jpg");
			
			if (!distFile.getParentFile().exists()){
				distFile.getParentFile().mkdirs();
			}
			
			if(distFile.exists()){
				distFile.delete();
			}
			fos = new FileOutputStream(distFile);
			fos.write(res);
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}



	public  void cashBase64Iamge(String res, String id) throws IOException {
		
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			
			File distFile = new File(path+id);
			
			if (!distFile.getParentFile().exists()){
				distFile.getParentFile().mkdirs();
			}
			
			if(distFile.exists()){
				distFile.delete();
			}
				
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));

			char buf[] = new char[1024]; // 字符缓冲区

			int len;

			while ((len = bufferedReader.read(buf)) != -1) {

				bufferedWriter.write(buf, 0, len);

			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					throw e;
				}
			}
		}
	}

	public String getCashBase64Iamge(String id) throws Exception {

		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		File file=new File(path+id);
		if(!file.exists()){
			return null;
		}
		try {
			reader = new InputStreamReader(new FileInputStream(path+id));
			// 将输入流写入输出流
			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					throw e;
				}
		}

		return writer.toString();

	}

}
