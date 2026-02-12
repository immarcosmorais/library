package com.mm.library.domain.reader;

import com.mm.library.domain.reader.validations.ValidateAlreadyExistingReader;
import com.mm.library.domain.user.User;
import com.mm.library.domain.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReaderService  {

    @Autowired
    private ReaderRepository readerRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public Reader save(ReaderBody readerBody) {
        Reader readerToCheck = this.readerRepository.findByEmailOrPhone(
                readerBody.email(),
                readerBody.phone()
        ).orElse(null);
        new ValidateAlreadyExistingReader().validate(readerToCheck);
        this.userService.create(readerBody);
        Reader readerToBeSaved = new Reader(readerBody);
        return this.readerRepository.save(readerToBeSaved);
    }

    @Transactional(readOnly = true)
    public Page<Reader> findAll(Pageable pageable) {
        return this.readerRepository.findAllByDeletedFalse(pageable);
    }

    @Transactional(readOnly = true)
    public Reader findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Transactional
    public Reader update(Long id, ReaderBody readerBody) {
        Reader readerToBeUpdated = this.findByIdOrThrowException(id);
        readerToBeUpdated.update(readerBody);
        this.userService.update(readerBody);
        return this.readerRepository.save(readerToBeUpdated);
    }

    @Transactional
    public void delete(Long id) {
        Reader readerToBeDeleted = this.findByIdOrThrowException(id);
        readerToBeDeleted.setDeleted(true);
        this.readerRepository.save(readerToBeDeleted);
        User userToBeDeleted = this.userService.findByEmail(readerToBeDeleted.getEmail()).orElse(null);
        if (userToBeDeleted != null) {
            userToBeDeleted.setDeleted(true);
            this.userService.update(userToBeDeleted);
        }
    }

    @Transactional
    public void destroy(Long id) {
        this.readerRepository.delete(this.readerRepository.getReferenceById(id));
    }

    private Reader findByIdOrThrowException(Long id) {
        return this.readerRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Reader with id %d not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Reader findByEmail(String username) {
        return this.readerRepository.findByEmailAndDeletedFalse(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Reader with email %s not found", username))
        );
    }
}
