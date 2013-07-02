/*
* Copyright(c) jmin Organization. All rights reserved.
 */
package org.jmin.jec.impl.xml;

import org.jmin.ioc.BeanContext;
import org.jmin.ioc.impl.config.BeanContextImpl;
import org.jmin.jec.EventException;
import org.jmin.jec.EventPublisher;

/**
 * 系统事件启动
 *
 * @author Chris Liao
 */

public class EventCenterXmlLoader{
	
	/**
	 * 默认Ioc Id
	 */
	private static final String EventCenter_Id="eventCenter";
	
  /**
   * 运行默认排期计划
   */
  public EventPublisher load(String file)throws EventException{
  	return load(file,EventCenter_Id);
  }

  /**
   * 运行默认排期计划
   */
  public EventPublisher load(String file,String centerId)throws EventException{
    try{
    	BeanContext context = new BeanContextImpl(file);
    	EventCenterBean eventCenterInfo =(EventCenterBean)(context.getBean(centerId));
    	return eventCenterInfo.load();
    }catch(Exception e){
     throw new EventException(e);
    }
  }
}