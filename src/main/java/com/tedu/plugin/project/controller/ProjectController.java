package com.tedu.plugin.project.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.file.util.io.file;
import com.tedu.base.task.SpringUtils;
import com.tedu.plugin.project.service.QRCodeService;

@Controller
@RequestMapping("/project")
public class ProjectController  {	
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

	@RequestMapping("/export")
	@ResponseBody
	public void export(HttpServletRequest request,HttpServletResponse response) throws IOException{
		System.out.println(request.getParameter("id"));
		
		String fileName = "统计.xls";
		//创建HSSFWorkbook对象
		HSSFWorkbook wkb = new HSSFWorkbook();
		//创建HSSFSheet对象
		HSSFSheet sheet = wkb.createSheet("sheet0");
		//创建HSSFRow对象
		HSSFRow row = sheet.createRow(0);
		//创建HSSFCell对象
		HSSFCell cell=row.createCell(0);
		cell.setCellValue("测评表统计");
		sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
		//设置单元格的值
		HSSFRow row2 = sheet.createRow(1);
		row2.createCell(0).setCellValue("姓名");
		row2.createCell(1).setCellValue("班级");    
		row2.createCell(2).setCellValue("笔试成绩");
		row2.createCell(3).setCellValue("机试成绩");    
	      //在sheet里创建第三行
	    HSSFRow row3=sheet.createRow(2);
	    row3.createCell(0).setCellValue("李明");
	    row3.createCell(1).setCellValue("As178");
	    row3.createCell(2).setCellValue(87);    
	    row3.createCell(3).setCellValue(78);
		
	    
		FileOutputStream output=new FileOutputStream("d:\\workbook.xls");
		wkb.write(output);
		output.flush();
		
		
	}
	
	
}

