package com.tedu.plugin.project.controller;


import java.io.File;
import java.io.IOException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;

import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.DateUtils;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.aspect.ILogicPlugin;
import com.tedu.base.engine.aspect.ILogicReviser;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.CustomFormModel;
import com.tedu.base.engine.model.FormEngineRequest;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.file.util.io.file;
import com.tedu.base.task.SpringUtils;
import com.tedu.plugin.project.service.QRCodeService;
import com.tedu.plugin.project.util.ExcelExportUtil;
import com.tedu.plugin.project.util.ImageUtil;

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
	public void export(HttpServletRequest request,HttpServletResponse response){
		System.out.println(request.getParameter("id"));
	}

}

