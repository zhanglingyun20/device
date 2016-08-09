package com.device;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DeviceMain {

	private static Logger logger  =  Logger.getLogger(DeviceMain.class );
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		SpringContext.setApplicationContext(context);
		StartServer.start();
	}
}
