package com.mac.springboot.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mac.springboot.builder.FileBuilder;
import com.mac.springboot.domain.Document;
import com.mac.springboot.dto.out.DocumentOut;
import com.mac.springboot.repository.DocumentRepository;

@Service
public class DocumentService {

	@Autowired
	private DocumentRepository fileRepository;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ResponseEntity<DocumentOut> insert(MultipartFile uploadedFile) {

		Document entity = FileBuilder.of(uploadedFile);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		fileRepository.save(entity);
		return new ResponseEntity<>(FileBuilder.of(entity), HttpStatus.OK);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public ResponseEntity<Resource> findOne(Long id) {
		HttpHeaders headers = new HttpHeaders();
		Optional<Document> entity = fileRepository.findById(id);
		Document document = entity.orElse(null);
		if (document == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		String headerFileName = "attachment; filename=" + document.getTitle();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, headerFileName);
		return new ResponseEntity<>(new ByteArrayResource(document.getFile()), headers, HttpStatus.OK);
	}
}
