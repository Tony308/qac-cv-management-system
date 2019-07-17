package com.qa.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.qa.domain.Cv;

import java.util.List;

public interface ICvRepository extends MongoRepository<Cv, String> {

    Cv findByName(String name);

}
