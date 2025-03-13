package com.publi.gestionpub.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.publi.gestionpub.service.FileStorageService;

@Service
public class FileStorageServiceImpl implements FileStorageService {
	
	private final String uploadDir = "C:/Users/HP/Downloads/gestionpub-backend/gestionpub-backend/uploads";
	private final Path fileStorageLocation = Paths.get("C:/Users/HP/Downloads/gestionpub-backend/gestionpub-backend/uploads").toAbsolutePath().normalize();
	public FileStorageServiceImpl() {
		
        // Crée le dossier s'il n'existe pas
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

	@Override
	public String saveFile(MultipartFile file) {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        try {
			Files.write(filePath, file.getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return filePath.toString();
}
	

	public byte[] getFile(String fileName) throws IOException {
	    if (fileName == null || fileName.isEmpty()) {
	        throw new IllegalArgumentException("Le nom du fichier ne peut pas être null ou vide");
	    }

	    Path filePath = fileStorageLocation.resolve(fileName).normalize();
	    
	    // Vérification de sécurité pour éviter le path traversal
	    if (!filePath.toAbsolutePath().startsWith(fileStorageLocation.toAbsolutePath())) {
	        throw new SecurityException("Accès au fichier non autorisé");
	    }

	    if (!Files.exists(filePath)) {
	        throw new FileNotFoundException("Le fichier " + fileName + " n'existe pas");
	    }

	    return Files.readAllBytes(filePath);
	}
	
}