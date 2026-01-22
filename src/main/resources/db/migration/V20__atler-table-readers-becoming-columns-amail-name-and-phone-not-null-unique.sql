-- Migration: Make name, email, and phone NOT NULL and UNIQUE

-- Make columns NOT NULL (name is already NOT NULL in original schema)
ALTER TABLE readers
    MODIFY COLUMN email VARCHAR(255) NOT NULL,
    MODIFY COLUMN phone VARCHAR(255) NOT NULL;

-- Add UNIQUE constraints
ALTER TABLE readers
    ADD UNIQUE INDEX uk_readers_email (email),
    ADD UNIQUE INDEX uk_readers_phone (phone),
    ADD UNIQUE INDEX uk_readers_name (name);