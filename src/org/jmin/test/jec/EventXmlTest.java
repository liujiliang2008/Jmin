package org.jmin.test.jec;

import org.jmin.jec.EventHandle;
import org.jmin.jec.EventPublisher;
import org.jmin.jec.impl.xml.EventCenterXmlLoader;

public class EventXmlTest {

  /**
   * 测试方法入口
   */
  public static void main(String args[]) throws Exception {
  	EventCenterXmlLoader starter = new EventCenterXmlLoader();
  	EventPublisher publisher = starter.load("org/jmin/test/jec/event.xml","EventCenter");
  	
  	EventHandle hanle = publisher.publishSynEvent(new NameEvent1(),true);
		String[] names = hanle.getEventProcessorNames();
		Object[] result = hanle.getResultObjects();
		for (int i = 0; i < names.length; i++) {
			System.out.print(names[i]);
			System.out.print(" : ");
			System.out.println(result[i]);
		}
  }
}
