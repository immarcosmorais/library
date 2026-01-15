package com.mm.library.domain.author;

import com.mm.library.common.BaseCRUDService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorService implements BaseCRUDService<Author, AuthorBody> {

    @Autowired
    AuthorRepository authorRepository;

    @Override
    @Transactional
    public Author save(AuthorBody authorBody) {
        Author authorToBeSaved = new Author(authorBody);
        return authorRepository.save(authorToBeSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Override
    @Transactional
    public Author update(Long id, AuthorBody authorBody) {
        Author author = this.findById(id);
        author.update(authorBody);
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Author authorToBeDeleted = authorRepository.getReferenceById(id);
        if (authorToBeDeleted.isDeleted()) {
            throw new EntityNotFoundException(String.format("Author with id %d not found", id));
        }
        authorToBeDeleted.setDeleted(true);
        authorRepository.save(authorToBeDeleted);
    }

    @Override
    @Transactional
    public void destroy(Long id) {
        this.authorRepository.delete(this.authorRepository.getReferenceById(id));
    }

    private Author findByIdOrThrowException(Long id) {
        return this.authorRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Author with id %d not found", id))
        );
    }


}
