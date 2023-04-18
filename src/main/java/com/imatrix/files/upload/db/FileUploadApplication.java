package com.imatrix.files.upload.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.imatrix.files.upload.db.service.FileStorageService;

@SpringBootApplication
public class FileUploadApplication {

	@Autowired
	private FileStorageService storageService;
	
	public static void main(String[] args) {
		SpringApplication.run(FileUploadApplication.class, args);
	}
	public void run(ApplicationArguments args) {
		storageService.appReady();
	}

}
