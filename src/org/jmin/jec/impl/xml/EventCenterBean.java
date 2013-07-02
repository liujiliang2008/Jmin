/*
 * Copyright (c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl.xml;

import java.util.Iterator;
import java.util.List;

import org.jmin.jec.EventException;
import org.jmin.jec.EventProcessor;
import org.jmin.jec.EventPublisher;
import org.jmin.jec.impl.MinEventCenter;

/**
 * 事件信息
 * 
 * @author Chris Liao
 */

public class EventCenterBean {
	
	/**
	 * 队列中事件最大数
	 */
	private int size;
	
	/**
	 * 是否后台运行
	 */
	private boolean daemon=true;
  
	/**
	 * 事件类型列表
	 */
	private List eventTypeList;
	
	/**
	 * 队列中事件最大数
	 */
	public int getSize() {
		return size;
	}
	/**
	 * 队列中事件最大数
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * 是否后台运行
	 */	
	public boolean isDaemon() {
		return daemon;
	}
	
	/**
	 * 是否后台运行
	 */
	public void setDaemon(boolean daemon) {
		this.daemon = daemon;
	}

	/**
	 * 事件类型列表
	 */
	public List getEventTypeList() {
		return eventTypeList;
	}

	/**
	 * 事件类型列表
	 */
	public void setEventTypeList(List eventTypeList) {
		this.eventTypeList = eventTypeList;
	}
	
	/**
	 * 装载信息
	 */
	public EventPublisher load()throws Exception{
		MinEventCenter center =null;
		if(size>0)
		  center = new MinEventCenter(size,daemon);
		else
			center = new MinEventCenter(daemon);
		 
		ClassLoader classLoader =EventCenterBean.class.getClassLoader();
		if(eventTypeList!=null){
			Iterator itor =eventTypeList.iterator();
			while(itor.hasNext()){
				EventBean eventInfo =(EventBean)itor.next();
				String eventName = eventInfo.getName();
				String className = eventInfo.getClassName();
				List processorInfoList = eventInfo.getProcessorList();
				Object registerKey=null;
				
				if(className==null && className.trim().length()==0)
					throw new Exception("Event classname property can't be null");
					
				Class eventClass=classLoader.loadClass(className.trim());
				if(eventName==null || eventName.trim().length()==0)
					registerKey = eventClass;
				else
					registerKey=eventName.trim();
				center.registerEventType(registerKey,eventClass);
				
				if(processorInfoList!=null){
					Iterator itor2 =processorInfoList.iterator();
					while(itor2.hasNext()){
						ProcessorBean info =(ProcessorBean)itor2.next();
						String processorName =info.getName();
						String processorClassName =info.getClassName();
						EventProcessor eventProcessor =info.getEventProcessor();
						if(eventProcessor!=null){
							if(processorName==null || processorName.trim().length()==0)
							 center.addEventProcessor(registerKey,eventProcessor);
							else
							 center.addEventProcessor(registerKey,processorName,eventProcessor);
						}else if(processorClassName!=null && processorClassName.trim().length()>0){
							Class processorClass=classLoader.loadClass(processorClassName.trim());
							if(!EventProcessor.class.isAssignableFrom(processorClass)){
								throw new Exception("Event("+registerKey+") processor class must be sub class of ["+EventProcessor.class.getName()+"]");
							}
							
							try {
								 eventProcessor=(EventProcessor)processorClass.newInstance();
								 if(processorName==null || processorName.trim().length()==0)
									 center.addEventProcessor(registerKey,eventProcessor);
								 else
									 center.addEventProcessor(registerKey,processorName,eventProcessor);
							} catch (InstantiationException e) {
								 throw new EventException(e);
							} catch (IllegalAccessException e) {
								 throw new EventException(e);
							} catch (EventException e) {
							 throw e;
							}
							
						}else{
							throw new Exception("Event("+registerKey+") [processorClassName]property can't be null");
						}
					}
				}
			}
		}
	
		return center.getEventPublisher();
	}
}
