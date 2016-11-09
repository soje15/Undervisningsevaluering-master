# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: localhost (MySQL 5.7.11)
# Database: project_eva
# Generation Time: 2016-10-19 11:01:11 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Dump of database project_eva
# ------------------------------------------------------------

DROP DATABASE IF EXISTS `project_eva`;

CREATE DATABASE `project_eva`;

USE project_eva;

# Dump of table course
# ------------------------------------------------------------

DROP TABLE IF EXISTS `course`;

CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) NOT NULL DEFAULT '',
  `name` varchar(255) NOT NULL DEFAULT '',
  `study_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  KEY `study_id` (`study_id`),
  CONSTRAINT `course_ibfk_1` FOREIGN KEY (`study_id`) REFERENCES `study` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `course` WRITE;
/*!40000 ALTER TABLE `course` DISABLE KEYS */;

INSERT INTO `course` (`id`, `code`, `name`, `study_id`)
VALUES
	(1,'BINT2020','DIS',1),
	(2,'BINT2021','ITF',1),
	(3,'BINT2022','VØS2',1),
	(4,'BINT2023','MAK',1),
	(5,'BBLS0101','INTC',2),
	(6,'BBAL0202','DLS',2),
	(7,'BBTS','ITP',3),
	(8,'BBNT','DLT',3),
	(9,'BBST','MOB',4),
	(10,'BBMS','FBGM',4);

/*!40000 ALTER TABLE `course` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table course_attendant
# ------------------------------------------------------------

DROP TABLE IF EXISTS `course_attendant`;

CREATE TABLE `course_attendant` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_id` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`),
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `course_attendant` WRITE;
/*!40000 ALTER TABLE `course_attendant` DISABLE KEYS */;

INSERT INTO `course_attendant` (`id`, `user_id`, `course_id`)
VALUES
	(1,1,1),
	(2,1,2),
	(3,1,3),
	(4,1,4),
	(5,2,1),
	(6,2,2),
	(7,2,3),
	(8,2,4),
	(9,3,5),
	(10,3,6),
	(11,4,5),
	(12,4,6),
	(13,5,5),
	(14,5,6),
	(15,6,7),
	(16,6,8),
	(17,7,7),
	(18,7,8),
	(19,11,1),
	(20,12,1),
	(21,13,1),
	(22,14,2),
	(23,15,2);

/*!40000 ALTER TABLE `course_attendant` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table lecture
# ------------------------------------------------------------

DROP TABLE IF EXISTS `lecture`;

CREATE TABLE `lecture` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `start` datetime NOT NULL,
  `end` datetime NOT NULL,
  `location` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `FK_LECTURE_COURSE_idx` (`course_id`),
  CONSTRAINT `FK_LECTURE_COURSE` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `lecture` WRITE;
/*!40000 ALTER TABLE `lecture` DISABLE KEYS */;

INSERT INTO `lecture` (`id`, `course_id`, `type`, `description`, `start`, `end`, `location`)
VALUES
	(1,1,'LA','Lecture','2016-10-02 10:00:00','2016-10-02 11:30:00',''),
	(2,2,'XB','Exercise','2016-10-02 11:40:00','2016-10-02 13:20:00',''),
	(3,3,'LA','Lecture','2016-10-01 12:30:00','2016-10-01 14:00:00',''),
	(4,1,'XB','Excercise','2016-10-01 11:00:00','2016-10-01 12:30:00',''),
	(5,1,'LA','Lecture','2016-10-03 10:00:00','2016-10-01 11:30:00',''),
	(6,2,'LA','Lecture','2016-10-03 13:30:00','2016-10-03 15:10:00',''),
	(7,2,'LA','Lecture','2016-10-04 08:00:00','2016-10-04 09:50:00',''),
	(8,3,'XB','Exercise','2016-10-04 10:00:00','2016-10-04 11:30:00','');

/*!40000 ALTER TABLE `lecture` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table review
# ------------------------------------------------------------

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `lecture_id` int(11) NOT NULL,
  `rating` int(11) NOT NULL,
  `comment` varchar(500) DEFAULT '',
  `is_deleted` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `lecture_id` (`lecture_id`),
  KEY `user_id` (`id`),
  KEY `review_ibfk_2` (`user_id`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`lecture_id`) REFERENCES `lecture` (`id`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;

INSERT INTO `review` (`id`, `user_id`, `lecture_id`, `rating`, `comment`, `comment_is_deleted`)
VALUES
	(1,1,2,3,'okay time',b'0'),
	(2,4,3,0,'lorte time',b'0'),
	(3,5,2,5,'Fantastisk',b'0'),
	(4,6,5,5,'bedste time ever!!!!!',b'0'),
	(5,8,1,0,'hader læren',b'0'),
	(6,2,8,1,'KEEEEDELIIIIG',b'0'),
	(7,2,2,2,'Meget gentagen ',b'0'),
	(8,3,4,4,'Flere øvelser',b'0'),
	(9,7,2,0,'',b'0'),
	(10,4,6,3,'',b'0'),
	(11,8,6,2,'fik ikke noget ud af timen',b'0'),
	(12,3,1,1,'din mor',b'1'),
	(13,1,7,0,'',b'0');

/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table study
# ------------------------------------------------------------

DROP TABLE IF EXISTS `study`;

CREATE TABLE `study` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `shortname` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `study` WRITE;
/*!40000 ALTER TABLE `study` DISABLE KEYS */;

INSERT INTO `study` (`id`, `name`)
VALUES
	(1,'HA(it.)'),
	(2,'BA(im.)'),
	(3,'HA(mat.)'),
	(4,'HA(jur.)'),
	(5,'HA(psyk.)'),
	(6,'HA(kom.)');

/*!40000 ALTER TABLE `study` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cbs_mail` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `type` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `cbs_mail` (`cbs_mail`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;

INSERT INTO `user` (`id`, `cbs_mail`, `password`, `type`)
VALUES
	(1,'te1bo@student.cbs.dk','1','student'),
	(2,'ab1cd@student.cbs.dk','1','student'),
	(3,'ab2cd@student.cbs.dk','1','student'),
	(4,'ab3cd@student.cbs.dk','1','student'),
	(5,'ab4cd@student.cbs.dk','1','student'),
	(6,'ab5cd@student.cbs.dk','1','student'),
	(7,'ab6cd@student.cbs.dk','1','student'),
	(8,'ab7cd@student.cbs.dk','1','student'),
	(9,'ab8cd@student.cbs.dk','1','student'),
	(10,'ab9cd@student.cbs.dk','1','student'),
	(11,'jbh.itm@cbs.dk','1','teacher'),
	(12,'ht.itm@cbs.dk','1','teacher'),
	(13,'kt.itm@cbs.dk','1','teacher'),
	(14,'ihf.itm@cbs.dk','1','teacher'),
	(15,'jss.itm@cbs.dk','1','teacher'),
	(16,'admin@admin.cbs.dk','1','admin'),
	(17,'jas.om@cbs.dk','1','teacher'),
	(18,'phh.om@cbs.dk','1','teacher');

/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
