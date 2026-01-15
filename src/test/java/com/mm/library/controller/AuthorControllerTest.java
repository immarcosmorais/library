package com.mm.library.controller;

import com.mm.library.domain.author.Author;
import com.mm.library.domain.author.AuthorBody;
import com.mm.library.domain.author.AuthorDTO;
import com.mm.library.domain.author.AuthorService;
import com.mm.library.util.AuthorCreator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for AuthorController")
class AuthorControllerTest {

    @InjectMocks
    private AuthorController controller;

    @Mock
    private AuthorService service;

    @BeforeEach
    void setUp() {
        Author validAuthor = AuthorCreator.createAuthorToBeSaved();
        PageImpl<Author> authorPage = new PageImpl<>(List.of(validAuthor));
        BDDMockito.when(service.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(authorPage);
        BDDMockito.when(service.findById(ArgumentMatchers.anyLong())).thenReturn(validAuthor);
        BDDMockito.when(service.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(AuthorBody.class)))
                .thenReturn(validAuthor);
        BDDMockito.doNothing().when(service).delete(ArgumentMatchers.anyLong());
        BDDMockito.when(service.save(ArgumentMatchers.any(AuthorBody.class))).thenReturn(validAuthor);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(service);
    }

    @Test
    @DisplayName("Save author when success")
    void saveAuthorWhenSuccess() {
        AuthorBody authorBody = AuthorCreator.createAuthorBody();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString("http://localhost:8080");
        ResponseEntity<?> response = controller.save(authorBody, uriBuilder);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        AuthorDTO authorDTO = (AuthorDTO) response.getBody();
        Assertions.assertEquals(authorBody.name(), authorDTO.name());
    }

    @Test
    @DisplayName("Find all authors when success")
    void findAllAuthorsWhenSuccess() {
        String authorName = AuthorCreator.createAuthorToBeSaved().getName();
        Pageable pageable = PageRequest.of(0, 10);
        ResponseEntity<?> response = controller.findAll(pageable);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Page<AuthorDTO> authorDTOPage = (Page<AuthorDTO>) response.getBody();
        Assertions.assertFalse(authorDTOPage.isEmpty());
        Assertions.assertEquals(1, authorDTOPage.getTotalElements());
        Assertions.assertEquals(authorName, authorDTOPage.getContent().get(0).name());
    }

    @Test
    @DisplayName("Find author by id when success")
    void findAuthorByIdWhenSuccess() {
        Long id = 1L;
        ResponseEntity<?> response = controller.findById(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        AuthorDTO authorDTO = (AuthorDTO) response.getBody();
    }

    @Test
    @DisplayName("Find author by id when not found")
    void findAuthorByIdWhenNotFound() {
        Long id = 1L;
        BDDMockito.when(service.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new RuntimeException("Author with id " + id + " not found"));
        Exception exception = Assertions.assertThrows(RuntimeException.class, () -> {
            controller.findById(id);
        });
        String expectedMessage = "Author with id " + id + " not found";
        String actualMessage = exception.getMessage();
        Assertions.assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Update author when success")
    void updateAuthorWhenSuccess() {
        Long id = 1L;
        AuthorBody authorBody = AuthorCreator.createAuthorBody();
        ResponseEntity<?> response = controller.update(id, authorBody);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete author when success")
    void deleteAuthorWhenSuccess() {
        Long id = 1L;
        ResponseEntity<?> response = controller.delete(id);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}