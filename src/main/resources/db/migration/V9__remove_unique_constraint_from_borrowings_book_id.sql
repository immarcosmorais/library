-- Drop the foreign key that depends on the index
ALTER TABLE `borrowings` DROP FOREIGN KEY `fk_borrowings_book`;

-- Drop the unique index
ALTER TABLE `borrowings` DROP INDEX `uk_borrowings_book`;

-- Create a non‑unique index to support the foreign key
ALTER TABLE `borrowings` ADD INDEX `idx_borrowings_book` (`book_id`);

-- Re‑add the foreign key constraint (it will use the new index automatically)
ALTER TABLE `borrowings` ADD CONSTRAINT `fk_borrowings_book`
    FOREIGN KEY (`book_id`) REFERENCES `books` (`id`)
        ON DELETE SET NULL ON UPDATE CASCADE;