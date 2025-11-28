-- sql

ALTER TABLE books
  ADD COLUMN author_id BIGINT;

-- Ensure existing rows have NULL (explicit)
UPDATE books
  SET author_id = NULL
  WHERE author_id IS NOT NULL;

-- Add index (MySQL does not support CREATE INDEX IF NOT EXISTS)
ALTER TABLE books
  ADD INDEX idx_books_author_id (author_id);

-- Add foreign key constraint; keep it nullable and set to NULL when the author is deleted
ALTER TABLE books
  ADD CONSTRAINT fk_books_author
  FOREIGN KEY (author_id)
  REFERENCES authors(id)
  ON DELETE SET NULL;
