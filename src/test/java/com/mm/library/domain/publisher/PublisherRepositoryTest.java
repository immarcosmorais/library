package com.mm.library.domain.publisher;


import com.mm.library.util.PublisherCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Tests for PublisherRepository")
@ActiveProfiles("test")
class PublisherRepositoryTest {

    @Autowired
    private PublisherRepository repository;

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Save author when success")
    void saveAuthorWhenSuccess() {
        Publisher author = PublisherCreator.createPublisherToBeSaved();
        author.setId(null);
        Publisher savedAuthor = this.repository.save(author);
        assertNotNull(savedAuthor);
        assertNotNull(savedAuthor.getId()); // ID should be generated
        assertEquals(savedAuthor.getName(), author.getName());
    }

    @Test
    @DisplayName("Update author when success")
    void updateAuthorWhenSuccess() {
        Publisher author = PublisherCreator.createPublisherToBeSaved();
        author.setId(null);
        Publisher savedAuthor = this.repository.save(author);
        savedAuthor.setName("Updated Name");
        Publisher updatedAuthor = this.repository.save(savedAuthor);
        assertNotNull(updatedAuthor);
        assertEquals(updatedAuthor.getId(), savedAuthor.getId());
        assertEquals("Updated Name", updatedAuthor.getName());
    }

    @Test
    @DisplayName("Find author by id when success")
    void findByIdAndDeletedFalseWhenSuccess() {
        Publisher author = PublisherCreator.createPublisherToBeSaved();
        author.setId(null);
        Publisher savedAuthor = this.repository.save(author);
        Publisher foundAuthor = this.repository.findByIdAndDeletedFalse(savedAuthor.getId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals(savedAuthor.getId(), foundAuthor.getId());
        assertEquals(savedAuthor.getName(), foundAuthor.getName());
    }

    @Test
    @DisplayName("Find author by id when not found")
    void findByIdAndDeletedFalseWhenNotFound() {
        Publisher foundAuthor = this.repository.findByIdAndDeletedFalse(999L).orElse(null);
        assertEquals(null, foundAuthor);
    }

    @Test
    @DisplayName("Find all authors when success")
    void findAllByDeletedFalseWhenSuccess() {
        Publisher author = PublisherCreator.createPublisherToBeSaved();
        author.setId(null);
        this.repository.save(author);
        var authorsPage = this.repository.findAllByDeletedFalse(PageRequest.of(0, 10));
        assertNotNull(authorsPage);
        assertEquals(1, authorsPage.getTotalElements());
    }

    @Test
    @DisplayName("Delete author when success")
    void deleteByIdWhenSuccess() {
        Publisher author = PublisherCreator.createPublisherToBeSaved();
        author.setId(null);
        Publisher savedAuthor = this.repository.save(author);
        this.repository.deleteById(savedAuthor.getId());
        var deletedAuthor = this.repository.findByIdAndDeletedFalse(savedAuthor.getId()).orElse(null);
        assertEquals(null, deletedAuthor);
    }


}