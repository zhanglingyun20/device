package com.device.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    
    private Double defaultPrice;

    private Integer billingTime;
    
	public enum State {
		ACTIVE("active"), FORBIDDEN("forbidden");

		private final String value;

		State(String value)
		{
			this.value = value;
		}

		public String getValue()
		{
			return value;
		}
	}
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

	public static List<String> getStates(){
		List<String> typeList = new ArrayList<String>();
		State states[] = Game.State.values();
		for (State item : states) {
			typeList.add(item.getValue());
		}
		return typeList;
	}
	
	
	
	public Double getDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(Double defaultPrice) {
		this.defaultPrice = defaultPrice;
	}



	@Override
	public String toString() {
		return "Game [id=" + id + ", gameCode=" + gameCode + ", gameName="
				+ gameName + ", gameVersion=" + gameVersion + ", gameProcess="
				+ gameProcess + ", state=" + state + ", createTime="
				+ createTime + ", defaultPrice=" + defaultPrice
				+ ", billingTime=" + billingTime + "]";
	}

	public Game(Integer id, String gameCode, String gameName,
			String gameVersion, String gameProcess, String state,
			Date createTime, Double defaultPrice, Integer billingTime) {
		super();
		this.id = id;
		this.gameCode = gameCode;
		this.gameName = gameName;
		this.gameVersion = gameVersion;
		this.gameProcess = gameProcess;
		this.state = state;
		this.createTime = createTime;
		this.defaultPrice = defaultPrice;
		this.billingTime = billingTime;
	}

	public Game() {
		super();
	}

	public Integer getBillingTime() {
		return billingTime;
	}

	public void setBillingTime(Integer billingTime) {
		this.billingTime = billingTime;
	}
    
	
}