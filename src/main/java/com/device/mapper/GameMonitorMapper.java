package com.device.mapper;

import com.device.model.GameMonitor;

public interface GameMonitorMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(GameMonitor record);
    int insertSelective(GameMonitor record);

    GameMonitor selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(GameMonitor record);

    int updateByPrimaryKey(GameMonitor record);
}