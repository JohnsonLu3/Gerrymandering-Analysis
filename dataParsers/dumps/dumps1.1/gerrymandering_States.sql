-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: cse308.ch4xgfzmcq2l.us-east-1.rds.amazonaws.com    Database: gerrymandering
-- ------------------------------------------------------
-- Server version	5.6.37-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `States`
--

DROP TABLE IF EXISTS `States`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `States` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `StateId` int(11) DEFAULT NULL,
  `StateName` varchar(45) DEFAULT NULL,
  `Year` int(11) DEFAULT NULL,
  `ClickCount` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idState_UNIQUE` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=451 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `States`
--

LOCK TABLES `States` WRITE;
/*!40000 ALTER TABLE `States` DISABLE KEYS */;
INSERT INTO `States` VALUES (1,1,'Alabama',2000,0),(2,1,'Alabama',2002,0),(3,1,'Alabama',2004,0),(4,1,'Alabama',2006,0),(5,1,'Alabama',2008,0),(6,1,'Alabama',2010,0),(7,1,'Alabama',2012,0),(8,1,'Alabama',2014,0),(9,2,'Alaska',2000,0),(10,2,'Alaska',2002,0),(11,2,'Alaska',2004,0),(12,2,'Alaska',2006,0),(13,2,'Alaska',2008,0),(14,2,'Alaska',2010,0),(15,2,'Alaska',2012,0),(16,2,'Alaska',2014,0),(17,4,'Arizona',2000,0),(18,4,'Arizona',2002,0),(19,4,'Arizona',2004,0),(20,4,'Arizona',2006,0),(21,4,'Arizona',2008,0),(22,4,'Arizona',2010,0),(23,4,'Arizona',2012,0),(24,4,'Arizona',2014,0),(25,5,'Arkansas',2000,0),(26,5,'Arkansas',2002,0),(27,5,'Arkansas',2004,0),(28,5,'Arkansas',2006,0),(29,5,'Arkansas',2008,0),(30,5,'Arkansas',2010,0),(31,5,'Arkansas',2012,0),(32,5,'Arkansas',2014,0),(33,6,'California',2000,0),(34,6,'California',2002,0),(35,6,'California',2004,0),(36,6,'California',2006,0),(37,6,'California',2008,0),(38,6,'California',2010,0),(39,6,'California',2012,0),(40,6,'California',2014,0),(41,8,'Colorado',2000,0),(42,8,'Colorado',2002,0),(43,8,'Colorado',2004,0),(44,8,'Colorado',2006,0),(45,8,'Colorado',2008,0),(46,8,'Colorado',2010,0),(47,8,'Colorado',2012,0),(48,8,'Colorado',2014,0),(49,9,'Connecticut',2000,0),(50,9,'Connecticut',2002,0),(51,9,'Connecticut',2004,0),(52,9,'Connecticut',2006,0),(53,9,'Connecticut',2008,0),(54,9,'Connecticut',2010,0),(55,9,'Connecticut',2012,0),(56,9,'Connecticut',2014,0),(57,10,'Delaware',2000,0),(58,10,'Delaware',2002,0),(59,10,'Delaware',2004,0),(60,10,'Delaware',2006,0),(61,10,'Delaware',2008,0),(62,10,'Delaware',2010,0),(63,10,'Delaware',2012,0),(64,10,'Delaware',2014,0),(65,12,'Florida',2000,0),(66,12,'Florida',2002,0),(67,12,'Florida',2004,0),(68,12,'Florida',2006,0),(69,12,'Florida',2008,0),(70,12,'Florida',2010,0),(71,12,'Florida',2012,0),(72,12,'Florida',2014,0),(73,13,'Georgia',2000,0),(74,13,'Georgia',2002,0),(75,13,'Georgia',2004,0),(76,13,'Georgia',2006,0),(77,13,'Georgia',2008,0),(78,13,'Georgia',2010,0),(79,13,'Georgia',2012,0),(80,13,'Georgia',2014,0),(81,15,'Hawaii',2000,0),(82,15,'Hawaii',2002,0),(83,15,'Hawaii',2004,0),(84,15,'Hawaii',2006,0),(85,15,'Hawaii',2008,0),(86,15,'Hawaii',2010,0),(87,15,'Hawaii',2012,0),(88,15,'Hawaii',2014,0),(89,16,'Idaho',2000,0),(90,16,'Idaho',2002,0),(91,16,'Idaho',2004,0),(92,16,'Idaho',2006,0),(93,16,'Idaho',2008,0),(94,16,'Idaho',2010,0),(95,16,'Idaho',2012,0),(96,16,'Idaho',2014,0),(97,17,'Illinois',2000,0),(98,17,'Illinois',2002,0),(99,17,'Illinois',2004,0),(100,17,'Illinois',2006,0),(101,17,'Illinois',2008,0),(102,17,'Illinois',2010,0),(103,17,'Illinois',2012,0),(104,17,'Illinois',2014,0),(105,18,'Indiana',2000,0),(106,18,'Indiana',2002,0),(107,18,'Indiana',2004,0),(108,18,'Indiana',2006,0),(109,18,'Indiana',2008,0),(110,18,'Indiana',2010,0),(111,18,'Indiana',2012,0),(112,18,'Indiana',2014,0),(113,19,'Iowa',2000,0),(114,19,'Iowa',2002,0),(115,19,'Iowa',2004,0),(116,19,'Iowa',2006,0),(117,19,'Iowa',2008,0),(118,19,'Iowa',2010,0),(119,19,'Iowa',2012,0),(120,19,'Iowa',2014,0),(121,20,'Kansas',2000,0),(122,20,'Kansas',2002,0),(123,20,'Kansas',2004,0),(124,20,'Kansas',2006,0),(125,20,'Kansas',2008,0),(126,20,'Kansas',2010,0),(127,20,'Kansas',2012,0),(128,20,'Kansas',2014,0),(129,21,'Kentucky',2000,0),(130,21,'Kentucky',2002,0),(131,21,'Kentucky',2004,0),(132,21,'Kentucky',2006,0),(133,21,'Kentucky',2008,0),(134,21,'Kentucky',2010,0),(135,21,'Kentucky',2012,0),(136,21,'Kentucky',2014,0),(137,22,'Louisiana',2000,0),(138,22,'Louisiana',2002,0),(139,22,'Louisiana',2004,0),(140,22,'Louisiana',2006,0),(141,22,'Louisiana',2008,0),(142,22,'Louisiana',2010,0),(143,22,'Louisiana',2012,0),(144,22,'Louisiana',2014,0),(145,23,'Maine',2000,0),(146,23,'Maine',2002,0),(147,23,'Maine',2004,0),(148,23,'Maine',2006,0),(149,23,'Maine',2008,0),(150,23,'Maine',2010,0),(151,23,'Maine',2012,0),(152,23,'Maine',2014,0),(153,24,'Maryland',2000,0),(154,24,'Maryland',2002,0),(155,24,'Maryland',2004,0),(156,24,'Maryland',2006,0),(157,24,'Maryland',2008,0),(158,24,'Maryland',2010,0),(159,24,'Maryland',2012,0),(160,24,'Maryland',2014,0),(161,25,'Massachusetts',2000,0),(162,25,'Massachusetts',2002,0),(163,25,'Massachusetts',2004,0),(164,25,'Massachusetts',2006,0),(165,25,'Massachusetts',2008,0),(166,25,'Massachusetts',2010,0),(167,25,'Massachusetts',2012,0),(168,25,'Massachusetts',2014,0),(169,26,'Michigan',2000,0),(170,26,'Michigan',2002,0),(171,26,'Michigan',2004,0),(172,26,'Michigan',2006,0),(173,26,'Michigan',2008,0),(174,26,'Michigan',2010,0),(175,26,'Michigan',2012,0),(176,26,'Michigan',2014,0),(177,27,'Minnesota',2000,0),(178,27,'Minnesota',2002,0),(179,27,'Minnesota',2004,0),(180,27,'Minnesota',2006,0),(181,27,'Minnesota',2008,0),(182,27,'Minnesota',2010,0),(183,27,'Minnesota',2012,0),(184,27,'Minnesota',2014,0),(185,28,'Mississippi',2000,0),(186,28,'Mississippi',2002,0),(187,28,'Mississippi',2004,0),(188,28,'Mississippi',2006,0),(189,28,'Mississippi',2008,0),(190,28,'Mississippi',2010,0),(191,28,'Mississippi',2012,0),(192,28,'Mississippi',2014,0),(193,29,'Missouri',2000,0),(194,29,'Missouri',2002,0),(195,29,'Missouri',2004,0),(196,29,'Missouri',2006,0),(197,29,'Missouri',2008,0),(198,29,'Missouri',2010,0),(199,29,'Missouri',2012,0),(200,29,'Missouri',2014,0),(201,30,'Montana',2000,0),(202,30,'Montana',2002,0),(203,30,'Montana',2004,0),(204,30,'Montana',2006,0),(205,30,'Montana',2008,0),(206,30,'Montana',2010,0),(207,30,'Montana',2012,0),(208,30,'Montana',2014,0),(209,31,'Nebraska',2000,0),(210,31,'Nebraska',2002,0),(211,31,'Nebraska',2004,0),(212,31,'Nebraska',2006,0),(213,31,'Nebraska',2008,0),(214,31,'Nebraska',2010,0),(215,31,'Nebraska',2012,0),(216,31,'Nebraska',2014,0),(217,32,'Nevada',2000,0),(218,32,'Nevada',2002,0),(219,32,'Nevada',2004,0),(220,32,'Nevada',2006,0),(221,32,'Nevada',2008,0),(222,32,'Nevada',2010,0),(223,32,'Nevada',2012,0),(224,32,'Nevada',2014,0),(225,33,'New Hampshire',2000,0),(226,33,'New Hampshire',2002,0),(227,33,'New Hampshire',2004,0),(228,33,'New Hampshire',2006,0),(229,33,'New Hampshire',2008,0),(230,33,'New Hampshire',2010,0),(231,33,'New Hampshire',2012,0),(232,33,'New Hampshire',2014,0),(233,34,'New Jersey',2000,0),(234,34,'New Jersey',2002,0),(235,34,'New Jersey',2004,0),(236,34,'New Jersey',2006,0),(237,34,'New Jersey',2008,0),(238,34,'New Jersey',2010,0),(239,34,'New Jersey',2012,0),(240,34,'New Jersey',2014,0),(241,35,'New Mexico',2000,0),(242,35,'New Mexico',2002,0),(243,35,'New Mexico',2004,0),(244,35,'New Mexico',2006,0),(245,35,'New Mexico',2008,0),(246,35,'New Mexico',2010,0),(247,35,'New Mexico',2012,0),(248,35,'New Mexico',2014,0),(249,36,'New York',2000,0),(250,36,'New York',2002,0),(251,36,'New York',2004,0),(252,36,'New York',2006,0),(253,36,'New York',2008,0),(254,36,'New York',2010,0),(255,36,'New York',2012,0),(256,36,'New York',2014,0),(257,37,'North Carolina',2000,0),(258,37,'North Carolina',2002,0),(259,37,'North Carolina',2004,0),(260,37,'North Carolina',2006,0),(261,37,'North Carolina',2008,0),(262,37,'North Carolina',2010,0),(263,37,'North Carolina',2012,0),(264,37,'North Carolina',2014,0),(265,38,'North Dakota',2000,0),(266,38,'North Dakota',2002,0),(267,38,'North Dakota',2004,0),(268,38,'North Dakota',2006,0),(269,38,'North Dakota',2008,0),(270,38,'North Dakota',2010,0),(271,38,'North Dakota',2012,0),(272,38,'North Dakota',2014,0),(273,39,'Ohio',2000,0),(274,39,'Ohio',2002,0),(275,39,'Ohio',2004,0),(276,39,'Ohio',2006,0),(277,39,'Ohio',2008,0),(278,39,'Ohio',2010,0),(279,39,'Ohio',2012,0),(280,39,'Ohio',2014,0),(281,40,'Oklahoma',2000,0),(282,40,'Oklahoma',2002,0),(283,40,'Oklahoma',2004,0),(284,40,'Oklahoma',2006,0),(285,40,'Oklahoma',2008,0),(286,40,'Oklahoma',2010,0),(287,40,'Oklahoma',2012,0),(288,40,'Oklahoma',2014,0),(289,41,'Oregon',2000,0),(290,41,'Oregon',2002,0),(291,41,'Oregon',2004,0),(292,41,'Oregon',2006,0),(293,41,'Oregon',2008,0),(294,41,'Oregon',2010,0),(295,41,'Oregon',2012,0),(296,41,'Oregon',2014,0),(297,42,'Pennsylvania',2000,0),(298,42,'Pennsylvania',2002,0),(299,42,'Pennsylvania',2004,0),(300,42,'Pennsylvania',2006,0),(301,42,'Pennsylvania',2008,0),(302,42,'Pennsylvania',2010,0),(303,42,'Pennsylvania',2012,0),(304,42,'Pennsylvania',2014,0),(305,44,'Rhode Island',2000,0),(306,44,'Rhode Island',2002,0),(307,44,'Rhode Island',2004,0),(308,44,'Rhode Island',2006,0),(309,44,'Rhode Island',2008,0),(310,44,'Rhode Island',2010,0),(311,44,'Rhode Island',2012,0),(312,44,'Rhode Island',2014,0),(313,45,'South Carolina',2000,0),(314,45,'South Carolina',2002,0),(315,45,'South Carolina',2004,0),(316,45,'South Carolina',2006,0),(317,45,'South Carolina',2008,0),(318,45,'South Carolina',2010,0),(319,45,'South Carolina',2012,0),(320,45,'South Carolina',2014,0),(321,46,'South Dakota',2000,0),(322,46,'South Dakota',2002,0),(323,46,'South Dakota',2004,0),(324,46,'South Dakota',2006,0),(325,46,'South Dakota',2008,0),(326,46,'South Dakota',2010,0),(327,46,'South Dakota',2012,0),(328,46,'South Dakota',2014,0),(329,47,'Tennessee',2000,0),(330,47,'Tennessee',2002,0),(331,47,'Tennessee',2004,0),(332,47,'Tennessee',2006,0),(333,47,'Tennessee',2008,0),(334,47,'Tennessee',2010,0),(335,47,'Tennessee',2012,0),(336,47,'Tennessee',2014,0),(337,48,'Texas',2000,0),(338,48,'Texas',2002,0),(339,48,'Texas',2004,0),(340,48,'Texas',2006,0),(341,48,'Texas',2008,0),(342,48,'Texas',2010,0),(343,48,'Texas',2012,0),(344,48,'Texas',2014,0),(345,49,'Utah',2000,0),(346,49,'Utah',2002,0),(347,49,'Utah',2004,0),(348,49,'Utah',2006,0),(349,49,'Utah',2008,0),(350,49,'Utah',2010,0),(351,49,'Utah',2012,0),(352,49,'Utah',2014,0),(353,50,'Vermont',2000,0),(354,50,'Vermont',2002,0),(355,50,'Vermont',2004,0),(356,50,'Vermont',2006,0),(357,50,'Vermont',2008,0),(358,50,'Vermont',2010,0),(359,50,'Vermont',2012,0),(360,50,'Vermont',2014,0),(361,51,'Virginia',2000,0),(362,51,'Virginia',2002,0),(363,51,'Virginia',2004,0),(364,51,'Virginia',2006,0),(365,51,'Virginia',2008,0),(366,51,'Virginia',2010,0),(367,51,'Virginia',2012,0),(368,51,'Virginia',2014,0),(369,53,'Washington',2000,0),(370,53,'Washington',2002,0),(371,53,'Washington',2004,0),(372,53,'Washington',2006,0),(373,53,'Washington',2008,0),(374,53,'Washington',2010,0),(375,53,'Washington',2012,0),(376,53,'Washington',2014,0),(377,54,'West Virginia',2000,0),(378,54,'West Virginia',2002,0),(379,54,'West Virginia',2004,0),(380,54,'West Virginia',2006,0),(381,54,'West Virginia',2008,0),(382,54,'West Virginia',2010,0),(383,54,'West Virginia',2012,0),(384,54,'West Virginia',2014,0),(385,55,'Wisconsin',2000,0),(386,55,'Wisconsin',2002,0),(387,55,'Wisconsin',2004,0),(388,55,'Wisconsin',2006,0),(389,55,'Wisconsin',2008,0),(390,55,'Wisconsin',2010,0),(391,55,'Wisconsin',2012,0),(392,55,'Wisconsin',2014,0),(393,56,'Wyoming',2000,0),(394,56,'Wyoming',2002,0),(395,56,'Wyoming',2004,0),(396,56,'Wyoming',2006,0),(397,56,'Wyoming',2008,0),(398,56,'Wyoming',2010,0),(399,56,'Wyoming',2012,0),(400,56,'Wyoming',2014,0),(401,1,'Alabama',2016,0),(402,2,'Alaska',2016,0),(403,4,'Arizona',2016,0),(404,5,'Arkansas',2016,0),(405,6,'California',2016,0),(406,8,'Colorado',2016,0),(407,9,'Connecticut',2016,0),(408,10,'Delaware',2016,0),(409,12,'Florida',2016,0),(410,13,'Georgia',2016,0),(411,15,'Hawaii',2016,0),(412,16,'Idaho',2016,0),(413,17,'Illinois',2016,0),(414,18,'Indiana',2016,0),(415,19,'Iowa',2016,0),(416,20,'Kansas',2016,0),(417,21,'Kentucky',2016,0),(418,22,'Louisiana',2016,0),(419,23,'Maine',2016,0),(420,24,'Maryland',2016,0),(421,25,'Massachusetts',2016,0),(422,26,'Michigan',2016,0),(423,27,'Minnesota',2016,0),(424,28,'Mississippi',2016,0),(425,29,'Missouri',2016,0),(426,30,'Montana',2016,0),(427,31,'Nebraska',2016,0),(428,32,'Nevada',2016,0),(429,33,'New Hampshire',2016,0),(430,34,'New Jersey',2016,0),(431,35,'New Mexico',2016,0),(432,36,'New York',2016,0),(433,37,'North Carolina',2016,0),(434,38,'North Dakota',2016,0),(435,39,'Ohio',2016,0),(436,40,'Oklahoma',2016,0),(437,41,'Oregon',2016,0),(438,42,'Pennsylvania',2016,0),(439,44,'Rhode Island',2016,0),(440,45,'South Carolina',2016,0),(441,46,'South Dakota',2016,0),(442,47,'Tennessee',2016,0),(443,48,'Texas',2016,0),(444,49,'Utah',2016,0),(445,50,'Vermont',2016,0),(446,51,'Virginia',2016,0),(447,53,'Washington',2016,0),(448,54,'West Virginia',2016,0),(449,55,'Wisconsin',2016,0),(450,56,'Wyoming',2016,0);
/*!40000 ALTER TABLE `States` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-12 17:21:56
