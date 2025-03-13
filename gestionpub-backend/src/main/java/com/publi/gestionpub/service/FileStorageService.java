package com.publi.gestionpub.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
	
	public String saveFile(MultipartFile file);
	public byte[] getFile(String filePath) throws IOException ;
}
