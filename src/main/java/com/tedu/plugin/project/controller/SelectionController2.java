package com.tedu.plugin.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ObjectUtils;
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
 * @author zhizhizhi
 *
 */
@Controller
@RequestMapping ("/selection1")
public class SelectionController2 {
	
    @Resource
    private FormService formService;
	
	@RequestMapping(value = "")
	public String QueryQuestions(Model model,HttpServletRequest request,FormModel formModel) {
		/*String qrCode = request.getParameter("qr_code");
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("id", qrCode);
		qp.setQueryParam("project/QryQrCode");
		List<Map<String,Object>> qrCodeList = formService.queryBySqlId(qp);
		model.addAttribute("test", qrCodeList);
		if (!qrCodeList.isEmpty()) {
			Map<String, Object> idsMap = formModel.getData();
			QueryPage qp2 = new QueryPage();
			qp2.setParamsByMap(idsMap);
			qp2.getData().put("ctlId",qrCodeList.get(0).get("QRId"));
			qp2.getData().put("ctlTestId",qrCodeList.get(0).get("testId"));
			qp2.getData().put("ctlProjectId",qrCodeList.get(0).get("projectId"));
			qp2.getData().put("ctlTesterId",qrCodeList.get(0).get("ztqzId"));
			qp2.getData().put("ctlProjGroupId",qrCodeList.get(0).get("projectGroupId"));
			qp2.setQueryParam("project/QryQrQuestion2");
			List<Map<String,Object>> questionsList = formService.queryBySqlId(qp2);
			model.addAttribute("qList", questionsList);
			qp2.setQueryParam("project/QryQrQTotal");
			List<Map<String,Object>> questionsTotal = formService.queryBySqlId(qp2);
			model.addAttribute("total", questionsTotal);
			Map<String,Object> chooseMap =  formModel.getData();
			QueryPage qp3 = new QueryPage();
			qp3.setParamsByMap(chooseMap);
			qp3.getData().put("",null );
			qp3.setQueryParam("project/QryQrChoose");
			List<Map<String,Object>> chooseList = formService.queryBySqlId(qp3);
			model.addAttribute("cList", chooseList);
			return "h5/Selection";
		}*/
		return "h5/Selection1";
	}
	
}
