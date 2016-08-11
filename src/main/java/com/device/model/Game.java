package com.device.model;

import java.util.Date;

public class Game {
    /**
     * 游戏id
     */
    private Integer id;

    /**
     * 游戏编码
     */
    private String gameCode;

    /**
     * 游戏名称
     */
    private String gameName;

    /**
     * 游戏版本
     */
    private String gameVersion;

    /**
     * 游戏进程
     */
    private String gameProcess;

    /**
     * 游戏状态
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getGameVersion() {
        return gameVersion;
    }

    public void setGameVersion(String gameVersion) {
        this.gameVersion = gameVersion;
    }

    public String getGameProcess() {
        return gameProcess;
    }

    public void setGameProcess(String gameProcess) {
        this.gameProcess = gameProcess;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
	public enum Sync {
		YES("yes"), 
		NO("no");
		private final String value;

		Sync(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}

	}
}