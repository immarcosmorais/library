-- File: 'src/main/resources/db/migration/V2__add_reservation_id_to_borrowings.sql'
START TRANSACTION;

ALTER TABLE `borrowings`
  ADD COLUMN `reservation_id` BIGINT NULL;

-- Explicitly set existing rows to NULL (redundant but explicit)
UPDATE `borrowings` SET `reservation_id` = NULL WHERE `reservation_id` IS NOT NULL;

-- Unique index to reflect @OneToOne(unique = true)
CREATE UNIQUE INDEX `ux_borrowings_reservation_id` ON `borrowings` (`reservation_id`);

-- Foreign key to reservations table; keeps referential integrity and sets to NULL on delete
ALTER TABLE `borrowings`
  ADD CONSTRAINT `fk_borrowings_reservation`
  FOREIGN KEY (`reservation_id`) REFERENCES `reservations` (`id`)
  ON DELETE SET NULL
  ON UPDATE CASCADE;

COMMIT;