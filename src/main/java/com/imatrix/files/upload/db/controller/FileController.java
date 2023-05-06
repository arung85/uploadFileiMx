package com.imatrix.files.upload.db.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.imatrix.files.upload.db.message.ResponseFile;
import com.imatrix.files.upload.db.message.ResponseMessage;
import com.imatrix.files.upload.db.model.FileDB;
import com.imatrix.files.upload.db.model.FileDTO;
import com.imatrix.files.upload.db.service.FileStorageService;

@Controller
@CrossOrigin("http://localhost:8081")
public class FileController {

	private static final Logger logger = LogManager.getLogger(FileController.class);

	@Autowired
	private FileStorageService storageService;

	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestBody FileDTO fileData) {
		String message = "";
		try {
			storageService.store(convertFiletoMultiPartByPath(fileData.getFilePath()), fileData);

			message = "Uploaded the file successfully: " + fileData.getFilePath();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + fileData.getFilePath() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@PostMapping("/uploadFileParam")
	public ResponseEntity<ResponseMessage> uploadFileParam(@RequestParam("reference") String reference,
			@RequestParam("partyName") String partyName, @RequestParam("fileDescription") String fileDescription, 
			@RequestParam("voucherType") String voucherType,@RequestParam("orderNo") String orderNo,
			@RequestParam("voucherNo") String voucherNo, @RequestParam("comGuid") String comGuid,
			@RequestParam("guid") String guid, @RequestParam("file") MultipartFile file) {
		String message = "";
		try {

			FileDTO fileDTO = new FileDTO();
			fileDTO.setReferance(reference);
			fileDTO.setPartyName(partyName);
			fileDTO.setFileDescription(fileDescription);
			fileDTO.setVoucherType(voucherType);
			fileDTO.setOrderNo(orderNo);
			fileDTO.setVoucherNo(voucherNo);
			fileDTO.setComGuid(comGuid);
			fileDTO.setGuid(guid);
			storageService.store(file, fileDTO);
			message = "Uploaded the file successfully: " + file.getName();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getName() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/getAllFiles")
	public ResponseEntity<List<ResponseFile>> getAllFiles() {
		List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getID() + "").toUriString();

			return new ResponseFile(dbFile.getFileName(), fileDownloadUri, dbFile.getType(), dbFile.getFile().length);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/getFileById")
	public ResponseEntity<byte[]> getFileById(@RequestBody FileDTO fileData) {
		if (ObjectUtils.isEmpty(fileData.getId()) || fileData.getId() < 0)
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "Id can't be empty").body(null);
		FileDB fileDB = storageService.getFileById(fileData.getId());

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
				.body(fileDB.getFile());
	}

	@GetMapping("/delFileById")
	public ResponseEntity<String> delFiles(@RequestBody FileDTO fileData) {
		if (ObjectUtils.isEmpty(fileData.getId()) || fileData.getId() < 0)
			return ResponseEntity.status(HttpStatus.OK).body("Id can't be empty");
		int i = storageService.delFileByOrderGuid(fileData.getId());
		if (i > 0)
			return ResponseEntity.status(HttpStatus.OK).body("File Deleted");
		else
			return ResponseEntity.status(HttpStatus.OK).body("File Not Deleted");
	}

	@GetMapping("/createAccess")
	public ResponseEntity<String> createAccess() {
		try {
			storageService.appReady();
			return ResponseEntity.status(HttpStatus.OK).body("File Deleted");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body("Unable to create Access:" + e.getMessage());
		}

	}

	private MultipartFile convertFiletoMultiPartByPath(String FILE_PATH) {
		MultipartFile multipartFile = null;
		try {
			File file = new File(FILE_PATH);

			if (file.exists()) {

				logger.info("File Exist => " + file.getName() + " :: " + file.getAbsolutePath());
			}
			FileInputStream input = new FileInputStream(file);
			multipartFile = new MockMultipartFile("file", file.getName(),
					file.toURI().toURL().openConnection().getContentType(), IOUtils.toByteArray(input));
			logger.info("multipartFile => " + multipartFile.isEmpty() + " :: " + multipartFile.getOriginalFilename()
					+ " :: " + multipartFile.getName() + " :: " + multipartFile.getSize() + " :: "
					+ multipartFile.getBytes());
		} catch (IOException e) {
			logger.info("Exception => " + e.getLocalizedMessage());
		}
		return multipartFile;
	}

}
