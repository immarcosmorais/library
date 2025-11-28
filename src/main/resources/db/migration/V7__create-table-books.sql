CREATE TABLE IF NOT EXISTS books (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    created_at DATETIME,
    updated_at DATETIME,
    deleted BOOLEAN DEFAULT FALSE,
    title VARCHAR(255) NOT NULL,
    isbn VARCHAR(255),
    publication_date DATETIME,
    publisher_id BIGINT,
    CONSTRAINT fk_books_publisher FOREIGN KEY (publisher_id) REFERENCES publishers(id) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;