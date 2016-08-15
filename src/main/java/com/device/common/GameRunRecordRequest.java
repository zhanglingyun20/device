package com.device.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.device.model.DeviceInfo;
import com.device.model.GameRunRecord;

/**
 * 
 * <b>Descriptions:</b>
 * <p>
 * 游戏记录同步请求实体
 * </p>
 * 
 * @author sawyer
 * @createDate 2016年8月6日
 * @file GameRunRecordRequest.java
 * @package com.management.service.sync
 * @project management
 * @version 1.0
 *
 */
public class GameRunRecordRequest {

	/**
	 * 场地账号
	 */
	private String account;
	/**
	 * 设备mac
	 */
	private String deviceMac;

	private String deviceCode;
	/**
	 * 设备名称
	 */
	private String deviceName;

	/**
	 * 记录上传时间
	 */
	private Date uploadTime;

	private List<GameRecordVO> record;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getDeviceMac() {
		return deviceMac;
	}

	public void setDeviceMac(String deviceMac) {
		this.deviceMac = deviceMac;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public List<GameRecordVO> getRecord() {
		return record;
	}

	public void setRecord(List<GameRecordVO> record) {
		this.record = record;
	}

	public GameRunRecordRequest(String account, String deviceMac,
			String deviceCode, String deviceName, Date uploadTime,
			List<GameRecordVO> record) {
		super();
		this.account = account;
		this.deviceMac = deviceMac;
		this.deviceCode = deviceCode;
		this.deviceName = deviceName;
		this.uploadTime = uploadTime;
		this.record = record;
	}

	public GameRunRecordRequest() {
		super();
	}

	@Override
	public String toString() {
		return "GameRunRecordRequest [account=" + account + ", deviceMac="
				+ deviceMac + ", deviceCode=" + deviceCode + ", deviceName="
				+ deviceName + ", uploadTime=" + uploadTime + ", record="
				+ record + "]";
	}

	public GameRunRecordRequest(DeviceInfo deviceInfo,List<GameRunRecord> gameRecords) {
		setAccount(deviceInfo.getAccount());
		setDeviceCode(deviceInfo.getDeviceCode());
		setDeviceMac(deviceInfo.getDeviceMac());
		setDeviceName(deviceInfo.getDeviceName());
		setUploadTime(new Date());
		List<GameRecordVO> recordVos = new ArrayList<GameRecordVO>();
		if (gameRecords!=null&&!gameRecords.isEmpty()) {
			for (GameRunRecord gameRunRecord : gameRecords) {
				GameRecordVO vo = new GameRecordVO();
				vo.setCreateTime(gameRunRecord.getCreateTime());
				vo.setGameCode(gameRunRecord.getGameCode());
				vo.setGameName(gameRunRecord.getGameName());
				vo.setRunCount(gameRunRecord.getRunCount());
				vo.setGameProcess(gameRunRecord.getGameProcess());
				recordVos.add(vo);
			}
		}
		setRecord(recordVos);
	}
}
