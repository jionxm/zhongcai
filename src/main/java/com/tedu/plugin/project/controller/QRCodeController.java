package com.tedu.plugin.project.controller;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.plugin.project.service.QRCodeService;



/**
 * 
 * @author lvzhenhui
 * 手机扫码相关界面
 *
 */
@Controller
@RequestMapping ("/qr")
public class QRCodeController {
	@Resource
	FormService formService;
	@Resource
	QRCodeService qRCodeService;

	@RequestMapping(value = "/view")
	public String getView(HttpServletRequest request,Model model,FormModel formModel) {
		String ctlId = request.getParameter("id");
/*		
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("qr_id", ctlId);
		qp.setQueryParam("project/QryTesterId");//查询主体id
		List<Map<String,Object>> testerlist = formService.queryBySqlId(qp);*/
		
	/*	Map<String,Object> viewMap = getParams("project/QryEmpList", "eq_id", ctlId).get(0);
		model.addAttribute("ctx", request.getContextPath());
		model.addAttribute("data",viewMap);*/
		model.addAttribute("code",qRCodeService.getBase64Code(ctlId,400,400));
		
		return "m/main";
	}
	
	
}
