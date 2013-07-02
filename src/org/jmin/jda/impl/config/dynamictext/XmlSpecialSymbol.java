package org.jmin.jda.impl.config.dynamictext;

import java.util.HashMap;
import java.util.Map;

import org.jmin.jda.impl.util.StringUtil;

/**
 * XML包含一些特殊字符，解析后需要将以正确字符替换进去
 * 
 * @author liao
 */
public class XmlSpecialSymbol {
	
	private static Map charMap = new HashMap();
	
	private static String[]specialChars = new String[]{"&amp;","&lt;","&gt;","&quot;","&apos;","&nbsp;"};
	static{
		charMap.put("&amp;","&");
		charMap.put("&lt;","<");
		charMap.put("&gt;",">");
		charMap.put("&quot;","\"");
		charMap.put("&apos;","'");
		charMap.put("&nbsp;"," ");
	}
	
	//获取特殊字符对应的实体符号
	public static String getMatchSymbol(String specialChar){
		return (String)charMap.get(specialChar);
	}
	
	//是否包含特殊字符
	public static boolean containsSpecialSymbol(String text){
		if(text!=null){
		  for(int i=0;i<specialChars.length;i++){
		  	if(text.indexOf(specialChars[i]) > -1){
		  		return true;
		  	}
		  }
		  return false;
		}else
			return false;
	}
 
	//替换特殊字符
	public static String replaceSpecialSymbol(String text){
	  for(int i=0;i<specialChars.length;i++){
	  	if(text.indexOf(specialChars[i]) > -1){
	  		text = StringUtil.replace(text,specialChars[i],getMatchSymbol(specialChars[i]));
	  	}
	  }
	  return text;
	}
}
