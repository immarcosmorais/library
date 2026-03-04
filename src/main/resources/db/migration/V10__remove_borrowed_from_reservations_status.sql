-- Ensures reservations.status ENUM only contains OPENED and CLOSED

-- Convert any existing 'BORROWED' rows to 'CLOSED' (if any)
UPDATE `reservations` SET `status` = 'CLOSED' WHERE `status` = 'BORROWED';

-- Modify the column to the desired ENUM set
ALTER TABLE `reservations`
    MODIFY COLUMN `status` ENUM('OPENED','CLOSED') NOT NULL DEFAULT 'OPENED';