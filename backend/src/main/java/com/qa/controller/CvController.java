package com.qa.controller;


import com.qa.domain.Cv;
import com.qa.service.CvService;

//import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Cv> getAllCv(@RequestParam("name") String name) {
	    return service.getUserCVs(name);
    }

	@PostMapping("/upload-cv")
	public ResponseEntity<String> uploadCv(@RequestParam("file") MultipartFile file,
                                           @RequestParam("user") String name,
										   @RequestParam("fileName") String fileName){
		return service.uploadCv(file, name,fileName);
	}


    @GetMapping("/retrieve/{id}")
    public Cv retrieveFile(@PathVariable String id) {
	    return service.getCV(id);
    }
    

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCv(@PathVariable String id){
	    return service.deleteCv(id);
    }

    @PutMapping("/update-cv/{id}")
    public ResponseEntity<String> updateCv(@PathVariable("id") String id,
										   @RequestParam("file") MultipartFile file,
										   @RequestParam("fileName") String fileName) {

        return service.updateCv(id, file, fileName);
    } 
    
}
