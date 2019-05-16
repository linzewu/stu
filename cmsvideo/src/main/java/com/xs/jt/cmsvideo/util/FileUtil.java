package com.xs.jt.cmsvideo.util;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileUtil {

	protected static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * 判断目录不存在则创建
	 * 
	 * @param path
	 */
	public static void createDirectory(String path) {
		File pathFile = new File(path);
		if (!pathFile.exists()) {
			pathFile.mkdir();
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param fileName 要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				log.info("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				log.info("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			log.info("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}
}
