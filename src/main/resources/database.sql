

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `client_verify`
-- ----------------------------
DROP TABLE IF EXISTS `client_verify`;
CREATE TABLE `client_verify` (
  `license_id` int(10) NOT NULL AUTO_INCREMENT,
  `encrypted_number` varchar(255) NOT NULL,
  `source_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`license_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `hi_tb`
-- ----------------------------
DROP TABLE IF EXISTS `hi_tb`;
CREATE TABLE `hi_tb` (
  `id` int(3) DEFAULT NULL,
  `name` char(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
--  Table structure for `license_detail`
-- ----------------------------
DROP TABLE IF EXISTS `license_detail`;
CREATE TABLE `license_detail` (
  `serial_number_id` int(100) NOT NULL AUTO_INCREMENT,
  `source_number` varchar(255) NOT NULL,
  `create_date` date NOT NULL,
  `expired_date` date NOT NULL,
  `encrypted_number` varchar(255) NOT NULL,
  `hospital_number` int(100) NOT NULL,
  `license_state` int(10) NOT NULL DEFAULT '0',
  `key_id` int(100) NOT NULL,
  PRIMARY KEY (`serial_number_id`),
  KEY `hospital_number` (`hospital_number`) COMMENT '(null)',
  KEY `key_id` (`key_id`) COMMENT '(null)'
) ENGINE=InnoDB AUTO_INCREMENT=260 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `license_hospital`
-- ----------------------------
DROP TABLE IF EXISTS `license_hospital`;
CREATE TABLE `license_hospital` (
  `hospital_number` int(100) NOT NULL AUTO_INCREMENT,
  `hospital_name` varchar(255) NOT NULL,
  `hospital_phone` varchar(255) DEFAULT NULL,
  `hospital_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`hospital_number`)
) ENGINE=InnoDB AUTO_INCREMENT=84 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `license_user`
-- ----------------------------
DROP TABLE IF EXISTS `license_user`;
CREATE TABLE `license_user` (
  `user_id` int(100) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `grade` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `rsa_key`
-- ----------------------------
DROP TABLE IF EXISTS `rsa_key`;
CREATE TABLE `rsa_key` (
  `key_id` int(100) NOT NULL AUTO_INCREMENT,
  `private_key` blob NOT NULL,
  `public_key` blob NOT NULL,
  PRIMARY KEY (`key_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL DEFAULT '123',
  `usergroup` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
