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

import com.tedu.base.auth.login.model.UserModel;
import com.tedu.base.common.error.ErrorCode;
import com.tedu.base.common.error.ServiceException;
import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.ConstantUtil;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.engine.util.FormLogger;
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
		
		 if (SessionUtils.getUserInfo() == null ||(SessionUtils.getUserInfo()!=null && SessionUtils.getUserInfo().getUserId()!=-1l)) {
	            UserModel user = new UserModel();
	            user.setUserId(-1l);
	            user.setName(UserModel.ANONYMOUS);
	            SessionUtils.setAttrbute(ConstantUtil.USER_INFO, user);
	            initResource();
	     }
		String qr_code = request.getParameter("qr_code");
	
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("id", qr_code);
		qp.setQueryParam("project/QryTestByQrId");//查询主体id
		List<Map<String,Object>> projectList = formService.queryBySqlId(qp);
		model.addAttribute("ctx", request.getContextPath());
		model.addAttribute("data",projectList.size()>0?projectList.get(0):"projectName=未查询到相关记录");
		model.addAttribute("size", projectList.size());
		return "h5/Identity";
	}
	  /**
     * token拦截器需要将所有可访问资源初始在内存中
     * ShiroFilerChainManager中的查询不正确。
     * 暂用此方式代替
     */
    public void initResource() {
        //load accessible url
        QueryPage queryPage = new QueryPage();
        queryPage.setQueryParam("ACLU");//所有当前用户可访问的url资源：满足授权的和不需授权的url
        List<Map<String, Object>> listUrl = formService.queryBySqlId(queryPage);
//    	//不限定权限的资源
        SessionUtils.setAccessibleUrl(listUrl);
        //load accessible control list
        try {
            queryPage = new QueryPage();
            queryPage.setQueryParam("ACL");
            List<Map<String, Object>> controlList = formService.queryBySqlId(queryPage);
            if (controlList != null) {
                Map<String, String> userControlMap = new HashMap<>();
                controlList.forEach(e -> userControlMap.put(ObjectUtils.toString(e.get("url")), ObjectUtils.toString(e.get("id"))));//"ui.panel.controlName"
                SessionUtils.setACL(userControlMap);
                FormLogger.logBegin(String.format("装载用户可访问组件{%s}个", controlList.size()));
            }
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.ACL_LOAD_FAILED, e.getMessage());
        }
    }
	
}
