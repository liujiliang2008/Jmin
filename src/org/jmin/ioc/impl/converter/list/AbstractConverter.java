/*
 * Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.converter.list;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

import org.jmin.ioc.BeanTypeConvertException;
import org.jmin.ioc.impl.converter.BeanTypeBaseConverter;
import org.jmin.ioc.impl.util.ArrayUtil;
import org.jmin.ioc.impl.util.StringUtil;

/**
 * List转换器
 * 
 * @author Liao
 */

public abstract class AbstractConverter extends BeanTypeBaseConverter{
	
	/**
	* 将对象转换为目标类型，如将结果转换为当前类型
	*/
	public void convert(Collection list,Object value)throws BeanTypeConvertException{
		if(value instanceof Collection){
		 list.addAll((Collection)value);
		}else if(value instanceof Iterator){
			Iterator itor = (Iterator)value;
			while(itor.hasNext())
			 list.add(itor.next());
		}else if(value instanceof Enumeration){
			Enumeration enumer =(Enumeration)value;
			while(enumer.hasMoreElements())
				list.add(enumer.nextElement());
		}else if(value instanceof String){
			String text =(String)value;
			String[]stringArray=new String[]{text};
			if(text.indexOf(",")!=-1)
			 stringArray=StringUtil.split(text,",");
			else if(text.indexOf("|")!=-1)
			 stringArray=StringUtil.split(text,"|");
		  else if(text.indexOf(" ")!=-1)
			 stringArray=StringUtil.split(text," ");
			for(int i=0;i<stringArray.length;i++)
				list.add(stringArray[i]);
		}else if(ArrayUtil.isArray(value)){
			int arraySize = ArrayUtil.getArraySize(value);
			for(int i=0;i<arraySize;i++)
				list.add(ArrayUtil.getObject(value,i));
		}else{
			throw new BeanTypeConvertException("Doesn't support object conversion from type: "+ value.getClass().getName() + " to type:"+list.getClass().getName());
		}
	}
}
