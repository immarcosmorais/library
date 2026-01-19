package com.mm.library.domain.publisher;


import com.mm.library.util.PublisherCreator;
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
@DisplayName("Tests for PublisherService")
class PublisherServiceTest {

    @InjectMocks
    private PublisherService service;

    @Mock
    private PublisherRepository repository;

    @BeforeEach
    void setUp() {
        Publisher entity = PublisherCreator.createPublisherToBeSaved();
        PageImpl<Publisher> entityPage = new PageImpl<>(java.util.List.of(entity));
        BDDMockito.when(repository.findAllByDeletedFalse(ArgumentMatchers.any(Pageable.class))).thenReturn(entityPage);
        BDDMockito.when(repository.save(ArgumentMatchers.any(Publisher.class))).thenReturn(entity);
        BDDMockito.when(repository.findByIdAndDeletedFalse(ArgumentMatchers.anyLong())).thenReturn(Optional.of(entity));
        BDDMockito.when(repository.getReferenceById(ArgumentMatchers.anyLong())).thenReturn(entity);
        BDDMockito.doNothing().when(repository).delete(ArgumentMatchers.any(Publisher.class));
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(repository);
    }

    @Test
    @DisplayName("Save publisher when success")
    void savePublisherWhenSuccess() {
        PublisherBody body = PublisherCreator.createPublisherBody();
        Publisher saved = service.save(body);
        assertNotNull(saved);
        assertEquals(body.name(), saved.getName());
    }

    @Test
    @DisplayName("Find all publishers when success")
    void findAllPublisherWhenSuccess() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Publisher> page = service.findAll(pageable);
        assertNotNull(page);
        assertFalse(page.isEmpty());
        assertEquals(1, page.getTotalElements());
    }

    @Test
    @DisplayName("Find publisher by id when success")
    void findPublisherByIdWhenSuccess() {
        Publisher publisher = service.findById(1L);
        assertNotNull(publisher);
    }

    @Test
    @DisplayName("Find publisher by id when not found")
    void findPublisherByIdWhenNotFound() {
        BDDMockito.when(repository.findByIdAndDeletedFalse(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());
        Exception exception = assertThrows(Exception.class, () -> {
            service.findById(1L);
        });
        String expectedMessage = "Publisher with id 1 not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("Update publisher when success")
    void updatePublisherWhenSuccess() {
        PublisherBody body = PublisherCreator.createPublisherBody();
        Publisher publisher = service.update(1L, body);
        assertNotNull(publisher);
        assertEquals(body.name(), publisher.getName());
    }

    @Test
    @DisplayName("Delete publisher when success")
    void deletePublisherWhenSuccess() {
        assertDoesNotThrow(() -> service.delete(1L));
    }

    @Test
    @DisplayName("Destroy publisher when success")
    void destroyPublisherWhenSuccess() {
        assertDoesNotThrow(() -> service.destroy(1L));
    }
}