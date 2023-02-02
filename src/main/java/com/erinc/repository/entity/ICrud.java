package com.erinc.repository.entity;

import java.util.List;
import java.util.Optional;

public interface ICrud <T>{

    Optional<T> findByUsername(String T);
    List<T> findAll();

    Optional<T> findbyId(Long id);


}
