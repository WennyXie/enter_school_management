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

 Date: 22/12/2022 15:55:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for admin
-- ----------------------------
-- 存储所有级别管理员的表
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin`  (
  `admin_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 管理员用户名
  `admin_type` int(0) NOT NULL, -- 管理员类型 1对应辅导员，2对应院系管理员，3对应超级管理员
  `admin_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 管理员姓名
  `admin_phnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 管理员手机号码
  `admin_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 管理员邮箱
  `admin_idtype` int(0) NOT NULL, -- 管理员身份证件类型
  `admin_idnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 管理员身份证件号码
  PRIMARY KEY (`admin_id`) USING BTREE,
  UNIQUE KEY `admin_idnum` (`admin_idnum`) USING BTREE
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
-- 校区表
DROP TABLE IF EXISTS `campus`;
CREATE TABLE `campus`  (
  `campus_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 校区自增id
  `campus_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 校区名称
  `campus_status` int(0) NOT NULL, -- 校区管控情况 0代表被管控，1代表解封
  PRIMARY KEY (`campus_id`) USING BTREE,
	UNIQUE KEY `campus_name` (`campus_name`) USING BTREE	
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
-- 入校申请表
DROP TABLE IF EXISTS `comin_approve`;
CREATE TABLE `comin_approve`  (
  `comin_app_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 自增id
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 申请学生学号
  `admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, 
	 -- 目标管理员用户名，例：学生应该交给对应班级的辅导员，辅导员若同意应该交给对应院系的管理员
  `comin_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 入校原因
  `exp_comindate` date NOT NULL, -- 预期返校时间
  `app_status` int(0) NOT NULL, -- 申请表状态 0代表被拒绝，1代表已提交，2代表辅导员已通过，3代表院系辅导员已通过
  `current_admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, -- 目前操作的辅导员用户名
  `reject_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, -- 驳回原因
  `my_date` date NOT NULL, -- 提交日期
  PRIMARY KEY (`comin_app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comin_approve
-- ----------------------------
INSERT INTO `comin_approve` VALUES (1, '20302010061', '12345678', '回学校', '2022-12-02', 1, NULL, NULL, '2022-12-02');

-- ----------------------------
-- Table structure for department
-- ----------------------------
-- 学院表
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department`  (
  `dept_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 学院自增id
  `dept_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学院名
  `dept_admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学院管理员用户名
  PRIMARY KEY (`dept_id`) USING BTREE,
	UNIQUE KEY `dept_name` (`dept_name`) USING BTREE,
	UNIQUE KEY `dept_admin_id` (`dept_admin_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES (1, '计算机科学技术学院', '87654321');

-- ----------------------------
-- Table structure for health_daily
-- ----------------------------
-- 健康日报表
DROP TABLE IF EXISTS `health_daily`;
CREATE TABLE `health_daily`  (
  `hd_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 健康日报自增id
  `stu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生学号
  `body_temperature` double NOT NULL, -- 学生体温，超过37.5度判为异常
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 所在城市
  `district` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 所在区
  `street` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 所在街道
  `date` date NOT NULL, -- 填写日期
  `time` time(0) NOT NULL, -- 填写时间
  `hd_status` int(0) NULL DEFAULT NULL, -- 健康日报状态 0为异常 1为正常，通过判断风险地区和体温得出
  PRIMARY KEY (`hd_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

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
  `leav_app_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 离校申请自增id
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生学号
  `admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, 
  -- 目标管理员用户名，例：学生应该交给对应班级的辅导员，辅导员若同意应该交给对应院系的管理员
  `leav_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 离校原因
  `dest_city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 目的地城市
  `dest_district` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 目的地区名
  `dest_street` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 目的地街道
  `exp_leavdate` date NOT NULL, -- 预计离校时间
  `exp_retdate` date NOT NULL, -- 预计返校时间
  `app_status` int(0) NOT NULL, -- 申请表状态 0代表被拒绝，1代表已提交，2代表辅导员已通过，3代表院系辅导员已通过
  `current_admin_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, -- 目前操作的辅导员用户名
  `reject_reason` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL, -- 驳回原因
  `my_date` date NOT NULL, -- 提交日期
  PRIMARY KEY (`leav_app_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of leave_application
-- ----------------------------
INSERT INTO `leave_application` VALUES (1, '20302010061', '12345678', '回家', '上海', '杨浦', '长海路街道', '2022-12-01', '2022-12-02', 1, NULL, NULL, '2022-12-02');

-- ----------------------------
-- Table structure for log
-- ----------------------------
-- 进出校记录表
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log`  (
  `log_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 记录自增id
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生学号
  `log_date` date NOT NULL, -- 记录日期
  `log_time` time(0) NOT NULL, -- 记录时间
  `log_status` int(0) NOT NULL, -- 记录状态 0为离校，1为进校
  `campus_id` bigint(0) NOT NULL, -- 校区id
  `dept_id` bigint(0) NOT NULL, -- 学院id
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
-- 每位同学每日离校时长表
DROP TABLE IF EXISTS `outside_time`;
CREATE TABLE `outside_time`  (
  `ot_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 离校时长自增id
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学号
  `date` date NOT NULL, -- 对应日期
  `ot_time` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 离校时长
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
-- 进校权限表
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `perm_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 进校权限自增id
  `campus_id` bigint(0) NOT NULL, -- 校区id
  `stu_id` char(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学号
  `permit_status` int(0) NOT NULL, -- 权限状态 0为无权限，1为有权限
  PRIMARY KEY (`perm_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, 1, '20302010061', 1);

-- ----------------------------
-- Table structure for risky_places
-- ----------------------------
-- 风险地区表
DROP TABLE IF EXISTS `risky_places`;
CREATE TABLE `risky_places`  (
  `rp_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 风险地区自增id
  `city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 风险城市
  `district` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 风险区名
  `street` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 风险街道
  PRIMARY KEY (`rp_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of risky_places
-- ----------------------------
INSERT INTO `risky_places` VALUES (1, '上海', '杨浦', '长海路街道');

-- ----------------------------
-- Table structure for school
-- ----------------------------
-- 学校表
DROP TABLE IF EXISTS `school`;
CREATE TABLE `school`  (
  `school_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 学校自增id
  `school_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 校名
  `school_city` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学校所在城市
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
-- 班级表
DROP TABLE IF EXISTS `stu_class`;
CREATE TABLE `stu_class`  (
  `class_id` bigint(0) NOT NULL AUTO_INCREMENT, -- 班级id
  `class_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 班级名
  `dept_id` bigint(0) NOT NULL, -- 院系id
  `instructor_id` char(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 辅导员用户名
  PRIMARY KEY (`class_id`) USING BTREE,
	UNIQUE KEY `instructor_id` (`instructor_id`) USING BTREE,
	UNIQUE KEY `class_name` (`class_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of stu_class
-- ----------------------------
INSERT INTO `stu_class` VALUES (1, '软工1班', 1, '12345678');

-- ----------------------------
-- Table structure for student
-- ----------------------------
-- 学生表
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student`  (
  `stu_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学号
  `stu_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生姓名
  `stu_phnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生手机号
  `stu_email` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生邮箱
  `stu_idtype` int(0) NOT NULL, -- 学生身份证件类型
  `stu_idnum` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL, -- 学生身份证件号码
  `stu_class_id` bigint(0) NOT NULL, -- 学生班级id
  `stu_depart_id` bigint(0) NOT NULL, -- 学生学院id
  `stu_school_id` bigint(0) NOT NULL, -- 学生学校id
  `hd_updated` int(0) NOT NULL, -- 学生今日是否填写健康日报 0为未填写，1为已提交
  PRIMARY KEY (`stu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of student
-- ----------------------------
INSERT INTO `student` VALUES ('20302010061', '谢子璇', '13818485220', 'xzx@gmail.com', 1, '881157829348593', 1, 1, 1, 0);

-- ----------------------------
-- View structure for instructors
-- ----------------------------
DROP VIEW IF EXISTS `instructors`;
CREATE ALGORITHM = UNDEFINED SQL SECURITY DEFINER VIEW `instructors` AS select `admin`.`admin_id` AS `admin_id`,`admin`.`admin_type` AS `admin_type`,`admin`.`admin_name` AS `admin_name`,`admin`.`admin_phnum` AS `admin_phnum`,`admin`.`admin_email` AS `admin_email`,`admin`.`admin_idtype` AS `admin_idtype`,`admin`.`admin_idnum` AS `admin_idnum` from `admin` where (`admin`.`admin_type` = 1);

SET FOREIGN_KEY_CHECKS = 1;
