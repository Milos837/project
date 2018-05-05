package com.example.project.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileHandlerImpl implements FileHandler {

	private static String UPLOADED_FOLDER = "/home/milos/Desktop/upload/";

	@Override
	public Path singleFileUpload(MultipartFile file) {
		if (file.isEmpty()) {
			return null;
		}
		try {
			
			byte[] bytes = file.getBytes();
			Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
			Files.write(path, bytes);
			
			return path;
			
		} catch (IOException e) {
			e.getStackTrace();
		}
		return null;
	}

}
