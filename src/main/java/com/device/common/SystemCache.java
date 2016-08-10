package com.device.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.device.SpringContext;
import com.device.mapper.DeviceInfoMapper;
import com.device.mapper.GameMapper;
import com.device.mapper.SysConfigMapper;
import com.device.model.DeviceInfo;
import com.device.model.Game;
import com.device.model.SysConfig;
import com.device.service.MonitorService;
/**
 * 
 * @author sawyer
 * @date 2016年8月10日
 */
public class SystemCache
{

	private static Logger logger  =  Logger.getLogger(SystemCache.class );
	
	private static Map<String,Object> systemCache = new HashMap<String, Object>();
	
	public static boolean  initCache(){
		try {
			initGames();
			initDeviceInfo();
			initSysConfig();
		} catch (Exception e) {
			logger.error("加载系统缓存失败", e);
			return false;
		}
		return true;
	}
	
	public static <T> T getChacheByClass(Class<?> clz) {
		if (systemCache.containsKey(clz.toString())) {
			return (T) systemCache.get(clz.toString());
		}
		return null;
	}
	
	public static  void initGames()
	{
		GameMapper gameMapper = SpringContext.getBean(GameMapper.class);
		systemCache.put(Game.class.toString(), gameMapper.getAllGames());
	}
	
	public static void initDeviceInfo(){
		DeviceInfoMapper deviceInfoMapper = SpringContext.getBean(DeviceInfoMapper.class);
		systemCache.put(DeviceInfo.class.toString(), deviceInfoMapper.getAllDevices());
	}
	
	public static void initSysConfig(){
		SysConfigMapper sysConfigMapper = SpringContext.getBean(SysConfigMapper.class);
		systemCache.put(SysConfig.class.toString(), sysConfigMapper.getAllConfig());
	}
	
	
	public static Game getGameByProcessName(String processName){
		if (StringUtils.isEmpty(processName)) {
			return null;
		}
		List<Game> games = (List<Game>) systemCache.get(Game.class.toString());
		if (games!=null&&!games.isEmpty()) {
			for (Game game : games) {
				if (processName.equalsIgnoreCase(game.getGameProcess())) {
					return  game;
				}
			}
		}
		return null;
	}
	
	public static String getSysConfigVauleByKey(String key){
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		List<SysConfig> sysConfigs = (List<SysConfig>) systemCache.get(SysConfig.class.toString());
		if (sysConfigs!=null&&!sysConfigs.isEmpty()) {
			for (SysConfig sysConfig : sysConfigs) {
				if (key.equalsIgnoreCase(sysConfig.getKeyName())) {
					return  sysConfig.getKeyValue();
				}
			}
		}
		return null;
	}
}
