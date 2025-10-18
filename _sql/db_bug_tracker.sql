-- phpMyAdmin SQL Dump
-- version 5.2.2
-- https://www.phpmyadmin.net/
--
-- Host: mysql
-- Generation Time: Oct 18, 2025 at 10:48 AM
-- Server version: 9.2.0
-- PHP Version: 8.2.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_bug_tracker`
--

-- --------------------------------------------------------

--
-- Table structure for table `bugs`
--

CREATE TABLE `bugs` (
  `id` bigint NOT NULL,
  `assignee_id` bigint DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `project_id` bigint DEFAULT NULL,
  `reporter_id` bigint DEFAULT NULL,
  `severity` enum('CRITICAL','HIGH','LOW','MEDIUM') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` enum('CLOSED','IN_PROGRESS','OPEN','RESOLVED') COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `bugs`
--

INSERT INTO `bugs` (`id`, `assignee_id`, `description`, `project_id`, `reporter_id`, `severity`, `status`, `title`) VALUES
(2, 201, 'Dashboard layout breaks below 600px width.', 1, 4, 'MEDIUM', 'OPEN', 'UI not responsive on mobile');

-- --------------------------------------------------------

--
-- Table structure for table `event_publication`
--

CREATE TABLE `event_publication` (
  `id` binary(16) NOT NULL,
  `completion_date` datetime(6) DEFAULT NULL,
  `event_type` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `listener_id` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publication_date` datetime(6) DEFAULT NULL,
  `serialized_event` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `event_publication`
--

INSERT INTO `event_publication` (`id`, `completion_date`, `event_type`, `listener_id`, `publication_date`, `serialized_event`) VALUES
(0x066cd74dd8cc49729521b9c222a19bd0, '2025-10-18 10:43:31.578595', 'io.github.abbassizied.bug_tracker.events.UserDeactivated', 'io.github.abbassizied.bug_tracker.projects.ProjectEventListener.handleUserDeactivated(io.github.abbassizied.bug_tracker.events.UserDeactivated)', '2025-10-18 10:43:31.529621', '{\"userId\":3}'),
(0x7448b824dea9472b8f5646c0c0093b51, '2025-10-18 10:45:15.655332', 'io.github.abbassizied.bug_tracker.events.UserDeactivated', 'io.github.abbassizied.bug_tracker.projects.ProjectEventListener.handleUserDeactivated(io.github.abbassizied.bug_tracker.events.UserDeactivated)', '2025-10-18 10:45:15.624154', '{\"userId\":2}'),
(0xafe91ddd7a8f4e33aecb4b2f0b6368bb, '2025-10-18 10:42:25.913259', 'io.github.abbassizied.bug_tracker.events.UserDeactivated', 'io.github.abbassizied.bug_tracker.projects.ProjectEventListener.handleUserDeactivated(io.github.abbassizied.bug_tracker.events.UserDeactivated)', '2025-10-18 10:42:25.834558', '{\"userId\":2}');

-- --------------------------------------------------------

--
-- Table structure for table `projects`
--

CREATE TABLE `projects` (
  `id` bigint NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `owner_id` bigint NOT NULL,
  `status` enum('ACTIVE','ARCHIVED') COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `projects`
--

INSERT INTO `projects` (`id`, `description`, `name`, `owner_id`, `status`) VALUES
(1, 'Collaborative bug tracking system for dev teams.', 'Bug Tracker Platform', 3, 'ACTIVE'),
(2, 'Upgraded ML model for higher classification accuracy.', 'AI Issue Classifier v2', 3, 'ACTIVE'),
(4, 'Migrating legacy modules to Kubernetes microservices.', 'Cloud Migration Initiative', 3, 'ACTIVE');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` enum('ROLE_ADMIN','ROLE_DEVELOPER','ROLE_PROJECT_MANAGER','ROLE_TESTER') COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_PROJECT_MANAGER'),
(3, 'ROLE_DEVELOPER'),
(4, 'ROLE_TESTER');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint NOT NULL,
  `active` bit(1) NOT NULL,
  `email` varchar(320) COLLATE utf8mb4_unicode_ci NOT NULL,
  `first_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `last_name` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `username` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `active`, `email`, `first_name`, `last_name`, `password`, `username`) VALUES
(1, b'0', 'abbassizied@outlook.fr', 'Zied', 'Abbassi', '$2a$10$u.7bNp/RyJyKMjyLWgzNfezEaJyJ05ePXL6mqH.Bjd6qyJ5R4tnYe', 'abbassi_zied'),
(4, b'0', 'admin@local.tn', 'admin', 'admin', '$2a$10$iEdKfwh4Gr2rydZqGQqCJeaMcgJgQ0/iCf2k6RzbIHDWvLXLk2AuS', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1),
(4, 1),
(1, 2),
(4, 2),
(4, 3),
(4, 4);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bugs`
--
ALTER TABLE `bugs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `event_publication`
--
ALTER TABLE `event_publication`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `projects`
--
ALTER TABLE `projects`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK1e447b96pedrvtxw44ot4qxem` (`name`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKr43af9ap4edm43mmtq01oddj6` (`username`),
  ADD UNIQUE KEY `UK6dotkott2kjsp8vw4d0m25fb7` (`email`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKh8ciramu9cc9q3qcqiv4ue8a6` (`role_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bugs`
--
ALTER TABLE `bugs`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `projects`
--
ALTER TABLE `projects`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FKh8ciramu9cc9q3qcqiv4ue8a6` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  ADD CONSTRAINT `FKhfh9dx7w3ubf1co1vdev94g3f` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
