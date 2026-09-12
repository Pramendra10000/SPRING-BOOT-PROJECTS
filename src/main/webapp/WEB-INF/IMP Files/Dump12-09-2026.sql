-- MySQL dump 10.13  Distrib 8.0.45, for Win64 (x86_64)
--
-- Host: localhost    Database: parammart_db
-- ------------------------------------------------------
-- Server version	8.0.46

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
-- Table structure for table `addresses`
--

DROP TABLE IF EXISTS `addresses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `addresses` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address_line1` varchar(255) NOT NULL,
  `address_line2` varchar(255) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
  `country` varchar(255) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `default_address` bit(1) NOT NULL,
  `full_name` varchar(255) NOT NULL,
  `mobile` varchar(255) NOT NULL,
  `pincode` varchar(255) NOT NULL,
  `state` varchar(255) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1fa36y2oqhao3wgg2rw1pi459` (`user_id`),
  CONSTRAINT `FK1fa36y2oqhao3wgg2rw1pi459` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `addresses`
--

LOCK TABLES `addresses` WRITE;
/*!40000 ALTER TABLE `addresses` DISABLE KEYS */;
/*!40000 ALTER TABLE `addresses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brands`
--

DROP TABLE IF EXISTS `brands`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brands` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `category_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKoce3937d2f4mpfqrycbr0l93m` (`name`),
  KEY `FK9q7xi3v910jhoa63aaiyibqex` (`category_id`),
  CONSTRAINT `FK9q7xi3v910jhoa63aaiyibqex` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brands`
--

LOCK TABLES `brands` WRITE;
/*!40000 ALTER TABLE `brands` DISABLE KEYS */;
INSERT INTO `brands` VALUES (2,_binary '','2026-08-20 05:41:15.889055','Samsung electronics brand offering smartphones and mobile devices.','Samsung','2026-08-20 05:41:15.889055',1),(3,_binary '','2026-08-20 05:41:28.540850','Apple brand offering premium smartphones and mobile devices.','Apple','2026-08-20 05:41:28.540850',1),(4,_binary '','2026-08-20 05:41:36.573758','OnePlus brand offering smartphones with powerful performance and modern features.','OnePlus','2026-08-20 05:41:36.573758',1),(5,_binary '','2026-08-20 05:41:44.234643','Dell brand offering laptops and computers for work, education and everyday use.','Dell','2026-08-20 05:41:44.234643',2),(6,_binary '','2026-08-20 05:41:51.517726','HP brand offering laptops, computers and technology products for work and personal use.','HP','2026-08-20 05:41:51.517726',2),(7,_binary '','2026-08-20 05:41:58.696834','Sony brand offering headphones, earbuds, speakers and premium audio products.','Sony','2026-08-20 05:41:58.696834',3),(8,_binary '','2026-08-20 05:42:05.762963','Logitech brand offering keyboards, mice, webcams and other computer accessories.','Logitech','2026-08-20 05:42:05.762963',6),(9,_binary '','2026-08-20 05:42:14.445296','Lenovo brand offering gaming laptops, gaming computers and gaming accessories.','Lenovo','2026-08-20 05:42:14.445296',8);
/*!40000 ALTER TABLE `brands` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_items`
--

