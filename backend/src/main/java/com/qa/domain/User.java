package com.qa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Document(collection = "user")
@Component
public class User {

    @Id
    private String id;
    @Field
    @Indexed(unique = true)
    private String username;
    @Field
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        this.password = encoder.encode(password);
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
//        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        this.password = encoder.encode(password);
        this.password = password;
    }
}
