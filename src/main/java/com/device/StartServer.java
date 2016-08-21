package com.device;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

import org.apache.log4j.Logger;

import com.device.common.SystemCache;
import com.device.common.util.ProcessUtils;
import com.device.service.MonitorService;
import com.device.service.SyncDataService;
/**
 * 
 * @author sawyer
 * date 2016年8月9日
 */
public class StartServer {

	private static Logger logger  =  Logger.getLogger(DeviceMain.class );
	
	Map<String,String> monitorMap = new HashMap<String, String>();
	
	public static final LinkedBlockingDeque<Set<String>> monitoeProcess = new LinkedBlockingDeque<Set<String>>();
	
	public static void start() {
		try {
			logger.info("server start...");
			if (SystemCache.initCache()) {
				scanProcess();
				processConsumer();
			}
		} catch (Exception e) {
			logger.error("系统启动失败{}"+e);
		}
	}

	/**
	 * 扫描进程
	 * @author sawyer
	 * @date 2016年8月9日
	 */
	private static void scanProcess() {
		logger.debug("开始扫描进程");
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.interrupted()) {
					try {
						monitoeProcess.offer(ProcessUtils.getAllProcess());
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
		;
	}
	
	/**
	 * 消费进程消息
	 * 
	 * @date 2016年8月10日
	 */
	private static void processConsumer(){
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
	
	/**
	 * 同步设备和远端的数据
	 * @author sawyer
	 * @date 2016年8月18日
	 */
	private static void syncSysConfig(){
		SyncDataService syncDataService = SpringContext.getBean(SyncDataService.class);
		syncDataService.syncGames();
//		syncDataService.syncDeviceInfo();
	}
}
