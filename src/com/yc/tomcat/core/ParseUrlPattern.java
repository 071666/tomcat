package com.yc.tomcat.core;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseUrlPattern {
	private String basePath = ConstantInfo.BASE_PATH;
	
	private static Map<String, String> urlPattern = new HashMap<String, String>();
	
	public ParseUrlPattern() {
		parse();
		urlPattern.forEach((key, val) -> {
			System.out.println(key + ":" + val);
		});
	}

	private void parse() {
		File[] files = new File(basePath).listFiles();//获取项目目录即webapps下的所有项目
		
		if(files == null || files.length <= 0) {//说明没有部署项目到服务器
			return;
		}
		String projectName = null;//存放项目名，即当前文件夹的名字
		File webFile = null;

		for(File fl : files) {
			if(!fl.isDirectory()) {
				continue;
			}
			
			projectName = fl.getName();
			
			//项目里面有没有WEB-INF/web.xml
			webFile = new File(fl, "WEB-INF/web.xml");
			if(!webFile.exists() || !webFile.isFile()) {//如果没有或者不是一个文件，则说明没有配置这个文件，不管
				continue;
			}
			parseXml(projectName, webFile);
		}
	}

	@SuppressWarnings("unchecked")
	private void parseXml(String projectName, File webFile) {
		SAXReader reader = new SAXReader();
		Document doc =  null;
		
		try {
			doc = reader.read(webFile);
			List<Element> servletList = doc.selectNodes("//servlet");
			
			if(servletList == null || servletList.isEmpty()) {
				return;
			}
			for(Element el : servletList) {
				urlPattern.put("/" + projectName + el.selectSingleNode("url-pattern").getText().trim(), el.selectSingleNode("servlet-class").getText().trim());//  /snacknect/login  /login
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public static String getClass(String url) {
		return urlPattern.getOrDefault(url, null);
	}
}
