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
-- Table structure for table `BelongsTo`
--

DROP TABLE IF EXISTS `BelongsTo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `BelongsTo` (
  `BelongsId` int(11) NOT NULL AUTO_INCREMENT,
  `SuperDistrict_Id` int(11) NOT NULL,
  `DistrictId` int(11) NOT NULL,
  PRIMARY KEY (`BelongsId`,`SuperDistrict_Id`,`DistrictId`),
  KEY `fk_SuperDistrict_has_District_District1_idx` (`DistrictId`),
  KEY `fk_SuperDistrict_has_District_SuperDistrict1_idx` (`SuperDistrict_Id`,`BelongsId`),
  CONSTRAINT `fk_SuperDistrict_has_District_District1` FOREIGN KEY (`DistrictId`) REFERENCES `Districts` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_SuperDistrict_has_District_SuperDistrict1` FOREIGN KEY (`SuperDistrict_Id`) REFERENCES `SuperDistricts` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `BelongsTo`
--

LOCK TABLES `BelongsTo` WRITE;
/*!40000 ALTER TABLE `BelongsTo` DISABLE KEYS */;
/*!40000 ALTER TABLE `BelongsTo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-11-14 13:28:31
