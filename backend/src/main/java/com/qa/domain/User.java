package com.qa.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Document
@Component
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    @Field
    private String username;

    @Field
    private String password;

    public User() {
    }

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getId() {
        return id;
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
