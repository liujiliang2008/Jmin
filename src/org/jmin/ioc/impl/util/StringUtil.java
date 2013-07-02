/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.util;

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
   * 查找某个字符串的位置
   */
	public static int indexOf(String src,String sub){
	 return indexOf(src,sub,0);
	}
	
  /**
   * 查找某个字符串的位置
   */
	public static int indexOf(String src,String sub,int begin){
	 return indexOf(src,sub,begin,false);
	}
	
  /**
   * 查找某个字符串的位置
   * 
   * ingore:是否忽略大小写
   */
	public static int indexOf(String src,String sub,boolean ingore){
		return indexOf(src,sub,0,ingore);
	}
	
  /**
   * 查找某个字符串的位置
   */
	public static int indexOf(String src,String sub,int begin,boolean ingore){
		if(ingore){
			src = src.toLowerCase();
			sub = sub.toLowerCase();
		}
		return src.indexOf(sub,begin);
	}

	/**
	 * 判断某字符串包含某个子串
	 */
	public static boolean contains(String str,String sub){
		return contains(str,sub,false);
	}
	
	/**
	 * 判断某字符串包含某个子串
	 */
	public static boolean contains(String str,String sub,boolean ingore){
		return(indexOf(str,sub,ingore)!=-1)? true:false;
	}

	/**
	 * 将字符串数组衔接起来得到一个新的字符串
	 */
	public static String concat(String str1, String str2) {
		return str1 + str2;
	}
	
	/**
	 * 将字符串数组衔接起来得到一个新的字符串
	 */
	public static String concat(String str1,String str2,String concat) {
		return str1 +concat+ str2;
	}
	
	/**
	 * 将字符串数组衔接起来得到一个新的字符串
	 */
	public static String concat(String[] array,String concat) {
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			buf.append(array[i]);
			if (i < array.length - 1)
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
	 * 获得过滤环境变量
	 */
	public static String getFilterValue(String value){
		int[] pos = getVariablePos(value);
		while(pos[1] >0 && pos[0] >=0 && pos[1] > pos[0]){
			String variableName = value.substring(pos[0]+2,pos[1]);//参数变量名
			String variableBlock = value.substring(pos[0],pos[1]+1);//参数变量块
			
			if(!StringUtil.isNull(variableName)){
				String variableValue = System.getProperty(variableName.trim());
				if(StringUtil.isNull(variableValue))
					throw new IllegalArgumentException("Not found system variable value with name:"+variableName);
					value = StringUtil.replace(value,variableBlock,variableValue);
			}
			pos = getVariablePos(value);
		}
		
		return value;
	}
	
	/**
	 * 获取环境变量位置
	 */
	private static int[]getVariablePos(String text){
		int index =text.indexOf("#{");
		int index2 =text.indexOf("${");
		int endPos = text.indexOf("}");
		int startPos= -1;
		
		if(index>=0 && index2>=0){
			startPos=(index<=index2)?index:index2;
		}else if(index==-1 && index2>=0){
			startPos = index2;
		}else if(index>=0 && index2==-1){
			startPos = index;
		}else if(index==-1 && index2==-1){
			startPos = -1;
		}
		return new int[]{startPos,endPos};
	}
	
	
	/**
	 * 删除前缀
	 */
	public static String removeProfix(String str, String prefix) {
		return removeProfix(str,prefix,false);
	}
	
	/**
	 * 删除前缀
	 */
	public static String removeProfix(String str,String prefix,boolean ingore) {
		int index = StringUtil.indexOf(str,prefix,ingore);
		if(index==0){
			return str.substring(prefix.length());
		}else{
			return str;
		}
	}
	
	/**
	 * 删除后缀
	 */
	public static String removeSuffix(String str,String suffix) {
		return removeSuffix(str,suffix,false);
	}
	
	/**
	 * 删除后缀
	 */
	public static String removeSuffix(String str,String suffix,boolean ingore) {
		int index = StringUtil.indexOf(str,suffix,ingore);
		if(index > 0){
			return str.substring(0,str.lastIndexOf(suffix));
		}else{
			return str;
		}
	}
	
	/**
	 * 替换指定位置的字符串
	 */
	public static String replace(String str,int start,int end,String rp) {
		StringBuffer sbf = new StringBuffer();
		sbf.append(str.substring(0, start));
		sbf.append(rp);
		sbf.append(str.substring(end + 1));
		return sbf.toString();
	}

	/**
	 * 字符串替换
	 */
	public static String replace(String str,String oldValue, String newValue) {
		return replace(str,oldValue,newValue,false);
	}

	/**
	 * 字符串替换
	 */
	public static String replace(String str,String pattern,String replace,boolean ingore) {
		int found =-1,start=0;
		final int len = pattern.length();
		StringBuffer sb = new StringBuffer();
		while ((found = indexOf(str,pattern,start)) != -1) {
			sb.append(str.substring(start, found));
			sb.append(replace);
			start = found + len;
		}
		sb.append(str.substring(start));
		return sb.toString();
	}

	/**
	 * 统计某个子串出现的次数
	 */
	public static int getSubCount(String src,String sub) {
		return getSubCount(src,sub,false);
	}
	
	/**
	 * 统计某个子串出现的次数
	 */
	public static int getSubCount(String src,String sub,boolean ingore) {
		int count=0,index=0,pos =0;
		int srcLength = src.length();
		int subLength = sub.length();
		while(index < srcLength) {
			pos =indexOf(src,sub,index,ingore);
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
	
	/**
	 * 搜索一批字串的位置
	 */
	public static int[] indexsOf(String value, String[] subString) {
		int[] posArray = new int[subString.length];
		for (int i = 0; i < subString.length; i++)
			posArray[i] = value.indexOf(subString[i]);

		return posArray;
	}

	/**
	 * 获取子串
	 */
	public static String subString(String str, int start, int end) {
		return str.substring(start,end+1);
	}

	/**
	 * 获取子串
	 */
	public static String subString(String str,String beginSub,String endSub) {
		return subString(str,0,beginSub, endSub);
	}

	/**
	 * 获取子串
	 */
	public static String subString(String str,int startIndex,String beginSub,String endSub) {
		return subString(str,startIndex,beginSub,endSub);
	}
	
	/**
	 * 获取子串
	 */
	public static String subString(String str,int startIndex,String beginSub,String endSub,boolean ingore) {
		int headPos = indexOf(str,beginSub,startIndex,ingore);
		if (headPos == -1) {
			return null;
		} else if (headPos == str.length()) {
			return null;
		} else {
			int tailPos = indexOf(str,endSub, headPos + 1,ingore);
			return str.substring(headPos,tailPos +endSub.length());
		}
	}
	
  /**
	 *两个对象是否相等
	 */
	public static boolean isEquals(String value1, String value2) {
		if (value1 == value2) {
			return true;
		}
		
		if (value1 == null) {
			return (value2 == null);
		}else{
			return value1.equals(value2);
		}
	}
}

