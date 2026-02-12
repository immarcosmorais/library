-- src/main/resources/db/migration/V2__create_books_table.sql
-- MySQL migration: books table with status as ENUM matching BookStatus

CREATE TABLE `books` (
     `id` BIGINT NOT NULL AUTO_INCREMENT,
     `title` VARCHAR(255),
     `isbn` VARCHAR(255) NOT NULL,
     `publication_date` DATETIME,
     `publisher_id` BIGINT,
     `author_id` BIGINT,
     `status` ENUM('AVAILABLE','RESERVED','BORROWED','LOST','DELAYED') NOT NULL DEFAULT 'AVAILABLE',
     `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
     `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
     `deleted` TINYINT(1) NOT NULL DEFAULT 0,
     PRIMARY KEY (`id`),
     UNIQUE KEY `uk_books_isbn` (`isbn`),
     KEY `fk_books_publisher` (`publisher_id`),
     KEY `fk_books_author` (`author_id`),
     CONSTRAINT `fk_books_publisher` FOREIGN KEY (`publisher_id`) REFERENCES `publishers` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
     CONSTRAINT `fk_books_author` FOREIGN KEY (`author_id`) REFERENCES `authors` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
