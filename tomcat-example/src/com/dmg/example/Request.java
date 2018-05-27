package com.dmg.example;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author tanzhe
 *
 */
public class Request {
	/**
	 *  从socket中获取输入流
	 */
	private InputStream in;
	/**
	 *  存储uri
	 */
	private String uri;

	public Request(InputStream in) {
		this.in = in;
	}

	/**
	 *  解析http请求中的原始数据
	 *  负责：读取字节流转换为String
	 */
	public void parse() {
		// 1.读取字节流到StringBuffer中
		StringBuffer request = new StringBuffer(2048);
		int i = 0;
		byte[] buffer = new byte[2048]; 
		try {
			i = in.read(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			i = -1;
		}
		for (int j = 0; j < i; j++) {
			request.append((char) buffer[j]);
		}
		System.out.println(request.toString());
		// 2.解析uri
		uri = parseUri(request.toString());
	}

	/**
	 * 解析http请求的uri
	 * 搜索请求行中的第一个空格和第二个空格，中间的内容即为uri
	 * 负责：解析String中的uri信息	
	 *
	 * @param requestString
	 * @return
	 */
	private String parseUri(String requestString) {
		// 定义两个变量表示第一个空格的索引和第二个空格的索引
		int index1, index2;
		index1 = requestString.indexOf(" ");
		if (index1 != -1) {
			index2 = requestString.indexOf(" ", index1 + 1);
			if (index2 > index1) {
				return requestString.substring(index1 + 1, index2);
			}
		}
		return null;
	}

	/**
	 * 返回http请求的uri
	 * 
	 * @return
	 */
	public String getUri() {
		return uri;
	}
}
