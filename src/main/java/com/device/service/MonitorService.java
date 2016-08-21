package com.device.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.device.common.MonitorCache;
import com.device.common.RunState;
import com.device.common.SystemCache;
import com.device.common.util.DateUtils;
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
		return SystemCache.getChacheByClass(Game.class);
	}
	
	/**
	 * 处理关闭游戏数据
	 * @author sawyer
	 * @date 2016年8月15日
	 * @param processList
	 */
	public void handleProcess(Set<String> processList){
		List<String> offGames = findOffGame(toLowerCase(processList));
		if (offGames!=null&&!offGames.isEmpty()) 
		{
			for (String processName : offGames) 
			{
				String gameCode = "unknow";
				String gameName = "unknow";
				Game game = gameMapper.findByProcessName(processName);
				if (game!=null) {
					gameCode = StringUtils.isEmpty(game.getGameCode())?"unknow":game.getGameCode();
					gameName = StringUtils.isEmpty(game.getGameName())?"unknow":game.getGameName();
				}
				GameRunRecord record = new GameRunRecord(null, 0, gameCode, gameName, processName, 1, GameRunRecord.Sync.NO.getValue(), new Date());
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
			Map<String, RunState> monitorCache = MonitorCache.getMonitorMap();
			
			//找出缓存中有，但是次此扫描没有扫描到程序进程。 这些进程为关闭了得进程
			if (monitorCache!=null&&!monitorCache.isEmpty()) 
			{
				for (java.util.Map.Entry<String, RunState> enrty : monitorCache.entrySet()) 
				{
					String key = enrty.getKey();
					RunState runState= enrty.getValue();
					String state = runState.getStateValue();
					//找出 缓存中已经设置成打开的进程，但扫描windows没有得到的进程  设置成已关闭。包含的部分不管他（说明上一直在运行）
					if (MonitorCache.OPEN.equalsIgnoreCase(state)&&!processSet.contains(key)) {
						int runTime = DateUtils.getDateDiffMinutes(runState.getTime(), new Date());
						int billingTime = getBillingTime(key);
						if (isBilling(billingTime, runTime)) {
							offGameList.add(key);//记录已关闭的记录入库
						}
						monitorCache.put(key, new RunState(MonitorCache.OFF, new Date()));
						logger.info("======== Process "+key +" 	off =======运行时间："+runTime+" 分钟,是否计费："+isBilling(billingTime, runTime));
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
							monitorCache.put(processName, new RunState(MonitorCache.OPEN, new Date()));
							logger.info("========Process "+processName +"open ===========开启时间："+DateUtils.dateToString(new Date(), DateUtils.TIME_FORMAT));
						}else{
							//缓存中有的，判断状态，如果是off状态，设置
							RunState runState = monitorCache.get(processName);
							String processState = runState.getStateValue();
							if (MonitorCache.OFF.equals(processState))
							{
								monitorCache.put(processName, new RunState(MonitorCache.OPEN, new Date()));
								logger.info("========进程"+processName +"	开启时间："+DateUtils.dateToString(new Date(), DateUtils.TIME_FORMAT));
							}else if(MonitorCache.OPEN.equals(processState)){
//								logger.debug("========进程"+processName +"正在跑===========");
							}
						}
					}
				}
			}
		}
		return offGameList;
	}
	
	
	/**
	 * 是否计费
	 * @author sawyer
	 * @date 2016年8月21日
	 * @param billingTime
	 * @param runTime
	 * @return
	 */
	private boolean isBilling(int billingTime,int runTime){
		return runTime>=billingTime;
	}
	
	/**
	 * 获取计费时间
	 * @author sawyer
	 * @date 2016年8月21日
	 * @param processName
	 * @return
	 */
	private int getBillingTime(String processName){
		Game game = SystemCache.getGameByProcessName(processName);
		if (game!=null) {
			return game.getBillingTime()==null?0:game.getBillingTime();
		}
		return 0;
	}
}
