package com.yc.web.core;

import com.yc.tomcat.core.ConstantInfo;

public class HttpServlet implements Servlet {

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) {
		switch(request.getMethod()) {
		case ConstantInfo.REQUEST_METHOD_GET:doGet(request, response);
		case ConstantInfo.REQUEST_METHOD_POST:doPost(request, response);
		}
	}

	@Override
	public void doGet(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doPost(ServletRequest request, ServletResponse response) {
		// TODO Auto-generated method stub
		
	}

}
