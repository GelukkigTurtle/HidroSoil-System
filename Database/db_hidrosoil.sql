CREATE DATABASE  IF NOT EXISTS `db_hidrosoil` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_hidrosoil`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: db_hidrosoil
-- ------------------------------------------------------
-- Server version	5.6.27-log

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
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cliente` (
  `CLI_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CLI_EMPRESA` varchar(45) NOT NULL,
  `CLI_CONTACTO` varchar(45) DEFAULT NULL,
  `CLI_TELEFONO` varchar(45) DEFAULT NULL,
  `CLI_CELULAR` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CLI_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'Alcaldia Juayua','Don William','22336655','77889966'),(2,'Alcaldia San Salvador','Nayib Bukele','22546987','71458965');
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `costos`
--

DROP TABLE IF EXISTS `costos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `costos` (
  `COS_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COS_NOMBRE` varchar(45) DEFAULT NULL,
  `COS_DESCRIPCION` mediumtext,
  `COS_VALOR` double DEFAULT NULL,
  `COS_PAGO_IVA` double DEFAULT NULL,
  `COS_PAGO_CUENTA` double DEFAULT NULL,
  `COS_FECHA` varchar(45) DEFAULT NULL,
  `IVA_IVA_ID` int(11) NOT NULL,
  PRIMARY KEY (`COS_ID`),
  KEY `fk_COSTOS_IVA1_idx` (`IVA_IVA_ID`),
  CONSTRAINT `fk_COSTOS_IVA1` FOREIGN KEY (`IVA_IVA_ID`) REFERENCES `iva` (`IVA_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `costos`
--

LOCK TABLES `costos` WRITE;
/*!40000 ALTER TABLE `costos` DISABLE KEYS */;
INSERT INTO `costos` VALUES (1,'Gasolina','Gasto de gasolina diario	',30,3.9000000000000004,0.6,'20/04/2012',1),(2,'Grua','Alquiler de grua	',500,65,10,'20/04/2012',1);
/*!40000 ALTER TABLE `costos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `iva`
--

DROP TABLE IF EXISTS `iva`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `iva` (
  `IVA_ID` int(11) NOT NULL AUTO_INCREMENT,
  `IVA_TOTAL` varchar(45) DEFAULT NULL,
  `IVA_FECHA` varchar(45) DEFAULT NULL,
  `IVA_PAGO_CUENTA` double DEFAULT NULL,
  `RENTA_REN_ID` int(11) NOT NULL,
  PRIMARY KEY (`IVA_ID`),
  KEY `fk_IVA_RENTA1_idx` (`RENTA_REN_ID`),
  CONSTRAINT `fk_IVA_RENTA1` FOREIGN KEY (`RENTA_REN_ID`) REFERENCES `renta` (`REN_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `iva`
--

LOCK TABLES `iva` WRITE;
/*!40000 ALTER TABLE `iva` DISABLE KEYS */;
INSERT INTO `iva` VALUES (1,'7','2016-11-14 11:35:37',30,1);
/*!40000 ALTER TABLE `iva` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `presupuesto`
--

DROP TABLE IF EXISTS `presupuesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `presupuesto` (
  `PRE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRE_COMBUSTIBLE` double DEFAULT NULL,
  `PRE_MATERIALES` double DEFAULT NULL,
  `PRE_EQUIPO` double DEFAULT NULL,
  `PRE_MANO_OBRA` double DEFAULT NULL,
  `PRE_VIATICOS` double DEFAULT NULL,
  `PRE_TOTAL_VENTA` double DEFAULT NULL,
  `PRE_TOTAL_COMPRA` double DEFAULT NULL,
  `PRE_GANANCIA_NETA` double DEFAULT NULL,
  `PRE_UTILIDAD_NETA` double DEFAULT NULL,
  `PRE_OP_CREDITO` tinyint(1) DEFAULT NULL,
  `PRE_OP_CONTADO` tinyint(1) DEFAULT NULL,
  `PRE_PRIMA` double DEFAULT NULL,
  `PRE_NUM_CUOTAS` int(11) DEFAULT NULL,
  `PRE_VAL_CUOTA` double DEFAULT NULL,
  `PRE_PAGO_RENTA` double DEFAULT NULL,
  `PRE_PAGO_IVA` double DEFAULT NULL,
  `PRE_PAGO_CUENTA` double DEFAULT NULL,
  `IVA_IVA_ID` int(11) NOT NULL,
  `PROYECTO_PRO_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRE_ID`),
  KEY `fk_PRESUPUESTO_IVA1_idx` (`IVA_IVA_ID`),
  KEY `fk_PRESUPUESTO_PROYECTO1_idx` (`PROYECTO_PRO_ID`),
  CONSTRAINT `fk_PRESUPUESTO_IVA1` FOREIGN KEY (`IVA_IVA_ID`) REFERENCES `iva` (`IVA_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_PRESUPUESTO_PROYECTO1` FOREIGN KEY (`PROYECTO_PRO_ID`) REFERENCES `proyecto` (`PRO_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `presupuesto`
--

LOCK TABLES `presupuesto` WRITE;
/*!40000 ALTER TABLE `presupuesto` DISABLE KEYS */;
/*!40000 ALTER TABLE `presupuesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proyecto`
--

DROP TABLE IF EXISTS `proyecto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proyecto` (
  `PRO_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRO_NOMBRE` varchar(45) DEFAULT NULL,
  `PRO_RUBRO` varchar(45) DEFAULT NULL,
  `PRO_DESCRIPCION` mediumtext,
  `PRO_DURACION` int(11) DEFAULT NULL,
  `PRO_ESTADO` varchar(45) DEFAULT NULL,
  `PRO_DEPARTAMENTO` varchar(45) DEFAULT NULL,
  `PRO_MUNICIPIO` varchar(45) DEFAULT NULL,
  `PRO_FECHA_INICIO` varchar(45) DEFAULT NULL,
  `PRO_FECHA_FIN` varchar(45) DEFAULT NULL,
  `CLIENTE_CLI_ID` int(11) NOT NULL,
  PRIMARY KEY (`PRO_ID`),
  KEY `fk_PROYECTO_CLIENTE1_idx` (`CLIENTE_CLI_ID`),
  CONSTRAINT `fk_PROYECTO_CLIENTE1` FOREIGN KEY (`CLIENTE_CLI_ID`) REFERENCES `cliente` (`CLI_ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proyecto`
--

LOCK TABLES `proyecto` WRITE;
/*!40000 ALTER TABLE `proyecto` DISABLE KEYS */;
INSERT INTO `proyecto` VALUES (1,'Pozo de agua Juayua','Agua','Instalacion de un pozo en area rural de Juayua',30,'ACTIVO','Sonsonate','Juayúa','01/05/2012','01/06/2012',1),(2,'Instalacion Electrica San Salvador','Electricidad','Reestrucutracion de cableado electrico para la alcaldia de San Salvador',30,'FINALIZADO','San Salvador','San Salvador','01/03/2012','01/03/2012',2);
/*!40000 ALTER TABLE `proyecto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `renta`
--

DROP TABLE IF EXISTS `renta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `renta` (
  `REN_ID` int(11) NOT NULL AUTO_INCREMENT,
  `REN_TOTAL` varchar(45) DEFAULT NULL,
  `REN_FECHA` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`REN_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `renta`
--

LOCK TABLES `renta` WRITE;
/*!40000 ALTER TABLE `renta` DISABLE KEYS */;
INSERT INTO `renta` VALUES (1,'250','2016-11-14 11:35:22');
/*!40000 ALTER TABLE `renta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'db_hidrosoil'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-11-14 17:06:30
