package com.tedu.plugin.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tedu.base.auth.login.controller.LoginController;
import com.tedu.base.common.error.ErrorCode;
import com.tedu.base.common.error.ServiceException;
import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.engine.util.FormLogger;
/**
 * 
 * @author chenh
 *
 */
@Controller
@RequestMapping ("/count")
public class CountListController {
	
    @Resource
    private FormService formService;
    public final Logger log = Logger.getLogger(this.getClass());
	@RequestMapping(value = "")
	public String QueryQuestions(Model model,HttpServletRequest request,FormModel formModel) {
		String projId = request.getParameter("projId");
		log.info("projId----:"+projId);
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("projId", projId);
		/*qp.setQueryParam("count/QryQuestionSql");
		List<Map<String,Object>> sql = formService.queryBySqlId(qp);//拼接sql
		String questionsql = sql.get(0).get("questionsql").toString();
		log.info("拼接用sql----:"+questionsql);*/
		log.info("qp1----:"+qp);
		qp.setQueryParam("count/QryQuestionById");
		List<Map<String,Object>> question = formService.queryBySqlId(qp);//查询该项目下所有的题目
		log.info("项目下所有题目----:"+question);
		model.addAttribute("question", question);
		
		QueryPage qp1 = new QueryPage();
		qp1.setParamsByMap(map);
		qp1.getData().put("id", projId);
		log.info("qp1----:"+qp1);
//		qp.getData().put("questionsql", questionsql);
		qp1.setQueryParam("count/QryQuestionResult");
		List<Map<String,Object>> result = formService.queryBySqlId(qp1);//查询该项目下所有的题目
		log.info("答题结果----:"+result);
		model.addAttribute("result", result);
		
		return "CountList";
	}
	
}
