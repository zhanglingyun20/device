package com.device.common;

import java.util.Date;
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
	 * 游戏关闭
	 */
	public static final String OFF = "off";
	
	public static String STATE="state";
	
	public static String TIME="time";

	/**
	 * 缓存监控 游戏map
	 */
	private static Map<String, RunState> monitorMap = new HashMap<String, RunState>();
	
	/**
	 * 正在运行的游戏
	 */
	private static Map<String, RunState> openMap = new HashMap<String, RunState>();
	
	
	private static Map<String, RunState> offMap = new HashMap<String, RunState>();

	private MonitorCache() {
		super();
	}

	/**
	 * 获取游戏监控记录缓存
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public static synchronized Map<String, RunState> getMonitorMap() {
		return monitorMap;
	}
	
	/**
	 * 获取正在运行的游戏缓存
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public static synchronized Map<String, RunState> getOpenMap() {
		return openMap;
	}

	/**
	 * 获取关闭状态的游戏
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public static synchronized Map<String, RunState> getOffMap() {
		return offMap;
	}
	
}
