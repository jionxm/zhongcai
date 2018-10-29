package com.tedu.plugin.project.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.tedu.base.engine.aspect.ILogicPlugin;
import com.tedu.base.engine.dao.FormMapper;
import com.tedu.base.engine.model.FormEngineRequest;
import com.tedu.base.engine.model.FormEngineResponse;
import com.tedu.base.engine.model.FormModel;
import com.tedu.base.engine.service.FormLogService;
import com.tedu.base.engine.util.FormLogger;

@Service("QRCodeService")
public class QRCodeService implements ILogicPlugin{

	@Resource
	FormLogService formLogService;
	@Resource
	private FormMapper formMapper;	
	
	private static final String XML = "frmResultTestDetail";
	
	@Value("${base.website}")
	public String weburl ;
	
	@Override
	public Object doBefore(FormEngineRequest requestObj, FormModel formModel) {
		return null;	
	}
	
	@Override
	public void doAfter(FormEngineRequest requestObj, FormModel formModel, Object beforeResult,FormEngineResponse responseObj) {
		
		//模板源数据
		String ctlId = requestObj.getData().get("ctlId").toString();

		//将二维码生成在界面
		Map<String, Object> map = formModel.getData();
		map.put("QRCode", getCodeUrl(ctlId));
		responseObj.setData(map);
	}
	


	/**
	 * 拼接前端页面串
	 * @param ctlId			员工id
	 * @return codeUrl
	 */
	public Object getCodeUrl(String ctlId) {
		//员工信息模板
		String  url = getUrl(ctlId);
       
        //二维码生成算法
        String  codeUrl = "";
		try {			
			codeUrl = "<img width='100' height='100' src='data:image/png;base64," 
		              +getCodeByUrl(createImage(url,300,300)) 
		              + "' />";
		}catch(Exception e){
			FormLogger.logFlow(e.getMessage(), FormLogger.LOG_TYPE_ERROR);
		} 
		return codeUrl;
	}
	
	public String getBase64Code(String ctlId,int w,int h) {
		//员工信息模板
		String  url = getUrl(ctlId);
        //二维码生成算法
        String  base64 = "";
		try {			
			base64 = getCodeByUrl(createImage(url,w,h)) ;
		}catch(Exception e){
			FormLogger.logFlow(e.getMessage(), FormLogger.LOG_TYPE_ERROR);
		} 
		return base64;
	}
	
	public String getUrl(String ctlId){
		return weburl+"/qr/view?id="+ctlId;
	}
	
	/**
	 * 二维码图片的生成
	 * @param content			链接
	 * @param qrcode_width		二维码宽
	 * @param qrcode_height		二维码高
	 * @return
	 * @throws Exception
	 */
    public static BufferedImage createImage(String content, int qrcode_width, int qrcode_height) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content,
                BarcodeFormat.QR_CODE, qrcode_width, qrcode_height, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000
                        : 0xFFFFFFFF);
            }
        }
        return image;
    }
    
	/**
	 * 二维码照片转换为base64码
	 * @throws IOException 
	 */
    public String getCodeByUrl(BufferedImage image) throws IOException {  	
    	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    	//输出二维码图片流
        ImageIO.write(image, "png",outputStream);    	       
        return Base64.encodeBase64String(outputStream.toByteArray());
    }
    
	
}
