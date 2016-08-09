package com.device.common;

import java.util.HashMap;
import java.util.Map;

/**
 * 监控缓存
 * @author sawyer
 * @date 2016年8月9日
 */
public class MonitorCache {

	/**
	 * 打开游戏
	 */
	public static final String OPEN = "open";
	
	/**
	 * 游戏正在运行中
	 */
	public static final String RUNNING = "running";
	
	/**
	 * 游戏关闭
	 */
	public static final String OFF = "off";

	/**
	 * 缓存监控 游戏map
	 */
	private static Map<String, String> monitorMap = new HashMap<String, String>();
	
	/**
	 * 正在运行的游戏
	 */
	private static Map<String, String> openMap = new HashMap<String, String>();
	
	
	private static Map<String, String> offMap = new HashMap<String, String>();

	private MonitorCache() {
		super();
	}

	/**
	 * 获取游戏监控记录缓存
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public static synchronized Map<String, String> getMonitorMap() {
		return monitorMap;
	}
	
	/**
	 * 获取正在运行的游戏缓存
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public static synchronized Map<String, String> getOpenMap() {
		return openMap;
	}

	/**
	 * 获取关闭状态的游戏
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public static synchronized Map<String, String> getOffMap() {
		return offMap;
	}
	
}
