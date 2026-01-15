package com.mm.library.domain.reader;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReaderRepository extends JpaRepository<Reader, Long> {

    Page<Reader> findAllByDeletedFalse(Pageable pageable);

    Optional<Reader> findByIdAndDeletedFalse(Long Id);

}
