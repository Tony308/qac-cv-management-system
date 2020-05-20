package com.qa.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;


@Document(collection = "user")
@Component
public class User {

    @Id
    private String id;
    @Field
    @Indexed(unique = true)
    @NotNull
    private String username;
    @Field
    @NotNull
    private String password;

    public User() {
    }

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

    public void setId(String id) {
        this.id = id;
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
