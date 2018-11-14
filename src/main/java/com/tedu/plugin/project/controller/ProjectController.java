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
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.file.util.io.file;
import com.tedu.base.task.SpringUtils;
import com.tedu.plugin.project.service.QRCodeService;
import com.tedu.plugin.project.util.DocUtil;

import freemarker.template.TemplateException;

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
	public void export(HttpServletRequest request,HttpServletResponse response, FormModel formModel, Model model) throws IOException, TemplateException{
		System.out.println(request.getParameter("id"));
		
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("projId", request.getParameter("id"));
		qp.setQueryParam("export/QryProjName");//查询项目名字
		List<Map<String,Object>> projName = formService.queryBySqlId(qp);

		
		qp.setQueryParam("count/QryQuestionById");
		List<Map<String,Object>> question = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("项目下所有题目----:"+question);
		
		qp.setQueryParam("count/QryQuestionResult");
		List<Map<String,Object>> result = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("答题结果----:"+result);
		
		Map<String, Object> maps = new HashMap<String,Object>();
		maps.put("deptName", "北玻有限公司");
		maps.put("createDate", "2018年11月13日");
		maps.put("year", "2018");
		maps.put("peopleCount", "11");
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		
		Map<String, String> map1 = new HashMap<String, String>();
		map1.put("type", "总部公司领导");
		map1.put("count", "");
		map1.put("percent", "");
		map1.put("leaderWeight", "40%");
		map1.put("peoWeight", "25%");
		list.add(map1);
		Map<String, String> map2 = new HashMap<String, String>();
		map2.put("type", "总部职能部门");
		map2.put("count", "");
		map2.put("percent", "");
		map2.put("leaderWeight", "20%");
		map2.put("peoWeight", "15%");
		list.add(map2);
		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("type", "领导班子正职");
		map3.put("count", "2");
		map3.put("percent", "(18%)");
		map3.put("leaderWeight", "10%");
		map3.put("peoWeight", "15%");
		list.add(map3);
		Map<String, String> map4 = new HashMap<String, String>();
		map4.put("type", "领导班子副职");
		map4.put("count", "3");
		map4.put("percent", "(27.27%)");
		map4.put("leaderWeight", "19%");
		map4.put("peoWeight", "15%");
		list.add(map4);
		
		Map<String, String> map5 = new HashMap<String, String>();
		map5.put("type", "中层领导人员");
		map5.put("count", "5");
		map5.put("percent", "(54.55%)");
		map5.put("leaderWeight", "19%");
		map5.put("peoWeight", "15%");
		list.add(map5);
		
		maps.put("peoples", list);
//		model.addAttribute("", maps);
		
		DocUtil.download(request, response, "领导班子的综合测评.doc", maps,"test.ftl");
		
	}
	
	
}

