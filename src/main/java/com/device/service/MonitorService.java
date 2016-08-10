package com.device.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.device.common.MonitorCache;
import com.device.common.SystemCache;
import com.device.mapper.GameMapper;
import com.device.mapper.GameRunRecordMapper;
import com.device.model.Game;
import com.device.model.GameRunRecord;

@Service
public class MonitorService {

	private static Logger logger  =  Logger.getLogger(MonitorService.class );
	
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
		return SystemCache.getMonitorGames();
	}
	
	public void handleProcess(Set<String> processList){
		logger.info(processList);
		List<String> offGames = findOffGame(toLowerCase(processList));
		if (offGames!=null&&!offGames.isEmpty()) 
		{
			for (String processName : offGames) {
				Game game = gameMapper.findByProcessName(processName);
				int gameId = 0;
				if (game!=null)
				{
					gameId = game.getId();
				}
				GameRunRecord record = new GameRunRecord(null, gameId, processName, 5d, GameRunRecord.Sync.NO.getValue(), new Date());
				gameRunRecordMapper.insertSelective(record);
			}
		}
	}
	
	private Set<String> toLowerCase(Set<String> processList){
		Set<String> resultSet = new HashSet<String>();
		if (processList!=null&&!processList.isEmpty())
		{
			for (String string : processList)
			{
				resultSet.add(string.toLowerCase());
			}
		}
		return resultSet;
	}
	/**
	 * 找到关闭的游戏
	 * @author sawyer
	 * @date 2016年8月9日
	 * @param processList windows 扫描到的进程
	 */
	public List<String> findOffGame(Set<String> processSet)
	{
		List<String> offGameList = new ArrayList<String>();
		Map<String,Game> monitorMap = new HashMap<String, Game>();
		List<Game> monitorGames = getMonitorGames();//需要监控的游戏进程
		if (monitorGames!=null&&!monitorGames.isEmpty()) {
			for (Game game : monitorGames) {
				monitorMap.put(game.getGameProcess().toLowerCase(), game);//进程名 key 全部转小写
			}
			Map<String, String> monitorCache = MonitorCache.getMonitorMap();
			
			//找出缓存中有，但是次此扫描没有扫描到程序进程。 这些进程为关闭了得进程
			if (monitorCache!=null&&!monitorCache.isEmpty()) 
			{
				for (java.util.Map.Entry<String, String> enrty : monitorCache.entrySet()) 
				{
					String key = enrty.getKey();
					String stateVaule = enrty.getValue();
					//找出 缓存中已经设置成打开的进程，但扫描windows没有得到的进程  设置成已关闭。包含的部分不管他（说明上一直在运行）
					if (MonitorCache.OPEN.equalsIgnoreCase(stateVaule)&&!processSet.contains(key)) {
						offGameList.add(key);//记录已关闭的记录入库
						monitorCache.put(key, MonitorCache.OFF);
						logger.info("========进程"+key +"关闭===========");
					}
				}
			}
			
			if (processSet!=null&&!processSet.isEmpty()) {
				for (String processName : processSet) 
				{
					processName = processName.toLowerCase();
					//进程名 包含在游戏监控列表里面才处理
					if (monitorMap.containsKey(processName)) {
						//缓存中没有的，标记为已打开。
						if (!monitorCache.containsKey(processName)) {
							monitorCache.put(processName, MonitorCache.OPEN);
							logger.info("========进程"+processName +"开启===========");
						}else{
							//缓存中有的，判断状态，如果是off状态，设置
							String processState = monitorCache.get(processName);
							if (MonitorCache.OFF.equals(processState))
							{
								monitorCache.put(processName, MonitorCache.OPEN);
								logger.info("========进程"+processName +"开启===========");
							}else if(MonitorCache.OPEN.equals(processState)){
								logger.info("========进程"+processName +"正在跑===========");
							}
						}
					}
				}
			}
		}
		return offGameList;
	}
}
