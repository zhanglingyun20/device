package com.device.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.device.common.GameRecordVO;
import com.device.common.GameRunRecordRequest;
import com.device.common.SystemCache;
import com.device.mapper.GameRunRecordMapper;
import com.device.model.DeviceInfo;
import com.device.model.GameRunRecord;

@Service
public class SyncDataService
{

	@Autowired
	private GameRunRecordMapper gameRunRecordMapper;
	
	
	public GameRunRecordRequest bulidGameRunRecordRequest(){
		GameRunRecordRequest request = new GameRunRecordRequest();
		
		DeviceInfo deviceInfo = SystemCache.getDeviceInfo();
		if (deviceInfo!=null)
		{
			List<GameRunRecord> gameRecords = gameRunRecordMapper.findByNoSync();
			List<GameRecordVO> bulidRecord=new ArrayList<GameRecordVO>();
			if (gameRecords!=null&&!gameRecords.isEmpty())
			{
				for (GameRunRecord gameRunRecord : gameRecords)
				{
					GameRecordVO vo =new GameRecordVO();
					vo.setCreateTime(gameRunRecord.getCreateTime());
					vo.setGameCode(gameRunRecord.getGameCode());
					vo.setRunCount(1);
				}
				
			}
		}
		return request;
	}
}
