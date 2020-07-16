package com.qa.domain;

import org.apache.tomcat.util.codec.binary.Base64;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Document(collection = "cv")
@Component
public class Cv {

	@Id
	private String id;
	@Field
	@NotBlank @Size(min = 5)
	private String name;
	@Field
	@NotNull @PastOrPresent
	private LocalDateTime lastModified;
	@Field @NotBlank
	private String fileName;
	@Field @NotNull
	private Binary cvFile;

	public Cv(){}

	public Cv(@NotBlank @Size(min = 5) String name, @NotBlank String fileName, @NotNull Binary cvFile) {
		this.name = name;
		this.fileName = fileName;
		this.cvFile = cvFile;
		this.lastModified = LocalDateTime.now(ZoneId.of("Europe/London"))
				.truncatedTo(ChronoUnit.SECONDS);
	}

	//Testing purposes only
	public Cv(String id, @NotBlank @Size(min = 5) String name, @NotBlank String fileName, @NotNull Binary cvFile) {
		this.id = id;
		this.name = name;
		this.fileName = fileName;
		this.cvFile = cvFile;
		this.lastModified = LocalDateTime.now(ZoneId.of("Europe/London"))
				.truncatedTo(ChronoUnit.SECONDS);
	}

	//Testing
	public Cv(String id, @NotBlank @Size(min = 5) String name, @NotNull @PastOrPresent LocalDateTime lastModified, @NotBlank String fileName, @NotNull Binary cvFile) {
		this.id = id;
		this.name = name;
		this.lastModified = lastModified;
		this.fileName = fileName;
		this.cvFile = cvFile;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getLastModified() {
		return lastModified;
	}

	public void setLastModified(LocalDateTime lastModified) {
		this.lastModified = lastModified;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Binary getCvFile() {
		return cvFile;
	}

	public void setCvFile(Binary cvFile) {
		this.cvFile = cvFile;
	}

	@Override
	public String toString() {

		String data = new String(
				Base64.encodeBase64(cvFile.getData()),
				StandardCharsets.UTF_8);
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		String formatDateTime = lastModified.format(format);

		return"{"+
				"\"fileName\":\""+fileName+"\""+
				",\"name\":\""+name+"\""+
				",\"id\":\""+id+"\""+
				",\"lastModified\":\""+formatDateTime+"\""+
				",\"cvFile\":"+
				"{\"data\":\""+data+"\""+
				",\"type\":"+cvFile.getType()+"}"+
				"}";
	}
}
