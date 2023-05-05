package com.imatrix.files.upload.db.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

	@Autowired
	private FileStorageService storageService;
	@PostMapping("/upload")
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("Referance") String Referance,
			@RequestParam("PartyName") String PartyName, @RequestParam("FileDescription") String FileDescription,
			@RequestParam("VoucherType") String VoucherType, @RequestParam("OrderNo") String OrderNo,
			@RequestParam("VoucherNo") String VoucherNo, @RequestParam("comGuid") String comGuid,
			@RequestParam("guid") String guid, @RequestParam("filePath") String filePath) {
		String message = "";
		try {
			FileDTO fileDTO = new FileDTO();
			fileDTO.setReferance(Referance);
			fileDTO.setPartyName(PartyName);
			fileDTO.setFileDescription(FileDescription);
			fileDTO.setVoucherType(VoucherType);
			fileDTO.setOrderNo(OrderNo);
			fileDTO.setVoucherNo(VoucherNo);
			fileDTO.setComGuid(comGuid);
			fileDTO.setGuid(guid);

			storageService.store(convertFiletoMultiPart(filePath), fileDTO);

			message = "Uploaded the file successfully: " + filePath;
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + filePath + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
		}
	}

	@GetMapping("/files")
	public ResponseEntity<List<ResponseFile>> getListFiles() {
		List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
			String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/files/")
					.path(dbFile.getID() + "").toUriString();

			return new ResponseFile(dbFile.getFileName(), fileDownloadUri, dbFile.getType(), dbFile.getFile().length);
		}).collect(Collectors.toList());

		return ResponseEntity.status(HttpStatus.OK).body(files);
	}

	@GetMapping("/getFiles")
	public ResponseEntity<byte[]> getFiles(@RequestParam("orderNo") String orderNo,
			@RequestParam("comGuid") String comGuid) {
		FileDB fileDB = storageService.getFileByOrderGuid(orderNo, comGuid);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
				.body(fileDB.getFile());
	}

	@GetMapping("/getFilesById")
	public ResponseEntity<byte[]> getFilesById(@RequestParam("id") Long id) {
		FileDB fileDB = storageService.getFileById(id);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getFileName() + "\"")
				.body(fileDB.getFile());
	}

	@GetMapping("/delFileById")
	public ResponseEntity<String> delFiles(@RequestParam("id") Long id) {
		int i = storageService.delFileByOrderGuid(id);
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

	private MultipartFile convertFiletoMultiPart(String FILE_PATH) {
		MultipartFile multipartFile = null;
		try {
			File file = new File(FILE_PATH);

			if (file.exists()) {
				System.out.println("File Exist => " + file.getName() + " :: " + file.getAbsolutePath());
			}
			FileInputStream input = new FileInputStream(file);
			multipartFile = new MockMultipartFile("file", file.getName(),
					file.toURI().toURL().openConnection().getContentType(), IOUtils.toByteArray(input));
			System.out.println("multipartFile => " + multipartFile.isEmpty() + " :: "
					+ multipartFile.getOriginalFilename() + " :: " + multipartFile.getName() + " :: "
					+ multipartFile.getSize() + " :: " + multipartFile.getBytes());
		} catch (IOException e) {
			System.out.println("Exception => " + e.getLocalizedMessage());
		}
		return multipartFile;
	}
}
