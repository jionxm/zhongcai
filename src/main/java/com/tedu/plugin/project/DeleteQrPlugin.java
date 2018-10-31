package com.tedu.plugin.project;


import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tedu.base.engine.aspect.ILogicPlugin;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.CustomFormModel;
import com.tedu.base.engine.model.FormEngineRequest;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.task.SpringUtils;

@Service("DeleteQrPlugin")
public class DeleteQrPlugin implements ILogicPlugin {
	@Resource
	FormService formService;
	FormMapper formMapper = SpringUtils.getBean("simpleDao");
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public Object doBefore(FormEngineRequest requestObj, FormModel formModel) {
		Map<String,Object> map =  formModel.getData();
		log.info(map);
		Map sqlMap = new HashMap();
		sqlMap.put("id", map.get("id"));
		//删除测评结果
		log.info("开始删除测评结果");
		CustomFormModel csmd = new CustomFormModel("","",sqlMap);
		csmd.setSqlId("project/DelResultByProjId");
		log.info("删除测评结果完毕");
		//删除测评主体人数
		log.info("开始测评主体人数");
    	CustomFormModel csmd1 = new CustomFormModel("","",sqlMap);
    	csmd1.setSqlId("project/DelNumByProjId");
    	log.info("删除测评主体人数");
    	//删除二维码
    	log.info("删除二维码");
    	CustomFormModel csmd2 = new CustomFormModel("","",sqlMap);
    	csmd2.setSqlId("project/DelQrByProjectId");
    	log.info("删除二维码");
    	//删除项目分组
    	log.info("开始删除项目分组");
    	CustomFormModel csmd3 = new CustomFormModel("","",sqlMap);
    	csmd3.setSqlId("project/DelPGroupByProjId");
    	log.info("删除项目分组");
    	
    	
    	formMapper.saveCustom(csmd);
    	formMapper.saveCustom(csmd1);
    	formMapper.saveCustom(csmd2);
    	formMapper.saveCustom(csmd3);
		return formModel;
	}
		
		
	
	

	@Override
	public void doAfter(FormEngineRequest requestObj, FormModel formModel, Object beforeResult,
			FormEngineResponse responseObj) {
		
	}

	

}
