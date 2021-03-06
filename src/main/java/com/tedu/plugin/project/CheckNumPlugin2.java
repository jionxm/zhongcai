package com.tedu.plugin.project;


import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.tedu.base.common.error.ErrorCode;
import com.tedu.base.common.error.ValidException;
import com.tedu.base.engine.aspect.ILogicPlugin;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.CustomFormModel;
import com.tedu.base.engine.model.FormEngineRequest;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.task.SpringUtils;

@Service("CheckNumPlugin2")
public class CheckNumPlugin2 implements ILogicPlugin {
	@Resource
	FormService formService;
	FormMapper formMapper = SpringUtils.getBean("simpleDao");
	public final Logger log = Logger.getLogger(this.getClass());

	@Override
	public Object doBefore(FormEngineRequest requestObj, FormModel formModel) {
		//主体权重校验(0-100%)
		String strDimension = formModel.getData().get("ctlWeight").toString();
		if (StringUtils.isNotEmpty(strDimension)){
			//判断是否为0到100的数
			Pattern pattern = Pattern.compile("^(((\\d|[1-9]\\d)(\\.\\d{1,2})?)|100|100.0|100.00)$");
			Matcher num = pattern.matcher(strDimension);
			Boolean flag = num.matches();
			if (!flag) {
				throw new ValidException(ErrorCode.INVALIDATE_FORM_DATA, "输入提示：默认已用%", "请输入0到100的数");
			}
		}
		return null;
	}
		
		
	
	

	@Override
	public void doAfter(FormEngineRequest requestObj, FormModel formModel, Object beforeResult,
			FormEngineResponse responseObj) {
		
	}

	

}
