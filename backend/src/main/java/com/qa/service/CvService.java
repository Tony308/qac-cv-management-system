package com.qa.service;

import com.qa.domain.Cv;
import com.qa.repository.ICvRepository;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CvService {

	@Autowired
	private ICvRepository iCvRepository;

	public ResponseEntity<?> getUserCVs(String name) {

	    List<Cv> list = iCvRepository.findAllByName(name);

	    if (list.isEmpty()){
            return ResponseEntity.notFound().build();
        }
	    return ResponseEntity.ok().body(list);
    }

    public ResponseEntity<?> uploadCv(MultipartFile file, String name, String fileName) {

        try {
                Binary fileToBinaryStorage = new Binary(BsonBinarySubType.BINARY, file.getBytes());

                Cv cv = new Cv(name, fileToBinaryStorage,fileName);

                iCvRepository.save(cv);

                URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/upload-cv").build().toUri();

                return ResponseEntity.created(location).body("File successfully uploaded");

        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Upload failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//        public Cv downloadCv(String id) {
//        Cv cv = iCvRepository.findById(id).get();
//        //Writes file to PC
////        Binary document = cv.getCvFile();
////        try {
////            FileOutputStream fos = null;
////            String fileDestination = "/home/tony308/Documents/" + cv.getName()+"'s_CV";
////            fos = new FileOutputStream(fileDestination);
////            //Will write Byte data to fileDestination
////            fos.write(document.getData());
////            fos.close();
////        } catch (IOException e) {
////            e.printStackTrace();
////            return cv;
////        }
//        return cv;
//    }

    
    public ResponseEntity<?> getCV(String id) {

      	Cv cv = null;
    	Optional<Cv> finder = iCvRepository.findById(id);

        if (!finder.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cv = finder.get();

        return ResponseEntity.ok(cv);

    }
	
	public ResponseEntity<?> deleteCv(String id) {
        Optional<Cv> foundCv = iCvRepository.findById(id);
        if (foundCv.isPresent()) {

            iCvRepository.delete(foundCv.get());
            return ResponseEntity.ok("CV successfully deleted");

        }
        return ResponseEntity.notFound().build();
	}

	public ResponseEntity<?> updateCv(String id, MultipartFile file, String fileName) {

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
            cvToUpdate.setLastModified(new Date());
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