package com.tedu.plugin.project;


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


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.DateUtils;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.aspect.ILogicPlugin;
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

@Service("insertRecommendQRPlugin")
public class insertRecommendQRPlugin implements ILogicPlugin {	
	@Value("${file.upload.path}")
	private String rootPath;
	@Resource
	FormService formService;
	FormMapper formMapper = SpringUtils.getBean("simpleDao");
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public Object doBefore(FormEngineRequest requestObj, FormModel formModel) {		
		return formModel;
	}
		
	@Resource
	QRCodeService qrCodeService;
	

	@Override
	public void doAfter(FormEngineRequest requestObj, FormModel formModel, Object beforeResult,
			FormEngineResponse responseObj) {
		log.info("这里是insertrecommendQRPlugin");
		Long emp = SessionUtils.getUserInfo().getEmpId();
		Map<String,Object> map =  formModel.getData();
		
		log.info("map"+map);
		log.info("recId"+map.get("ctlId"));
		
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("recId", formModel.getData().get("ctlId"));
		qp.setQueryParam("recommend/QryPartId");//查询参会人员id
		List<Map<String,Object>> part = formService.queryBySqlId(qp);		
		log.info("参会人员id----:"+part);
		for(Map partmap:part){
			String fileUUID = UUID.randomUUID().toString().replaceAll("-", "");
			Map sqlMap = new HashMap();
	    	sqlMap.put("recId", map.get("ctlId"));
	    	sqlMap.put("qrCode", fileUUID);
	    	sqlMap.put("partId", part.get(0).get("id"));
	    	sqlMap.put("empId",emp);	    		    	
	    	CustomFormModel csmd = new CustomFormModel("","",sqlMap);
	    	csmd.setSqlId("recommend/InsertRecQR");
	    	formMapper.saveCustom(csmd);
		}
		
		
	}
	
}

