package com.mm.library.controller;

import com.mm.library.common.BaseCRUDController;
import com.mm.library.domain.reader.ReaderBody;
import com.mm.library.domain.reader.ReaderDTO;
import com.mm.library.domain.reader.ReaderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("readers")
@SecurityRequirement(name = "bearer-key")
public class ReaderController implements BaseCRUDController<ReaderDTO, ReaderBody> {

    @Autowired
    ReaderService readerService;

    @Override
    public ResponseEntity save(ReaderBody readerBody, UriComponentsBuilder uriBuilder) {
        ReaderDTO readerDTO = new ReaderDTO(this.readerService.save(readerBody));
        var uri = uriBuilder.path("/readers/{id}").buildAndExpand(readerDTO.id()).toUri();
        return ResponseEntity.created(uri).body(readerDTO);
    }

    public ResponseEntity<Page<ReaderDTO>> findAll(@PageableDefault(size = 999, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(this.readerService.findAll(pageable).map(ReaderDTO::new));
    }

    public ResponseEntity findById(Long id) {
        return ResponseEntity.ok(new ReaderDTO(this.readerService.findById(id)));
    }

    public ResponseEntity update(Long id, ReaderBody body) {
        return ResponseEntity.ok(new ReaderDTO(this.readerService.update(id, body)));
    }

    public ResponseEntity delete(Long id) {
        this.readerService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
