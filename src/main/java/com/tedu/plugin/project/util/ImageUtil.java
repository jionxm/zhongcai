package com.tedu.plugin.project.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class ImageUtil {

	/**
	 * 本地图片转换成base64字符串
	 * 
	 * @param imgFile
	 *            图片本地路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:40:46
	 */
	public static String ImageToBase64ByLocal(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理

		InputStream in = null;
		byte[] data = null;

		// 读取图片字节数组
		try {
			in = new FileInputStream(imgFile);

			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();

		return encoder.encode(data);// 返回Base64编码过的字节数组字符串
	}

	/**
	 * 在线图片转换成base64字符串
	 * 
	 * @param imgURL
	 *            图片线上路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @dateTime 2018-02-23 14:43:18
	 */
	public static String ImageToBase64ByOnline(String imgURL) {
		ByteArrayOutputStream data = new ByteArrayOutputStream();
		try {
			// 创建URL
			URL url = new URL(imgURL);
			byte[] by = new byte[1024];
			// 创建链接
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			InputStream is = conn.getInputStream();
			// 将内容读取内存中
			int len = -1;
			while ((len = is.read(by)) != -1) {
				data.write(by, 0, len);
			}
			// 关闭流
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data.toByteArray());
	}

	/**
	 * base64字符串转换成图片
	 * 
	 * @param imgStr
	 *            base64字符串
	 * @param imgFilePath
	 *            图片存放路径
	 * @return
	 *
	 * @author ZHANGJL
	 * @throws IOException
	 * @dateTime 2018-02-23 14:42:17
	 */
	public static boolean Base64ToImage(String imgStr, String imgFilePath, String fileName) throws IOException { // 对字节数组字符串进行Base64解码并生成图片

		if (imgStr == null || "".equals(imgStr)) // 图像数据为空
			return false;

		BASE64Decoder decoder = new BASE64Decoder();
		// Base64解码
		byte[] b = decoder.decodeBuffer(imgStr);
		for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {// 调整异常数据
				b[i] += 256;
			}
		}
		File file = new File(imgFilePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		File newFile = new File(imgFilePath + fileName);
		OutputStream out = new FileOutputStream(newFile);
		out.write(b);
		out.flush();
		out.close();

		return true;

	}

}
