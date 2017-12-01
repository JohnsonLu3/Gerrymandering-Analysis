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
-- Table structure for table `StateBoundaries`
--

DROP TABLE IF EXISTS `StateBoundaries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `StateBoundaries` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `BoundaryId` int(11) NOT NULL,
  `StateId` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idState_UNIQUE` (`Id`),
  KEY `StateId_idx` (`StateId`),
  KEY `fk_Boundary_Id` (`BoundaryId`),
  CONSTRAINT `fk_Boundary_Id` FOREIGN KEY (`BoundaryId`) REFERENCES `Boundaries` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_State_Id` FOREIGN KEY (`StateId`) REFERENCES `States` (`Id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `StateBoundaries`
--

LOCK TABLES `StateBoundaries` WRITE;
/*!40000 ALTER TABLE `StateBoundaries` DISABLE KEYS */;
INSERT INTO `StateBoundaries` VALUES (1,1,1),(2,2,2),(3,3,2),(4,4,2),(5,5,2),(6,6,2),(7,7,2),(8,8,2),(9,9,2),(10,10,2),(11,11,2),(12,12,2),(13,13,2),(14,14,2),(15,15,2),(16,16,2),(17,17,2),(18,18,2),(19,19,2),(20,20,2),(21,21,2),(22,22,2),(23,23,2),(24,24,2),(25,25,2),(26,26,2),(27,27,2),(28,28,2),(29,29,2),(30,30,2),(31,31,2),(32,32,2),(33,33,2),(34,34,2),(35,35,2),(36,36,2),(37,37,2),(38,38,2),(39,39,2),(40,40,2),(41,41,2),(42,42,2),(43,43,2),(44,44,2),(45,45,2),(46,46,2),(47,47,2),(48,48,2),(49,49,4),(50,50,5),(51,51,6),(52,52,6),(53,53,6),(54,54,6),(55,55,6),(56,56,6),(57,57,8),(58,58,9),(59,59,10),(60,60,11),(61,61,12),(62,62,12),(63,63,12),(64,64,12),(65,65,13),(66,66,15),(67,67,15),(68,68,15),(69,69,15),(70,70,15),(71,71,15),(72,72,15),(73,73,15),(74,74,16),(75,75,17),(76,76,18),(77,77,19),(78,78,20),(79,79,21),(80,80,22),(81,81,23),(82,82,23),(83,83,24),(84,84,24),(85,85,25),(86,86,25),(87,87,25),(88,88,26),(89,89,26),(90,90,26),(91,91,26),(92,92,26),(93,93,26),(94,94,27),(95,95,28),(96,96,29),(97,97,30),(98,98,31),(99,99,32),(100,100,33),(101,101,34),(102,102,35),(103,103,36),(104,104,36),(105,105,37),(106,106,38),(107,107,39),(108,108,39),(109,109,40),(110,110,41),(111,111,42),(112,112,72),(113,113,72),(114,114,72),(115,115,72),(116,116,44),(117,117,44),(118,118,45),(119,119,46),(120,120,47),(121,121,48),(122,122,49),(123,123,50),(124,124,51),(125,125,51),(126,126,53),(127,127,53),(128,128,54),(129,129,55),(130,130,55),(131,131,55),(132,132,56);
/*!40000 ALTER TABLE `StateBoundaries` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-11 20:56:52
