package com.mm.library.domain.publisher;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Page<Publisher> findAllByDeletedFalse(Pageable pageable);

    Optional<Publisher> findByIdAndDeletedFalse(Long Id);

}
