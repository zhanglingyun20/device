package com.device.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.device.common.MonitorCache;
import com.device.mapper.GameMapper;
import com.device.mapper.GameRunRecordMapper;
import com.device.model.Game;
import com.device.model.GameRunRecord;
import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry.Entry;

@Service
public class MonitorService {

	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private GameRunRecordMapper gameRunRecordMapper;
	
	/**
	 * 获取要监控的游戏
	 * @author sawyer
	 * @date 2016年8月9日
	 * @return
	 */
	public List<Game> getMonitorGames()
	{
		return gameMapper.getAllGames();
	}
	
	
	public void handleProcess(List<String> processList){
		List<String> offGames = findOffGame(processList);
		if (offGames!=null&&!offGames.isEmpty()) {
			for (String string : offGames) {
				GameRunRecord record = new GameRunRecord(null, 1, string, 5d, "0", new Date());
				gameRunRecordMapper.insertSelective(record);
			}
		}
	}
	/**
	 * 找到关闭的游戏
	 * @author sawyer
	 * @date 2016年8月9日
	 * @param processList windows 所有正在运行的程序
	 */
	public List<String> findOffGame(List<String> processList)
	{
		List<String> offGameList = new ArrayList<String>();
		Map<String,Game> monitorMap = new HashMap<String, Game>();
		List<Game> monitorGames = getMonitorGames();//需要监控的游戏进程
		if (monitorGames!=null&&!monitorGames.isEmpty()) {
			for (Game game : monitorGames) {
				monitorMap.put(game.getGameProcess().toLowerCase(), game);//进程名 key 全部转小写
			}
			Map<String, String> monitorCache = MonitorCache.getMonitorMap();
			if (processList!=null&&!processList.isEmpty()) {
				for (String processName : processList) 
				{
					//进程名 包含在游戏监控列表里面才处理
					if (monitorMap.containsKey(processName)) {
						
						//缓存中没有的，说明是第一次打开
						if (!monitorCache.containsKey(processName)) {
							monitorCache.put(processName, MonitorCache.OPEN);
						}
						if (monitorCache!=null&&!monitorCache.isEmpty()) {
							for (java.util.Map.Entry<String, String> enrty : monitorCache.entrySet()) 
							{
								String key = enrty.getKey();
								String stateVaule = enrty.getValue();
								//找出 缓存中已经设置成打开的进程，但扫描windows没有得到的进程  设置成已关闭。包含的部分不管他（说明上一直在运行）
								if (MonitorCache.OPEN.equalsIgnoreCase(stateVaule)&&!(processName.equalsIgnoreCase(key))) {
									offGameList.add(key);//记录已关闭的记录入库
									monitorCache.put(key, MonitorCache.OFF);
								}
							}
						}
					}
					}
				}
		}
		return offGameList;
	}
}
