package com.publi.gestionpub.serviceImpl;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.publi.gestionpub.Dto.PublicationDto;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;
import com.publi.gestionpub.enume.PublicationStatus;
import com.publi.gestionpub.repository.PublicationRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.EmailService;
import com.publi.gestionpub.service.FileStorageService;
import com.publi.gestionpub.service.PublicationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Service
public class PublicationServiceImpl implements PublicationService {
	private static final Logger log = LoggerFactory.getLogger(PublicationServiceImpl.class);
	 @Autowired
 private PublicationRepository publicationRepository;

	 @Autowired
 private EmailService emailService;

	    @Autowired
private UserRepository userRepository;
	    @Autowired
private FileStorageService fileStorageService;

	@Override
	public Publication  createPublication(PublicationDto publicationDto, MultipartFile file) {
		 // Récupére l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Récupére les détails de l'utilisateur dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasChercheurRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("CHERCHEUR"));
        log.info("L'utilisateur a le rôle CHERCHEUR : " + hasChercheurRole);
        if (!hasChercheurRole) {
            throw new AccessDeniedException("Vous devez être un chercheur pour ajouter une publication");
        }
     // Sauvegarde le fichier PDF
        String filePath;
        filePath = fileStorageService.saveFile(file);
        Publication publication = new Publication();
        publication.setTitre(publicationDto.getTitre());
        publication.setResume(publicationDto.getResume());
        publication.setFichierPDF(filePath);
        publication.setDateSoumission(new Date()); 
        publication.setStatus(PublicationStatus.SOUMISE);
        publication.setType(publicationDto.getType());
        publication.setUtilisateur(utilisateur);
        log.info("Publication créée : " + publication);
        String adminEmail = "kouloubekoli@gmail.com";
        String subject = "Nouvelle publication soumise";
        String body = "Une nouvelle publication intitulée '" + publication.getTitre() +
                      "' a été soumise par " + publication.getUtilisateur().getUsername() + ".";

        emailService.sendEmail(adminEmail, subject, body);
        return publicationRepository.save(publication);
       
		
	}

	@Override
	public List<Publication> getPublicationsForCurrentUser() {
	    // Obtenir le nom d'utilisateur (email ou username) de l'utilisateur connecté
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // Récupérer l'utilisateur connecté dans la base de données
	    AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

	    // Récupérer les publications liées à cet utilisateur avec un statut "soumise"
	    return publicationRepository.findByUtilisateurIdAndStatus(utilisateur.getId(), PublicationStatus.SOUMISE);
	}


