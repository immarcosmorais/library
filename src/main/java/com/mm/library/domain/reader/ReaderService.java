package com.mm.library.domain.reader;

import com.mm.library.common.BaseCRUDService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReaderService implements BaseCRUDService<Reader, ReaderBody> {

    @Autowired
    private ReaderRepository readerRepository;

    @Override
    @Transactional
    public Reader save(ReaderBody readerBody) {
        Reader readerToBeSaved = new Reader(readerBody);
        return this.readerRepository.save(readerToBeSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Reader> findAll(Pageable pageable) {
        return this.readerRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Reader findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Override
    @Transactional
    public Reader update(Long id, ReaderBody readerBody) {
        Reader readerToBeUpdated = this.findById(id);
        readerToBeUpdated.update(readerBody);
        return this.readerRepository.save(readerToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Reader readerToBeDeleted = this.readerRepository.getReferenceById(id);
        readerToBeDeleted.setDeleted(true);
        this.readerRepository.save(readerToBeDeleted);
    }

    @Override
    @Transactional
    public void destroy(Long id) {
        this.readerRepository.delete(this.readerRepository.getReferenceById(id));
    }

    private Reader findByIdOrThrowException(Long id) {
        return this.readerRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Reader with id %d not found", id))
        );
    }
}
