package com.tedu.plugin.project.util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPicture;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.jam.mutable.MPackage;

import jxl.write.WriteException;

public class ExcelExportUtil {
	/**
	 * 导出图片
	 * 
	 * @param model
	 * @param query
	 * @param request
	 * @param workbook
	 * @param response
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalStateException
	 * @throws ServletException
	 * @throws IOException
	 * @throws WriteException
	 * @throws SQLException
	 */

	public final Logger logger = Logger.getLogger(this.getClass());

	public  boolean exportQrImage(List<List<List<Map<String,String>>>> list,String outPath,String fileName) throws IOException {
		boolean flag = true;
		long startTime=System.currentTimeMillis();
		XSSFWorkbook input_work = new XSSFWorkbook();
		
		// 设置样式
		XSSFCellStyle textStyle10 = input_work.createCellStyle();
		textStyle10.setAlignment(XSSFCellStyle.ALIGN_CENTER); // 居中
		// 设置垂直居中
		textStyle10.setAlignment(HorizontalAlignment.CENTER);
		textStyle10.setVerticalAlignment((short) 1);
		// 设置细边框
		textStyle10.setWrapText(true);
		XSSFFont fontText2 = input_work.createFont();
		// 字体号码
		fontText2.setFontHeightInPoints((short) 12);
		// 字体名称
		fontText2.setFontName("微软雅黑");
		textStyle10.setFont(fontText2);
		// 设置样式
		XSSFCellStyle textStyle = input_work.createCellStyle();
		textStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);// 左右居中
		textStyle10.setAlignment(HorizontalAlignment.CENTER);// 上下居中
		// 设置细边框
		textStyle.setWrapText(true);
		XSSFFont fontText = input_work.createFont();
		// 字体名称
		fontText.setFontName("微软黑体");
		// 字体号码
		fontText.setFontHeightInPoints((short) 18);
		fontText.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 加粗
		textStyle.setFont(fontText);
	
		//第一个list是行
		for(int a = 0 ;a<list.size();a++){
			
			XSSFRow input_row1 = null;
			XSSFRow input_row2 = null;
			XSSFRow input_row3 = null;
			XSSFRow input_row4 = null;
			List<List<Map<String,String>>> lists = list.get(a);
			String groupName = lists.get(0).get(0).get("groupName").toString();
			//创建所对应的组的sheet
			XSSFSheet inpub_sheet = input_work.createSheet(groupName);
			for(int i=0;i<lists.size();i++){

				List<Map<String,String>> mapList =  lists.get(i);
				//标题行
				input_row1 = inpub_sheet.createRow(i*4);
				inpub_sheet.addMergedRegion(new CellRangeAddress(i*4, i*4, 0, mapList.size()-1));
				input_row1.setHeight(Short.parseShort(200*3+""));
				Cell titleCell= input_row1.createCell(0);
				titleCell.setCellStyle(textStyle);
				
				titleCell.setCellValue(mapList.get(0).get("testerName"));
				input_row2 = inpub_sheet.createRow(i*4+1);
				input_row2.setHeight(Short.parseShort(200*16+""));
				input_row3 = inpub_sheet.createRow(i*4+2);
				input_row4 = inpub_sheet.createRow(i*4+3);
				//获取的是列
				for(int j = 0; j< mapList.size(); j++){
					inpub_sheet.setColumnWidth(j, 256*30);
					Map<String, String> map = mapList.get(j);
					String imagePath = map.get("qrImagePath").toString();
					String testerName = map.get("testerName").toString();
					//插入图片
					BufferedImage  inStream = ImageIO.read(new File(imagePath)); // 得到流对象
					ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();  
					ImageIO.write(inStream, "png", byteArrayOut);
					int pictureIdx = input_work.addPicture(byteArrayOut.toByteArray(), input_work.PICTURE_TYPE_PNG);// 向excel中插入图片
					XSSFDrawing drawing = inpub_sheet.createDrawingPatriarch(); // 创建绘图对象
					// XSSFClientAnchor的参数说明：
					// 参数 说明
					// dx1 第1个单元格中x轴的偏移量
					// dy1 第1个单元格中y轴的偏移量
					// dx2 第2个单元格中x轴的偏移量
					// dy2 第2个单元格中y轴的偏移量
					// col1 第1个单元格的列号
					// row1 第1个单元格的行号
					// col2 第2个单元格的列号
					// row2 第2个单元格的行号
					XSSFClientAnchor anchor = new XSSFClientAnchor(1000, 0, 100, 0, (short)j, i*4+1, (short) j,i*4+1);// 定位图片的位置
					XSSFPicture pict = drawing.createPicture(anchor, pictureIdx);
					pict.resize();
					
					//写入主体身份名称
					Cell testerNameCell= input_row3.createCell(j);
					testerNameCell.setCellStyle(textStyle10);
					testerNameCell.setCellValue(mapList.get(0).get("testerName"));
				}
			
			}
		}
		
		try {
			File file = new File(outPath ); // 指定存放目录
			if(!file.exists()){
				file.mkdirs();
			}
			FileOutputStream fout = new FileOutputStream(new File(outPath+fileName+".xlsx"));
			input_work.write(fout);
			fout.flush();
			fout.close();
			long endTime = System.currentTimeMillis(); // 获取结束时间
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		
		return flag;
	}
}