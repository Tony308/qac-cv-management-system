package com.qa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.qa.domain.Cv;
import java.util.List;


@Repository
public interface ICvRepository extends MongoRepository<Cv, String> {

    Cv findByName(String name);
    List<Cv> findAllByName(String username);
}
