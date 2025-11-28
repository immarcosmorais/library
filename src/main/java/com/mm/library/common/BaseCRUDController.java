package com.mm.library.common;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

public interface BaseCRUDController<DTO, BODY> {

    @PostMapping
    ResponseEntity save(@RequestBody @Valid BODY body, UriComponentsBuilder uriBuilder);

    @GetMapping
    ResponseEntity<Page<DTO>> findAll(Pageable pageable);

    @GetMapping("/{id}")
    ResponseEntity findById(@PathVariable Long id);

    @PutMapping("/{id}")
    ResponseEntity update(@PathVariable Long id, @RequestBody @Valid BODY body);

    @DeleteMapping("/{id}")
    ResponseEntity delete(@PathVariable Long id);

}
