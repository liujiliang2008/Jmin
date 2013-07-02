/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.ioc.impl.config;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.BeanContextFactory;
import org.jmin.ioc.BeanException;

/**
 * Bean Context Factory implement
 *
 * @author Chris Liao
 * @version 1.0
 */
public class BeanContextFactoryImpl implements BeanContextFactory{
	
	/**
	 * Create Bean context
	 */
	public BeanContext createContext(Map initMap)throws BeanException{
		Object source =initMap.get(BeanContext.BEAN_CONTEXT_FILE);
		if(source!=null){
		  if(source instanceof String){
		  	String file = (String)source;
		  	if(file.trim().length()==0)
		  		throw new BeanException("Context file name can't be null");
		  	else
		  	 return new BeanContextImpl();
		  }else if(source instanceof String[]){
		  	String[]files = (String[])source;
		  	if(files.length==0)
		  		throw new BeanException("Context file array siz must be more than zero");
		  	
		  	return new BeanContextImpl((String[])source);
			}else if(source instanceof Collection){
		    int i=0;
		    Collection collection =(Collection)source;
		  	if(collection.size()==0)
		  		throw new BeanException("Context file list siz must be more than zero");
		  	
				String[]files=new String[collection.size()];
				Iterator itor=collection.iterator();
				while(itor.hasNext()){
					files[i++]=(String)itor.next();
				}
			 	return new BeanContextImpl(files);
			}else{
				throw new BeanException("Not support context source type:["+source.getClass().getName()+"]");
			}
		}else{
			throw new BeanException("Not found context file["+BeanContext.BEAN_CONTEXT_FILE+"]in map");
		}
	}
}
