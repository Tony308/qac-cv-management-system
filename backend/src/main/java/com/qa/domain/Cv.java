package com.qa.domain;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import java.util.Date;

@Document(collection = "cv")
@Component
public class Cv {

	@Id
	private String id;
	@Field
	private String name;
	@Field
	private Date lastModified;
	@Field
	private String fileName;
	@Field
	private Binary cvFile;

	public Cv(String name, Binary cvFile, String fileName) {
		this.name = name;
		this.cvFile = cvFile;
		this.fileName = fileName;
		this.lastModified = new Date();
	}

	public Cv() {}

	public Cv(String id, String name, Binary cvFile, String fileName) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.cvFile = cvFile;
		this.lastModified = new Date();
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

    public Binary getCvFile() {
        return cvFile;
    }

    public void setCvFile(Binary cvFile) {
        this.cvFile = cvFile;
    }

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setId(String id) {
		this.id = id;
	}

}
