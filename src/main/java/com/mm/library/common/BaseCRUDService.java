package com.mm.library.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseCRUDService<Entity, BODY> {

    public Entity save(BODY body);

    public Page<Entity> findAll(Pageable pageable);

    public Entity findById(Long id);

    public Entity update(Long id, BODY body);

    public void delete(Long id);

    public void destroy(Long id);

}
