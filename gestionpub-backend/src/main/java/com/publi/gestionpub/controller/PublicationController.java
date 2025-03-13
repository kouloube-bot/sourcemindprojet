package com.publi.gestionpub.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.publi.gestionpub.Dto.PublicationDto;
import com.publi.gestionpub.Dto.StatistiquesDTO;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;
import com.publi.gestionpub.enume.PublicationStatus;
import com.publi.gestionpub.repository.PublicationRepository;
import com.publi.gestionpub.service.FileStorageService;
import com.publi.gestionpub.service.PublicationService;




@CrossOrigin(origins = "*")
@RestController
public class PublicationController {
	@Autowired
    private PublicationService publicationService;
	@Autowired
	PublicationRepository publicationRepository;
	@Autowired
	FileStorageService fileStorageService;
	
	private static final String UPLOAD_DIR = "C:/Users/HP/Downloads/gestionpub-backend/gestionpub-backend/uploads";
	@PostMapping("/api/publications")
	 public ResponseEntity<Publication> create(
	            @RequestParam("titre") String titre,
	            @RequestParam("resume") String resume,
	            @RequestParam("type") String type,
	            @RequestParam("file") MultipartFile file) {
		PublicationDto publicationDto = new PublicationDto();
        publicationDto.setTitre(titre);
        publicationDto.setResume(resume);
        publicationDto.setType(type);
		Publication savedPublication = publicationService.createPublication(publicationDto, file);
        return ResponseEntity.ok(savedPublication);
	
    }
	@GetMapping("/mypublication")
    public ResponseEntity<List<Publication>> getMyPublications() {
        List<Publication> publications = publicationService.getPublicationsForCurrentUser();
        return ResponseEntity.ok(publications);
    }
	@GetMapping("/publications/all")
    public ResponseEntity<List<Publication>> getAllPublications() {
         
        return ResponseEntity.ok(publicationService.getAll());
    }

