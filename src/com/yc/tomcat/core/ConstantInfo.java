package com.yc.tomcat.core;

public interface ConstantInfo {

	String BASE_PATH = ReadConfig.getInstance().getProperty("path");
	String DEFAULT_RESOURCE = ReadConfig.getInstance().getProperty("default");//获取默认页面
	String REQUEST_METHOD_GET = "GET";
	String REQUEST_METHOD_POST = "POST";
}
