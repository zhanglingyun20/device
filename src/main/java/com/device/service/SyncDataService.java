package com.device.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.device.common.GameRunRecordRequest;
import com.device.common.Result;
import com.device.common.SysConstants;
import com.device.common.SystemCache;
import com.device.common.util.HttpRequest;
import com.device.common.util.MD5Util;
import com.device.mapper.DeviceInfoMapper;
import com.device.mapper.GameMapper;
import com.device.mapper.GameRunRecordMapper;
import com.device.model.DeviceInfo;
import com.device.model.Game;
import com.device.model.GameRunRecord;

@Service
public class SyncDataService
{

	private static Logger logger  =  Logger.getLogger(SyncDataService.class );
	@Autowired
	private GameRunRecordMapper gameRunRecordMapper;
	
	@Autowired
	private GameMapper gameMapper;
	
	@Autowired
	private DeviceInfoMapper deviceInfoMapper;
	
	
	public void uploadGameRecord(){
		List<GameRunRecord> gameRecords = gameRunRecordMapper.findByNoSync();
		List<DeviceInfo> deviceInfoList = SystemCache.getChacheByClass(DeviceInfo.class);
		DeviceInfo deviceInfo = null;
		if (deviceInfoList!=null&&!deviceInfoList.isEmpty()) {
			deviceInfo = deviceInfoList.get(0);
		}
		if (gameRecords!=null&&!gameRecords.isEmpty()&&deviceInfo!=null) {
			GameRunRecordRequest requstData = new GameRunRecordRequest(deviceInfo, gameRecords);
			String url = bulidRequesUrl(SysConstants.SYNC_GAME_RECORD);
			if (StringUtils.isNotBlank(url)) {
				HttpRequest httpRequest = new HttpRequest();
				Map<String,Object> params =  new HashMap<String, Object>();
				params.put("token", MD5Util.createToken());
				params.put("data", JSONObject.toJSONString(requstData));
				String resultStr = httpRequest.post(url, params);
				logger.info("上传参数=="+params);
				try {
					Result result = JSONObject.parseObject(resultStr, Result.class);
					if (result!=null) {
						if (Result.Code.SUCCESS.getValue().equals(result.getCode())) {
							clearGameRecordHistiry(gameRecords);
						}else
						{
							logger.info("uploadGameRecord 同步消息返回："+result.toString());
						}
					}else
					{
						logger.info("请求超时");
					}

				} catch (Exception e) {
					logger.error("uploadGameRecord error", e);;
				}
			}else{
				logger.warn("uploadGameRecord 请求url为空");
			}
		}else
		{
			logger.debug("uploadGameRecord 上传数据为空");
		}
	}
	
	public void syncGames(){
		String url = bulidRequesUrl(SysConstants.GAME_PULL);
		if (StringUtils.isNotBlank(url)) {
			HttpRequest httpRequest = new HttpRequest();
			Map<String,Object> params =  new HashMap<String, Object>();
			params.put("token", MD5Util.createToken());
			String resultStr = httpRequest.post(url, params);
			try {
				Result result = JSONObject.parseObject(resultStr, Result.class);
				if (Result.Code.SUCCESS.getValue().equals(result.getCode())) 
				{
					List<Game> gameList= JSONArray.parseArray(result.getContent().toString(), Game.class);
					if (gameList!=null&&!gameList.isEmpty()) {
						saveGames(gameList);
						SystemCache.initGames();
					}
				}else
				{
					logger.info("syncGames 同步消息返回："+result.toString());
				}
			} catch (Exception e) {
				logger.error("uploadGameRecord error", e);;
			}
		}else{
			logger.warn("syncGames 请求url为空");
		}
	}
	
	
	@Transactional
	public void saveGames(List<Game> gameList){
		gameMapper.clearGames();
		gameMapper.batchInsertGame(gameList);
	}
	
	public void syncDeviceInfo(){
		String url = bulidRequesUrl(SysConstants.ACTIVE_USER);
		List<DeviceInfo> infos = deviceInfoMapper.getNoSync();
		if (infos==null||infos.isEmpty()) {
			logger.info("syncDeviceInfo 没有同步的设备信息");
			return;
		}
		if (StringUtils.isNotBlank(url)) {
			HttpRequest httpRequest = new HttpRequest();
			Map<String,Object> params =  new HashMap<String, Object>();
			params.put("token", MD5Util.createToken());
			params.put("data", JSONObject.toJSONString(infos.get(0)));
			String resultStr = httpRequest.post(url, params);
			try {
				Result result = JSONObject.parseObject(resultStr, Result.class);
				if (Result.Code.SUCCESS.getValue().equals(result.getCode())) 
				{
					deviceInfoMapper.updateSync();
					SystemCache.initDeviceInfo();
				}else
				{
					logger.info("syncDeviceInfo 同步消息返回："+result.toString());
				}
			} catch (Exception e) {
				logger.error("uploadGameRecord error", e);;
			}
		}else{
			logger.warn("syncDeviceInfo  请求url为空");
		}
	}
	
	/**
	 * 清楚上传完成的历史纪录
	 * @author sawyer
	 * @date 2016年8月10日
	 * @param gameRecords
	 */
	public void clearGameRecordHistiry(List<GameRunRecord> gameRecords){
		if (gameRecords!=null&&!gameRecords.isEmpty()) {
			gameRunRecordMapper.batchDeleteHistory(gameRecords);
		}
	}
	
	private String bulidRequesUrl(String path){
		String domain = SystemCache.getSysConfigVauleByKey(SysConstants.SERVER_DOMAIN);
		path = SystemCache.getSysConfigVauleByKey(path);
		return domain+path;
	}
}
