-- src/main/resources/db/migration/V5__create_readers_table.sql
-- MySQL migration: readers table

CREATE TABLE `readers` (
   `id` BIGINT NOT NULL AUTO_INCREMENT,
   `name` VARCHAR(255),
   `email` VARCHAR(255) NOT NULL,
   `phone` VARCHAR(255) NOT NULL,
   `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
   `deleted` TINYINT(1) NOT NULL DEFAULT 0,
   PRIMARY KEY (`id`),
   UNIQUE KEY `uk_readers_email` (`email`),
   UNIQUE KEY `uk_readers_phone` (`phone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
