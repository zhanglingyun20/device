package com.device.common;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.SpringContext;
import com.device.mapper.DeviceInfoMapper;
import com.device.mapper.GameMapper;
import com.device.model.DeviceInfo;
import com.device.model.Game;

public class SystemCache
{

	private static List<Game> gamesCahce = new ArrayList<Game>();
	
	private static DeviceInfo deviceInfo = new DeviceInfo();
	
//	@Autowired
//	private DeviceInfoMapper deviceInfoMapper;
	
	
	public static List<Game> getMonitorGames()
	{
		if (gamesCahce==null||gamesCahce.isEmpty())
		{
			GameMapper gameMapper =SpringContext.getBean(GameMapper.class);
			gamesCahce = gameMapper.getAllGames();
		}
		return gamesCahce;
	}
	
	public static  void setMonitorGames(List<Game> games)
	{
		gamesCahce = games;
	}
	
	
	public static DeviceInfo getDeviceInfo()
	{
		DeviceInfoMapper deviceInfoMapper =SpringContext.getBean(DeviceInfoMapper.class);
		List<DeviceInfo> deviceList = deviceInfoMapper.getAllDevices();
		return deviceInfo;
	}
	
}
