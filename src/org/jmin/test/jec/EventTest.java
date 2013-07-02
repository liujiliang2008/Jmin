package org.jmin.test.jec;

import org.jmin.jec.EventCenter;
import org.jmin.jec.EventHandle;
import org.jmin.jec.EventPublisher;
import org.jmin.jec.impl.MinEventCenter;

public class EventTest {

	public static void main(String[] args) throws Exception {
		EventCenter center = new MinEventCenter();
		center.registerEventType(NameEvent1.class);
		center.addEventProcessor(NameEvent1.class, "processor1",new NameEventProcessor1());
		center.addEventProcessor(NameEvent1.class, "processor2",new NameEventProcessor2());
		EventPublisher publisher =center.getEventPublisher();
	
		EventHandle hanle = publisher.publishSynEvent(new NameEvent1());
		hanle = publisher.publishSynEvent(new NameEvent1(),true);
	
		String[] names = hanle.getEventProcessorNames();
		Object[] result = hanle.getResultObjects();

		for (int i = 0; i < names.length; i++) {
			System.out.print(names[i]);
			System.out.print(" : ");
			System.out.println(result[i]);
		}
	}
}
