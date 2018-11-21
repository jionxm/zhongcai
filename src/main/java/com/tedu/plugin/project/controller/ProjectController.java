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
public class ProjectController {
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
	public void export(HttpServletRequest request, HttpServletResponse response, FormModel formModel, Model model)
			throws IOException, TemplateException {
		String projectId = request.getParameter("projectId");
		String groupId = request.getParameter("groupId");

		Map<String, Object> map = formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("projId", projectId);
		qp.getData().put("groupId", groupId);
		qp.setQueryParam("export/QryProjName");// 查询项目名字
		List<Map<String, Object>> project = formService.queryBySqlId(qp);
		// 项目相关信息查询
		Map<String, Object> maps = new HashMap<String, Object>();
		if (project.size() == 1) {
			maps = project.get(0);
		}

		// 查询综合测评主体情况
		// 查询综合测评主体情况

		qp.setQueryParam("count/QryTesterDetail");
		List<Map<String, Object>> peoples = formService.queryBySqlId(qp);
		// 人数合计
		int peoCount = 0;
		for (Map peoMap : peoples) {
			int count = Integer.parseInt(peoMap.get("count") == null ? 0 + "" : peoMap.get("count").toString());
			peoCount += count;
		}
		maps.put("peoCount", peoCount);

		// 查询该项目下所有维度
		qp.setQueryParam("count/QryDimension");
		List<Map<String, Object>> questionTitle = formService.queryBySqlId(qp);

		// 查询所有问题
		qp.setQueryParam("count/QryQuestionsList");
		List<Map<String, Object>> questionList = formService.queryBySqlId(qp);
		maps.put("questionCount", questionList.size());
		// 查询主体身份信息
		qp.setQueryParam("count/QryTesterByGroup");
		List<Map<String, Object>> testerList = formService.queryBySqlId(qp);

		// 答题结果查询
		List resultList = new ArrayList();
		for (Map<String, Object> testerMap : testerList) {
			if (testerMap == null) {
				break;
			}
			String dimension = testerMap.get("dimension").toString();
			String dimensionName = (String) testerMap.get("dimensionName");
			qp.getData().put("dimension", dimension);
			qp.setQueryParam("count/QryQuestionResult");
			List<Map<String, Object>> result = formService.queryBySqlId(qp);
			double avg = 0;
			String temp = "";
			int count = 0;
			int size = result.size();
			for (int i = 0; i < size; i++) {
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap = result.get(i);
				if (resMap.get("dimension") != null) {
					String dimensions = resMap.get("dimension").toString();
					if (temp == "") {
						temp = dimensions;
						avg += Double.parseDouble(resMap.get("avgScore").toString());
						count++;
					} else if (temp.equals(dimensions) && (i + 1) != size) {
						avg += Double.parseDouble(resMap.get("avgScore").toString());
						count++;
					} else {
						
						BigDecimal bigDecimal = new BigDecimal(avg);
						BigDecimal resDecimal = bigDecimal.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
						Map<String, Object> totalMap = new HashMap<String, Object>();
						totalMap.put("avgTotal", resDecimal);
						totalMap.put("colspan", (i + 1) == size ? (count + 1) : count);
						totalMap.put("testerName", dimensionName);
						result.add(totalMap);
						temp = "";
						avg = 0;
						count = 0;
						temp = dimensions;
						avg += Double.parseDouble(resMap.get("avgScore").toString());
						count++;
					}
				}

			}
			resultList.add(result);

		}

		/**
		 * 如果为测评为人员组，则查询领导班子成员得分情况否则不查
		 * 
		 */
		List<Map<String, Object>> persionResult = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> testerResult = new ArrayList<Map<String, Object>>();
		if ("person".equals(maps.get("type").toString())) {
			// 查询该项目下所有维度
			qp.setQueryParam("count/QryPersionQuestion");
			persionResult = formService.queryBySqlId(qp);

			int size = persionResult.size();
			for (int i = 0; i < size; i++) {
				double avg = 0;
				String temp = "";
				int count = 0;
				double davg = 0;
				String dtemp = "";
				int dcount = 0;
				int cols = 0;
				StringBuilder sResult = new StringBuilder();
				StringBuilder sDimension = new StringBuilder();
				StringBuilder sDimensionCount = new StringBuilder();
				Map<String, Object> resMap = new HashMap<String, Object>();
				resMap = persionResult.get(i);
				String[] questions = resMap.get("questions").toString().split(",");
				String[] results = resMap.get("results").toString().split(",");
				String[] dimensions = resMap.get("dimension").toString().split(",");
				for (int j = 0; j < questions.length; j++) {
					
					String question = questions[j];
					if (temp == "") {
						temp = question;
						avg += Double.parseDouble(results[j]);
						count++;
					} else if (temp.equals(question) && (j + 1) != questions.length) {
						avg += Double.parseDouble(results[j]);
						count++;
					} else {
						if((j + 1) == questions.length){
							count++;
						}
						cols = count;
						BigDecimal bigDecimal = new BigDecimal(avg);
						BigDecimal resDecimal = bigDecimal.divide(new BigDecimal(count), 2, BigDecimal.ROUND_HALF_UP);
						sResult.append(resDecimal).append(",");
						temp = "";
						avg = 0;
						count = 0;
						temp = question;
						avg += Double.parseDouble(results[j]);
						count++;
					}
					
					String dimension = dimensions[j];
					if (dtemp == "") {
						dtemp = dimension;
						davg += Double.parseDouble(results[j]);
						dcount++;
					} else if (dtemp.equals(dimension) && (j + 1) != questions.length) {
						davg += Double.parseDouble(results[j]);
						dcount++;
					} else {
						if((j + 1) == questions.length){
							dcount++;
						}
						BigDecimal bigDecimal = new BigDecimal(davg);
						BigDecimal resDecimal = bigDecimal.divide(new BigDecimal(dcount), 2, BigDecimal.ROUND_HALF_UP);
						sDimension.append(resDecimal).append(",");
						
						sDimensionCount.append(dcount/cols).append(",");
						dtemp = "";
						davg = 0;
						dcount = 0;
						dtemp = dimension;
						davg += Double.parseDouble(results[j]);
						dcount++;
					}
					
				}
				
				resMap.put("results", sResult.substring(0, sResult.lastIndexOf(",")));
				resMap.put("dimentsion", sDimension.substring(0, sDimension.lastIndexOf(",")));
				resMap.put("dCount", sDimensionCount.substring(0, sDimensionCount.lastIndexOf(",")));
			}
			/**
			 * 按测评主体得分统计情况
			 * 
			 */
			// 查询该项目下所有维度
			qp.setQueryParam("count/QryTesterResult");
			testerResult = formService.queryBySqlId(qp);
			
		}
		
		maps.put("testerResult", testerResult);
		maps.put("persionResult", persionResult);
		maps.put("peoples", peoples);
		maps.put("titleSize", questionTitle.size());
		maps.put("questionTitle", questionTitle);
		maps.put("questions", questionList);
		maps.put("results", resultList);
		log.info("maps----:" + maps);
		// model.addAttribute("", maps);

		DocUtil.download(request, response, rootPath, "领导班子的综合测评.doc", maps, "exportWeight.ftl");

	}

}
