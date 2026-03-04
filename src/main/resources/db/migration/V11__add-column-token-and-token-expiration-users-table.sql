ALTER TABLE `users`
    ADD COLUMN `token` VARCHAR(255) NULL,
    ADD COLUMN `token_expiration` TIMESTAMP NULL;