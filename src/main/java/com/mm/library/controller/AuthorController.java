package com.mm.library.controller;

import com.mm.library.common.BaseCRUDController;
import com.mm.library.domain.author.AuthorBody;
import com.mm.library.domain.author.AuthorDTO;
import com.mm.library.domain.author.AuthorService;
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
@RequestMapping("authors")
@SecurityRequirement(name = "bearer-key")
public class AuthorController implements BaseCRUDController<AuthorDTO, AuthorBody> {

    @Autowired
    AuthorService authorService;

    @Override
    public ResponseEntity save(AuthorBody authorBody, UriComponentsBuilder uriBuilder) {
        AuthorDTO authorDTO = new AuthorDTO(this.authorService.save(authorBody));
        URI uri = uriBuilder.path("/authors/{id}").buildAndExpand(authorDTO.id()).toUri();
        return ResponseEntity.created(uri).body(authorDTO);
    }

    @Override
    public ResponseEntity<Page<AuthorDTO>> findAll(@PageableDefault(size = 999, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(this.authorService.findAll(pageable).map(AuthorDTO::new));
    }

    @Override
    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new AuthorDTO(this.authorService.findById(id)));
    }

    @Override
    public ResponseEntity update(Long id, AuthorBody authorBody) {
        return ResponseEntity.ok(new AuthorDTO(this.authorService.update(id, authorBody)));
    }

    @Override
    public ResponseEntity delete(Long id) {
        this.authorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
