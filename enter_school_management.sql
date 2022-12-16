/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : enter_school_management

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 16/12/2022 15:19:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_type` int(0) NOT NULL,
  `admin_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_phnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `admin_idtype` int(0) NOT NULL,
  `admin_idnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`admin_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('00000000', 3, '程序员', '13818185120', 'cxy@gmail.com', 1, '881164752548593');
INSERT INTO `admin` VALUES ('12345678', 1, '杨范', '13818485110', 'yf@gmail.com', 1, '881157822548593');
INSERT INTO `admin` VALUES ('87654321', 2, '付雁', '13818185110', 'fy@gmail.com', 1, '881154752548593');

-- ----------------------------
-- Table structure for campus
-- ----------------------------
DROP TABLE IF EXISTS `campus`;
CREATE TABLE `campus`  (
  `campus_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `campus_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `campus_status` int(0) NOT NULL,
  PRIMARY KEY (`campus_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of campus
-- ----------------------------
INSERT INTO `campus` VALUES (1, '邯郸', 1);
INSERT INTO `campus` VALUES (2, '枫林', 1);
INSERT INTO `campus` VALUES (3, '江湾', 1);
INSERT INTO `campus` VALUES (4, '张江', 1);

-- ----------------------------
-- Table structure for comin_approve
-- ----------------------------
DROP TABLE IF EXISTS `comin_approve`;
CREATE TABLE `comin_approve`  (
  `comin_app_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `comin_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exp_comindate` date NOT NULL,
  `status` int(0) NOT NULL,
  `current_admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reject_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `current_date` date NOT NULL,
  PRIMARY KEY (`comin_app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comin_approve
-- ----------------------------
INSERT INTO `comin_approve` VALUES (1, '20302010061', '回学校', '2022-12-02', 1, NULL, NULL, '2022-12-02');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `dept_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `dept_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept_admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '计算机科学技术学院', '87654321');

-- ----------------------------
-- Table structure for health_daily
-- ----------------------------
DROP TABLE IF EXISTS `health_daily`;
CREATE TABLE `health_daily`  (
  `hd_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `stu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `body_temperature` double NOT NULL,
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `district` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `street` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` date NOT NULL,
  `time` time(0) NOT NULL,
  `hd_status` int(0) NULL DEFAULT NULL,
  PRIMARY KEY (`hd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of health_daily
-- ----------------------------
INSERT INTO `health_daily` VALUES (1, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-12-01', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (2, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-12-02', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (3, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-12-03', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (4, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-11-30', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (5, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-11-29', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (6, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-11-28', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (7, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-11-27', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (8, '20302010061', 36.6, '上海', '杨浦', '五角场街道', '2022-11-26', '09:50:33', NULL);
INSERT INTO `health_daily` VALUES (9, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:06:59', NULL);
INSERT INTO `health_daily` VALUES (10, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:07:55', NULL);
INSERT INTO `health_daily` VALUES (11, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:08:39', NULL);
INSERT INTO `health_daily` VALUES (12, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:09:36', NULL);
INSERT INTO `health_daily` VALUES (13, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:12:19', NULL);
INSERT INTO `health_daily` VALUES (14, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:12:36', NULL);
INSERT INTO `health_daily` VALUES (15, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:13:37', NULL);
INSERT INTO `health_daily` VALUES (16, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:14:17', NULL);
INSERT INTO `health_daily` VALUES (17, '20302010061', 37.8, '上海', '杨浦', '五角场街道', '2022-12-04', '22:18:37', 0);
INSERT INTO `health_daily` VALUES (18, '20302010061', 36.8, '杭州', '杨浦', '五角场街道', '2022-12-04', '22:20:50', 0);
INSERT INTO `health_daily` VALUES (19, '20302010061', 36.8, '上海', '杨浦', '长海路街道', '2022-12-04', '22:21:00', 0);
INSERT INTO `health_daily` VALUES (20, '20302010061', 36.8, '上海', '杨浦', '西八街道', '2022-12-04', '22:21:08', NULL);
INSERT INTO `health_daily` VALUES (21, '20302010061', 36.8, '上海', '杨浦', '西八街道', '2022-12-04', '22:23:05', NULL);
INSERT INTO `health_daily` VALUES (22, '20302010061', 36.8, '上海', '杨浦', '西八街道', '2022-12-04', '22:24:09', 1);

-- ----------------------------
-- Table structure for leave_application
-- ----------------------------
DROP TABLE IF EXISTS `leave_application`;
CREATE TABLE `leave_application`  (
  `leav_app_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `leav_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dest_city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dest_district` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dest_street` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `exp_leavdate` date NOT NULL,
  `exp_retdate` date NOT NULL,
  `status` int(0) NOT NULL,
  `current_admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `reject_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `current_date` date NOT NULL,
  PRIMARY KEY (`leav_app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leave_application
-- ----------------------------
INSERT INTO `leave_application` VALUES (1, '20302010061', '回家', '上海', '杨浦', '长海路街道', '2022-12-01', '2022-12-02', 1, NULL, NULL, '2022-12-02');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `log_date` date NOT NULL,
  `log_time` time(0) NOT NULL,
  `log_status` int(0) NOT NULL,
  `campus_id` bigint(0) NOT NULL,
  `dept_id` bigint(0) NOT NULL,
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES (1, '20302010061', '2022-12-02', '10:21:03', 0, 1, 1);
INSERT INTO `log` VALUES (3, '20302010061', '2022-12-06', '10:21:03', 0, 1, 1);
INSERT INTO `log` VALUES (4, '20302010061', '2022-12-06', '10:21:20', 1, 1, 1);
INSERT INTO `log` VALUES (5, '20302010061', '2022-12-07', '10:21:20', 1, 1, 1);
INSERT INTO `log` VALUES (6, '20302010061', '2022-12-07', '22:01:20', 0, 1, 1);
INSERT INTO `log` VALUES (7, '20302010061', '2022-12-07', '22:21:20', 1, 1, 1);
INSERT INTO `log` VALUES (8, '20302010061', '2022-12-07', '08:21:20', 0, 1, 1);
INSERT INTO `log` VALUES (9, '20302010061', '2022-12-07', '23:12:55', 0, 1, 1);

-- ----------------------------
-- Table structure for outside_time
-- ----------------------------
DROP TABLE IF EXISTS `outside_time`;
CREATE TABLE `outside_time`  (
  `ot_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `date` date NOT NULL,
  `ot_time` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`ot_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1600742829727182855 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of outside_time
-- ----------------------------
INSERT INTO `outside_time` VALUES (1, '20302010061', '2022-12-02', '424');
INSERT INTO `outside_time` VALUES (1600742829727182850, '20302010061', '2022-12-07', '86400000');
INSERT INTO `outside_time` VALUES (1600742829727182851, '20302010061', '2022-12-07', '0');
INSERT INTO `outside_time` VALUES (1600742829727182852, '20302010061', '2022-12-07', '38480000');
INSERT INTO `outside_time` VALUES (1600742829727182853, '20302010061', '2022-12-07', '8400000');
INSERT INTO `outside_time` VALUES (1600742829727182854, '20302010061', '2022-12-07', '11224000');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `perm_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `campus_id` bigint(0) NOT NULL,
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `permit_status` int(0) NOT NULL,
  PRIMARY KEY (`perm_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 1, '20302010061', 1);

-- ----------------------------
-- Table structure for risky_places
-- ----------------------------
DROP TABLE IF EXISTS `risky_places`;
CREATE TABLE `risky_places`  (
  `rp_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `district` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `street` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`rp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risky_places
-- ----------------------------
INSERT INTO `risky_places` VALUES (1, '上海', '杨浦', '长海路街道');

-- ----------------------------
-- Table structure for school
-- ----------------------------
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `school_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `school_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `school_city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`school_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of school
-- ----------------------------
INSERT INTO `school` VALUES (1, '复旦大学', '上海');
INSERT INTO `school` VALUES (2, '复旦大学', '上海');

-- ----------------------------
-- Table structure for stu_class
-- ----------------------------
DROP TABLE IF EXISTS `stu_class`;
CREATE TABLE `stu_class`  (
  `class_id` bigint(0) NOT NULL AUTO_INCREMENT,
  `class_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `dept_id` bigint(0) NOT NULL,
  `instructor_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  PRIMARY KEY (`class_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_class
-- ----------------------------
INSERT INTO `stu_class` VALUES (1, '软工1班', 1, '12345678');

-- ----------------------------
-- Table structure for student
-- ----------------------------
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_phnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_idtype` int(0) NOT NULL,
  `stu_idnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `stu_class_id` bigint(0) NOT NULL,
  `stu_school_id` bigint(0) NOT NULL,
  `hd_updated` int(0) NOT NULL,
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('20302010061', '谢子璇', '13818485220', 'xzx@gmail.com', 1, '881157829348593', 1, 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
