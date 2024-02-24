/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : sm_db

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 25/02/2024 01:38:30
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for news_headline
-- ----------------------------
DROP TABLE IF EXISTS `news_headline`;
CREATE TABLE `news_headline`  (
  `hid` int NOT NULL AUTO_INCREMENT COMMENT '頭條id',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '頭條標題',
  `article` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '頭條新聞內容',
  `type` int NOT NULL COMMENT '頭條類型id',
  `publisher` int NOT NULL COMMENT '頭條發布用戶id',
  `page_views` int NOT NULL COMMENT '頭條瀏覽量',
  `create_time` datetime NULL DEFAULT NULL COMMENT '頭條發布時間',
  `update_time` datetime NULL DEFAULT NULL COMMENT '頭條最後的修改時間',
  `version` int NULL DEFAULT 1 COMMENT '樂觀鎖',
  `is_deleted` int NULL DEFAULT 0 COMMENT '頭條是否被刪除 1 刪除  0 未刪除',
  PRIMARY KEY (`hid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news_headline
-- ----------------------------
INSERT INTO `news_headline` VALUES (19, '123', '123', 2, 1, 0, '2024-02-05 15:36:07', '2024-02-05 15:36:07', 1, 0);
INSERT INTO `news_headline` VALUES (20, '11', '111', 1, 1, 6, '2024-02-05 16:06:35', '2024-02-05 16:06:35', 7, 0);
INSERT INTO `news_headline` VALUES (21, '今天天氣好熱', '123123123123123123....11', 1, 1, 0, '2024-02-23 20:21:46', '2024-02-23 20:29:23', 3, 0);

-- ----------------------------
-- Table structure for news_type
-- ----------------------------
DROP TABLE IF EXISTS `news_type`;
CREATE TABLE `news_type`  (
  `tid` int NOT NULL AUTO_INCREMENT COMMENT '新聞類型id',
  `tname` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '新聞類型描述',
  `version` int NULL DEFAULT 1 COMMENT '樂觀鎖',
  `is_deleted` int NULL DEFAULT 0 COMMENT '頭條是否被刪除 1 刪除  0 未刪除',
  PRIMARY KEY (`tid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news_type
-- ----------------------------
INSERT INTO `news_type` VALUES (1, '新聞', 1, 0);
INSERT INTO `news_type` VALUES (2, '體育', 1, 0);
INSERT INTO `news_type` VALUES (3, '娛樂', 1, 0);
INSERT INTO `news_type` VALUES (4, '科技', 1, 0);
INSERT INTO `news_type` VALUES (5, '其他', 1, 0);

-- ----------------------------
-- Table structure for news_user
-- ----------------------------
DROP TABLE IF EXISTS `news_user`;
CREATE TABLE `news_user`  (
  `uid` int NOT NULL AUTO_INCREMENT COMMENT '用戶id',
  `username` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用戶登錄名',
  `user_pwd` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用戶登錄密碼密文',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用戶昵稱',
  `version` int NULL DEFAULT 1 COMMENT '樂觀鎖',
  `is_deleted` int NULL DEFAULT 0 COMMENT '頭條是否被刪除 1 刪除  0 未刪除',
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username_unique`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of news_user
-- ----------------------------
INSERT INTO `news_user` VALUES (1, 'root', 'e10adc3949ba59abbe56e057f20f883e', 'root', 1, 0);
INSERT INTO `news_user` VALUES (2, 'white', 'e10adc3949ba59abbe56e057f20f883e', '小白', 1, 0);
INSERT INTO `news_user` VALUES (3, 'black', 'e10adc3949ba59abbe56e057f20f883e', '小黑', 1, 0);
INSERT INTO `news_user` VALUES (4, 'payne11', 'e10adc3949ba59abbe56e057f20f883e', 'phph', 1, 0);
INSERT INTO `news_user` VALUES (5, 'redd', 'e10adc3949ba59abbe56e057f20f883e', '小紅', 1, 0);

SET FOREIGN_KEY_CHECKS = 1;
