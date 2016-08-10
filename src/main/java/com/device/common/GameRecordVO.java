package com.device.common;

import java.util.Date;

public class GameRecordVO {

	private String gameCode;
	private Integer runCount;
	private Date createTime;
	private String processName;

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



	public GameRecordVO(String gameCode, Integer runCount, Date createTime)
	{
		super();
		this.gameCode = gameCode;
		this.runCount = runCount;
		this.createTime = createTime;
	}

	
	@Override
	public String toString()
	{
		return "GameRecordVO [gameCode=" + gameCode + ", runCount=" + runCount + ", createTime=" + createTime + "]";
	}

	
	public String getProcessName()
	{
		return processName;
	}

	public void setProcessName(String processName)
	{
		this.processName = processName;
	}

	public GameRecordVO()
	{
		super();
	}
}
