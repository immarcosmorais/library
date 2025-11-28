CREATE TABLE IF NOT EXISTS reservations (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME,
    updated_at DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    book_id BIGINT,
    reader_id BIGINT,
    deadline DATETIME,
    CONSTRAINT fk_reservations_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE SET NULL,
    CONSTRAINT fk_reservations_reader FOREIGN KEY (reader_id) REFERENCES readers(id) ON DELETE SET NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

