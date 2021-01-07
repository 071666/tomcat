package com.yc.wowo.util;

/**
 * 判断字符串是否为空
 * company 源辰信息
 * @author Admin
 * @data 2020年10月25日
 * Email 1171595422@qq.com
 */
public class StringUtil {
	/**
	 * 空判断
	 * @param strs
	 * @return
	 */
	public static boolean checkNull(String ... strs) {
		if(strs == null || strs.length <= 0) {
			return true;
		}
		for(String str : strs) {
			if(str == null || "".equals(str)) {
				return true;
			}
		}
		return false;
	}
}
