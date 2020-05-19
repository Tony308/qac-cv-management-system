package com.qa.domain;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bson.types.Binary;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@Document(collection = "cv")
@Component
@Configuration
public class Cv {

	@Id
	private String id;
	@Field
	@NotNull
	private String name;
	@Field
	@NotNull
	private LocalDateTime lastModified;
	@Field
	@NotNull
	private String fileName;
	@Field
	@NotNull
	private Binary cvFile;

	public Cv() {}

	public Cv(String name, Binary cvFile, String fileName) {
		this.name = name;
		this.cvFile = cvFile;
		this.fileName = fileName;
		this.lastModified = LocalDateTime.now(ZoneId.of("Europe/London"))
				.truncatedTo(ChronoUnit.SECONDS);
	}

	//Testing purposes only
	public Cv(String id, String name, Binary cvFile, String fileName) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.cvFile = cvFile;
		this.lastModified = LocalDateTime.now(ZoneId.of("Europe/London"))
				.truncatedTo(ChronoUnit.SECONDS);

	}

	//Testing
	public Cv(String id, @NotNull String name, @NotNull LocalDateTime lastModified, @NotNull String fileName, @NotNull Binary cvFile) {
		this.id = id;
		this.name = name;
		this.lastModified = lastModified;
		this.fileName = fileName;
		this.cvFile = cvFile;
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

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	@Override
	public String toString() {

		String data = new String(
				Base64.encodeBase64(cvFile.getData()),
				StandardCharsets.UTF_8);

		return"{"+
				"\"fileName\":\""+fileName+"\""+
				",\"name\":\""+name+"\""+
				",\"id\":\""+id+"\""+
				",\"lastModified\":\""+this.lastModified+"\""+
				",\"cvFile\":"+
				"{\"data\":\""+data+"\""+
				",\"type\":"+cvFile.getType()+"}"+
				"}";
	}
}
