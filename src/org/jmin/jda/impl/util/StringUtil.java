/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.util;

import java.util.StringTokenizer;

/**
 * 字符川辅助类
 * 
 * @author Chris
 */
public class StringUtil {

	/**
	 * 判断字符是否为空
	 */
	public static boolean isNull(String value) {
		return value == null || value.trim().length()==0;
	}

	/**
	 * 统计某个子串出现的次数
	 */
	public static int getWordCount(String src,String sub) {
		if(src==null || sub== null){
			return -1;
		}else{	
			int count=0,index=0,pos =0;
			int srcLength = src.length();
			int subLength = sub.length();
			while(index < srcLength) {
				pos =src.indexOf(sub,index);
				if (pos != -1) {
					count++;
					index = pos;
					index += subLength;
				} else {
					break;
				}
			}
			return count;
		}
	}
	
	/**
	 * 将字符串数组衔接起来得到一个新的字符串
	 */
	public static String concat(String[] array,String concat) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buf.append(array[i]);
			if(i < array.length - 1)
				buf.append(concat);
		}
		return buf.toString();
	}

	/**
	 * 将字符串分割成多个子串
	 */
	public static String[] split(String source,String sub) {
		int i=0;
		StringTokenizer token = new StringTokenizer(source,sub);
		String[] subStrings= new String[token.countTokens()];
		while(token.hasMoreElements()){
			subStrings[i++] =(String)token.nextElement();
		}
		return subStrings;
	}
 
	/**
	 * 替换指定位置的字符串
	 */
	public static String replace(String str,int start,int end,String subNew) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(str.substring(0,start));
		sbf.append(subNew);
		sbf.append(str.substring(end + 1));
		return sbf.toString();
	}

	/**
	 * 字符串替换
	 */
	public static String replace(String str,String subOld,String subNew) {
		int found =-1,start=0;
		final int len = subOld.length();
		StringBuffer sb = new StringBuffer();
		while((found = str.indexOf(subOld,start))>-1) {
			sb.append(str.substring(start, found));
			sb.append(subNew);
			start = found + len;
		}
		sb.append(str.substring(start));
		return sb.toString();
	}
}

