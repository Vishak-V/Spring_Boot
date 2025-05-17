package com.vishak.pgdb.services;


import com.vishak.pgdb.domain.Entities.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<AuthorEntity> findAll();


    AuthorEntity save(AuthorEntity author);

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void delete(Long id);
}


