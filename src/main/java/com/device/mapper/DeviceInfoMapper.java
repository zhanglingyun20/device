package com.device.mapper;

import java.util.List;

import com.device.model.DeviceInfo;
import com.device.model.Game;

public interface DeviceInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(DeviceInfo record);
    int insertSelective(DeviceInfo record);

    DeviceInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DeviceInfo record);

    int updateByPrimaryKey(DeviceInfo record);
    
    List<DeviceInfo> getAllDevices();
    
    List<DeviceInfo> getNoSync();
    
    int updateSync();
}