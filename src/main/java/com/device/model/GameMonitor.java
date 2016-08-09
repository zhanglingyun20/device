package com.device.model;

public class GameMonitor {
    /**
     * 游戏id
     */
    private Integer id;

    /**
     * 游戏id
     */
    private Integer gameId;

    /**
     * 游戏编码
     */
    private String gameCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }
}