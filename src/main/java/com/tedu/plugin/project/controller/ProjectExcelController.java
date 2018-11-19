package com.tedu.plugin.project.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.DateUtils;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.CustomFormModel;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.file.util.io.file;
import com.tedu.base.task.SpringUtils;
import com.tedu.plugin.project.service.QRCodeService;
import com.tedu.plugin.project.util.DocUtil;

import freemarker.template.TemplateException;

@Controller
@RequestMapping("/project")
public class ProjectExcelController  {	
	@Value("${file.upload.path}")
	private String rootPath;
	@Resource
	FormService formService;
	FormMapper formMapper = SpringUtils.getBean("simpleDao");
	public final Logger log = Logger.getLogger(this.getClass());

		
	@Resource
	QRCodeService qrCodeService;
	@Autowired
	HttpServletRequest request;

	@RequestMapping("/excel")
	@ResponseBody
	public void export(HttpServletRequest request,HttpServletResponse response, FormModel formModel, Model model) throws IOException, TemplateException{
		String projId = request.getParameter("id");
		Long emp = SessionUtils.getUserInfo().getEmpId();
		String date = DateUtils.getDateToStr("YYYYMMdd", new Date());
    	String separator = File.separator;
    	String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		if(!rootPath.endsWith("/")&&!rootPath.endsWith("\\")){
			rootPath = rootPath + separator;
		}
		String filePath = rootPath+separator+"private"+separator+"export"+separator+date +separator;
		log.info("filePath----:"+filePath);
		String qrfilePath = filePath + UUID.randomUUID().toString().replaceAll("-", "") + separator;			
		String excelPath = filePath + "excel" + separator;
		String path = excelPath+uuid.substring(0, 2)+separator;
		log.info("path----:"+path);
		String type = request.getParameter("type");
		
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("projId", projId);
		qp.setQueryParam("excel/QryProjName");//查询项目名字
		List<Map<String,Object>> projName = formService.queryBySqlId(qp);
		System.out.println("项目名称----:"+projName);
		String fileName = projName.get(0).get("name")+".xls";
		System.out.println("导出名称----:"+fileName);
		
		qp.setQueryParam("count/QryQuestionById");
		List<Map<String,Object>> question = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("项目下所有题目----:"+question);
		
		qp.setQueryParam("count/QryQuestionResult");
		List<Map<String,Object>> result = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("答题结果----:"+result);
		
		
		//创建HSSFWorkbook对象
		HSSFWorkbook wkb = new HSSFWorkbook();
		//创建HSSFSheet对象
		HSSFSheet sheet = wkb.createSheet("sheet0");
		//创建HSSFRow对象
		HSSFRow row = sheet.createRow(0);
		//创建HSSFCell对象
		HSSFCell cell=row.createCell(0);
		cell.setCellValue(projName.get(0).get("name").toString()+"数据统计");
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		//设置单元格的值
		HSSFRow row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("单位");
		for(int i=0;i<question.size();i++){
			String questionName = question.get(i).get("question").toString();
			row1.createCell(i+1).setCellValue(questionName);			
		}
		
		HSSFRow[] rows = new HSSFRow[result.size()];
	    for(int j=0;j<result.size();j++){
	    	rows[j] = sheet.createRow(j+2);
	    	String resultName = null;
	    	String testerName = result.get(j).get("name").toString();
	    	for(int k=0;k<question.size();k++){
	    		String quesName = question.get(k).get("question").toString();
	    		resultName = (String) result.get(j).get(quesName);
	    		log.info("答题结果----:"+testerName+quesName+resultName);
	    		rows[j].createCell(0).setCellValue(testerName);
	    		rows[j].createCell(k+1).setCellValue(resultName);
	    	}
	    }
	    
	    
		File file = new File(path ); // 指定存放目录
		if(!file.exists()){
			file.mkdirs();
		}
	    FileOutputStream output=new FileOutputStream(path+uuid+".xls");
		wkb.write(output);
		output.flush();
	    
	    
	    String downFileName = new String(fileName+".xls");
	    try{
	    	downFileName = URLEncoder.encode(downFileName, "UTF-8");
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    try{
	    	//清空respone
	    	response.reset();
	    	response.setContentType("application/msexcel");//设置生成的文件类型
	    	response.setCharacterEncoding("UTF-8");//设置文件头编码方式和文件名
	    	response.setHeader("Content-Disposition", "attachment; filename=" + new String(downFileName.getBytes("utf-8"), "ISO8859-1"));	    	
            OutputStream os = response.getOutputStream();
	    	wkb.write(os);
	    	os.flush();
	    	os.close();
	    }catch(IOException e){
	    	e.printStackTrace();
	    }
	    
	    
	    Map sqlMap = new HashMap();
    	sqlMap.put("projectId", projId);
    	sqlMap.put("path", path);
    	sqlMap.put("type", type);
    	sqlMap.put("createBy", emp);	    		    	
    	CustomFormModel csmd = new CustomFormModel("","",sqlMap);
    	csmd.setSqlId("excel/InsertRecord");
    	formMapper.saveCustom(csmd);
	}
		
		
	
	
	
}

