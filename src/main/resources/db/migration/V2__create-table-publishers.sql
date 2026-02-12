-- src/main/resources/db/migration/V3__create_publishers_table.sql
CREATE TABLE `publishers` (
      `id` BIGINT NOT NULL AUTO_INCREMENT,
      `name` VARCHAR(255) NOT NULL,
      `country` VARCHAR(255) NULL,
      `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      `deleted` TINYINT(1) NOT NULL DEFAULT 0,
      PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
