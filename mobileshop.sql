/*
Navicat MySQL Data Transfer

Source Server         : 连接
Source Server Version : 50096
Source Host           : localhost:3306
Source Database       : mobileshop

Target Server Type    : MYSQL
Target Server Version : 50096
File Encoding         : 65001

Date: 2022-01-04 21:06:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for mobileclassify
-- ----------------------------
DROP TABLE IF EXISTS `mobileclassify`;
CREATE TABLE `mobileclassify` (
  `id` int(11) NOT NULL auto_increment,
  `name` char(200) NOT NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobileclassify
-- ----------------------------
INSERT INTO `mobileclassify` VALUES ('1', 'iPhone');
INSERT INTO `mobileclassify` VALUES ('2', 'Android');
INSERT INTO `mobileclassify` VALUES ('3', 'Win Phone手机');

-- ----------------------------
-- Table structure for mobileform
-- ----------------------------
DROP TABLE IF EXISTS `mobileform`;
CREATE TABLE `mobileform` (
  `mobile_version` char(50) NOT NULL,
  `mobile_name` char(60) default NULL,
  `mobile_made` char(200) default NULL,
  `mobile_price` float(4,0) default NULL,
  `mobile_mess` char(255) default NULL,
  `mobile_pic` char(200) NOT NULL,
  `id` int(11) NOT NULL,
  PRIMARY KEY  (`mobile_version`,`mobile_pic`),
  KEY `id` (`id`),
  CONSTRAINT `mobileform_ibfk_1` FOREIGN KEY (`id`) REFERENCES `mobileclassify` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mobileform
-- ----------------------------
INSERT INTO `mobileform` VALUES ('A89S6', '苹果S5手机', '苹果公司', '9876', '高清大屏幕', 'apple.jpg', '1');
INSERT INTO `mobileform` VALUES ('B8978', '三星A98手机', '三星公司', '8976', '支持图形非常好', 'aa.jpg', '2');
INSERT INTO `mobileform` VALUES ('C555', '中兴N986', '中兴公司', '3567', '双卡双待', 'cc.jpg', '3');

-- ----------------------------
-- Table structure for orderform
-- ----------------------------
DROP TABLE IF EXISTS `orderform`;
CREATE TABLE `orderform` (
  `id` int(10) NOT NULL auto_increment,
  `logname` char(255) default NULL,
  `mess` char(255) default NULL,
  `sum` float default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orderform
-- ----------------------------
INSERT INTO `orderform` VALUES ('1', 'pikaqiu', '1:(A89S6,苹果S5手机,苹果公司,9876.0)', '9876');
INSERT INTO `orderform` VALUES ('2', 'aa', '1:(B8978,三星A98手机,三星公司,8976.0)', '8976');
INSERT INTO `orderform` VALUES ('3', 'aa', '1:(B8978,三星A98手机,三星公司,8976.0)', '8976');
INSERT INTO `orderform` VALUES ('4', 'aa', '1:(B8978,三星A98手机,三星公司,8976.0)', '8976');
INSERT INTO `orderform` VALUES ('5', 'aa', '1:(B8978,三星A98手机,三星公司,8976.0)', '8976');
INSERT INTO `orderform` VALUES ('6', 'aa', '1:(B8978,三星A98手机,三星公司,8976.0)', '8976');
INSERT INTO `orderform` VALUES ('7', 'aa', '1:(A89S6,苹果S5手机,苹果公司,9876.0)', '9876');
INSERT INTO `orderform` VALUES ('8', 'aa', '1:(A89S6,苹果S5手机,苹果公司,9876.0)', '9876');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `logname` char(255) NOT NULL,
  `password` char(255) default NULL,
  `phone` char(255) default NULL,
  `address` char(255) default NULL,
  `realname` char(255) default NULL,
  PRIMARY KEY  (`logname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('aa', '121212', '13731475683', '1', 'dd');
INSERT INTO `user` VALUES ('ab', '123456', '13731475683', '河北保定顺平县', 'dd');
