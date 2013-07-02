/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jda.impl.converter.date;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jmin.jda.JdaTypeConvertException;
import org.jmin.jda.impl.converter.JdaTypeBaseConverter;

/**
 * 日历转换
 * 
 * @author chris
 */

public class UtilDateConverter extends JdaTypeBaseConverter{
	
	/**
	 * 转换为目标类型
	 */
	public Object convert(Object value)throws JdaTypeConvertException{
		if(value ==null){
			return null;
		}else if(value instanceof Date){
			return value;
		}else if(value instanceof Number){
			Number numValue =(Number)value;
			return new Date(numValue.longValue());
		}else if(value instanceof Calendar){
			Calendar calendar =(Calendar)value;
			return calendar.getTime();
		}else if(value instanceof String){
			try{
				long longVale = Long.parseLong((String)value);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date(longVale));
				return calendar;
			}catch(Throwable e){
				return UtilDateConverter.stringToDate((String)value);
			}
		}else{
			throw new JdaTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type: Date.class");
		}
	}
	
	/** 
   * 字符串转换为java.util.Date<br> 
   * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br> 
   * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br> 
   * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br> 
   * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br> 
   * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br> 
   * @param time String 字符串<br> 
   * @return Date 日期<br> 
   */ 
  public static Date stringToDate(String time){ 
    SimpleDateFormat formatter=null; 
    if(time.indexOf(":")>0){//带有时间
	    int tempPos=time.indexOf("AD") ; 
	    time=time.trim() ; 
	    formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z"); 
	    if(tempPos>-1){ 
	      time=time.substring(0,tempPos)+ "公元"+time.substring(tempPos+"AD".length());//china 
	      formatter = new SimpleDateFormat ("yyyy.MM.dd G 'at' hh:mm:ss z"); 
	    }else if((time.indexOf(" ")<0) && time.length()>8){ 
	      formatter = new SimpleDateFormat ("yyyyMMddHHmmssZ"); 
	    }else if((time.indexOf("/")==2) &&(time.indexOf(" ")>-1)){ 
	      formatter = new SimpleDateFormat ("dd/MM/yyyy HH:mm:ss"); 
	    }else if((time.indexOf("/")==4) &&(time.indexOf(" ")>-1)){ 
	      formatter = new SimpleDateFormat ("yyyy/MM/dd HH:mm:ss"); 
	    }else if((time.indexOf("\\")==2) &&(time.indexOf(" ")>-1)){ 
	      formatter = new SimpleDateFormat ("dd\\MM\\yyyy HH:mm:ss"); 
	    }else if((time.indexOf("\\")==4) &&(time.indexOf(" ")>-1)){ 
	      formatter = new SimpleDateFormat ("yyyy\\MM\\dd HH:mm:ss"); 
	 
	    }else if((time.indexOf("/")==2) &&(time.indexOf("am")>-1) || (time.indexOf("pm")>-1)){ 
	      formatter = new SimpleDateFormat ("dd/MM/yyyy KK:mm:ss a"); 
	    }else if((time.indexOf("/")==4) &&(time.indexOf("am")>-1) || (time.indexOf("pm")>-1)){ 
	      formatter = new SimpleDateFormat ("yyyy/MM/dd KK:mm:ss a"); 
	    }else if((time.indexOf("\\")==2) &&(time.indexOf("am")>-1) || (time.indexOf("pm")>-1)){ 
	      formatter = new SimpleDateFormat ("dd\\MM\\yyyy KK:mm:ss a"); 
	    }else if((time.indexOf("\\")==4) &&(time.indexOf("am")>-1) || (time.indexOf("pm")>-1)){ 
	      formatter = new SimpleDateFormat ("yyyy\\MM\\dd KK:mm:ss a");
	      
	    }else if((time.indexOf("-")==2) &&(time.indexOf(" ")>-1)){ 
	      formatter = new SimpleDateFormat ("dd-MM-yyyy HH:mm:ss"); 
	    }else if((time.indexOf("-")==4) &&(time.indexOf(" ")>-1)){ 
	      formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");   
	      
	    }else if((time.indexOf("-")==2) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){ 
	      formatter = new SimpleDateFormat ("dd-MM-yyyy KK:mm:ss a"); 
	    }else if((time.indexOf("-")==4) &&(time.indexOf("am")>-1) ||(time.indexOf("pm")>-1)){ 
	      formatter = new SimpleDateFormat ("yyyy-MM-dd KK:mm:ss a"); 
	    } 
    }else{
      if(time.indexOf("/")>0){
    		int pos = time.indexOf("/");
	      if(pos==2){
	      	formatter = new SimpleDateFormat("dd/MM/yyyy"); 
	      }else if(pos==4){
	      	formatter = new SimpleDateFormat("yyyy/MM/dd"); 
	      }
	     }else if(time.indexOf("\\")>0){
    		int pos = time.indexOf("\\");
	      if(pos==2){
	      	formatter = new SimpleDateFormat("dd\\MM\\yyyy"); 
	      }else if(pos==4){
	      	formatter = new SimpleDateFormat("yyyy\\MM\\dd"); 
	      }
      }else if(time.indexOf("-")>0){
    		int pos = time.indexOf("-");
    	  if(pos==2){
	      	formatter = new SimpleDateFormat("dd-MM-yyyy"); 
	      }else if(pos==4){
	      	formatter = new SimpleDateFormat("yyyy-MM-dd"); 
	      }
    	}else if(time.trim().length()==8){
    		try {
					formatter = new SimpleDateFormat("yyyyMMdd"); 
					formatter.parse(time);
				} catch (ParseException e) {
					formatter = new SimpleDateFormat("ddMMyyyy"); 
				}
    	}
    }
    
    
    ParsePosition pos = new ParsePosition(0); 
    java.util.Date ctime = formatter.parse(time, pos); 

    return ctime; 
  } 
}
