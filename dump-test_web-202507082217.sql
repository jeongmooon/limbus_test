-- MySQL dump 10.13  Distrib 8.0.39, for Win64 (x86_64)
--
-- Host: localhost    Database: test_web
-- ------------------------------------------------------
-- Server version	8.0.36

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `PRISONER`
--

DROP TABLE IF EXISTS `PRISONER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `PRISONER` (
  `IDX` int NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL,
  `LEVEL` varchar(20) DEFAULT NULL,
  `ICON` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`IDX`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `PRISONER`
--

LOCK TABLES `PRISONER` WRITE;
/*!40000 ALTER TABLE `PRISONER` DISABLE KEYS */;
INSERT INTO `PRISONER` VALUES (1,'이상','50','1'),(2,'파우스트','50','1'),(3,'돈키호테','50','1'),(4,'로슈','50','1'),(5,'뫼르소','50','1'),(6,'홍루','50','1'),(7,'히스클리프','50','1'),(8,'이스마엘','50','1'),(9,'로쟈','50','1'),(10,'싱클레어','50','1'),(11,'오티스','50','1'),(12,'그레고르','50','1');
/*!40000 ALTER TABLE `PRISONER` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_INFO`
--

DROP TABLE IF EXISTS `USER_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_INFO` (
  `IDX` int NOT NULL AUTO_INCREMENT,
  `USER_ID` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `PASS` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `GAME_CODE` varchar(20) DEFAULT NULL,
  `SALT` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '',
  PRIMARY KEY (`IDX`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_INFO`
--

LOCK TABLES `USER_INFO` WRITE;
/*!40000 ALTER TABLE `USER_INFO` DISABLE KEYS */;
INSERT INTO `USER_INFO` VALUES (1,'admin','1234','dfasdn',''),(2,'admin2','1234','sdafsda',''),(3,'admin3','1234','adfsdsff',''),(4,'정정정정정정정정정정정정정정정정정정정정','1234','dfasdn',''),(5,'한글','1234','213','');
/*!40000 ALTER TABLE `USER_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `USER_TEST_INFO`
--

DROP TABLE IF EXISTS `USER_TEST_INFO`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `USER_TEST_INFO` (
  `USER_ID` varchar(20) NOT NULL,
  `PASS` varchar(100) DEFAULT NULL,
  `SALT` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `USER_TEST_INFO`
--

LOCK TABLES `USER_TEST_INFO` WRITE;
/*!40000 ALTER TABLE `USER_TEST_INFO` DISABLE KEYS */;
INSERT INTO `USER_TEST_INFO` VALUES ('admin','admin','admin'),('admin2','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin'),('admin3','$2a$10$J0DbpFVgDMie4w8I0FYnRu6noNXfKzQwtywz9Dzy0dse0tTDqHI5m','2f615dac75e214e920eb2f8ad876a94f'),('admin4','110198831a426807bccd9dbdf54b6dcb5298bc5d31ac49069e0ba3d210d970ae','admin');
/*!40000 ALTER TABLE `USER_TEST_INFO` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `delete_dvsn` varchar(255) DEFAULT NULL,
  `regist_date` date DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `view` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1xh5xt0bpr6v4ftrc80k2705h` (`user_id`),
  CONSTRAINT `FK1xh5xt0bpr6v4ftrc80k2705h` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'<p>1</p>','N','2025-07-08','1234',8,11),(2,'<p><a rel=\"nofollow\">123</a></p>','N','2025-07-08','123',8,11);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `code_info`
--

DROP TABLE IF EXISTS `code_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `code_info` (
  `cd_id` varchar(255) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`cd_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_info`
--

LOCK TABLES `code_info` WRITE;
/*!40000 ALTER TABLE `code_info` DISABLE KEYS */;
INSERT INTO `code_info` VALUES ('YOUTUBE_CHANNELID','UCfTGW15PCQnPVAauEBq0iBg'),('YOUTUBE_PLAYLIST','단빵숲1'),('YOUTUBE_UPDATE_LAST_VIDEO','Y');
/*!40000 ALTER TABLE `code_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deck`
--

DROP TABLE IF EXISTS `deck`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deck` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `deck_list_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKax9ol98rcwi3fu2y0q27bqubu` (`deck_list_id`),
  CONSTRAINT `FKax9ol98rcwi3fu2y0q27bqubu` FOREIGN KEY (`deck_list_id`) REFERENCES `deck_list` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deck`
--

LOCK TABLES `deck` WRITE;
/*!40000 ALTER TABLE `deck` DISABLE KEYS */;
INSERT INTO `deck` VALUES (9,'🧩 덱 1',NULL),(10,'🧩 덱 2',NULL),(13,'🧩 덱 1',NULL),(14,'🧩 덱 2',NULL),(23,'🧩 덱 1',6),(24,'🧩 덱 2',6),(27,'🧩 덱 1',1),(28,'🧩 덱 2',1);
/*!40000 ALTER TABLE `deck` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deck_identity`
--

DROP TABLE IF EXISTS `deck_identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deck_identity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `slot_order` int DEFAULT NULL,
  `deck_id` bigint DEFAULT NULL,
  `identity_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKdhk01yogqxsgj791157hlckmj` (`deck_id`),
  KEY `FK7wtif1vny27fgejn8mxjluu6x` (`identity_id`),
  CONSTRAINT `FK7wtif1vny27fgejn8mxjluu6x` FOREIGN KEY (`identity_id`) REFERENCES `identity` (`id`),
  CONSTRAINT `FKdhk01yogqxsgj791157hlckmj` FOREIGN KEY (`deck_id`) REFERENCES `deck` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=109 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deck_identity`
--

LOCK TABLES `deck_identity` WRITE;
/*!40000 ALTER TABLE `deck_identity` DISABLE KEYS */;
INSERT INTO `deck_identity` VALUES (53,0,9,4),(54,1,9,5),(55,2,9,46),(56,0,10,2),(57,1,10,4),(58,2,10,5),(59,3,10,23),(60,4,10,24),(61,5,10,34),(62,6,10,47),(63,7,10,55),(73,0,13,16),(74,1,13,22),(75,0,14,4),(76,1,14,18),(77,2,14,24),(78,3,14,49),(90,0,23,23),(91,1,23,24),(92,0,24,23),(103,0,27,19),(104,1,27,21),(105,2,27,24),(106,0,28,19),(107,1,28,21),(108,2,28,23);
/*!40000 ALTER TABLE `deck_identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deck_list`
--

DROP TABLE IF EXISTS `deck_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `deck_list` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `regist_date` date DEFAULT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_gylvaib1m6xyk91r6nbdcb3ra` (`uuid`),
  KEY `FKrmvbjoniu4pucvv4fgy1rcmyq` (`user_id`),
  CONSTRAINT `FKrmvbjoniu4pucvv4fgy1rcmyq` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deck_list`
--

LOCK TABLES `deck_list` WRITE;
/*!40000 ALTER TABLE `deck_list` DISABLE KEYS */;
INSERT INTO `deck_list` VALUES (1,'2025-05-11','715B031A9BAC45D9BD7CE539598FC3A2',11),(5,'2025-05-11','9A5843C7B8454F30838B249B0B3BDA7B',11),(6,'2025-05-11','D5EEB33786604DFE8C7A654BB5415EB5',11);
/*!40000 ALTER TABLE `deck_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `identity`
--

DROP TABLE IF EXISTS `identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `identity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `grade` int DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `season` int DEFAULT NULL,
  `sinner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK3n15gsdo2bo72edldrn48372s` (`sinner_id`),
  CONSTRAINT `FK3n15gsdo2bo72edldrn48372s` FOREIGN KEY (`sinner_id`) REFERENCES `sinner` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `identity`
--

LOCK TABLES `identity` WRITE;
/*!40000 ALTER TABLE `identity` DISABLE KEYS */;
INSERT INTO `identity` VALUES (2,3,'2','로보토미 E.G.O::적안 · 참회','2024-09-05',4,4),(3,3,'2','로보토미 E.G.O::엄숙한 애도','2024-09-05',4,1),(4,3,'2','마침표 사무소 해결사','2025-01-09',5,7),(5,3,'2','마침표 사무소 해결사','2025-01-09',5,6),(6,3,'3','흑수 - 묘','2025-03-20',5,4),(13,1,'1','LCB 수감자','2023-01-01',1,1),(14,1,'1','LCB 수감자','2023-01-01',1,2),(15,1,'1','LCB 수감자','2023-01-01',1,3),(16,1,'1','LCB 수감자','2023-01-01',1,4),(17,1,'1','LCB 수감자','2023-01-01',1,5),(18,1,'1','LCB 수감자','2023-01-01',1,6),(19,1,'1','LCB 수감자','2023-01-01',1,7),(20,1,'1','LCB 수감자','2023-01-01',1,8),(21,1,'1','LCB 수감자','2023-01-01',1,9),(22,1,'1','LCB 수감자','2023-01-01',1,10),(23,1,'1','LCB 수감자','2023-01-01',1,11),(24,1,'1','LCB 수감자','2023-01-01',1,12),(28,3,'28','LCE E.G.O::홍염살','2025-05-09',5,2),(29,3,'29','라만차랜드 실장','2025-05-09',5,3),(30,2,'30','남부 세븐 협회 6과','2025-05-10',1,1),(31,3,'31','검계 살수','2025-05-10',1,1),(32,3,'32','개화 E.G.O :: 동백','2025-05-10',2,1),(33,2,'33','어금니 사무소 해결사','2025-05-10',2,1),(34,3,'34','W사 3등급 정리 요원','2025-05-10',3,1),(35,2,'35','피쿼드호 일등 항해사','2025-05-10',3,1),(36,3,'36','남부 디에치 협회 4과','2025-05-10',3,1),(37,3,'37','약지 점묘파 스튜던트','2025-05-10',4,1),(38,2,'38','LCE E.G.O::초롱','2025-05-10',5,1),(39,3,'39','남부 리우 협회 3과','2025-05-10',5,1),(40,2,'40','W사 2등급 정리 요원','2025-05-10',1,2),(41,2,'41','살아남은 로보토미 직원','2025-05-10',1,2),(42,3,'42','쥐는 자','2025-05-10',1,2),(43,2,'43','남부 츠바이 협회 4과','2025-05-10',2,2),(44,3,'44','남부 세븐 협회 4과','2025-05-10',2,2),(45,3,'45','로보토미 E.G.O :: 후회','2025-05-10',3,2),(46,3,'46','검계 살수','2025-05-10',3,2),(47,2,'47','워더링하이츠 버틀러','2025-05-10',4,2),(48,3,'48','멀티크랙 사무소 대표','2025-05-10',4,2),(49,2,'49','남부 시 협회 5과 부장','2025-05-10',1,3),(50,3,'50','W사 3등급 정리 요원','2025-05-10',1,3),(51,2,'51','N사 중간 망치','2025-05-10',1,3),(52,3,'52','남부 섕크 협회 5과 부장','2025-05-10',2,3),(53,3,'53','중지 작은 아우','2025-05-10',3,3),(54,2,'54','로보토미 E.G.O::초롱','2025-05-10',3,3),(55,2,'55','검계 살수','2025-05-10',3,3),(56,3,'56','T사 3등급 징수직 직원','2025-05-10',4,3),(57,3,'57','동부 섕크 협회 3과','2025-05-10',5,3);
/*!40000 ALTER TABLE `identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nav_menu`
--

DROP TABLE IF EXISTS `nav_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nav_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nav_menu`
--

LOCK TABLES `nav_menu` WRITE;
/*!40000 ALTER TABLE `nav_menu` DISABLE KEYS */;
INSERT INTO `nav_menu` VALUES (10,'캐릭터 도감','USER','/user-identity'),(11,'인격 추가','ADMIN','/identity'),(12,'덱 빌딩','USER','/deck'),(13,'로그아웃','USER','/user/logout');
/*!40000 ALTER TABLE `nav_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sinner`
--

DROP TABLE IF EXISTS `sinner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sinner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sinner`
--

LOCK TABLES `sinner` WRITE;
/*!40000 ALTER TABLE `sinner` DISABLE KEYS */;
INSERT INTO `sinner` VALUES (1,'1','이상'),(2,'1','파우스트'),(3,'1','돈키호테'),(4,'1','로슈'),(5,'1','뫼르소'),(6,'1','홍루'),(7,'1','히스클리프'),(8,'1','이스마엘'),(9,'1','로쟈'),(10,'1','싱클레어'),(11,'1','오티스'),(12,'1','그레고르');
/*!40000 ALTER TABLE `sinner` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `test_table`
--

DROP TABLE IF EXISTS `test_table`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `test_table` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `score` varchar(20) DEFAULT NULL,
  `pass` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `test_table`
--

LOCK TABLES `test_table` WRITE;
/*!40000 ALTER TABLE `test_table` DISABLE KEYS */;
INSERT INTO `test_table` VALUES (1,'doldol','33','1234'),(2,'nomaltic','80','2222'),(3,'admin','80','admin'),(4,'jeongmoon','50','3333');
/*!40000 ALTER TABLE `test_table` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_identity`
--

DROP TABLE IF EXISTS `user_identity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_identity` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `regist_date` date DEFAULT NULL,
  `identity_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKm5yu2s1oxl82whftns3hkp3jh` (`identity_id`),
  KEY `FKli0keegcid73dh6b6h8n2rayf` (`user_id`),
  CONSTRAINT `FKli0keegcid73dh6b6h8n2rayf` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FKm5yu2s1oxl82whftns3hkp3jh` FOREIGN KEY (`identity_id`) REFERENCES `identity` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_identity`
--

LOCK TABLES `user_identity` WRITE;
/*!40000 ALTER TABLE `user_identity` DISABLE KEYS */;
INSERT INTO `user_identity` VALUES (55,'2025-05-10',2,8),(56,'2025-05-10',3,8),(57,'2025-05-10',5,8),(58,'2025-05-10',13,8),(59,'2025-05-10',14,8),(60,'2025-05-10',15,8),(61,'2025-05-10',16,8),(62,'2025-05-10',17,8),(63,'2025-05-10',18,8),(64,'2025-05-10',28,8),(65,'2025-05-10',29,8),(69,'2025-05-10',38,8),(70,'2025-05-10',40,8),(71,'2025-05-10',47,8),(72,'2025-05-10',48,8),(73,'2025-05-10',55,8),(74,'2025-05-10',56,8),(75,'2025-05-10',57,8),(81,'2025-05-10',34,8),(82,'2025-05-10',35,8),(83,'2025-07-08',33,11),(84,'2025-07-08',34,11),(85,'2025-07-08',35,11);
/*!40000 ALTER TABLE `user_identity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_info`
--

DROP TABLE IF EXISTS `user_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_info` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `game_code` varchar(255) DEFAULT NULL,
  `pass` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKhixwjgx0ynne0cq4tqvoawoda` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_info`
--

LOCK TABLES `user_info` WRITE;
/*!40000 ALTER TABLE `user_info` DISABLE KEYS */;
INSERT INTO `user_info` VALUES (1,'asdfdsf','$2a$10$hOQZegXjNk.noU8LhIDJ3On/M41OVn8R2CT5j80LnacOiDnYgD/ey','8d049b8226653e6029a96f8d8ca14be5','test1','USER'),(2,'asdfdsf','$2a$10$GPL6lU76M8RUOn4MKQKh0uM7go7lTOFinZ.DVbD4cJ1IlhaCp9oNC','21137c9d6f7c3be4d6429e8e312323db','test2','USER'),(3,'sdaffds','$2a$10$/TvIg2ajnArnAdZ6.ZFMQOdmULpOXGniI2JJRikjK246QpKYeD8ie','055c4a8b8725a42b6909f6fc01b1ce49','test3','USER'),(4,'asfds','$2a$10$eUTvZ08vce8io3XQ68UsDOZmb0dTg882SIEPVvyN0molVhWXklDBq','8165716f6fb17a0fa4ab9652f3e5ae15','test4','USER'),(5,'fads','$2a$10$Flur4JEIInXm5qfIUomIIemqKBO6.W3IuLr5UISMPOwsHzGIcGlhu','1e7c2de8b18a2a9668eec84a5aaa0a2a','test5','USER'),(6,'12332123','$2a$10$4qoZJL7SW8otWHMg1I31fu.3m0/kiHgew8VlBAqPe5XGooSVpGfUW','eaca9b2cdf5463eaaf6093a2dcae7787','test11','USER'),(7,'123123','$2a$10$Pp7IwM2B9OURBBXdenhyLudhQPTiZRPDJ/tUMaMIQbi8mPktxUtaa','46bf4ac8ee03fd05930c365f8298f437','test111','USER'),(8,'afsdds','$2a$10$mPR7QAGzYlmgUM8.3ba4Y.qKPAbUPW9sY2nO0jnyUIKc8o1IObV1.','a114fd4822356a7a2258ef80e5d73a68','test1234','ADMIN'),(9,'fsadfds','$2a$10$JMjNlxb5b/j0CUv8PhetZutk.YZDEOV1O.YcDMA7EK5esvHACJKQC','2ce94f9f377b741976116c7e46382f6f','test2222','USER'),(10,'sadfsfdadf','$2a$10$5FMugRWpQDUO8bh5rP1B4.ayjs0UmL7r8d3.xDmZTLOorSGlKklnq','ec73b089362b485590f9d8fbb51dc3e8','test123445','USER'),(11,'sadfdfasdfs','$2a$10$Uy./QSKYBx.VIi2zWRv8COQZjcZ7BMokGhYPZtiWvrkKVPQLSxFnu','5b400149431a0f30172a4e35613c7e11','test','USER');
/*!40000 ALTER TABLE `user_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'test_web'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-07-08 22:17:35
