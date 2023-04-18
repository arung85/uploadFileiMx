package com.imatrix.files.upload.db.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("Referance") String Referance, @RequestParam("PartyName") String PartyName,
			@RequestParam("FileDescription") String FileDescription, @RequestParam("VoucherType") String VoucherType,
			@RequestParam("OrderNo") String OrderNo, @RequestParam("VoucherNo") String VoucherNo,
			@RequestParam("comGuid") String comGuid, @RequestParam("guid") String guid) {
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

			System.out.println("fileDTO.toString(): " + fileDTO.toString());
			storageService.store(file, fileDTO);

			message = "Uploaded the file successfully: " + file.getOriginalFilename();
			return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
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

	@GetMapping("/delFiles")
	public ResponseEntity<String> delFiles(@RequestParam("orderNo") String orderNo,
			@RequestParam("comGuid") String comGuid) {
		int i = storageService.delFileByOrderGuid(orderNo, comGuid);
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
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.OK).body("Unable to create Access:"+e.getMessage());
		}

	}
}
