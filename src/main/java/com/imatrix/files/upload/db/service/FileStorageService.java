package com.imatrix.files.upload.db.service;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.imatrix.files.upload.db.model.FileDB;
import com.imatrix.files.upload.db.model.FileDTO;
import com.imatrix.files.upload.db.model.User;
import com.imatrix.files.upload.db.repository.FileDBRepository;
import com.imatrix.files.upload.db.repository.UserRepository;

@Service
public class FileStorageService {

	@Autowired
	private FileDBRepository fileDBRepository;
	@Autowired
	private UserRepository userRepository;

	public void appReady() {
		userRepository.save(new User("admin", "admin", "Admin"));
		userRepository.save(new User("user", "user", "User"));
	}

	public FileDB store(MultipartFile file, FileDTO fileDto) throws IOException {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		FileDB FileDB = new FileDB(fileDto.getReferance(), fileDto.getPartyName(), fileDto.getFileDescription(),
				fileName, getFileExt(fileName).get(), file.getContentType(), file.getBytes(), fileDto.getVoucherType(),
				fileDto.getOrderNo(), fileDto.getVoucherNo(), fileDto.getComGuid(), fileDto.getGuid(), 1);

		return fileDBRepository.save(FileDB);
	}

	public FileDB getFile(String id) {
		return fileDBRepository.findById(id).get();
	}

	public FileDB getFileByOrderGuid(String order_no, String com_guid) {
		return fileDBRepository.getFileByOrderGuid(order_no, com_guid);
	}

	public int delFileByOrderGuid(String order_no, String com_guid) {
		return fileDBRepository.delFileByOrderGuid(order_no, com_guid);
	}

	public Stream<FileDB> getAllFiles() {
		return fileDBRepository.findAll().stream();
	}

	public Optional<String> getFileExt(String filename) {
		return Optional.ofNullable(filename).filter(f -> f.contains("."))
				.map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
