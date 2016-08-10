package com.device.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.device.common.GameRunRecordRequest;
import com.device.common.Result;
import com.device.common.SysConstants;
import com.device.common.SystemCache;
import com.device.common.util.HttpRequest;
import com.device.common.util.MD5Util;
import com.device.mapper.GameRunRecordMapper;
import com.device.model.DeviceInfo;
import com.device.model.GameRunRecord;

@Service
public class SyncDataService
{

	private static Logger logger  =  Logger.getLogger(SyncDataService.class );
	@Autowired
	private GameRunRecordMapper gameRunRecordMapper;
	
	
	public void uploadGameRecord(){
		List<GameRunRecord> gameRecords = gameRunRecordMapper.findByNoSync();
		List<DeviceInfo> deviceInfoList = SystemCache.getChacheByClass(DeviceInfo.class);
		DeviceInfo deviceInfo = null;
		if (deviceInfoList!=null&&!deviceInfoList.isEmpty()) {
			deviceInfo = deviceInfoList.get(0);
		}
		if (gameRecords!=null&&!gameRecords.isEmpty()&&deviceInfo!=null) {
			GameRunRecordRequest requstData = new GameRunRecordRequest(deviceInfo, gameRecords);
			String domain = SystemCache.getSysConfigVauleByKey(SysConstants.SERVER_DOMAIN);
			String path = SystemCache.getSysConfigVauleByKey(SysConstants.SYNC_GAME_RECORD);
			if (StringUtils.isNotBlank(domain)&&StringUtils.isNotBlank(path)) {
				HttpRequest httpRequest = new HttpRequest();
				Map<String,Object> params =  new HashMap<String, Object>();
				params.put("token", MD5Util.createToken());
				params.put("data", JSONObject.toJSONString(requstData));
				String resultStr = httpRequest.post(domain+path, params);
				try {
					Result result = JSONObject.parseObject(resultStr, Result.class);
					if (Result.Code.SUCCESS.getValue().equals(result.getCode())) {
						clearGameRecordHistiry(gameRecords);
					}
				} catch (Exception e) {
					logger.error("uploadGameRecord error", e);;
				}
			}else{
				logger.warn("请求url为空");
			}
		}else
		{
			logger.debug("上传数据为空");
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
}
