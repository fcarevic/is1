-- MySQL dump 10.13  Distrib 8.0.18, for Win64 (x86_64)
--
-- Host: localhost    Database: zahteviid
-- ------------------------------------------------------
-- Server version	8.0.18

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `zahtevi`
--

DROP TABLE IF EXISTS `zahtevi`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `zahtevi` (
  `id` varchar(15) NOT NULL,
  `JMBG` varchar(15) NOT NULL,
  `ime` varchar(15) NOT NULL,
  `prezime` varchar(15) NOT NULL,
  `imeMajke` varchar(15) DEFAULT NULL,
  `prezimeMajke` varchar(15) DEFAULT NULL,
  `imeOca` varchar(15) DEFAULT NULL,
  `prezimeOca` varchar(15) DEFAULT NULL,
  `pol` varchar(15) DEFAULT NULL,
  `datumRodjenja` varchar(15) DEFAULT NULL,
  `nacionalnost` varchar(15) DEFAULT NULL,
  `profesija` varchar(15) DEFAULT NULL,
  `bracnoStanje` varchar(15) DEFAULT NULL,
  `opstinaPrebivalista` varchar(15) DEFAULT NULL,
  `ulicaPrebivalista` varchar(15) DEFAULT NULL,
  `brojPrebivalista` varchar(15) DEFAULT NULL,
  `status` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `JedinstveniID_UNIQUE` (`id`),
  UNIQUE KEY `JMBG_UNIQUE` (`JMBG`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zahtevi`
--

LOCK TABLES `zahtevi` WRITE;
/*!40000 ALTER TABLE `zahtevi` DISABLE KEYS */;
INSERT INTO `zahtevi` VALUES ('170650000001','1234567812346','Vukasin','Draskovic','','','','','','','','','','','','','kreiran'),('170650000002','2009998782818','Filip','Carevic','','','','','','','','','','','','','kreiran'),('170650000003','2009998782819','Filip','Carevic','','','','','','','','','','','','','kreiran'),('170650000004','1234','Filip','Carevic','','','','','','','','','','','','','kreiran'),('170650000005','2009998782820','Filip','Carevic','','','','','','','','','','','','','kreiran'),('170650000006','2009998782821','Filip','Carevic','','','','','','','','','','','','','kreiran');
/*!40000 ALTER TABLE `zahtevi` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'zahteviid'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-22 17:55:26
