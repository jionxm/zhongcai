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

@Service("insertQRPlugin")
public class insertQRPlugin implements ILogicPlugin {	
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
		
		
    	/***
    	 * 生成二维码对应excel文件
    	**/
    	
		QueryPage qp1 = new QueryPage();
		qp1.setParamsByMap(map);
		qp1.getData().put("id", formModel.getData().get("ctlId"));
		String fileName = formModel.getData().get("ctlTName1").toString();
		qp1.setQueryParam("project/QryQrById");//查询qr
		List<Map<String,Object>> qrList = formService.queryBySqlId(qp1);
		String date = DateUtils.getDateToStr("YYYYMMdd", new Date());
		String separator = File.separator;
		if(!rootPath.endsWith("/")&&!rootPath.endsWith("\\")){
			rootPath = rootPath + separator;
		}
		String filePath = rootPath+separator+"private"+separator+"export"+separator+date +separator;
		
		String qrfilePath = filePath + UUID.randomUUID().toString().replaceAll("-", "") + separator;
		
		String excelPath = filePath + "excel" + separator;
		Map<String,String> tempMap = null;
		
		List<List<Map<String,String>>> resultlist = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String,String>> tempList = null;
		String temp = "";
		if(qrList.size()>0){
			for(int i = 0 ;i<qrList.size(); i++){
				try {
					Map qrMap = qrList.get(i);
					String QRCode = qrMap.get("QRCode").toString();
					
					tempMap = new HashMap<String,String>();
					String testerName = qrMap.get("testerName").toString();
					if("".equals(temp)||!temp.equals(testerName)){
						if(tempList!=null&&tempList.size()>0){
						  resultlist.add(tempList);
						}
						tempList = new ArrayList<Map<String,String>>();
					}
					temp = testerName;
					ImageUtil.Base64ToImage(qrCodeService.getBase64Code(QRCode, 200, 200), qrfilePath,QRCode+".png");
					tempMap.put("qrImagePath", qrfilePath+QRCode+".png");
					tempMap.put("testerName", testerName);
					tempList.add(tempMap);
					if(i==qrList.size()-1){
						resultlist.add(tempList);
					}
				} catch (IOException e) {
					System.err.println(e.getMessage());
				}
			}
			
		}
		
		ExcelExportUtil excelExportUtil = new ExcelExportUtil();
		
		
		try {
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			boolean flag = excelExportUtil.exportQrImage(resultlist,excelPath+uuid.substring(0, 2)+separator,fileName);
			//创建成功后删除无用数据并且存入附件表中
			if(flag){
				Map sqlMap = new HashMap();
		    	sqlMap.put("uuid", uuid);
		    	sqlMap.put("filename", fileName);
		    	sqlMap.put("file_type", "xlsx");
		    	sqlMap.put("length", 0);
		    	sqlMap.put("storage_type","local");
		    	sqlMap.put("access_type","private");
		    	sqlMap.put("source","export");
		    	sqlMap.put("path",excelPath+uuid.substring(0, 2)+separator);
		    	sqlMap.put("create_by",emp);
		    	sqlMap.put("create_time",DateUtils.getDateToStr("YYYY-MM-dd HH:mm:ss", new Date()));
		    	//log.info("sqlMap:"+sqlMap);	    		    	
		    	CustomFormModel csmd = new CustomFormModel("","",sqlMap);
		    	csmd.setSqlId("project/InsertQrFile");
		    	formMapper.saveCustom(csmd);
		    	File qrFile = new File(qrfilePath); 
		    	deleteDir(qrFile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
		
	 private static boolean deleteDir(File dir) {
	        if (dir.isDirectory()) {
	            String[] children = dir.list();
	            for (int i=0; i<children.length; i++) {
	                boolean success = deleteDir(new File(dir, children[i]));
	                if (!success) {
	                    return false;
	                }
	            }
	        }
	        // 目录此时为空，可以删除
	        return dir.delete();
	    }
	
}

