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

 Date: 16/03/2022 10:45:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for myiot_sensor
-- ----------------------------
DROP TABLE IF EXISTS `myiot_sensor`;
CREATE TABLE `myiot_sensor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of myiot_sensor
-- ----------------------------
INSERT INTO `myiot_sensor` VALUES (1, '温度传感器');
INSERT INTO `myiot_sensor` VALUES (2, '压力传感器');
INSERT INTO `myiot_sensor` VALUES (3, '气压传感器');
INSERT INTO `myiot_sensor` VALUES (4, '油液传感器');
INSERT INTO `myiot_sensor` VALUES (7, 'test');
INSERT INTO `myiot_sensor` VALUES (9, 'zhoujian');

SET FOREIGN_KEY_CHECKS = 1;
