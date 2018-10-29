package com.tedu.plugin.project;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.SessionUtils;
import com.tedu.base.engine.aspect.ILogicPlugin;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.CustomFormModel;
import com.tedu.base.engine.model.FormEngineRequest;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.task.SpringUtils;
import com.tedu.plugin.project.service.QRCodeService;

@Service("insertQRPlugin")
public class insertQRPlugin implements ILogicPlugin {	
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
		Long emp = SessionUtils.getUserInfo().getEmpId();
		Map<String,Object> map =  formModel.getData();
		//log.info("map"+map);
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("projectId", formModel.getData().get("id"));
		qp.setQueryParam("project/QryTesterId");//查询主体id
		List<Map<String,Object>> testerlist = formService.queryBySqlId(qp);
		//log.info("testerlist"+testerlist);
		
		
		qp.setQueryParam("project/QryProGroupId");//查询对象id
		List<Map<String,Object>> grouplist = formService.queryBySqlId(qp);
		//log.info("grouplist"+grouplist);
		for(Map testermap:testerlist){
			String testerId =  testermap.get("id").toString();
			
	    	for(Map groupmap:grouplist){
	    		String progroupId =  groupmap.get("id").toString();
	    		//log.info("testerId"+testerId);
	    		//log.info("progroupId"+progroupId);
	    		qp.getData().put("testerId", testerId);
	    		qp.getData().put("progroupId", progroupId);
	    		qp.setQueryParam("project/QryTesterNumber");//查询主体人数
	    		List<Map<String,Object>> numberlist = formService.queryBySqlId(qp);
	    		//log.info("numberlist"+numberlist);
	    		for(int i=0;i<numberlist.size();i++){
	    			Object number =0;
	    			Map<String,Object> mapnumber = numberlist.get(i);
	    			//log.info("mapnumber"+mapnumber);
	    			for(String key : mapnumber.keySet()){
	    				number = mapnumber.get(key);
	    				  }
	    			//log.info("number:"+number);
	    			Integer num = Integer.parseInt(String.valueOf(number));
	    			//log.info("num:"+num);
	    			for(int j=0;j<num;j++){
	    				String fileUUID = UUID.randomUUID().toString().replaceAll("-", "");
	    				Map sqlMap = new HashMap();
	    		    	sqlMap.put("projectId", formModel.getData("id"));
	    		    	sqlMap.put("QRCode", fileUUID);
	    		    	sqlMap.put("proGroupId", progroupId);
	    		    	sqlMap.put("testerId", testerId);
	    		    	sqlMap.put("empId",emp);
	    		    	//log.info("sqlMap:"+sqlMap);	    		    	
	    		    	CustomFormModel csmd = new CustomFormModel("","",sqlMap);
	    		    	csmd.setSqlId("project/InsertQR");
	    		    	formMapper.saveCustom(csmd);
	    			}
	    			
	    			
	    		}
	    		// 生成二维码信息
	    		qp.getData().put("project_Id", formModel.getData().get("id"));
	    		qp.setQueryParam("project/QryQrByProjectId");// 查询主体id
	    		List<Map<String, Object>> qr_List = formService.queryBySqlId(qp);
	    		for(Map<String,Object> qr_Map:qr_List){
	    			//获取二维码ID
	    			String qrCode = qr_Map.get("qrCode").toString();
	    			
	    			//获取qr_id的base64值
	    			String file_id = (String) qrCodeService.getCodeUrl(qrCode);
	    			Map sqlMap = new HashMap();
	    			sqlMap.put("id",  qr_Map.get("id").toString());
	    			sqlMap.put("file_id",file_id);
	    			CustomFormModel customFormModel = new CustomFormModel("", "", sqlMap);
	    			customFormModel.setSqlId("project/UpdateQR");
	    			formMapper.saveCustom(customFormModel);
	    		}
	    	}
		}
		
		
		/*QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("trainFileId", formModel.getData().get("trainFileId"));
		qp.setQueryParam("trainSystem/QryFile");
	    List<Map<String,Object>> list = formService.queryBySqlId(qp);
	    for(Map nmap:list){
	    	String id =  nmap.get("id").toString();
	    	Map sqlMap = new HashMap();
	    	sqlMap.put("id", id);
	    	sqlMap.put("traineeId", formModel.getData("ctlTraineeId"));
	    	CustomFormModel csmd = new CustomFormModel("","",sqlMap);
	    	csmd.setSqlId("trainSystem/InsertCourse");
	    	formMapper.saveCustom(csmd);
	    	
	    	
	}*/
	    }

	
}

