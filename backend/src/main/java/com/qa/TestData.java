//package com.qa;
//
//import java.io.File;
//import java.nio.file.Files;
//
//import org.apache.commons.io.FileUtils;
//import org.bson.BsonBinarySubType;
//import org.bson.types.Binary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//
//import com.qa.domain.Cv;
//import com.qa.domain.User;
//import com.qa.repository.ICvRepository;
//import com.qa.repository.UserRepository;
//
//@EnableMongoRepositories(basePackageClasses = ICvRepository.class)
//@Configuration
//public class TestData implements CommandLineRunner {
//	
//	@Autowired
//	private ICvRepository iCvRepository;
//	
//	@Autowired
//	private UserRepository userRepository;
//	
//	@Override
//	public void run(String... args) throws Exception {
//		// TODO Auto-generated method stub
//		
//
//		File file = new File("/Users/tony/Downloads/testFile.txt");		
//		byte[] binaryData = FileUtils.readFileToByteArray(file);
//		
//				  
//          Binary fileToBinaryStorage = new Binary(BsonBinarySubType.BINARY, binaryData);
//
////		  User alex = new User("alex", "user", "test");
//		  User bob = new User("bob", "test", "user");
////		  userRepository.save(alex);
//		  userRepository.save(bob);
//		  	
////		  iCvRepository.save(new Cv("alex", fileToBinaryStorage, "testFile.txt"));
//		  iCvRepository.save(new Cv("bob", fileToBinaryStorage, "testFile.txt"));
//
//		  
//		  System.out.println("-------------------------------"); 
//		  for (Cv cv:
//			  iCvRepository.findAll()) { System.out.println(cv); } System.out.println();
//
//		  System.out.println("Customer found with findByFirstName('alex'):");
//		  System.out.println("--------------------------------");
//		  System.out.println(iCvRepository.findByName("alex"));
//		
//	}
//
//	
//}
