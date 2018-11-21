package com.tedu.plugin.project.util;

/**     
 * @文件名称: DocUtil.java   
 * @描述: TODO  
 * @作者：  wuwh
 * @时间：2018年4月10日 上午10:36:08  
 * @版本：V1.0     
 */

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;

import com.tedu.base.common.demo.FreeMarkerUtil;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @类功能说明：
 * 1.该代码在web项目中调用使用，需要在项目中的webroot目录下新建一个template文件夹，然后将预定义word的模板转成word.
 * xml文件放入template文件下即可 2.将要输出的数据放到map集合中，作为参数传入即可 依赖jar包:
 * freemarker-2.3.13.jar @作者： wuwh
 * 
 * @创建时间：2018年4月10日 上午10:36:08 @版本：V1.0
 */
public class DocUtil {
	
	public static void download(HttpServletRequest request,HttpServletResponse response,String rootPath,String newWordName,Map<String,Object> dataMap,String temName) throws TemplateException, IOException {
		
//		new FreeMarkerUtil().print("test.ftl", "/template", dataMap);
		
		Configuration configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");                                       //注意这里要设置编码
 
       //模板文件word.xml是放在WebRoot/template目录下的
		configuration.setServletContextForTemplateLoading(request.getSession()
				.getServletContext(), "/template");
 
		Template t = null;
		InputStream fis = null;
		OutputStream toClient = null;
		OutputStreamWriter outputStreamWriter = null;
		FileOutputStream fileOutputStream = null;
		File outFile = null;
		Writer out = null;
		String filename = newWordName;
		try {
			//word.xml是要生成Word文件的模板文件
			t = configuration.getTemplate(temName,"utf-8");                  // 文件名 还有这里要设置编码
			outFile = new File(rootPath+File.separator+UUID.randomUUID().toString().replace("-", "")+".doc");
			fileOutputStream = new FileOutputStream(outFile); 
			outputStreamWriter = new OutputStreamWriter(fileOutputStream,"utf-8");
			out = new BufferedWriter(outputStreamWriter);                 //还有这里要设置编码
		
			t.process(dataMap,out);
	    
			out.flush();
			out.close();
		
		
			fis = new BufferedInputStream(new FileInputStream(outFile));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			filename = URLEncoder.encode(filename, "utf-8");                                  //这里要用URLEncoder转下才能正确显示中文名称
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename=" + 
					java.net.URLEncoder.encode(newWordName, "UTF-8"));
			toClient = new BufferedOutputStream(response.getOutputStream());
			toClient.write(buffer);
			toClient.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			if(outputStreamWriter!=null){
				outputStreamWriter.close();
			}
			if(fileOutputStream!=null){
				fileOutputStream.close();
			}
			if(fis!=null){
				fis.close();
			}
			if(toClient!=null){
				toClient.close();
			}
			boolean flag = outFile.delete();
			
		}
	}
}
