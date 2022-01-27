package com.mac.springboot.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mac.springboot.dto.out.DocumentOut;
import com.mac.springboot.service.DocumentService;

@RestController
@RequestMapping(value = "/document")
public class RestApiController {

	public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

	@Autowired
	private DocumentService documentService;

	@GetMapping(value = "/status", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> ok() {

		return new ResponseEntity<>("ok", HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:3333")
	@PostMapping(value = "/upload", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DocumentOut> upload(@RequestParam("file") MultipartFile file) {
		return documentService.insert(file);
	}

	@CrossOrigin(origins = "http://localhost:3333")
	@GetMapping(value = "{id}/download/", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public ResponseEntity<Resource> download(@PathVariable(value = "id") Long id, HttpServletRequest request) {
		return documentService.findOne(id);
	}
}
