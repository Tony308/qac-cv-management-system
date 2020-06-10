package com.qa.repository;

import com.qa.domain.Cv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Repository
@Validated
public interface ICvRepository extends MongoRepository<Cv, String> {

    Cv findByName(@NotBlank String name);
    List<Cv> findAllByName(@NotBlank String username);
}
