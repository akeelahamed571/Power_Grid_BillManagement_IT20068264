-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 14, 2022 at 04:50 PM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 8.0.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `sampleleco`
--

-- --------------------------------------------------------

--
-- Table structure for table `samplebill`
--

CREATE TABLE `samplebill` (
  `id` int(11) NOT NULL,
  `years` int(4) DEFAULT NULL,
  `months` int(2) DEFAULT NULL,
  `acno` varchar(10) DEFAULT NULL,
  `totalunits` decimal(8,2) DEFAULT NULL,
  `totalunitcharge` decimal(8,2) DEFAULT NULL,
  `fixedcharge` decimal(8,2) DEFAULT NULL,
  `total` decimal(8,2) DEFAULT NULL,
  `due` decimal(8,2) DEFAULT NULL
) ;

--
-- Dumping data for table `samplebill`
--

INSERT INTO `samplebill` (`id`, `years`, `months`, `acno`, `totalunits`, `totalunitcharge`, `fixedcharge`, `total`, `due`) VALUES
(5, 2024, 9, 'ac12345676', '100.00', '840.00', '540.00', '1388.00', '8.00'),
(7, 2027, 10, 'ac12345676', '10.00', '60.00', '540.00', '630.00', '30.00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `samplebill`
--
ALTER TABLE `samplebill`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `samplebill`
--
ALTER TABLE `samplebill`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
