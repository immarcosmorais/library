package com.mm.library.controller;

import com.mm.library.common.BaseCRUDController;
import com.mm.library.domain.publisher.PublisherBody;
import com.mm.library.domain.publisher.PublisherDTO;
import com.mm.library.domain.publisher.PublisherService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("publishers")
@SecurityRequirement(name = "bearer-key")
public class PublisherController implements BaseCRUDController<PublisherDTO, PublisherBody> {

    @Autowired
    PublisherService publisherService;

    @Override
    public ResponseEntity save(PublisherBody publisherBody, UriComponentsBuilder uriBuilder) {
        PublisherDTO publisherDTO = new PublisherDTO(this.publisherService.save(publisherBody));
        URI uri = uriBuilder.path("/publishers/{id}").buildAndExpand(publisherDTO.id()).toUri();
        return ResponseEntity.created(uri).body(publisherDTO);
    }

    @Override
    public ResponseEntity<Page<PublisherDTO>> findAll(@PageableDefault(size = 999, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(this.publisherService.findAll(pageable).map(PublisherDTO::new));
    }

    @Override
    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new PublisherDTO(this.publisherService.findById(id)));
    }

    @Override
    public ResponseEntity update(Long id, PublisherBody body) {
        return ResponseEntity.ok(new PublisherDTO(this.publisherService.update(id, body)));
    }

    @Override
    public ResponseEntity delete(Long id) {
        this.publisherService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
