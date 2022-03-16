/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50528
 Source Host           : localhost:3306
 Source Schema         : my_iot

 Target Server Type    : MySQL
 Target Server Version : 50528
 File Encoding         : 65001

 Date: 16/03/2022 10:45:22
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for myiot_device
-- ----------------------------
DROP TABLE IF EXISTS `myiot_device`;
CREATE TABLE `myiot_device`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of myiot_device
-- ----------------------------
INSERT INTO `myiot_device` VALUES (1, '数控车床');
INSERT INTO `myiot_device` VALUES (2, '机械手臂');
INSERT INTO `myiot_device` VALUES (3, '铣床');
INSERT INTO `myiot_device` VALUES (4, '牛头刨');
INSERT INTO `myiot_device` VALUES (6, 'test1');
INSERT INTO `myiot_device` VALUES (16, '飞机');
INSERT INTO `myiot_device` VALUES (18, 'zhoujian');

SET FOREIGN_KEY_CHECKS = 1;
