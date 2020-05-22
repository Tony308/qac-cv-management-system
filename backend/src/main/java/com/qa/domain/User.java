package com.qa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Document(collection = "user")
@Component
public class User {

    @Id
    private String id;
    @Field
    @NotBlank @Size(min = 5, message = "Username must be at least 5 characters long")
    @Indexed(unique = true)
    private String username;
    @Field
    @NotBlank @Size(min = 7, message = "Password mus tbe at lesat 7 characters long")
    private String password;

    public User() {
    }

    public User(@Valid @NotBlank @Size(min = 5, message = "Username must be at least 5 characters long") String username,
                @Valid @NotBlank @Size(min = 7, message = "Password mus tbe at lesat 7 characters long") String password) {
        this.username = username;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
