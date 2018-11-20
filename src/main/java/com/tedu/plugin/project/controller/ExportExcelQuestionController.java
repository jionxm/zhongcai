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
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
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
@RequestMapping("/export")
public class ExportExcelQuestionController  {	
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

	@RequestMapping("/excelquestion")
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
		String fileName = projName.get(0).get("name").toString();
		System.out.println("导出名称----:"+fileName);
		
		/*qp.setQueryParam("excel/QryGroupById");//查询该项目下所有分组的答案
		List<Map<String,Object>> groupresult = formService.queryBySqlId(qp);
		log.info("项目下所有分组的答案----:"+groupresult);*/
		
		qp.setQueryParam("excel/QryGroupNameById");//查询该项目下所有的分组
		List<Map<String,Object>> group = formService.queryBySqlId(qp);
		log.info("所有的分组----:"+group);
		
		int excellentCount = 0;
		int competentCount = 0;
		int incompetentCount = 0;
		String excellentGroup = "";
		String competentGroup = "";
		String incompetentGroup = "";
		for(Map groupmap:group){
			String groupname = groupmap.get("groupName").toString();
			qp.getData().put("groupname", groupname);
			qp.setQueryParam("excel/QryGroupById");//查询该项目下所有分组的答案
			List<Map<String,Object>> groupresult = formService.queryBySqlId(qp);
			for(Map resultmap:groupresult){
				String result = resultmap.get("result").toString();
				String group1 = resultmap.get("groupName").toString();				
				Double score  = Double.parseDouble(result);
				if(score>=90){
					excellentCount++;
					excellentGroup = excellentGroup+groupname+",";
				}
				if(score<90&&score>=60){
					competentCount++;
					competentGroup = competentGroup+groupname+",";
				}
				if(score<60){
					incompetentCount++;
					incompetentGroup = incompetentGroup+groupname+",";
				}
			}
		}
		if(excellentGroup!=""){
			excellentGroup = excellentGroup.substring(0, excellentGroup.length()-1);			
		}
		if(competentGroup!=""){
			competentGroup = competentGroup.substring(0, competentGroup.length()-1);		
		}
		if(incompetentGroup!=""){
			incompetentGroup = incompetentGroup.substring(0, incompetentGroup.length()-1);	
		}
		
		
		log.info("优秀---:"+excellentCount+"-"+excellentGroup);
		log.info("称职---:"+competentCount+"-"+competentGroup);
		log.info("不称职---:"+incompetentCount+"-"+incompetentGroup);
		
		
		/*int excellentCount = 0;
		int competentCount = 0;
		int incompetentCount = 0;
		int resultCount = 0;
		for(Map groupmap:group){
			String groupname = groupmap.get("groupName").toString();
			for(Map resultmap:groupresult){
				String result = resultmap.get("result").toString();
				String group1 = resultmap.get("groupName").toString();
				int score = Integer.parseInt(result);
				if(group1.equals(groupname)){
					resultCount = resultCount+score;
				}
				log.info("分组-答案-分组1---:"+groupname+"-"+result+"-"+group1);
			}
			log.info("分组-答案总分---:"+groupname+"-"+resultCount);
			resultCount = 0;
		}*/
		
		
		//创建HSSFWorkbook对象
		HSSFWorkbook wkb = new HSSFWorkbook();
		//创建HSSFSheet对象
		HSSFSheet sheet = wkb.createSheet("sheet0");
		HSSFCellStyle setBorder = wkb.createCellStyle();
		setBorder.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
		//创建HSSFRow对象
		HSSFRow row = sheet.createRow(0);
		//创建HSSFCell对象
		HSSFCell cell=row.createCell(0);
		cell.setCellValue(projName.get(0).get("name").toString()+"测评总体结果");
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		//设置单元格的值
		sheet.setColumnWidth(0, 3000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		sheet.setColumnWidth(1, 7000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		sheet.setColumnWidth(2, 2000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		sheet.setColumnWidth(3, 10000); //第一个参数代表列id(从0开始),第2个参数代表宽度值
		
		HSSFRow row1 = sheet.createRow(1);
		row1.createCell(0).setCellValue("测评等级");
		row1.createCell(1).setCellValue("分数段");
		row1.createCell(2).setCellValue("单位数量");
		row1.createCell(3).setCellValue("单位名称");
		
		HSSFRow row2 = sheet.createRow(2);
		row2.createCell(0).setCellValue("优秀");
		row2.createCell(1).setCellValue("90分及以上");
		row2.createCell(2).setCellValue(excellentCount);
		row2.createCell(3).setCellValue(excellentGroup);
		
		HSSFRow row3 = sheet.createRow(3);
		row3.createCell(0).setCellValue("称职");
		row3.createCell(1).setCellValue("90分以下60分以上");
		row3.createCell(2).setCellValue(competentCount);
		row3.createCell(3).setCellValue(competentGroup);
		
		HSSFRow row4 = sheet.createRow(4);
		row4.createCell(0).setCellValue("不称职");
		row4.createCell(1).setCellValue("60分以下");
		row4.createCell(2).setCellValue(incompetentCount);
		row4.createCell(3).setCellValue(incompetentGroup);
		
		
		
	    
		File file = new File(path ); // 指定存放目录
		if(!file.exists()){
			file.mkdirs();
		}
	    FileOutputStream output=new FileOutputStream(path+uuid+".xls");
		wkb.write(output);
		output.flush();
	    
	    
	    String downFileName = new String(fileName+"总体结果.xls");
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

