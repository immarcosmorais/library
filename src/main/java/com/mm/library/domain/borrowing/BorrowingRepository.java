package com.mm.library.domain.borrowing;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    Page<Borrowing> findAllByDeletedFalse(Pageable pageable);

    Optional<Borrowing> findByIdAndDeletedFalse(Long Id);

    Optional<Borrowing> findByBookIdAndDeletedFalse(Long bookId);

}
