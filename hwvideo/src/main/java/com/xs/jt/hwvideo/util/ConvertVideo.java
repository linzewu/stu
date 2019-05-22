package com.xs.jt.hwvideo.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
public class ConvertVideo {

	protected static Log log = LogFactory.getLog(ConvertVideo.class);

	private static String inputPath = "";

	private static String outputPath = "";

	private static String ffmpegPath = ClientDemo.EXTEND_PATH + "\\ffmpeg\\";

	private static String fileName = "";


//	public static void getPath(String inputpath, String outputpath, String filename) {
//		inputPath = inputpath;// "E:\\C1_1.mp4";
//		outputPath = outputpath;// "E:\\vod\\";
//		fileName = filename;
//		ffmpegPath = ClientDemo.EXTEND_PATH + "\\ffmpeg\\";
//	}

//	public static boolean process(String oldFile,String newFile) throws Exception {
//		int type = checkContentType();
//		boolean status = false;
//		//status = processMp4(inputPath);// 直接转成mp4格式
//		return status;
//	}

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

	public static boolean processMp4(String oldfilepath,String newFiles,String outPath) throws Exception {

		if (!checkfile(oldfilepath)) {
			return false;
		}
		List<String> command = new ArrayList<String>();
		command.add(ffmpegPath + "ffmpeg");
		command.add("-y");
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
		command.add(outPath+"\\"+newFiles);
		try {
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
