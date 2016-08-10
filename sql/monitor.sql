/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50714
Source Host           : localhost:3306
Source Database       : monitor

Target Server Type    : MYSQL
Target Server Version : 50714
File Encoding         : 65001

Date: 2016-08-11 01:00:47
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for device_info
-- ----------------------------
DROP TABLE IF EXISTS `device_info`;
CREATE TABLE `device_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(20) DEFAULT NULL COMMENT '账号',
  `username` varchar(20) DEFAULT '' COMMENT '用户名',
  `password` varchar(20) DEFAULT '' COMMENT '密码',
  `device_mac` varchar(20) DEFAULT NULL COMMENT 'VR唯一标识mac',
  `device_code` varchar(20) DEFAULT NULL COMMENT 'VR设备编号',
  `device_name` varchar(20) DEFAULT NULL COMMENT 'VR设备名称',
  `site_name` varchar(50) DEFAULT NULL COMMENT '场地名称',
  `province_code` varchar(20) DEFAULT NULL COMMENT '省编码',
  `city_code` varchar(20) DEFAULT NULL COMMENT '城市编码',
  `site_code` varchar(20) DEFAULT NULL COMMENT '场地编码',
  `is_sync` varchar(10) DEFAULT NULL COMMENT '是否已经同步到服务端',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='设备信息表';

-- ----------------------------
-- Records of device_info
-- ----------------------------
INSERT INTO `device_info` VALUES ('1', 'zly@163.com', 'sawyer', '123456', '00000000000000000', 'NO.1', '深圳', 'sawyer的场地', null, null, null, null, null);

-- ----------------------------
-- Table structure for game
-- ----------------------------
DROP TABLE IF EXISTS `game`;
CREATE TABLE `game` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '游戏id',
  `game_code` varchar(50) DEFAULT NULL COMMENT '游戏编码',
  `game_name` varchar(50) DEFAULT NULL COMMENT '游戏名称',
  `game_version` varchar(50) DEFAULT NULL COMMENT '游戏版本',
  `game_process` varchar(100) DEFAULT NULL COMMENT '游戏进程',
  `state` varchar(10) DEFAULT NULL COMMENT '游戏状态',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='游戏表';

-- ----------------------------
-- Records of game
-- ----------------------------
INSERT INTO `game` VALUES ('1', 'qq', 'qq游戏', '1.0', 'qq.exe', 'active', '2016-08-09 21:52:31');
INSERT INTO `game` VALUES ('2', 'eclipse', 'eclipse', '1.0', 'eclipse.exe', 'active', '2016-08-09 21:53:19');
INSERT INTO `game` VALUES ('3', 'chrome', 'chrome', '1.0', 'chrome.exe', 'active', '2016-08-10 00:55:28');

-- ----------------------------
-- Table structure for game_monitor
-- ----------------------------
DROP TABLE IF EXISTS `game_monitor`;
CREATE TABLE `game_monitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '游戏id',
  `game_id` int(11) NOT NULL COMMENT '游戏id',
  `game_code` varchar(50) DEFAULT NULL COMMENT '游戏编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='游戏监控表';

-- ----------------------------
-- Records of game_monitor
-- ----------------------------

-- ----------------------------
-- Table structure for game_run_record
-- ----------------------------
DROP TABLE IF EXISTS `game_run_record`;
CREATE TABLE `game_run_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `game_id` int(11) NOT NULL COMMENT '游戏id',
  `game_code` varchar(50) DEFAULT NULL COMMENT '游戏编码',
  `run_count` int(11) NOT NULL COMMENT '运行次数',
  `is_sync` varchar(10) DEFAULT NULL COMMENT '是否已经同步到服务端',
  `game_process` varchar(50) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='游戏运行记录表';

-- ----------------------------
-- Records of game_run_record
-- ----------------------------

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key_name` varchar(50) DEFAULT NULL COMMENT '配置key名称',
  `key_value` varchar(50) DEFAULT NULL COMMENT '配置key值',
  `key_remark` varchar(50) DEFAULT NULL COMMENT '描述',
  `is_valid` varchar(10) DEFAULT NULL COMMENT '是否有效',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统配置表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'server_domain', 'http://localhost:8080/management', null, null, '2016-08-10 23:26:19');
INSERT INTO `sys_config` VALUES ('2', 'sync_game_record', '/sync/game_run_record', null, null, '2016-08-10 23:26:42');
INSERT INTO `sys_config` VALUES ('3', 'game_pull', '/sync/get_games', null, null, '2016-08-10 23:36:45');
INSERT INTO `sys_config` VALUES ('4', 'active_user', '/sync/active_account', null, null, '2016-08-10 23:37:31');
