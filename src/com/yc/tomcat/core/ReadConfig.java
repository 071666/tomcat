package com.yc.tomcat.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置文件类
 * company 源辰信息
 * @author Admin
 * @data 2021年1月2日
 * Email 1171595422@qq.com
 */
public class ReadConfig extends Properties {
	private static final long serialVersionUID = -613052409643698399L;
	private static ReadConfig instance = new ReadConfig();
	
	private ReadConfig() {
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream("web.properties")){
			load(is);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ReadConfig getInstance() {
		return instance;
	}
}
