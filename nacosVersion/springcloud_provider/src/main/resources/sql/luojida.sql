/*
Navicat MySQL Data Transfer

Source Server         : 444
Source Server Version : 50736
Source Host           : localhost:3306
Source Database       : luojida

Target Server Type    : MYSQL
Target Server Version : 50736
File Encoding         : 65001

Date: 2023-03-19 21:48:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for apply_info
-- ----------------------------
DROP TABLE IF EXISTS `apply_info`;
CREATE TABLE `apply_info` (
  `apply_id` bigint(20) NOT NULL,
  `group_code` varchar(255) DEFAULT NULL,
  `calc_scene_code` varchar(255) DEFAULT NULL,
  `buss_scene_code` varchar(255) DEFAULT NULL,
  `apply_name` varchar(255) DEFAULT NULL,
  `role_node` varchar(255) DEFAULT NULL,
  `check_right_mode` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`apply_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for dimension_right
-- ----------------------------
DROP TABLE IF EXISTS `dimension_right`;
CREATE TABLE `dimension_right` (
  `dimension_right_id` bigint(20) NOT NULL,
  `role_code` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `group_code_list` varchar(255) DEFAULT NULL,
  `calc_scene_list` varchar(255) DEFAULT NULL,
  `buss_snene_list` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`dimension_right_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for hobby
-- ----------------------------
DROP TABLE IF EXISTS `hobby`;
CREATE TABLE `hobby` (
  `hobby_id` int(11) DEFAULT NULL,
  `hobby_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login_user
-- ----------------------------
DROP TABLE IF EXISTS `login_user`;
CREATE TABLE `login_user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `nick_name` varchar(255) DEFAULT NULL,
  `hobby_id` varchar(255) DEFAULT NULL,
  `school_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for my_order
-- ----------------------------
DROP TABLE IF EXISTS `my_order`;
CREATE TABLE `my_order` (
  `order_id` bigint(64) NOT NULL,
  `order_no` varchar(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school` (
  `school_id` int(11) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for serial_t
-- ----------------------------
DROP TABLE IF EXISTS `serial_t`;
CREATE TABLE `serial_t` (
  `serial_id` bigint(20) NOT NULL,
  `serial_no` bigint(20) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `attr1` varchar(255) DEFAULT NULL,
  `attr2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`serial_id`),
  KEY `idx_serial_n1` (`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for update_test
-- ----------------------------
DROP TABLE IF EXISTS `update_test`;
CREATE TABLE `update_test` (
  `update_test_id` int(11) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `user_name_en` varchar(255) DEFAULT NULL,
  `school_name` varchar(255) DEFAULT NULL,
  `teacher_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`update_test_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