DROP TABLE IF EXISTS `cart_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` decimal(38,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `cart_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpcttvuq4mxppo8sxggjtn5i2c` (`cart_id`),
  KEY `FK1re40cjegsfvw58xrkdp6bac6` (`product_id`),
  CONSTRAINT `FK1re40cjegsfvw58xrkdp6bac6` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `FKpcttvuq4mxppo8sxggjtn5i2c` FOREIGN KEY (`cart_id`) REFERENCES `carts` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_items`
--

LOCK TABLES `cart_items` WRITE;
/*!40000 ALTER TABLE `cart_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `cart_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carts`
--

DROP TABLE IF EXISTS `carts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `carts` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `total_items` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK64t7ox312pqal3p7fg9o503c2` (`user_id`),
  CONSTRAINT `FKb5o626f86h46m4s7ms6ginnop` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carts`
--

LOCK TABLES `carts` WRITE;
/*!40000 ALTER TABLE `carts` DISABLE KEYS */;
/*!40000 ALTER TABLE `carts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(100) NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKt8o6pivur7nn124jehx7cygw5` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,_binary '','2026-08-20 05:30:24.745234','Smartphones and mobile phones from different brands.','Mobiles','2026-08-20 05:30:24.745234'),(2,_binary '','2026-08-20 05:31:29.630673','Laptops and notebooks for work, study, gaming and everyday use.','Laptops','2026-08-20 05:31:29.630673'),(3,_binary '','2026-08-20 05:31:39.166232','Headphones, wireless earbuds, speakers and other audio devices.','Audio','2026-08-20 05:31:39.166232'),(4,_binary '','2026-08-20 05:31:47.756484','Smart watches and wearable devices for fitness, health tracking and notifications.','Smart Watches','2026-08-20 05:31:47.756484'),(5,_binary '','2026-08-20 05:31:55.263989','Smart TVs, LED TVs, OLED TVs and 4K televisions for home entertainment.','Televisions','2026-08-20 05:31:55.263989'),(6,_binary '','2026-08-20 05:32:03.656371','Computer mice, keyboards, webcams, monitors and other computer peripherals.','Computer Accessories','2026-08-20 05:32:03.656371'),(7,_binary '','2026-08-20 05:32:13.657892','Tablets for entertainment, education, productivity and everyday use.','Tablets','2026-08-20 05:32:13.658890'),(8,_binary '','2026-08-20 05:32:20.803308','Gaming consoles, controllers, gaming accessories and other gaming devices.','Gaming','2026-08-20 05:32:20.803308'),(9,_binary '','2026-08-20 05:32:28.930235','Mobile chargers, cables, power banks, cases and other electronic accessories.','Accessories','2026-08-20 05:32:28.930235');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventory` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `available_stock` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `maximum_stock` int NOT NULL,
  `minimum_stock` int NOT NULL,
  `reserved_stock` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `warehouse_location` varchar(100) DEFAULT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKce3rbi3bfstbvvyne34c1dvyv` (`product_id`),
  CONSTRAINT `FKq2yge7ebtfuvwufr6lwfwqy9l` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `price` decimal(38,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `total_price` decimal(38,2) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbioxgbv59vetrxe0ejfubep1w` (`order_id`),
  KEY `FKocimc7dtr037rh4ls4l95nlfi` (`product_id`),
  CONSTRAINT `FKbioxgbv59vetrxe0ejfubep1w` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `FKocimc7dtr037rh4ls4l95nlfi` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `ordered_at` datetime(6) DEFAULT NULL,
  `status` enum('CANCELLED','CONFIRMED','DELIVERED','PENDING','PROCESSING','SHIPPED') DEFAULT NULL,
  `total_amount` decimal(38,2) DEFAULT NULL,
  `total_items` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK32ql8ubntj5uh44ph9659tiih` (`user_id`),
  CONSTRAINT `FK32ql8ubntj5uh44ph9659tiih` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` decimal(38,2) NOT NULL,
  `payment_date` datetime(6) DEFAULT NULL,
  `payment_method` enum('COD','CREDIT_CARD','DEBIT_CARD','NET_BANKING','UPI') DEFAULT NULL,
  `payment_status` enum('FAILED','PENDING','REFUNDED','SUCCESS') DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKlryndveuwa4k5qthti0pkmtlx` (`transaction_id`),
  KEY `FK81gagumt0r8y3rmudcgpbk42l` (`order_id`),
  CONSTRAINT `FK81gagumt0r8y3rmudcgpbk42l` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_media`
--

DROP TABLE IF EXISTS `product_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_media` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) NOT NULL,
  `alt_text` varchar(500) DEFAULT NULL,
  `content_type` varchar(100) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `display_order` int NOT NULL,
  `file_name` varchar(255) NOT NULL,
  `file_size` bigint NOT NULL,
  `is_primary` bit(1) NOT NULL,
  `media_type` enum('DOCUMENT','IMAGE','VIDEO') NOT NULL,
  `media_url` varchar(1000) DEFAULT NULL,
  `storage_key` varchar(500) NOT NULL,
  `updated_at` datetime(6) NOT NULL,
  `product_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrddbmk8eee6urapki8lvebqm4` (`storage_key`),
  KEY `idx_product_media_type` (`media_type`),
  KEY `idx_product_media_product_active_order` (`product_id`,`active`,`display_order`),
  KEY `idx_product_media_product_primary_active` (`product_id`,`is_primary`,`active`),
  CONSTRAINT `fk_product_media_product` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_media`
--

LOCK TABLES `product_media` WRITE;
/*!40000 ALTER TABLE `product_media` DISABLE KEYS */;
INSERT INTO `product_media` VALUES (2,_binary '','Samsung Galaxy S25 front view','image/jpeg','2026-09-12 07:45:10.626690',1,'Samsung S25 5g.jpg',11738,_binary '\0','IMAGE','http://localhost:8080/media/products/1/images/91f5af8d-20c6-4ca7-8ec1-4d5619aea868.jpg','products/1/images/91f5af8d-20c6-4ca7-8ec1-4d5619aea868.jpg','2026-09-12 08:58:33.578601',1),(3,_binary '','Samsung Galaxy S25 front view','image/avif','2026-09-12 08:49:36.167298',2,'2ndsamsung.avif',8773,_binary '\0','IMAGE','http://localhost:8080/media/products/1/images/8c70f883-0715-4487-b134-8f1f21e9a8f6.avif','products/1/images/8c70f883-0715-4487-b134-8f1f21e9a8f6.avif','2026-09-12 08:49:36.167298',1),(4,_binary '','Samsung Galaxy S25 front view','image/avif','2026-09-12 08:50:01.305968',3,'3rdsamsung.avif',61045,_binary '\0','IMAGE','http://localhost:8080/media/products/1/images/2e79589a-87aa-4804-8d13-1bc34d7ab0f1.avif','products/1/images/2e79589a-87aa-4804-8d13-1bc34d7ab0f1.avif','2026-09-12 09:24:27.699122',1),(5,_binary '','Samsung Galaxy S25 front view','image/avif','2026-09-12 08:50:14.404004',4,'4thsam.avif',60918,_binary '\0','IMAGE','http://localhost:8080/media/products/1/images/d4443727-88b1-493b-866f-5b9116942678.avif','products/1/images/d4443727-88b1-493b-866f-5b9116942678.avif','2026-09-12 08:50:14.404004',1),(7,_binary '','Samsung Galaxy S25 front view','image/webp','2026-09-12 08:58:35.497369',6,'primarynewsam.webp',2236,_binary '','IMAGE','http://localhost:8080/media/products/1/images/d5bcde5d-53c5-476c-b464-f7488f14a7ce.webp','products/1/images/d5bcde5d-53c5-476c-b464-f7488f14a7ce.webp','2026-09-12 09:24:27.697619',1);
/*!40000 ALTER TABLE `product_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `active` bit(1) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `name` varchar(150) NOT NULL,
  `price` decimal(12,2) NOT NULL,
  `sku` varchar(50) NOT NULL,
  `stock` int NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `brand_id` bigint DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKfhmd06dsmj6k0n90swsh8ie9g` (`sku`),
  KEY `FKa3a4mpsfdf4d2y6r8ra3sc8mv` (`brand_id`),
  KEY `FKog2rp4qthbtt2lfyhfo32lsw9` (`category_id`),
  CONSTRAINT `FKa3a4mpsfdf4d2y6r8ra3sc8mv` FOREIGN KEY (`brand_id`) REFERENCES `brands` (`id`),
  CONSTRAINT `FKog2rp4qthbtt2lfyhfo32lsw9` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,_binary '','2026-08-20 05:43:43.254095','Samsung Galaxy S25 with 6.2-inch AMOLED display, 12GB RAM, 256GB storage and advanced camera system.','Samsung Galaxy S25',74999.00,'SAM-S25-256-BLK',25,'2026-08-20 05:43:43.254095',2,1),(2,_binary '','2026-08-20 05:45:09.678337','Apple iPhone 16 with a powerful processor, advanced dual-camera system, 6.1-inch display and 128GB storage.','Apple iPhone 16',69999.00,'APL-IP16-128-BLK',20,'2026-08-20 05:45:09.678337',3,1),(3,_binary '','2026-08-20 05:45:26.937942','OnePlus 13 with a high-performance processor, 12GB RAM, 256GB storage, premium display and fast charging.','OnePlus 13',64999.00,'OP-13-256-BLU',30,'2026-08-20 05:45:26.937942',4,1),(4,_binary '','2026-08-20 05:45:37.595314','Dell Inspiron 15 laptop with Intel Core i5 processor, 16GB RAM, 512GB SSD and Full HD display.','Dell Inspiron 15',64999.00,'DEL-INS15-I5-512',15,'2026-08-20 05:45:37.595314',5,2),(5,_binary '','2026-08-20 05:45:46.437018','HP Pavilion 14 laptop with Intel Core i5 processor, 16GB RAM, 512GB SSD and compact Full HD display.','HP Pavilion 14',61999.00,'HP-PAV14-I5-512',18,'2026-08-20 05:45:46.437018',6,2),(6,_binary '','2026-08-20 05:45:53.493058','Premium wireless headphones with active noise cancellation, high-quality audio and long battery life.','Sony WH-1000XM5 Wireless Headphones',29999.00,'SON-WH1000XM5-BLK',20,'2026-08-20 05:45:53.493058',7,3),(7,_binary '','2026-08-20 06:00:34.490936','Premium wireless earbuds with active noise cancellation, high-resolution audio and a compact charging case.','Sony WF-1000XM5 Wireless Earbuds',24999.00,'SON-WF1000XM5-BLK',25,'2026-08-20 06:00:34.490936',7,3),(8,_binary '','2026-08-20 06:00:48.447235','Advanced wireless productivity mouse with precision tracking, ergonomic design and customizable buttons.','Logitech MX Master 3S',8495.00,'LOG-MX3S-GRAPH',45,'2026-08-20 06:00:48.447235',8,6),(9,_binary '','2026-08-20 06:00:55.592415','Compact wireless keyboard with Bluetooth connectivity, comfortable typing and support for multiple devices.','Logitech K380 Wireless Keyboard',3295.00,'LOG-K380-WHT',50,'2026-08-20 06:00:55.592415',8,6),(10,_binary '','2026-08-20 06:01:02.978894','Lenovo Legion gaming laptop with high-performance processor, dedicated graphics, 16GB RAM and 1TB SSD.','Lenovo Legion Gaming Laptop',109999.00,'LEN-LEGION-I7-1TB',8,'2026-08-20 06:01:02.978894',9,8),(11,_binary '','2026-08-20 06:01:09.972076','Lenovo Legion gaming mouse with precision tracking, ergonomic design and responsive gaming controls.','Lenovo Legion Gaming Mouse',3999.00,'LEN-LEGION-MOUSE-BLK',30,'2026-08-20 06:01:09.972076',9,8);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `credentials_expired` bit(1) NOT NULL,
  `email` varchar(150) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `mobile` varchar(15) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('ADMIN','CUSTOMER','EMPLOYEE','MANAGER') NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`),
  UNIQUE KEY `UK63cf888pmqtt5tipcne79xsbm` (`mobile`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,_binary '\0',_binary '\0','2026-08-20 05:24:29.934396',_binary '\0','param@gmail.com',_binary '','Pramendra','Singh','8928391908','$2a$10$e4cDvE6AcqGB9hPFISpg2eplD1O0bkLnqSDzNcvigpGhQNEOCL3Ia','ADMIN','2026-08-20 05:24:29.934396'),(2,_binary '\0',_binary '\0','2026-08-20 12:10:48.274872',_binary '\0','rahul@abc.com',_binary '','Rahul','Kumar','9876543211','$2a$10$5OBNrHBLH/UIhDdMxVqOdemw5i7/3GaZrpAsL1kbohDrYzaQZriHO','ADMIN','2026-08-20 12:10:48.274872'),(3,_binary '\0',_binary '\0','2026-09-09 04:33:10.340577',_binary '\0','Tony@gmail.com',_binary '','Tony','Stark','6779809551','$2a$10$4gkMD1TNzF2x3ngbNYy5UuWQ3ItiQOvmbWx1aThxdI.u1XjeUYxRK','CUSTOMER','2026-09-09 04:33:10.340577');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-09-12 18:56:26
