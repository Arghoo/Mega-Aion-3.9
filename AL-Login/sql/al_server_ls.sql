/*
Navicat MySQL Data Transfer

Source Server         : MegAion Database
Source Server Version : 50557
Source Host           : localhost:3306
Source Database       : al_server_ls

Target Server Type    : MYSQL
Target Server Version : 50557
File Encoding         : 65001

Date: 2025-02-28 16:30:37
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `account_data`
-- ----------------------------
DROP TABLE IF EXISTS `account_data`;
CREATE TABLE `account_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `password` varchar(65) NOT NULL,
  `activated` tinyint(1) NOT NULL DEFAULT '1',
  `access_level` tinyint(3) NOT NULL DEFAULT '0',
  `membership` tinyint(3) NOT NULL DEFAULT '0',
  `old_membership` tinyint(3) NOT NULL DEFAULT '0',
  `last_server` tinyint(3) NOT NULL DEFAULT '-1',
  `last_ip` varchar(20) DEFAULT NULL,
  `last_mac` varchar(20) NOT NULL DEFAULT 'xx-xx-xx-xx-xx-xx',
  `ip_force` varchar(20) DEFAULT NULL,
  `toll` bigint(13) NOT NULL DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  `pin` varchar(6) DEFAULT NULL,
  `membershipExpiry` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=285 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_data
-- ----------------------------
INSERT INTO `account_data` VALUES ('277', 'admin', 'coh2v8skasPS8VjPw3A9eribQT8=', '1', '3', '2', '0', '1', '24.171.68.115', '50-EB-F6-CE-48-F9', null, '99000', null, null, null);
INSERT INTO `account_data` VALUES ('278', 'arghoke', '9MrYP7Pwe81kdDjXPBkx4M3g85Q=', '1', '3', '2', '0', '1', '5.49.25.137', '08-62-66-B6-F2-89', null, '95550', null, null, null);
INSERT INTO `account_data` VALUES ('279', 'waterbomb', 'X6TWAgcjN8hVIAmomerxZfVlDDs=', '1', '3', '2', '0', '1', '189.115.15.179', '04-BF-1B-89-A4-63', null, '100000', null, null, null);
INSERT INTO `account_data` VALUES ('280', 'maxkirby', 'g48byUYKI+OOWMckyoryC3HseZE=', '1', '3', '0', '0', '1', '83.204.207.90', '70-4D-7B-86-E0-A8', null, '20000', null, null, null);
INSERT INTO `account_data` VALUES ('281', 'wendell', 'rRuDt439ySgBfIg6QVqEk1cgg9I=', '1', '3', '0', '0', '1', '45.168.200.183', 'A8-A1-59-3C-85-D1', null, '20000', null, null, null);
INSERT INTO `account_data` VALUES ('282', 'testos', 'uxb/rzKhqGOYGqCtnED4P62EOpk=', '1', '0', '0', '0', '1', '223.206.233.229', '50-7B-9D-CD-52-DB', null, '0', null, null, null);
INSERT INTO `account_data` VALUES ('283', 'arghokee', '9MrYP7Pwe81kdDjXPBkx4M3g85Q=', '1', '0', '2', '0', '1', '5.49.25.137', '08-62-66-B6-F2-89', null, '199000', null, null, '2025-03-30 11:16:44');
INSERT INTO `account_data` VALUES ('284', 'tawwibg', 'CSQMvYEuaNDUyBptqk4dIyveohQ=', '1', '0', '2', '0', '1', '79.100.220.159', '34-E6-D7-57-63-2C', null, '15300', null, null, '2025-03-30 11:12:46');

-- ----------------------------
-- Table structure for `account_rewards`
-- ----------------------------
DROP TABLE IF EXISTS `account_rewards`;
CREATE TABLE `account_rewards` (
  `uniqId` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `added` varchar(70) NOT NULL DEFAULT '',
  `points` decimal(20,0) NOT NULL DEFAULT '0',
  `received` varchar(70) NOT NULL DEFAULT '0',
  `rewarded` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`uniqId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_rewards
-- ----------------------------

-- ----------------------------
-- Table structure for `account_time`
-- ----------------------------
DROP TABLE IF EXISTS `account_time`;
CREATE TABLE `account_time` (
  `account_id` int(11) NOT NULL,
  `last_active` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `expiration_time` timestamp NULL DEFAULT NULL,
  `session_duration` int(10) DEFAULT '0',
  `accumulated_online` int(10) DEFAULT '0',
  `accumulated_rest` int(10) DEFAULT '0',
  `penalty_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`account_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of account_time
-- ----------------------------
INSERT INTO `account_time` VALUES ('277', '2025-02-27 07:24:04', null, '101312', '1205809', '12152864', null);
INSERT INTO `account_time` VALUES ('278', '2025-02-28 16:13:32', null, '5823087', '12838622', '9645655', null);
INSERT INTO `account_time` VALUES ('279', '2025-02-25 17:58:06', null, '115938', '115938', '0', null);
INSERT INTO `account_time` VALUES ('280', '2025-02-28 10:37:56', null, '8111618', '8111618', '0', null);
INSERT INTO `account_time` VALUES ('281', '2025-02-28 01:22:47', null, '970595', '970595', '0', null);
INSERT INTO `account_time` VALUES ('282', '2025-02-27 10:27:19', null, '611331', '611331', '0', null);
INSERT INTO `account_time` VALUES ('283', '2025-02-28 10:17:21', null, '4673213', '5354725', '49171', null);
INSERT INTO `account_time` VALUES ('284', '2025-02-28 10:01:45', null, '2524222', '2524222', '0', null);

-- ----------------------------
-- Table structure for `banned_ip`
-- ----------------------------
DROP TABLE IF EXISTS `banned_ip`;
CREATE TABLE `banned_ip` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mask` varchar(45) NOT NULL,
  `time_end` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mask` (`mask`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banned_ip
-- ----------------------------

-- ----------------------------
-- Table structure for `banned_mac`
-- ----------------------------
DROP TABLE IF EXISTS `banned_mac`;
CREATE TABLE `banned_mac` (
  `uniId` int(10) NOT NULL AUTO_INCREMENT,
  `address` varchar(20) NOT NULL,
  `time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `details` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`uniId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of banned_mac
-- ----------------------------

-- ----------------------------
-- Table structure for `gameservers`
-- ----------------------------
DROP TABLE IF EXISTS `gameservers`;
CREATE TABLE `gameservers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mask` varchar(45) NOT NULL,
  `password` varchar(65) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of gameservers
-- ----------------------------
INSERT INTO `gameservers` VALUES ('1', '62.171.173.57', 'Azertyuiop789');

-- ----------------------------
-- Table structure for `player_transfers`
-- ----------------------------
DROP TABLE IF EXISTS `player_transfers`;
CREATE TABLE `player_transfers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `source_server` tinyint(3) NOT NULL,
  `target_server` tinyint(3) NOT NULL,
  `source_account_id` int(11) NOT NULL,
  `target_account_id` int(11) NOT NULL,
  `player_id` int(11) NOT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `time_added` varchar(100) DEFAULT NULL,
  `time_performed` varchar(100) DEFAULT NULL,
  `time_done` varchar(100) DEFAULT NULL,
  `comment` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of player_transfers
-- ----------------------------

-- ----------------------------
-- Table structure for `tasks`
-- ----------------------------
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `task_type` enum('SHUTDOWN','RESTART','CLEAN_ACCOUNTS') NOT NULL,
  `trigger_type` enum('FIXED_IN_TIME','AFTER_RESTART') NOT NULL,
  `exec_param` text,
  `trigger_param` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tasks
-- ----------------------------
