package com.device;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * 程序入口
 * @author sawyer
 * @date 2016年8月11日
 */
public class DeviceMain {

	private static Logger logger  =  Logger.getLogger(DeviceMain.class );
	public static void main(String[] args) {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-context.xml");
		SpringContext.setApplicationContext(context);
		StartServer.start();
	}
}
