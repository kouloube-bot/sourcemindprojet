package com.publi.gestionpub.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.publi.gestionpub.Dto.RevisionDto;
import com.publi.gestionpub.Dto.RevisionsoumetDto;
import com.publi.gestionpub.entité.AppUser;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;
import com.publi.gestionpub.enume.PublicationStatus;
import com.publi.gestionpub.repository.PublicationRepository;
import com.publi.gestionpub.repository.RevisionRepository;
import com.publi.gestionpub.repository.UserRepository;
import com.publi.gestionpub.service.EmailService;
import com.publi.gestionpub.service.RevisionService;

@Service
public class RevisionServiceImpl implements RevisionService{
	private static final Logger log = LoggerFactory.getLogger(RevisionServiceImpl.class);
	@Autowired
	 private EmailService emailService;
	@Autowired
	PublicationRepository publicationRepository ;
	@Autowired
   UserRepository userRepository;
	@Autowired
	RevisionRepository  revisionRepository ;

	@Override
	public void AffecterRevision(RevisionDto dto) {
		if (dto.getIdPublication() == null || dto.getIdUtilisateur() == null) {
	        throw new IllegalArgumentException("Les IDs de publication et de réviseur doivent être fournis.");
	    }

		// Récupérer la publication par ID
	    Publication publication = publicationRepository.findById(dto.getIdPublication())
	            .orElseThrow(() -> new IllegalArgumentException("Publication introuvable"));

	 // Vérification si le statut de la publication est "SOUMISE"
	    if (!publication.getStatus().equals(PublicationStatus.SOUMISE)) {
	        throw new IllegalArgumentException("La publication doit être soumise avant d'être affectée à un réviseur.");
	    }

	    // Vérification de l'utilisateur pour la révision
	    AppUser appUser = userRepository.findById(dto.getIdUtilisateur())
	            .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable"));
	    // Récupérer l'utilisateur connecté
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
	    AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
	    
	    log.info("Utilisateur trouvé : " + utilisateur.getUsername());
	    
	    // Vérifier si l'utilisateur connecté est un admin
	    boolean hasAdminRole = utilisateur.getRoles().stream()
	            .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
	    log.info("L'utilisateur a le rôle ADMIN : " + hasAdminRole);
	    
	    if (!hasAdminRole) {
	        throw new AccessDeniedException("Vous devez être un admin pour effectuer cette opération");
	    }

	    // Créer et initialiser l'objet Revision
	    Revision revision = new Revision();
	    revision.setPublication(publication);
	    revision.setUtilisateur(appUser);
	    revision.setCommentaires(null);
	    revision.setNote(0);
	    revision.setEffectuee(false);

	    // Envoi d'un email au réviseur
	    String subject = "Nouvelle publication affectée pour révision";
	    String body = "Vous avez été assigné à la révision de la publication intitulée '" +
	                  publication.getTitre() + "'. Veuillez la réviser.";

	    emailService.sendEmail(appUser.getEmail(), subject, body);
	    
	    // Enregistrer la révision
	    revisionRepository.save(revision);

	    // Mettre à jour le statut de la publication à "ENREVISION"
	    publication.setStatus(PublicationStatus.ENREVISION);
	    publicationRepository.save(publication);

	    // Log pour confirmer le changement
	    log.info("Le statut de la publication a été mis à jour à 'ENREVISION'");
	}

