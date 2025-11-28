-- sql
-- Drop borrowing_date column from borrowings table (compatible with older MySQL)
ALTER TABLE `borrowings`
  DROP COLUMN `borrowing_date`;
