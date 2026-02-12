-- src/main/resources/db/migration/V1__create_users_table.sql
-- MySQL migration: users table with profile as ENUM

CREATE TABLE `users` (
     `id` BIGINT NOT NULL AUTO_INCREMENT,
     `username` VARCHAR(255) NOT NULL,
     `password` VARCHAR(255),
     `name` VARCHAR(255),
     `email` VARCHAR(255) NOT NULL,
     `profile` ENUM('READER','ADMIN','LIBRARIAN') NOT NULL,
     `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `deleted` TINYINT(1) NOT NULL DEFAULT 0,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_users_username` (`username`),
     UNIQUE KEY `uk_users_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
