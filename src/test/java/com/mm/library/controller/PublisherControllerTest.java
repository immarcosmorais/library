package com.mm.library.controller;

import com.mm.library.domain.author.AuthorDTO;
import com.mm.library.domain.publisher.Publisher;
import com.mm.library.domain.publisher.PublisherBody;
import com.mm.library.domain.publisher.PublisherDTO;
import com.mm.library.domain.publisher.PublisherService;
import com.mm.library.util.PublisherCreator;
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
@DisplayName("Tests for PublisherController")
class PublisherControllerTest {

    @InjectMocks
    private PublisherController controller;

    @Mock
    private PublisherService service;

    @BeforeEach
    void setUp() {
        Publisher validPublisher = PublisherCreator.createPublisherToBeSaved();
        PageImpl<Publisher> publisherPage = new PageImpl<>(List.of(validPublisher));
        BDDMockito.when(service.findAll(ArgumentMatchers.any(Pageable.class))).thenReturn(publisherPage);
        BDDMockito.when(service.findById(ArgumentMatchers.anyLong())).thenReturn(validPublisher);
        BDDMockito.when(service.update(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PublisherBody.class)))
                .thenReturn(validPublisher);
        BDDMockito.doNothing().when(service).delete(ArgumentMatchers.anyLong());
        BDDMockito.when(service.save(ArgumentMatchers.any(PublisherBody.class))).thenReturn(validPublisher);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(service);
    }

    @Test
    @DisplayName("Save publisher when success")
    void savePublisherWhenSuccess() {
        PublisherBody body = PublisherCreator.createPublisherBody();
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString("http://localhost:8080");
        ResponseEntity<?> response = controller.save(body, uriBuilder);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        PublisherDTO dto = (PublisherDTO) response.getBody();
        Assertions.assertEquals(body.name(), dto.name());
        Assertions.assertEquals(body.country(), dto.country());
    }

    @Test
    @DisplayName("Find all publishers when success")
    void findAllPublisherWhenSuccess() {
        String publisherName = PublisherCreator.createPublisherToBeSaved().getName();
        Pageable pageable = PageRequest.of(0, 10);
        ResponseEntity<?> response = controller.findAll(pageable);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
        Page<AuthorDTO> dtosPages = (Page<AuthorDTO>) response.getBody();
        Assertions.assertFalse(dtosPages.isEmpty());
        Assertions.assertEquals(1, dtosPages.getTotalElements());
        Assertions.assertEquals(publisherName, dtosPages.getContent().get(0).name());
    }

    @Test
    @DisplayName("Find publisher by id when success")
    void findPublisherByIdWhenSuccess() {
        Long id = 1L;
        ResponseEntity<?> response = controller.findById(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Find publisher by id when not found")
    void findPublisherByIdWhenNotFound() {
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
    @DisplayName("Update publisher when success")
    void updatePublisherWhenSuccess() {
        Long id = 1L;
        PublisherBody body = PublisherCreator.createPublisherBody();
        ResponseEntity<?> response = controller.update(id, body);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Delete publisher when success")
    void deletePublisherWhenSuccess() {
        Long id = 1L;
        ResponseEntity<?> response = controller.delete(id);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}