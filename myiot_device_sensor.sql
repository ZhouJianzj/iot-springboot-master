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

 Date: 16/03/2022 10:45:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for myiot_device_sensor
-- ----------------------------
DROP TABLE IF EXISTS `myiot_device_sensor`;
CREATE TABLE `myiot_device_sensor`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sensorId` int(11) NULL DEFAULT NULL,
  `deviceId` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of myiot_device_sensor
-- ----------------------------
INSERT INTO `myiot_device_sensor` VALUES (1, 1, 1);
INSERT INTO `myiot_device_sensor` VALUES (15, 2, 1);
INSERT INTO `myiot_device_sensor` VALUES (16, 3, 1);
INSERT INTO `myiot_device_sensor` VALUES (17, 4, 1);
INSERT INTO `myiot_device_sensor` VALUES (18, 1, 2);
INSERT INTO `myiot_device_sensor` VALUES (19, 2, 2);
INSERT INTO `myiot_device_sensor` VALUES (20, 3, 2);
INSERT INTO `myiot_device_sensor` VALUES (21, 4, 2);
INSERT INTO `myiot_device_sensor` VALUES (22, 1, 3);
INSERT INTO `myiot_device_sensor` VALUES (23, 2, 3);
INSERT INTO `myiot_device_sensor` VALUES (24, 1, 4);
INSERT INTO `myiot_device_sensor` VALUES (25, 2, 4);
INSERT INTO `myiot_device_sensor` VALUES (28, 3, 6);
INSERT INTO `myiot_device_sensor` VALUES (29, 4, 6);
INSERT INTO `myiot_device_sensor` VALUES (33, 1, 16);
INSERT INTO `myiot_device_sensor` VALUES (37, 3, 18);
INSERT INTO `myiot_device_sensor` VALUES (38, 4, 18);

SET FOREIGN_KEY_CHECKS = 1;