	@PutMapping("/{id}/valider")
 public ResponseEntity<String> validerPublication(@PathVariable Long id) {
	        publicationService.validerPublication(id);
	        return ResponseEntity.ok("Publication validée avec succès");
	    }
	@PutMapping("/{id}/rejetter")
	 public ResponseEntity<String> rejetterPublication(@PathVariable Long id) {
		        publicationService.rejetterPublication(id);
		        return ResponseEntity.ok("desolé votre publication a été rejeter");
		    }
	@PutMapping("/{id}/enrevision")
	 public ResponseEntity<String> enrevisionPublication(@PathVariable Long id) {
		        publicationService.EncourdeRevisionPublication(id);
		        return ResponseEntity.ok("votre publication est actuellement en revision");
		    }
	@GetMapping("/type/all")
    public ResponseEntity<List<Publication>> rechercherParType(@RequestParam String type) {
        List<Publication> publications = publicationService.rechercherParType(type);
        return ResponseEntity.ok(publications);
    }
	 @GetMapping("/api/publications/download/{fileName}")
	    public ResponseEntity<FileSystemResource> downloadFile(@PathVariable String fileName) {
	        // Crée le chemin complet vers le fichier
	        File file = new File(UPLOAD_DIR + fileName);
	        // Vérifier si le fichier existe
	        if (!file.exists()) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	        // Cree une ressource de fichier pour le téléchargement
	        FileSystemResource resource = new FileSystemResource(file);
	        // Ajoute des headers pour spécifier que c'est un fichier à télécharger
	        HttpHeaders headers = new HttpHeaders();
	        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

	        return ResponseEntity.ok()
	                             .headers(headers)
	                             .body(resource);
	    }


	 
	         @GetMapping("/file/{idPublication}")
	     	public ResponseEntity<byte[]> getFile(@PathVariable Long idPublication) {
	     	    // Validation de l'ID
	     	    if (idPublication == null || idPublication <= 0) {
	     	        return ResponseEntity.badRequest()
	     	            .body(null);
	     	    }

	     	    try {
	     	        // Récupération de la publication
	     	        Publication publication = publicationRepository.findById(idPublication)
	     	            .orElseThrow(() -> new RuntimeException("Publication non trouvée avec l'ID: " + idPublication));

	     	        // Vérification du nom du fichier
	     	        if (publication.getFichierPDF() == null || publication.getFichierPDF().isEmpty()) {
	     	            return ResponseEntity.badRequest()
	     	                .body(null);
	     	        }

	     	        // Récupération du fichier
	     	        byte[] file = fileStorageService.getFile(publication.getFichierPDF());
	     	        
	     	        return ResponseEntity.ok()
	     	            .contentType(MediaType.APPLICATION_PDF)
	     	            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + publication.getFichierPDF() + "\"")
	     	            .body(file);

	     	    } catch (ConfigDataResourceNotFoundException e) {
	     	        return ResponseEntity.notFound().build();
	     	    } catch (IOException e) {
	     	        return ResponseEntity.internalServerError().build();
	     	    } catch (Exception e) {
	     	        return ResponseEntity.internalServerError().build();
	     	    }
	     	}

	 
	 @GetMapping("/valides/all")
	    public List<PublicationDto> getValidatedPublications() {
	        return publicationService.getValidatedPublications();
	    }
	 @GetMapping("/validevisiteur/all")
	    public List<PublicationDto> getValidatedPublicationsVisiteur() {
	        return publicationService.getValidatedPublicationsAll();
	    }
	 @GetMapping("/soumis/all")
	    public List<PublicationDto> getSoumisPublications() {
	        return publicationService.getSoumisPublications();
	    }
	 @GetMapping("/en-revision/all")
	    public List<PublicationDto> getrevisionPublications() {
	        return publicationService.getrevisionPublications();
	    }
	 @GetMapping("/count/validated")
	    public ResponseEntity<Long> countValidatedPublications() {
	        long count = publicationService.countValidatedPublications();
	        return ResponseEntity.ok(count);
	    }
	 @GetMapping("/count/soumis")
	    public ResponseEntity<Long> countSoumisPublications() {
	        long count = publicationService.countSoumisPublications();
	        return ResponseEntity.ok(count);
	    }
	 @GetMapping("/count/enrevision")
	    public ResponseEntity<Long> countEncourPublications() {
	        long count = publicationService.countEnrevisionPublications();
	        return ResponseEntity.ok(count);
	    }
	 @PutMapping("publicationmod/{idPublication}")
	 public ResponseEntity<?> updatePublication(@PathVariable Long idPublication, @RequestBody PublicationDto publicationDto) {
	     try {
	         Publication updatedPublication = publicationService.updatePublication(idPublication, publicationDto);
	         return ResponseEntity.ok(updatedPublication);
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	     }
	 }
	 @DeleteMapping("publicationdele/{idPublication}")
	 public ResponseEntity<?> deletePublication(@PathVariable("idPublication") Long idPublication) {
	     try {
	         publicationService.deletePublication(idPublication);
	         return ResponseEntity.ok("Publication supprimée avec succès.");
	     } catch (Exception e) {
	         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
	     }
	 }
	 @GetMapping("/publications/en-revision")
	 public List<Publication> getPublicationsEnRevision() {
	     String username = SecurityContextHolder.getContext().getAuthentication().getName();
	     return publicationService.getPublicationsEnRevisionNonEffectuee(username);
	 }
	 @GetMapping("/validated/pubm")
	    public ResponseEntity<List<Publication>> getValidatedPublicat() {
	        // Récupérer l'email de l'utilisateur connecté
	        String email = SecurityContextHolder.getContext().getAuthentication().getName();

	        List<Publication> publications = publicationService.getValidatedRevisionsForCurrentUserchercheur(email);
	        return ResponseEntity.ok(publications);
	    }
	 @GetMapping("/stats")
	    public StatistiquesDTO getStats() {
	        long total = publicationRepository.count();
	        long valides = publicationRepository.countByStatus(PublicationStatus.VALIDE);
	        long enRevision = publicationRepository.countByStatus(PublicationStatus.ENREVISION);
	        long soumis = publicationRepository.countByStatus(PublicationStatus.SOUMISE);
	        
	        return new StatistiquesDTO(total, valides, enRevision, soumis);
	    }
	 
}
