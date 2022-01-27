package com.mac.springboot.builder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.mac.springboot.domain.Document;
import com.mac.springboot.dto.out.DocumentOut;

public class FileBuilder {

	public static Document of(MultipartFile uploadedFile) {
		Document document = new Document();
		Map<FileAttribute, Date> fileBasicAttributesFile = fileBasicAttributes(mutipartToFile(uploadedFile));
		try {
			document.setTitle(uploadedFile.getOriginalFilename());
			document.setDescription(uploadedFile.getContentType());
			document.setCreationDate(fileBasicAttributesFile.get(FileAttribute.LAST_ACCESS));
			document.setUploadDate(new Date());
			document.setFile(uploadedFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return document;
	}

	public static DocumentOut of(Document document) {
		DocumentOut documentOut = new DocumentOut();
		documentOut.setId(document.getId());
		documentOut.setTitle(document.getTitle());
		documentOut.setDescription(document.getDescription());
		return documentOut;
	}

	private static Map<FileAttribute, Date> fileBasicAttributes(File file) {
		EnumMap<FileAttribute, Date> attributes = new EnumMap<>(FileAttribute.class);
		if (file == null)
			return attributes;

		try {
			BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
			attributes.put(FileAttribute.CREATION_DATE, new Date(attr.creationTime().toMillis()));
			attributes.put(FileAttribute.LAST_ACCESS, new Date(attr.lastAccessTime().toMillis()));
			attributes.put(FileAttribute.LAST_MODIFY, new Date(attr.lastModifiedTime().toMillis()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return attributes;
	}

	private static File mutipartToFile(MultipartFile multipart) {

		File file = new File(multipart.getOriginalFilename());
		try {
			file.createNewFile();
			copyToFile(multipart, file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return file;

	}

	private static void copyToFile(MultipartFile multipart, File file) throws IOException {
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(multipart.getBytes());
		fos.close();

	}

}
