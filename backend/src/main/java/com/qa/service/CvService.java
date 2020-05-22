package com.qa.service;

import com.qa.domain.Cv;
import com.qa.domain.User;
import com.qa.repository.ICvRepository;
import com.qa.repository.UserRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class CvService {

	@Autowired
	private ICvRepository iCvRepository;

	@Autowired
    private UserRepository userRepository;

	public ResponseEntity getUserCVs(String name) {

	    List<Cv> list = iCvRepository.findAllByName(name);

	    if (list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
	    return ResponseEntity.ok().body(list);
    }

    public ResponseEntity uploadCv(MultipartFile file, String name, String fileName) {

        Optional<User> found = userRepository.findByUsername(name);

        if(!found.isPresent()) {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .build().toUri();

        try {
            if (file.getSize() >= 16000000) {
                System.out.println("Should to be stored in GridFS \n Still to be implemented");
            }

            Binary binary = new Binary(BsonBinarySubType.BINARY, file.getBytes());
            Cv cv = new Cv(name, fileName, binary);
            iCvRepository.save(cv);

        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.created(location).body("File successfully uploaded");

    }

    public ResponseEntity getCV(String id) {

      	Cv cv = null;
    	Optional<Cv> finder = iCvRepository.findById(id);

        if (!finder.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cv = finder.get();

        return ResponseEntity.ok(cv);

    }
	
	public ResponseEntity deleteCv(String id) {
        Optional<Cv> foundCv = iCvRepository.findById(id);
        if (foundCv.isPresent()) {

            iCvRepository.delete(foundCv.get());
            return ResponseEntity.ok("CV successfully deleted");
        }
        return ResponseEntity.notFound().build();
	}

	public ResponseEntity updateCv(String id, MultipartFile file, String fileName) {

	    Optional<Cv> cv = iCvRepository.findById(id);
	    Cv cvToUpdate = null;

        if (cv.isPresent()){
            cvToUpdate = cv.get();
        } else {
            return new ResponseEntity<>("Unable to find CV", HttpStatus.NOT_FOUND);
        }

        try {
            Binary updatedCvBinary = new Binary(BsonBinarySubType.BINARY, file.getBytes());
            cvToUpdate.setCvFile(updatedCvBinary);
            cvToUpdate.setLastModified(LocalDateTime.now(ZoneId.of("Europe/London")).truncatedTo(ChronoUnit.SECONDS));
            cvToUpdate.setFileName(fileName);
            iCvRepository.save(cvToUpdate);
        }
        catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to update.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("CV successfully updated.", HttpStatus.OK);
    }
}