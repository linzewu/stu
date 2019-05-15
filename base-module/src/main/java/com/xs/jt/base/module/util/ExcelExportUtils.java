package com.xs.jt.base.module.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import net.sf.jxls.transformer.XLSTransformer;

public class ExcelExportUtils {
	protected static Log log = LogFactory.getLog(ExcelExportUtils.class);
	
	/**
     * 根据excel模板生成新的excel
     *
     * @param beans
     * @param file
     * @param path
     * @return
     * @throws Exception 
     */
    public static File createNewFile(Map<String, Object> beans, String path,String name,InputStream is) throws Exception {
        XLSTransformer transformer = new XLSTransformer();

        //可以写工具类来生成命名规则
       
        File newFile = new File(path + name);


        try (InputStream in = new BufferedInputStream(is);
             OutputStream out = new FileOutputStream(newFile)) {
            Workbook workbook = transformer.transformXLS(in, beans);
            workbook.write(out);
            out.flush();
            return newFile;
        } catch (Exception e) {
        	log.error("生成Excel失败！", e);
            throw e;
        }
        //return newFile;
    }

    /**
     * 将服务器新生成的excel从浏览器下载
     *
     * @param response
     * @param excelFile
     * @throws IOException 
     */
    public static void downloadFile(HttpServletResponse response, File excelFile) throws IOException {
        /* 设置文件ContentType类型，这样设置，会自动判断下载文件类型 */
        response.setContentType("multipart/form-data");
        /* 设置文件头：最后一个参数是设置下载文件名 */
        response.setHeader("Content-Disposition", "attachment;filename=" + excelFile.getName());
        try (
                InputStream ins = new FileInputStream(excelFile);
                OutputStream os = response.getOutputStream()
        ) {
            byte[] b = new byte[1024];
            int len;
            while ((len = ins.read(b)) > 0) {
                os.write(b, 0, len);
            }
        } catch (IOException ioe) {
        	log.error("下载Excel失败！", ioe);
        	throw ioe;
        }
    }

}
