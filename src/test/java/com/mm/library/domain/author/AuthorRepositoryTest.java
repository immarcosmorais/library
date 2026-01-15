package com.mm.library.domain.author;

import com.mm.library.util.AuthorCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("Tests for AuthorRepository")
@ActiveProfiles("test")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @AfterEach
    void tearDown() {
        authorRepository.deleteAll();
    }

    @Test
    @DisplayName("Save author when success")
    void saveAuthorWhenSuccess() {
        Author author = AuthorCreator.createAuthorToBeSaved();
        author.setId(null);
        Author savedAuthor = this.authorRepository.save(author);
        assertNotNull(savedAuthor);
        assertNotNull(savedAuthor.getId()); // ID should be generated
        assertEquals(savedAuthor.getName(), author.getName());
    }

    @Test
    @DisplayName("Update author when success")
    void updateAuthorWhenSuccess() {
        Author author = AuthorCreator.createAuthorToBeSaved();
        author.setId(null);
        Author savedAuthor = this.authorRepository.save(author);
        savedAuthor.setName("Updated Name");
        Author updatedAuthor = this.authorRepository.save(savedAuthor);
        assertNotNull(updatedAuthor);
        assertEquals(updatedAuthor.getId(), savedAuthor.getId());
        assertEquals("Updated Name", updatedAuthor.getName());
    }

    @Test
    @DisplayName("Find author by id when success")
    void findByIdAndDeletedFalseWhenSuccess() {
        Author author = AuthorCreator.createAuthorToBeSaved();
        author.setId(null);
        Author savedAuthor = this.authorRepository.save(author);
        Author foundAuthor = this.authorRepository.findByIdAndDeletedFalse(savedAuthor.getId()).orElse(null);
        assertNotNull(foundAuthor);
        assertEquals(savedAuthor.getId(), foundAuthor.getId());
        assertEquals(savedAuthor.getName(), foundAuthor.getName());
    }

    @Test
    @DisplayName("Find author by id when not found")
    void findByIdAndDeletedFalseWhenNotFound() {
        Author foundAuthor = this.authorRepository.findByIdAndDeletedFalse(999L).orElse(null);
        assertEquals(null, foundAuthor);
    }

    @Test
    @DisplayName("Find all authors when success")
    void findAllByDeletedFalseWhenSuccess() {
        Author author = AuthorCreator.createAuthorToBeSaved();
        author.setId(null);
        this.authorRepository.save(author);
        var authorsPage = this.authorRepository.findAllByDeletedFalse(PageRequest.of(0, 10));
        assertNotNull(authorsPage);
        assertEquals(1, authorsPage.getTotalElements());
    }

    @Test
    @DisplayName("Delete author when success")
    void deleteByIdWhenSuccess() {
        Author author = AuthorCreator.createAuthorToBeSaved();
        author.setId(null);
        Author savedAuthor = this.authorRepository.save(author);
        this.authorRepository.deleteById(savedAuthor.getId());
        var deletedAuthor = this.authorRepository.findByIdAndDeletedFalse(savedAuthor.getId()).orElse(null);
        assertEquals(null, deletedAuthor);
    }


}