package com.device.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.device.common.SystemCache;
import com.device.mapper.GameRunRecordMapper;
import com.device.model.DeviceInfo;

@Component
public class SyncDadaSchedule
{

	@Autowired
	private GameRunRecordMapper gameRunRecordMapper;
	
	@Scheduled(cron = "0 0/1 * * * ?")
	public void syncGameRunRecord()
	{
		DeviceInfo deviceInfo = SystemCache.getDeviceInfo();
		
	}
}
