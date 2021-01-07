package com.yc.tomcat.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ParseWebXml {
	private static Map<String, String> map = new HashMap<String, String>();
	
	public ParseWebXml() {
		init();
	}

	/**
	 * 初始化
	 */
	@SuppressWarnings("unchecked")
	private void init() {
		SAXReader reader = new SAXReader();
		Document doc = null;
		
		try {
			doc = reader.read(this.getClass().getClassLoader().getResourceAsStream("web.xml"));
			List<Element> mines = doc.selectNodes("//mime-mapping");
			for(Element el : mines) {
				map.put(el.selectSingleNode("extension").getText().trim(), el.selectSingleNode("mime-type").getText().trim());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, String> getMap(){
		return map;
	}
	
	/**
	 * 根据后缀返回对应的类型
	 * @param key
	 * @return
	 */
	public static String getContentType(String key) {
		return map.getOrDefault(key, null);
	}
}
