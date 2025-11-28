CREATE TABLE IF NOT EXISTS borrowings (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME,
    updated_at DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    book_id BIGINT,
    reader_id BIGINT,
    borrowing_date DATETIME,
    expected_return_date DATETIME,
    return_date DATETIME,
    CONSTRAINT fk_borrowings_book FOREIGN KEY (book_id) REFERENCES books(id) ON DELETE SET NULL,
    CONSTRAINT fk_borrowings_reader FOREIGN KEY (reader_id) REFERENCES readers(id) ON DELETE SET NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

