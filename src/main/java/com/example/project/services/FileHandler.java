package com.example.project.services;

import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public interface FileHandler {
	
	Path singleFileUpload(MultipartFile file);

}
