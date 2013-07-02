package org.jmin.test.jec;

import org.jmin.jec.Event;
import org.jmin.jec.EventProcessor;

public class NameEventProcessor1 implements EventProcessor{
	
	/**
	 * 处理事件
	 */
	public Object process(Event event){
		return "Chris";
	}
}
