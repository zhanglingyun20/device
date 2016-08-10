package com.device.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.device.model.Game;

public interface GameMapper {


    int deleteByPrimaryKey(Integer id);

    int insert(Game record);

    int insertSelective(Game record);

    Game selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Game record);

    int updateByPrimaryKey(Game record);
    
    List<Game> getAllGames();
    
    Game findByProcessName(@Param("processName")String processName);
}