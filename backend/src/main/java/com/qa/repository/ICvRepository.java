package com.qa.repository;

import com.qa.domain.Cv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICvRepository extends MongoRepository<Cv, String> {

    Cv findByName(String name);
    List<Cv> findAllByName(String username);
}
