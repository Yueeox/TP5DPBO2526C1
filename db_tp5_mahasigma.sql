-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: tp5_mahasigma
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `mahasigma`
--

DROP TABLE IF EXISTS `mahasigma`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mahasigma` (
  `nim` varchar(255) NOT NULL,
  `nama` varchar(255) NOT NULL,
  `ttl` varchar(255) NOT NULL,
  `prodi` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `status_mahasiswa` enum('AKTIF','CUTI','TIDAK_ADA_KETERANGAN','LULUS') NOT NULL,
  PRIMARY KEY (`nim`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mahasigma`
--

LOCK TABLES `mahasigma` WRITE;
/*!40000 ALTER TABLE `mahasigma` DISABLE KEYS */;
INSERT INTO `mahasigma` VALUES ('2500482','Gita Gutawa','27-04-1992','Ilmu Komputer','gita.gutawa@student.upi.edu','LULUS'),('2501938','Siti Rahmawati','22-03-1995','Pendidikan Ilmu Komputer','siti.rahmawati@student.upi.edu','AKTIF'),('2502749','Joko Widodo','03-05-1987','Pendidikan Ilmu Komputer','joko.widodo@student.upi.edu','LULUS'),('2503104','Dewi Lestari','19-01-1985','Pendidikan Ilmu Komputer','dewi.lestari@student.upi.edu','LULUS'),('2504821','Budi Santoso','14-08-1988','Ilmu Komputer','budi.santoso@student.upi.edu','AKTIF'),('2505364','Indah Permata','21-07-1999','Ilmu Komputer','indah.permata@student.upi.edu','CUTI'),('2506291','Eko Prasetyo','30-06-1998','Ilmu Komputer','eko.prasetyo@student.upi.edu','AKTIF'),('2507513','Fitriani','12-09-2003','Pendidikan Ilmu Komputer','fitriani@student.upi.edu','TIDAK_ADA_KETERANGAN'),('2508472','Jesika Br Sitanggang','05-11-2002','Ilmu Komputer','ahmad.rizky@student.upi.edu','AKTIF');
/*!40000 ALTER TABLE `mahasigma` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-14 14:52:23
