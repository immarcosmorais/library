-- src/main/resources/db/migration/V3__create_reservations_table.sql
-- MySQL migration: reservations table with status as ENUM matching ReservationStatus

CREATE TABLE `reservations` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `book_id` BIGINT,
    `reader_id` BIGINT,
    `deadline` DATETIME,
    `status` ENUM('OPENED','CLOSED') NOT NULL DEFAULT 'OPENED',
    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` TINYINT(1) NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`),
    KEY `fk_reservations_book` (`book_id`),
    KEY `fk_reservations_reader` (`reader_id`),
    CONSTRAINT `fk_reservations_book` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT `fk_reservations_reader` FOREIGN KEY (`reader_id`) REFERENCES `readers` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
