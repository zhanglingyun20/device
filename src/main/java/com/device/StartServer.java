package com.device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;

import com.device.common.ProcessUtils;
import com.device.service.MonitorService;
/**
 * 
 * @author sawyer
 * date 2016年8月9日
 */
public class StartServer {

	Map<String,String> monitorMap = new HashMap<String, String>();
	
	public static final LinkedBlockingDeque<List<String>> monitoeProcess = new LinkedBlockingDeque<List<String>>();
	
	public static void start() {
		scanProcess();
		processConsumer();
	}

	/**
	 * 扫描进程
	 * @author sawyer
	 * @date 2016年8月9日
	 */
	public static void scanProcess() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						monitoeProcess.offer(ProcessUtils.getAllProcess());
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		;
	}
	
	public static void processConsumer(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				MonitorService monitorService = SpringContext.getBean(MonitorService.class);
				while (!Thread.interrupted()) {
					try {
						monitorService.handleProcess(monitoeProcess.take());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
