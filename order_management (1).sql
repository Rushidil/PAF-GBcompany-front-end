-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: May 14, 2021 at 02:26 PM
-- Server version: 5.7.31
-- PHP Version: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `order_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
CREATE TABLE IF NOT EXISTS `cart` (
  `cID` int(11) NOT NULL AUTO_INCREMENT,
  `ptname` varchar(50) NOT NULL,
  `unitPrice` double NOT NULL,
  `qty` int(11) NOT NULL,
  PRIMARY KEY (`cID`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`cID`, `ptname`, `unitPrice`, `qty`) VALUES
(18, 'Gaming Laptop', 220000, 1),
(3, 'Motar', 12000, 3),
(17, 'Printer', 3000, 5),
(16, 'Camera', 6700, 2),
(9, 'BiKN tracking device', 4000, 2);

-- --------------------------------------------------------

--
-- Table structure for table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
CREATE TABLE IF NOT EXISTS `orderdetails` (
  `oID` int(11) NOT NULL AUTO_INCREMENT,
  `cID` int(11) NOT NULL,
  `date` varchar(50) NOT NULL,
  `cname` varchar(50) NOT NULL,
  `phone` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`oID`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `orderdetails`
--

INSERT INTO `orderdetails` (`oID`, `cID`, `date`, `cname`, `phone`, `address`, `email`, `total`) VALUES
(13, 17, '2021-03-14', 'Udeni Damayanthi', '0734442589', 'Mahara', 'udeni@gmail.com', 15000),
(12, 16, '2021-05-12', 'Thirushi Dilmika', '0711442589', 'Gampaha', 'thiru@gmail.com', 13400),
(11, 9, '2021-02-06', 'Ruwan Dias', '0784567547', '45 / 34 ,Malabe', 'ruwan44@gmail.com', 8000),
(8, 3, '2021-04-12', 'Dilini Thari', '0711442356', 'Kadawatha', 'thiru@gmail.com', 36000),
(14, 18, '2021-05-15', 'Tharushi Himaya', '0761234567', 'Rathmalana', 'tharu555@gmail.com', 220000);

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
CREATE TABLE IF NOT EXISTS `payment` (
  `pID` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) NOT NULL,
  `lname` varchar(50) NOT NULL,
  `cardNo` varchar(50) NOT NULL,
  `cdate` varchar(50) NOT NULL,
  `ccv` varchar(10) NOT NULL,
  PRIMARY KEY (`pID`) USING BTREE
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`pID`, `fname`, `lname`, `cardNo`, `cdate`, `ccv`) VALUES
(1, 'Thirushi', 'Dilmika', '345 4356 5643 213 ', '2021-04-21', '013'),
(3, 'Hiruni', 'Sakunika', '234 5678 6786 234', '2021-03-18', '234'),
(4, 'Eshan', 'Sandeepa', '234 8765 0758 122 ', '2021-04-24', '087'),
(8, 'Sonu', 'Petta', '342 4567 4567 128', '2021-04-28', '001'),
(10, 'Charith', 'Dias', '001 0987 7568 342', '2021-04-24', '908');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
