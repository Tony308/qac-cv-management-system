package com.qa.controller;


import com.qa.domain.Cv;
import com.qa.service.CvService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cvsystem")
@CrossOrigin(origins = "*")
public class CvController {
	
	@Autowired
	private CvService service;
	
	public CvController(CvService service) {
		this.service = service;
	}

	@GetMapping("/get")
    public ResponseEntity getAllCv(@RequestParam("name") String name) {
	    return service.getUserCVs(name);
    }

	@PostMapping("/upload-cv")
	public ResponseEntity uploadCv(@RequestParam("file") MultipartFile file,
                                           @RequestParam("user") String name,
										   @RequestParam("fileName") String fileName){
		return service.uploadCv(file, name,fileName);
	}

    @GetMapping("/retrieve/{id}")
    public ResponseEntity retrieveFile(@PathVariable String id) {
	    return service.getCV(id);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCv(@PathVariable String id){
	    return service.deleteCv(id);
    }

    @PutMapping("/update-cv/{id}")
    public ResponseEntity updateCv(@PathVariable("id") String id,
										   @RequestParam("file") MultipartFile file,
										   @RequestParam("fileName") String fileName) {
        return service.updateCv(id, file, fileName);
    } 
    
}