	@Override
	public void SoumettreRevisionPublication(RevisionsoumetDto dto) {
	    // Obtenir le nom d'utilisateur (email ou username) de l'utilisateur connecté
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();

	    // Récupérer l'utilisateur connecté dans la base de données
	    AppUser utilisateur = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));

	    log.info("Utilisateur trouvé : " + utilisateur.getUsername());

	    // Vérifier le rôle de l'utilisateur (par exemple, REVISEUR)
	    boolean hasReviserRole = utilisateur.getRoles().stream()
	            .anyMatch(role -> role.getRoleName().equalsIgnoreCase("REVISEUR"));

	    log.info("L'utilisateur a le rôle REVISEUR : " + hasReviserRole);

	    if (!hasReviserRole) {
	        throw new AccessDeniedException("Vous devez être un REVISEUR pour effectuer cette action.");
	    }

	    // Vérifier si une révision avec cet ID existe
	    Revision revision = revisionRepository.findById(dto.getIdRevision())
	            .orElseThrow(() -> new IllegalArgumentException("Révision introuvable avec l'ID spécifié"));

	    // Vérifier si la révision appartient à l'utilisateur connecté
	    if (!revision.getUtilisateur().getId().equals(utilisateur.getId())) {
	        throw new AccessDeniedException("Vous n'avez pas le droit de modifier cette révision.");
	    }

	    // Mise à jour des informations de la révision
	    revision.setCommentaires(dto.getCommentaires());
	    revision.setNote(dto.getNote());
	    revision.setEffectuee(true);

	    // Sauvegarder les modifications
	    revisionRepository.save(revision);

	    log.info("Révision mise à jour avec succès pour la publication ID : " + revision.getPublication().getIdPublication());
	}

	@Override
	public List<Revision> getRevisionForCurrentUser() {
		// Obtenir le nom d'utilisateur (email ou username) de l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Récupérer l'utilisateur connecté dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
		return revisionRepository.findByUtilisateurId(utilisateur.getId());
	}
	@Override
	public List<Revision> getRevisionAllTrue() {
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
            throw new AccessDeniedException("Vous devez être un admin pour effectuer cette operation");
        }
        
		return revisionRepository.findByeffectueTrue();
	}
	@Override
	public List<Revision> getRevisionAllFalse() {
		// Récupére l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Récupére les détails de l'utilisateur dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifie le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour effectuer cette operation");
        }
        
		return revisionRepository.findByeffectueFalse();
	}
	@Override
	public List<Revision> getRevisionCurrentUser() {
        // Obteni le nom d'utilisateur (email ou username) de l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Récupére l'utilisateur connecté dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Récupére toutes les révisions de l'utilisateur
        List<Revision> allRevisions = revisionRepository.findByUtilisateurId(utilisateur.getId());

        // Filtre les révisions pour ne garder que celles qui sont "en révision" et non effectuées
        return allRevisions.stream()
                .filter(revision -> revision.getPublication().getStatus() == PublicationStatus.ENREVISION && !revision.isEffectuee())
                .collect(Collectors.toList());
    }

	@Override
	public List<Revision> getRevisionForCurrentUserValide() {
		// Obtenir le nom d'utilisateur (email ou username) de l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Récupérer l'utilisateur connecté dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // Récupérer toutes les révisions de l'utilisateur
        List<Revision> allRevisions = revisionRepository.findByUtilisateurId(utilisateur.getId());

        // Filtrer les révisions pour ne garder que celles qui sont validées et effectuées
        return allRevisions.stream()
                .filter(revision -> revision.getPublication().getStatus() == PublicationStatus.VALIDE && revision.isEffectuee())
                .collect(Collectors.toList());
    }
	@Transactional
	@Override
	public String validerPublication(Long idRevision) {
		// Récupére l'utilisateur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        // Récupére les détails de l'utilisateur dans la base de données
        AppUser utilisateur = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur connecté introuvable"));
        log.info("Utilisateur trouvé : " + utilisateur.getUsername());
        // Vérifie le rôle de l'utilisateur
        boolean hasadminRole = utilisateur.getRoles().stream()
                .anyMatch(role -> role.getRoleName().equalsIgnoreCase("ADMIN"));
        log.info("L'utilisateur a le rôle ADMIN : " + hasadminRole);
        if (!hasadminRole) {
            throw new AccessDeniedException("Vous devez être un admin pour effectuer cette operation");
        }
        
		// Récupére la révision par ID
        Revision revision = revisionRepository.findById(idRevision)
          .orElseThrow(() -> new IllegalArgumentException("Révision introuvable pour l'ID : " + idRevision));
        // Vérifie que la publication est en cours de révision et que la révision est effectuée
        if (!revision.isEffectuee() || revision.getPublication().getStatus() != PublicationStatus.ENREVISION) {
            throw new IllegalStateException("La publication ne peut pas être validée. Conditions non remplies.");
        }

        // Met à jour le statut de la publication
        Publication publication = revision.getPublication();
        publication.setStatus(PublicationStatus.VALIDE);
        publicationRepository.save(publication);

        return "Publication validée avec succès pour l'ID : " + publication.getIdPublication();
	}

	

	@Override
	public List<Revision> getValidatedRevisionsForCurrentUserchercheur() {
		// Récupérer l'email de l'utilisateur connecté
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        
        // Récupérer les révisions avec le statut VALIDEE
        return revisionRepository.findByPublicationStatusAndUtilisateurEmail(
            PublicationStatus.VALIDE, 
            email
        );
	}

	@Override
	public List<Revision> getRevisionsByTerminer(String email) {
		 
		        return revisionRepository.findCompletedRevisionsByReviewer(email);
		    
	}
	
	}
	
	
	

	
	
 
	




