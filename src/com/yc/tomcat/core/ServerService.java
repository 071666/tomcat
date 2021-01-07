package com.yc.tomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;

import com.yc.web.core.HttpServletRequest;
import com.yc.web.core.HttpServletResponse;
import com.yc.web.core.Servlet;
import com.yc.web.core.ServletRequest;
import com.yc.web.core.ServletResponse;
import com.yc.wowo.util.StringUtil;

/**
 * 负责处理请求信息的类
 * company 源辰信息
 * @author Admin
 * @data 2021年1月2日
 * Email 1171595422@qq.com
 */
public class ServerService implements Runnable{
	private Socket sk;
	private InputStream is;
	private OutputStream os;
	
	public ServerService(Socket sk) {
		this.sk = sk;
	}

	@Override
	public void run() {
		try {
			this.is = sk.getInputStream();
			this.os = sk.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//解析请求头信息
		ServletRequest request = new HttpServletRequest(is);
		
		String url = request.getUrl();
		String temp = url.substring(1);//去掉最前面的 /
		String projectName = temp.contains("/") ? temp.substring(0, url.indexOf("/")) : temp;
		try {
			url = URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		ServletResponse response = new HttpServletResponse(os, projectName);
		
		//是不是动态访问
		String clazz = ParseUrlPattern.getClass(url);
		if(StringUtil.checkNull(clazz)) {
			response.sendRedirect(url);
			return;
		}
		URLClassLoader loader = null;
		URL classPath = null;
		
		try {
			classPath = new URL("file", null, ConstantInfo.BASE_PATH + "\\" + projectName + "\\bin");
			
			//创建类加载器
			loader = new URLClassLoader(new URL[] {classPath});
			Class<?> cls = loader.loadClass(clazz);//到指定路径下加载指定的类
			
			Servlet servlet = (Servlet) cls.newInstance();
			servlet.service(request, response);
		} catch (Exception e) {
			send500(e);
			e.printStackTrace();
		}
	}

	private void send500(Exception e) {
		try {
			String responseHeader = "HTTP/1.1 500 Error\r\n\r\n" + e.getMessage();
			os.write(responseHeader.getBytes());
			os.flush();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			if(os != null) {
				try {
					os.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
