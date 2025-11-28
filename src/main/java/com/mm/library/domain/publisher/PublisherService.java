package com.mm.library.domain.publisher;

import com.mm.library.common.BaseCRUDService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PublisherService implements BaseCRUDService<Publisher, PublisherBody> {

    @Autowired
    PublisherRepository publisherRepository;

    @Override
    @Transactional
    public Publisher save(PublisherBody publisherBody) {
        Publisher publisherToBeSaved = new Publisher(publisherBody);
        return publisherRepository.save(publisherToBeSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Publisher> findAll(Pageable pageable) {
        return publisherRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Publisher findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Override
    @Transactional
    public Publisher update(Long id, PublisherBody publisherBody) {
        Publisher publisherToBeUpdated = this.findById(id);
        publisherToBeUpdated.update(publisherBody);
        return publisherRepository.save(publisherToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Publisher publisherToBeDeleted = publisherRepository.getReferenceById(id);
        publisherToBeDeleted.setDeleted(true);
        this.publisherRepository.save(publisherToBeDeleted);
    }


    @Override
    @Transactional
    public void destroy(Long id) {
        this.publisherRepository.delete(this.publisherRepository.getReferenceById(id));
    }

    private Publisher findByIdOrThrowException(Long id) {
        return this.publisherRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Publisher with id %d not found", id))
        );
    }
}
