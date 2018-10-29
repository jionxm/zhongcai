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
		String qr_id = request.getParameter("id");
	
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("id", qr_id);
		qp.setQueryParam("project/QryTestByQrId");//查询主体id
		List<Map<String,Object>> projectList = formService.queryBySqlId(qp);
		model.addAttribute("ctx", request.getContextPath());
		model.addAttribute("data",projectList.size()>0?projectList.get(0):"projectName=未查询到相关记录");
		
		return "h5/Identity";
	}
	
	
}
