package com.tedu.plugin.project.controller;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
		String projectId = request.getParameter("projectId");
		String groupId = request.getParameter("groupId");
		
		
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("projId", projectId);
		qp.getData().put("groupId", groupId);
		qp.setQueryParam("export/QryProjName");//查询项目名字
		List<Map<String,Object>> project = formService.queryBySqlId(qp);
		//项目相关信息查询
		Map<String, Object> maps = new HashMap<String,Object>();
		if(project.size()==1){
			maps=project.get(0);
		}
		
		//查询综合测评主体情况
		//查询综合测评主体情况
	
		qp.setQueryParam("count/QryTesterDetail");
		List<Map<String,Object>> peoples = formService.queryBySqlId(qp);
		//人数合计
		int peoCount = 0;
		for(Map peoMap:peoples){
			int count = Integer.parseInt(peoMap.get("count").toString());
			peoCount+=count;
		}
		maps.put("peoCount",peoCount);
		
		//查询该项目下所有维度
		qp.setQueryParam("count/QryDimension");
		List<Map<String,Object>> questionTitle = formService.queryBySqlId(qp);
		
		//查询所有问题
		qp.setQueryParam("count/QryQuestionsList");
		List<Map<String,Object>> questionList = formService.queryBySqlId(qp);
		
		
		//查询主体身份信息
		qp.setQueryParam("count/QryTesterByGroup");
		List<Map<String,Object>> testerList = formService.queryBySqlId(qp);
		
		//答题结果查询
		List resultList = new ArrayList();
		for(Map<String,Object> testerMap:testerList){
			String dimension = testerMap.get("dimension").toString();
			String dimensionName = (String) testerMap.get("dimensionName");
			qp.getData().put("dimension", dimension);
			qp.setQueryParam("count/QryQuestionResult");
			List<Map<String,Object>> result = formService.queryBySqlId(qp);
			double avg = 0;
			String temp = "";
			int count = 0;
			int size = result.size();
			for(int i=0;i<size;i++){
				Map<String,Object> resMap = new HashMap<String,Object>();
				resMap = result.get(i);
				String dimensions = resMap.get("dimension").toString();
				if(temp==""){
					temp=dimensions;
					avg += Double.parseDouble(resMap.get("avgScore").toString());
					count ++;
				}else if(temp.equals(dimensions)&&(i+1)!=size){
					avg += Double.parseDouble(resMap.get("avgScore").toString());
					count ++;
				}else {
					BigDecimal bigDecimal = new BigDecimal(avg);
					BigDecimal resDecimal = bigDecimal.divide(new BigDecimal(count),2,BigDecimal.ROUND_HALF_UP);
					Map<String,Object> totalMap = new HashMap<String,Object>();
					totalMap.put("avgTotal", resDecimal);
					totalMap.put("colspan", (i+1)==size?(count+1):count);
					totalMap.put("testerName", dimensionName);
					result.add(totalMap);
					temp="";
					avg = 0;
					count = 0;
					temp=dimensions;
					avg += Double.parseDouble(resMap.get("avgScore").toString());
					count ++;
				}
				
			}
			resultList.add(result);
			
			
			
			
		}
		
		
		
		/*qp.setQueryParam("count/QryQuestionResult");
		List<Map<String,Object>> result = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("答题结果----:"+result);
		
		qp.setQueryParam("export/QryProjNumber");
		List<Map<String,Object>> allNumber = formService.queryBySqlId(qp);//查询该项目下总人数
		log.info("总人数----:"+allNumber);
		
		
		qp.setQueryParam("export/QryTypeResult");
		List<Map<String,Object>> type = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("分组统计----:"+type);*/
		
		
		
		/*
		 * 分组人数权重统计
		 */
		/*List<Map<String, String>> grouplist = new ArrayList<Map<String, String>>();		
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
		*/
		
		maps.put("peoples", peoples);
		maps.put("titleSize", questionTitle.size());
		maps.put("questionTitle", questionTitle);
		maps.put("questions", questionList);
		maps.put("results", resultList);
		log.info("maps----:"+maps);
//		model.addAttribute("", maps);
		
		DocUtil.download(request, response, "领导班子的综合测评.doc", maps,"exportWeight.ftl");
		
	}
	
	
}

