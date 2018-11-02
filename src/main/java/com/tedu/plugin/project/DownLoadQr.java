package com.tedu.plugin.project;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.fileupload.FileUpload;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sdicons.json.validator.impl.predicates.Array;
import com.tedu.base.common.page.QueryPage;
import com.tedu.base.common.utils.DateUtils;
import com.tedu.base.common.utils.FileUtil;
import com.tedu.base.engine.aspect.ILogicPlugin;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.FormEngineRequest;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormService;
import com.tedu.base.file.util.io.FilePathUtil;
import com.tedu.base.task.SpringUtils;
import com.tedu.plugin.project.service.QRCodeService;
import com.tedu.plugin.project.util.ExcelExportUtil;
import com.tedu.plugin.project.util.ImageUtil;
@Service("DownLoadQr")
public class DownLoadQr implements ILogicPlugin {
	@Value("${file.upload.path}")
	private String rootPath;
	@Resource
	FormService formService;
	FormMapper formMapper = SpringUtils.getBean("simpleDao");
	public final Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	QRCodeService qrCodeService;
	@Override
	public Object doBefore(FormEngineRequest requestObj, FormModel formModel) {
		Map<String,Object> map =  formModel.getData();
		QueryPage qp = new QueryPage();
		qp.setParamsByMap(map);
		qp.getData().put("id", formModel.getData().get("ctlId"));
		qp.setQueryParam("project/QryQrById");//查询qr
		List<Map<String,Object>> qrList = formService.queryBySqlId(qp);
		String fileType = "QR";
		String date = DateUtils.getDateToStr("YYYYMMdd", new Date());
		String separator = File.separator;
		if(!rootPath.endsWith("/")&&!rootPath.endsWith("\\")){
			rootPath = rootPath + separator;
		}
		String filePath = rootPath+separator+"private"+separator+"export"+date +separator+ fileType+separator;
		Map<String,String> tempMap = null;
		
		List<List<Map<String,String>>> resultlist = new ArrayList<List<Map<String,String>>>();
		
		List<Map<String,String>> tempList = null;//new ArrayList<Map<String,String>>();
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
					ImageUtil.Base64ToImage(qrCodeService.getBase64Code(QRCode, 200, 200), filePath,QRCode+".png");
					tempMap.put("qrImagePath", filePath+QRCode+".png");
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
			excelExportUtil.exportQrImage(resultlist, "E:\\Excel","");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return formModel;
	}
		
		
	
	

	@Override
	public void doAfter(FormEngineRequest requestObj, FormModel formModel, Object beforeResult,
			FormEngineResponse responseObj) {
		
	}

	

}
