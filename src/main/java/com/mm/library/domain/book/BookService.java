package com.mm.library.domain.book;

import com.mm.library.common.BaseCRUDService;
import com.mm.library.domain.author.Author;
import com.mm.library.domain.author.AuthorService;
import com.mm.library.domain.publisher.Publisher;
import com.mm.library.domain.publisher.PublisherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BookService implements BaseCRUDService<Book, BookBody> {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private PublisherService publisherService;

    @Autowired
    private AuthorService authorService;

    @Override
    @Transactional
    public Book save(BookBody bookBody) {
        Publisher publisher = this.publisherService.findById(bookBody.publisherId());
        Author author = this.authorService.findById(bookBody.authorId());
        Book bookToBeSaved = new Book(bookBody, publisher, author);
        return this.bookRepository.save(bookToBeSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAll(Pageable pageable) {
        return this.bookRepository.findAllByDeletedFalse(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return this.findByIdOrThrowException(id);
    }

    @Override
    @Transactional
    public Book update(Long id, BookBody bookBody) {
        Book bookToBeUpdated = this.findById(id);
        Publisher publisher = this.publisherService.findById(bookBody.publisherId());
        Author author = this.authorService.findById(bookBody.authorId());
        bookToBeUpdated.update(bookBody, publisher, author);
        return this.bookRepository.save(bookToBeUpdated);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book bookToBeDeleted = this.bookRepository.getReferenceById(id);
        bookToBeDeleted.setDeleted(true);
        this.bookRepository.save(bookToBeDeleted);
    }

    @Override
    @Transactional
    public void destroy(Long id) {
        this.bookRepository.delete(this.bookRepository.getReferenceById(id));
    }

    private Book findByIdOrThrowException(Long id) {
        return this.bookRepository.findByIdAndDeletedFalse(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Book with id %d not found", id))
        );
    }

    public void updateBookStatus(Long id, BookStatus bookStatus) {
        Book bookToBeReserve = this.bookRepository.getReferenceById(id);
        bookToBeReserve.setStatus(bookStatus);
        this.bookRepository.save(bookToBeReserve);
    }

}