	@Override
	public List<Publication> getAll() {
		 // Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Récupérer les détails de l'utilisateur dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir toutes les publications");
        }
		return publicationRepository.findAll();
	}

	@Override
	public void validerPublication(Long idPublication) {
		 // Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour valide toutes les publications");
        }
		Publication publication = publicationRepository.findById(idPublication)
                .orElseThrow(() -> new RuntimeException("Publication non trouvée"));
        publication.setStatus(PublicationStatus.VALIDE);
        String subject = "Votre publication a été validée";
	    String body = "Félicitations ! Votre publication intitulée '" + publication.getTitre() +
                "' a été validée avec succès.";
	    emailService.sendEmail(publication.getUtilisateur().getEmail(), subject, body);
        publicationRepository.save(publication);
		
	}

	@Override
	public void rejetterPublication(Long idPublication) {
		// Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour valide toutes les publications");
        }
		Publication publication = publicationRepository.findById(idPublication)
                .orElseThrow(() -> new RuntimeException("Publication non trouvée"));
        publication.setStatus(PublicationStatus.REJETTE);
        String subject = "Votre publication a été rejettée";
	    String body = "desolé! Votre publication intitulée '" + publication.getTitre() +
                "' a été rejetée.";
	    emailService.sendEmail(publication.getUtilisateur().getEmail(), subject, body);
        publicationRepository.save(publication);
		
	}

	@Override
	public void EncourdeRevisionPublication(Long idPublication) {
		// Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifier le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour valide toutes les publications");
        }
		Publication publication = publicationRepository.findById(idPublication)
                .orElseThrow(() -> new RuntimeException("Publication non trouvée"));
        publication.setStatus(PublicationStatus.ENREVISION);
        String subject = "Votre publication est actuelle en revision";
	    String body = " Votre publication intitulée '" + publication.getTitre() +
                "' est actuellement en revision.";
	    emailService.sendEmail(publication.getUtilisateur().getEmail(), subject, body);
        publicationRepository.save(publication);
		
	}
	
	@Override
	public List<Publication> rechercherParType(String type) {
		return publicationRepository.rechercherParType(type);
	}

	@Override
	public List<PublicationDto> getValidatedPublications() {
        // Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        
        // Vérifier le rôle de l'utilisateur
        boolean hasAdminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (!hasAdminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir les publications validées");
        }
        
        // Récupérer les publications validées
        List<Publication> publications = publicationRepository.findByStatus(PublicationStatus.VALIDE);

        // Vérifier si la liste est vide
        if (publications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune publication validée trouvée");
        }
        
        // Convertir les publications en PublicationDto
        return publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
	// Convertir une publication en PublicationDto
    private PublicationDto convertToDto(Publication publication) {
        PublicationDto publicationDto = new PublicationDto();
        publicationDto.setIdPublication(publication.getIdPublication());
        publicationDto.setTitre(publication.getTitre());
        publicationDto.setResume(publication.getResume());
        publicationDto.setFichierPDF(publication.getFichierPDF());
        publicationDto.setDateSoumission(publication.getDateSoumission());
        publicationDto.setStatus(publication.getStatus());
        publicationDto.setUtilisateurId(publication.getUtilisateur().getId());
        publicationDto.setType(publication.getType());
        
        return publicationDto;
    }

	@Override
	public List<PublicationDto> getSoumisPublications() {
		 // Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        
        // Vérifier le rôle de l'utilisateur
        boolean hasAdminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (!hasAdminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir les publications validées");
        }
        
        // Récupérer les publications validées
        List<Publication> publications = publicationRepository.findByStatus(PublicationStatus.SOUMISE);

        // Vérifier si la liste est vide
        if (publications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune publication validée trouvée");
        }
        
        // Convertir les publications en PublicationDto
        return publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}

	@Override
	public List<PublicationDto> getrevisionPublications() {
		 // Récupérer l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        
        // Vérifier le rôle de l'utilisateur
        boolean hasAdminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (!hasAdminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir les publications validées");
        }
        
        // Récupérer les publications validées
        List<Publication> publications = publicationRepository.findByStatus(PublicationStatus.ENREVISION);

        // Vérifier si la liste est vide
        if (publications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune publication validée trouvée");
        }
        
        // Convertir les publications en PublicationDto
        return publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}

	@Override
	public List<PublicationDto> getValidatedPublicationsAll() {
        // Récupérer les publications validées
        List<Publication> publications = publicationRepository.findByStatus(PublicationStatus.VALIDE);

        // Vérifier si la liste est vide
        if (publications.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune publication validée trouvée");
        }
        
        // Convertir les publications en PublicationDto
        return publications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
	}

	@Override
	public long countValidatedPublications() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        
        // Vérifier le rôle de l'utilisateur
        boolean hasAdminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (!hasAdminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir les publications validées");
        }
		return publicationRepository.countPublicationsByStatus(PublicationStatus.VALIDE);
	}

	@Override
	public long countSoumisPublications() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        
        // Vérifier le rôle de l'utilisateur
        boolean hasAdminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (!hasAdminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir les publications validées");
        }
		return publicationRepository.countPublicationsByStatus(PublicationStatus.SOUMISE);
	}

	@Override
	public long countEnrevisionPublications() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        
        // Vérifier le rôle de l'utilisateur
        boolean hasAdminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));

        if (!hasAdminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour voir les publications validées");
        }
		return publicationRepository.countPublicationsByStatus(PublicationStatus.ENREVISION);
		
	}

	@Override
	public Publication updatePublication(Long idPublication, PublicationDto publicationDto) {
	    // Obtenir le nom d'utilisateur de l'utilisateur connecté
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // Récupérer l'utilisateur connecté
	    AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

	    // Récupérer la publication par ID
	    Publication publication = publicationRepository.findById(idPublication)
	            .orElseThrow(() -> new RuntimeException("Publication introuvable"));

	    // Vérifier que l'utilisateur est l'auteur de la publication
	    if (!publication.getUtilisateur().getId().equals(utilisateur.getId())) {
	        throw new AccessDeniedException("Vous n'êtes pas autorisé à modifier cette publication");
	    }

	    // Vérifier que le statut de la publication est "SOUMISE"
	    if (!publication.getStatus().equals(PublicationStatus.SOUMISE)) {
	        throw new IllegalArgumentException("La publication ne peut être modifiée que si son statut est 'SOUMISE'");
	    }

	    // Mettre à jour les champs de la publication
	    publication.setTitre(publicationDto.getTitre());
	    publication.setResume(publicationDto.getResume());
	    publication.setType(publicationDto.getType());
	    if (publicationDto.getFichierPDF() != null) {
	        publication.setFichierPDF(publicationDto.getFichierPDF());
	    }
	    log.info("Début mise à jour publication: {}", idPublication);
	    log.info("Status actuel: {}", publication.getStatus());
	    log.info("Utilisateur: {}", username);
	    // Enregistrer les modifications
	    return publicationRepository.save(publication);
	}
	@Override
	public void deletePublication(Long idPublication) {
	    // Obtenir le nom d'utilisateur de l'utilisateur connecté
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // Récupérer l'utilisateur connecté
	    AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

	    // Récupérer la publication par ID
	    Publication publication = publicationRepository.findById(idPublication)
	            .orElseThrow(() -> new RuntimeException("Publication introuvable"));

	    // Vérifier que l'utilisateur est l'auteur de la publication
	    if (!publication.getUtilisateur().getId().equals(utilisateur.getId())) {
	        throw new AccessDeniedException("Vous n'êtes pas autorisé à supprimer cette publication.");
	    }

	    // Vérifier que le statut de la publication est "SOUMISE"
	    if (!publication.getStatus().equals(PublicationStatus.SOUMISE)) {
	        throw new IllegalArgumentException("Vous ne pouvez supprimer une publication que si son statut est 'SOUMISE'.");
	    }

	    // Supprimer la publication
	    publicationRepository.delete(publication);
	}

	@Override
	public List<Publication> getPublicationsEnRevisionNonEffectuee(String username) {
		AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
	    return publicationRepository.findPublicationsEnRevisionNonEffectuee(utilisateur.getId(), PublicationStatus.ENREVISION);
	}
	
	@Override
	public List<Publication> getValidatedRevisionsForCurrentUserchercheur(String email) {
		
		return publicationRepository.findByStatusAndUtilisateur_Email(PublicationStatus.VALIDE, email);
	    }

	
	
	
	}

	

