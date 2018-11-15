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
		
		qp.setQueryParam("export/QryProjNumber");
		List<Map<String,Object>> allNumber = formService.queryBySqlId(qp);//查询该项目下总人数
		log.info("总人数----:"+allNumber);
		
		
		qp.setQueryParam("export/QryTypeResult");
		List<Map<String,Object>> type = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("分组统计----:"+type);
		
		Map<String, Object> maps = new HashMap<String,Object>();
		maps.put("deptName", "北玻有限公司");
		maps.put("createDate", "2018年11月13日");
		maps.put("year", "2018");
		maps.put("peopleCount", "11");
		
		/*
		 * 分组人数权重统计
		 */
		List<Map<String, String>> grouplist = new ArrayList<Map<String, String>>();		
		Map<String, String>[] groupmap = new Map[type.size()];
		for(int i=0;i<type.size();i++){
			groupmap[i] = new HashMap<String, String>();
			groupmap[i].put("type", type.get(i).get("groupName").toString());
			groupmap[i].put("count", type.get(i).get("number").toString());
			groupmap[i].put("percent", "("+type.get(i).get("percent").toString()+")");
			groupmap[i].put("leaderWeight", "40%");
			groupmap[i].put("peoWeight", "25%");
			grouplist.add(groupmap[i]);
		}
		
		/*
		 * 答题结果统计
		 */
		List<Map<String, String>> resultlist = new ArrayList<Map<String, String>>();		
		Map<String, String>[] resultmap = new Map[result.size()];
		for(int j=0;j<result.size();j++){
			resultmap[j] = new HashMap<String, String>();
			for(int k=0;k<question.size();k++){
				String quesName = question.get(k).get("question").toString();
				log.info("quesName----:"+quesName);
				resultmap[j].put("testerName", result.get(j).get("name").toString());
				resultmap[j].put(quesName, result.get(k).get(quesName).toString());
			}
			resultlist.add(resultmap[j]);
		}
		
		maps.put("peoples", grouplist);
		maps.put("results", resultlist);
		log.info("maps----:"+maps);
//		model.addAttribute("", maps);
		
		DocUtil.download(request, response, "领导班子的综合测评.doc", maps,"exportWeight.ftl");
		
	}
	
	
}

