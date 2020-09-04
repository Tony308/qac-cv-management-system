package com.qa.controller;

import com.qa.service.CvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/cvsystem")
@CrossOrigin(value = {"http://localhost:3000", "https://localhost:3000", "https://cv-management-sys-frontend.herokuapp.com"},
		allowCredentials = "true")
public class CvController {

	@Autowired
	final private CvService service;

	public CvController(CvService service) {
		this.service = service;
	}

	@GetMapping("/get")
	public ResponseEntity getAllCv(
			@CookieValue(value = "authToken", defaultValue = "isEmpty") String token,
			@RequestParam("name") String name) {
		return service.getUserCVs(name, token);
	}

	@PostMapping("/upload-cv")
	public ResponseEntity uploadCv(
			@CookieValue(value = "authToken", defaultValue = "isEmpty") String token,
			@RequestParam("file") MultipartFile file,
			@RequestParam("user") String name,
			@RequestParam("fileName") String fileName){

		return service.uploadCv(file, name, fileName, token);
	}

	@GetMapping("/retrieve/{id}")
	public ResponseEntity retrieveFile(
			@CookieValue(value = "authToken", defaultValue = "isEmpty") String token,
			@PathVariable String id) {
		return service.getCV(id, token);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity deleteCv(
			@CookieValue(value = "authToken", defaultValue = "isEmpty") String token,
			@PathVariable String id){
		return service.deleteCv(id, token);
	}

	@PutMapping("/update-cv/{id}")
	public ResponseEntity updateCv(
			@CookieValue(value = "authToken", defaultValue = "isEmpty") String token,
			@PathVariable("id") String id,
			@RequestParam("file") MultipartFile file,
			@RequestParam("fileName") String fileName) {
		return service.updateCv(id, file, fileName, token);
	}

}

