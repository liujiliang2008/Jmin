package org.jmin.test.log;

import org.jmin.log.Logger;
import org.jmin.log.printer.LogUserIdSetter;

public class LogTest {
	public static void main(String[] args) {
	 for(int i=0;i<100;i++){
			new LogThread().start();
		}
	}
}

class LogThread extends Thread{
	Logger log = Logger.getLogger(LogThread.class);
	public void run(){
		LogUserIdSetter.setUserID(this.getName());
		while(true){
			 log.info("Hello: ");
		}
	}
}