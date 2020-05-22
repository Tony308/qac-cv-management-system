package com.qa.repository;

import com.qa.domain.Cv;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;


@Repository
@Validated
public interface ICvRepository extends MongoRepository<Cv, String> {

    Cv findByName(@NotBlank @Size(min = 5) String name);
    List<Cv> findAllByName(@NotBlank @Size(min = 5)String username);
}
