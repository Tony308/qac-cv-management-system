package com.qa.repository;

import com.qa.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Optional;

@Repository
@Validated
public interface UserRepository extends MongoRepository<User, String> {

    User findUserByUsernameAndPassword(@NotBlank String username, @NotBlank String password);

    Optional<User> findByUsernameAndPassword(@NotBlank String username, @NotBlank String password);

    Optional<User> findByUsername(@NotBlank @Size(min = 5) String username);
}
