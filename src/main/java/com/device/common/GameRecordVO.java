package com.device.common;

import java.util.Date;

public class GameRecordVO {

	private String gameCode;
	private String gameName;
	private String gameProcess;
	private Integer runCount;
	private Date createTime;

	
	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getGameProcess() {
		return gameProcess;
	}

	public void setGameProcess(String gameProcess) {
		this.gameProcess = gameProcess;
	}

	public String getGameCode()
	{
		return gameCode;
	}

	public void setGameCode(String gameCode)
	{
		this.gameCode = gameCode;
	}



	public Integer getRunCount()
	{
		return runCount;
	}

	public void setRunCount(Integer runCount)
	{
		this.runCount = runCount;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}





	@Override
	public String toString() {
		return "GameRecordVO [gameCode=" + gameCode + ", gameName=" + gameName
				+ ", gameProcess=" + gameProcess + ", runCount=" + runCount
				+ ", createTime=" + createTime + "]";
	}

	public GameRecordVO(String gameCode, String gameName, String gameProcess,
			Integer runCount, Date createTime) {
		super();
		this.gameCode = gameCode;
		this.gameName = gameName;
		this.gameProcess = gameProcess;
		this.runCount = runCount;
		this.createTime = createTime;
	}

	public GameRecordVO()
	{
		super();
	}
}
