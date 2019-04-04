package com.xs.jt.vehvideo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConvertVideo {

	protected static Log log = LogFactory.getLog(ConvertVideo.class);

	private static String inputPath = "";

	private static String outputPath = "";

	private static String ffmpegPath = "";

	private static String fileName = "";

//	public static void main(String args[]) throws IOException {

	// getPath("E:\\C1_1.mp4","E:\\vod\\","C1_1");
	// System.out.println("C1_1.mp4".substring(0, "C1_1.mp4".indexOf(".")));

//		if (!checkfile(inputPath)) {
//			System.out.println(inputPath + " is not file");
//			return;
//		}
//		if (process()) {
//			System.out.println("ok");
//		}
	// System.out.println(new Date().getTime());
//		Calendar now = Calendar.getInstance();  
//        System.out.println("年: " + now.get(Calendar.YEAR));  
//        System.out.println("月: " + (now.get(Calendar.MONTH) + 1) + "");  
//        System.out.println("日: " + now.get(Calendar.DAY_OF_MONTH));  
//        System.out.println("时: " + now.get(Calendar.HOUR_OF_DAY));  
//        System.out.println("分: " + now.get(Calendar.MINUTE));  
//        System.out.println("秒: " + now.get(Calendar.SECOND));  
//	}

	public static void getPath(String inputpath, String outputpath, String filename) {
		inputPath = inputpath;// "E:\\C1_1.mp4";
		outputPath = outputpath;// "E:\\vod\\";
		fileName = filename;
		ffmpegPath = ClientDemo.EXTEND_PATH + "\\ffmpeg\\";
	}

	public static boolean process() throws Exception {
		int type = checkContentType();
		boolean status = false;
		status = processMp4(inputPath);// 直接转成mp4格式
		return status;
	}

	private static int checkContentType() {
		String type = inputPath.substring(inputPath.lastIndexOf(".") + 1, inputPath.length()).toLowerCase();
		// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
		if (type.equals("avi")) {
			return 0;
		} else if (type.equals("mpg")) {
			return 0;
		} else if (type.equals("wmv")) {
			return 0;
		} else if (type.equals("3gp")) {
			return 0;
		} else if (type.equals("mov")) {
			return 0;
		} else if (type.equals("mp4")) {
			return 0;
		} else if (type.equals("asf")) {
			return 0;
		} else if (type.equals("asx")) {
			return 0;
		} else if (type.equals("flv")) {
			return 0;
		}
		// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等),
		// 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
		else if (type.equals("wmv9")) {
			return 1;
		} else if (type.equals("rm")) {
			return 1;
		} else if (type.equals("rmvb")) {
			return 1;
		}
		return 9;
	}

	public static boolean checkfile(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return false;
		}
		return true;
	}

	// 对ffmpeg无法解析的文件格式(wmv9，rm，rmvb等), 可以先用别的工具（mencoder）转换为avi(ffmpeg能解析的)格式.
	private static String processAVI(int type) {
		List<String> commend = new ArrayList<String>();
		commend.add(ffmpegPath + "mencoder");
		commend.add(inputPath);
		commend.add("-oac");
		commend.add("lavc");
		commend.add("-lavcopts");
		commend.add("acodec=mp3:abitrate=64");
		commend.add("-ovc");
		commend.add("xvid");
		commend.add("-xvidencopts");
		commend.add("bitrate=600");
		commend.add("-of");
		commend.add("mp4");
		commend.add("-o");
		commend.add(outputPath + fileName + ".AVI");
		try {
			ProcessBuilder builder = new ProcessBuilder();
			Process process = builder.command(commend).redirectErrorStream(true).start();
			new PrintStream(process.getInputStream());
			new PrintStream(process.getErrorStream());
			process.waitFor();
			return outputPath + fileName + ".AVI";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ffmpeg能解析的格式：（asx，asf，mpg，wmv，3gp，mp4，mov，avi，flv等）
	private static boolean processFlv(String oldfilepath) {

		if (!checkfile(inputPath)) {
			return false;
		}
		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath + "ffmpeg");
		command.add("-i");
		command.add(oldfilepath);
		command.add("-ab");
		command.add("56");
		command.add("-ar");
		command.add("22050");
		command.add("-qscale");
		command.add("8");
		command.add("-r");
		command.add("15");
		command.add("-s");
		command.add("600x500");
		command.add(outputPath + fileName + ".flv");
		try {

			// 方案1
//            Process videoProcess = Runtime.getRuntime().exec(ffmpegPath + "ffmpeg -i " + oldfilepath 
//                    + " -ab 56 -ar 22050 -qscale 8 -r 15 -s 600x500 "
//                    + outputPath + "a.flv");

			// 方案2
			Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

			new PrintStream(videoProcess.getErrorStream()).start();

			new PrintStream(videoProcess.getInputStream()).start();

			videoProcess.waitFor();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static boolean processMp4(String oldfilepath) throws Exception {

		if (!checkfile(inputPath)) {
			return false;
		}
		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath + "ffmpeg");
		command.add("-i");
		command.add(oldfilepath);
		command.add("-c:v");
		command.add("libx264");
		command.add("-mbd");
		command.add("0");
		command.add("-c:a");
		command.add("aac");
		command.add("-strict");
		command.add("-2");
		command.add("-pix_fmt");
		command.add("yuv420p");
		command.add("-movflags");
		command.add("faststart");
		command.add(outputPath + fileName + ".mp4");
		try {

//			log.info(ffmpegPath + "ffmpeg -i " + oldfilepath
//					+ " -c:v libx264 -mbd 0 -c:a aac -strict -2 -pix_fmt yuv420p -movflags faststart " + outputPath
//					+ fileName + ".mp4");
			
			// 方案1
//			Process videoProcess = Runtime.getRuntime()
//					.exec(ffmpegPath + "ffmpeg -i " + oldfilepath
//							+ " -c:v libx264 -mbd 0 -c:a aac -strict -2 -pix_fmt yuv420p -movflags faststart "
//							+ outputPath + fileName + ".mp4");

			// 方案2
			Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();

			new PrintStream(videoProcess.getErrorStream()).start();

			new PrintStream(videoProcess.getInputStream()).start();

			videoProcess.waitFor();

			return true;
		} catch (Exception e) {
			throw e;

		}
	}

}
