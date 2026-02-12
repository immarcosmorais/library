-- src/main/resources/db/migration/V4__create_borrowings_table.sql
-- MySQL migration: borrowings table with status as ENUM matching BorrowingStatus

CREATE TABLE `borrowings` (
      `id` BIGINT NOT NULL AUTO_INCREMENT,
      `book_id` BIGINT,
      `reader_id` BIGINT,
      `expected_return_date` DATETIME,
      `return_date` DATETIME,
      `status` ENUM('OPENED','CLOSED') NOT NULL DEFAULT 'OPENED',
      `reservation_id` BIGINT,
      `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
      `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
      `deleted` TINYINT(1) NOT NULL DEFAULT 0,
      PRIMARY KEY (`id`),
      UNIQUE KEY `uk_borrowings_book` (`book_id`),
      UNIQUE KEY `uk_borrowings_reservation` (`reservation_id`),
      KEY `fk_borrowings_reader` (`reader_id`),
      CONSTRAINT `fk_borrowings_book` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
      CONSTRAINT `fk_borrowings_reader` FOREIGN KEY (`reader_id`) REFERENCES `readers` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
      CONSTRAINT `fk_borrowings_reservation` FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
