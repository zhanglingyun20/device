package com.device.schedule;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.device.service.SyncDataService;

@Component
public class SyncDadaSchedule
{
	private static Logger logger  =  Logger.getLogger(SyncDadaSchedule.class );
	@Autowired
	private SyncDataService syncDataService;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void syncGameRunRecord()
	{
		logger.info("执行上传游戏数据任务====开始");
		syncDataService.uploadGameRecord();
		logger.info("执行上传游戏数据====结束");
	}
}
