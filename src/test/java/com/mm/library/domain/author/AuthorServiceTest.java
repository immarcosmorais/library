package com.mm.library.domain.author;

import com.mm.library.util.AuthorCreator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthorService")
class AuthorServiceTest {

    @InjectMocks
    private AuthorService authorService;
    @Mock
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        Author author = AuthorCreator.createAuthorToBeSaved();
        PageImpl<Author> authorPage = new PageImpl<>(java.util.List.of(author));
        BDDMockito.when(authorRepository.findAllByDeletedFalse(ArgumentMatchers.any(Pageable.class))).thenReturn(authorPage);
        BDDMockito.when(authorRepository.save(ArgumentMatchers.any(Author.class))).thenReturn(author);
        BDDMockito.when(authorRepository.findByIdAndDeletedFalse(ArgumentMatchers.anyLong())).thenReturn(Optional.of(author));
        BDDMockito.when(authorRepository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(author);
        BDDMockito.doNothing().when(authorRepository).delete(ArgumentMatchers.any(Author.class));
    }


    @AfterEach
    void tearDown() {
        Mockito.reset(authorRepository);
    }

    @Test
    @DisplayName("Save author when success")
    void saveAuthorWhenSuccess() {
        AuthorBody authorBody = AuthorCreator.createAuthorBody();
        Author savedAuthor = authorService.save(authorBody);
        assertNotNull(savedAuthor);
        assertEquals(authorBody.name(), savedAuthor.getName());
    }

    @Test
    @DisplayName("Find all authors when success")
    void findAllAuthorWhenSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Author> page = authorService.findAll(pageable);
        assertNotNull(page);
        assertFalse(page.isEmpty());
        assertEquals(1, page.getTotalElements());
    }

    @Test
    @DisplayName("Find author by id when success")
    void findAuthorByIdWhenSuccess() {
        Author author = authorService.findById(1L);
        assertNotNull(author);
    }

    @Test
    @DisplayName("Find author by id when not found")
    void findAuthorByIdWhenNotFound() {
        BDDMockito.when(authorRepository.findByIdAndDeletedFalse(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            authorService.findById(1L);
        });
        String expectedMessage = "Author with id 1 not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Update author when success")
    void updateAuthorWhenSuccess() {
        AuthorBody authorBody = AuthorCreator.createAuthorBody();
        Author savedAuthor = authorService.update(1L, authorBody);
        assertNotNull(savedAuthor);
        assertEquals(authorBody.name(), savedAuthor.getName());
    }

    @Test
    @DisplayName("Delete author when success")
    void deleteAuthorWhenSuccess() {
        assertDoesNotThrow(() -> authorService.delete(1L));
    }

    @Test
    @DisplayName("Destroy author when success")
    void destroyAuthorWhenSuccess() {
        assertDoesNotThrow(() -> authorService.destroy(1L));
    }
}