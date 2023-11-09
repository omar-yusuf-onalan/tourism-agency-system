-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: agency
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `facility`
--

DROP TABLE IF EXISTS `facility`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `facility` (
  `facility_id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NOT NULL,
  `type` enum('Free Parking','Free Wifi','Swimming Pool','Fitness Center','Hotel Concierge','Spa','Room Service') NOT NULL,
  PRIMARY KEY (`facility_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facility`
--

LOCK TABLES `facility` WRITE;
/*!40000 ALTER TABLE `facility` DISABLE KEYS */;
INSERT INTO `facility` VALUES (1,1,'Free Parking'),(2,2,'Free Wifi'),(3,3,'Swimming Pool'),(4,4,'Fitness Center'),(5,5,'Hotel Concierge');
/*!40000 ALTER TABLE `facility` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hotel`
--

DROP TABLE IF EXISTS `hotel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hotel` (
  `hotel_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `city` varchar(255) NOT NULL,
  `region` varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `telephone` varchar(255) NOT NULL,
  `star` varchar(255) NOT NULL,
  PRIMARY KEY (`hotel_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hotel`
--

LOCK TABLES `hotel` WRITE;
/*!40000 ALTER TABLE `hotel` DISABLE KEYS */;
INSERT INTO `hotel` VALUES (1,'Luxury Resort','Maldives','North Atoll','123 Paradise Island','luxury@example.com','+1234567890','5 stars'),(2,'Mountain Lodge','Switzerland','Swiss Alps','456 Alpine Rd','mountain@example.com','+9876543210','4 stars'),(3,'Beachfront Hotel','Florida','Miami Beach','789 Ocean Drive','beachfront@example.com','+1122334455','4 stars'),(4,'City Center Inn','France','Paris','101 Eiffel Tower Ave','city@example.com','+3366998877','3 stars'),(5,'Cozy Cabin','Canada','Banff','234 Forest Road','cabin@example.com','+1555666777','2 stars');
/*!40000 ALTER TABLE `hotel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lodging`
--

DROP TABLE IF EXISTS `lodging`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `lodging` (
  `lodging_id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NOT NULL,
  `type` enum('Ultra Everything','Everything','Room Breakfast','Full Lodging','Half Lodging','Only Bed','Full Credit') NOT NULL,
  PRIMARY KEY (`lodging_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lodging`
--

LOCK TABLES `lodging` WRITE;
/*!40000 ALTER TABLE `lodging` DISABLE KEYS */;
INSERT INTO `lodging` VALUES (1,1,'Ultra Everything'),(2,2,'Everything'),(3,3,'Room Breakfast'),(4,4,'Full Lodging'),(5,5,'Only Bed');
/*!40000 ALTER TABLE `lodging` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `period`
--

DROP TABLE IF EXISTS `period`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `period` (
  `period_id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NOT NULL,
  `winter_start` date NOT NULL,
  `winter_end` date NOT NULL,
  `summer_start` date NOT NULL,
  `summer_end` date NOT NULL,
  PRIMARY KEY (`period_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `period`
--

LOCK TABLES `period` WRITE;
/*!40000 ALTER TABLE `period` DISABLE KEYS */;
INSERT INTO `period` VALUES (1,1,'2023-12-01','2024-02-29','2024-06-01','2024-08-31'),(2,2,'2023-12-01','2024-03-31','2024-07-01','2024-09-30'),(3,3,'2024-01-01','2024-03-31','2024-07-01','2024-09-30'),(4,4,'2024-01-01','2024-04-30','2024-06-01','2024-08-31'),(5,5,'2023-12-01','2024-03-31','2024-06-01','2024-08-31');
/*!40000 ALTER TABLE `period` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price` (
  `price_id` int NOT NULL AUTO_INCREMENT,
  `lodging_id` int NOT NULL,
  `room_id` int NOT NULL,
  `winter_adult_price` double NOT NULL,
  `winter_child_price` double NOT NULL,
  `summer_adult_price` double NOT NULL,
  `summer_child_price` double NOT NULL,
  PRIMARY KEY (`price_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price`
--

LOCK TABLES `price` WRITE;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` VALUES (1,1,1,400,100,500,150),(2,2,2,600,150,750,200),(3,3,3,800,200,1000,250),(4,4,4,300,75,400,100),(5,5,5,500,125,600,150);
/*!40000 ALTER TABLE `price` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `reservation_id` int NOT NULL AUTO_INCREMENT,
  `room_id` int NOT NULL,
  `contact_name` varchar(255) NOT NULL,
  `contact_telephone` varchar(255) NOT NULL,
  `contact_email` varchar(255) NOT NULL,
  `note` varchar(255) NOT NULL,
  `adult_information` varchar(255) NOT NULL,
  `child_information` varchar(255) NOT NULL,
  `arrival` date NOT NULL,
  `departure` date NOT NULL,
  PRIMARY KEY (`reservation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (1,1,'John Doe','+1234567890','john@example.com','Special requests: None','Adult 1','Child 1','2024-06-15','2024-06-22'),(2,2,'Alice Smith','+9876543210','alice@example.com','Special requests: Vegetarian meals','Adult 1, Adult 2','Child 1','2024-07-10','2024-07-17'),(3,3,'David Brown','+1122334455','david@example.com','Special requests: Late check-in','Adult 1, Adult 2, Adult 3','Child 1','2024-08-05','2024-08-12'),(4,4,'Eva White','+3366998877','eva@example.com','Special requests: Airport transfer','Adult 1','Child 1','2024-06-20','2024-06-27'),(5,5,'Michael Johnson','+1555666777','michael@example.com','Special requests: Spa package','Adult 1, Adult 2','Child 1','2024-06-30','2024-07-07');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `room_id` int NOT NULL AUTO_INCREMENT,
  `hotel_id` int NOT NULL,
  `period_id` int NOT NULL,
  `name` enum('Single','Double','Suite') NOT NULL,
  `number_of_beds` int NOT NULL,
  `item` varchar(255) DEFAULT NULL,
  `square_meter` varchar(255) DEFAULT NULL,
  `stock` int NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (1,1,1,'Single',1,'Ocean View','30 sqm',10),(2,2,2,'Double',2,'Mountain View','40 sqm',20),(3,3,3,'Suite',3,'Beachfront','60 sqm',15),(4,4,4,'Single',1,'City View','25 sqm',12),(5,5,5,'Double',2,'Forest View','35 sqm',18);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  `role` enum('admin','agent') NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Admin User','admin@example.com','adminpassword','admin'),(2,'Agent 1','agent1@example.com','agent1password','agent'),(3,'Agent 2','agent2@example.com','agent2password','agent'),(4,'Agent 3','agent3@example.com','agent3password','agent'),(5,'Agent 4','agent4@example.com','agent4password','agent');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-09 16:13:21
