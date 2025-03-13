package com.publi.gestionpub.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.publi.gestionpub.Dto.RevisionDto;
import com.publi.gestionpub.Dto.RevisionsoumetDto;
import com.publi.gestionpub.entité.Publication;
import com.publi.gestionpub.entité.Revision;
import com.publi.gestionpub.service.PublicationService;
import com.publi.gestionpub.service.RevisionService;


@RestController
@CrossOrigin(origins = "*")
public class RevisionController {
	Logger log = LoggerFactory.getLogger(RevisionController.class);
	@Autowired
	RevisionService revisionService;
	@Autowired
	PublicationService publicationService;
	
	@PostMapping("/revision/affecterReviseur")
	public ResponseEntity<String> AffectpubReviseur(@RequestBody RevisionDto dto) {
	    log.info("Requête reçue : idPublication={}, idUtilisateur={}", dto.getIdPublication(), dto.getIdUtilisateur());
	    try {
	        revisionService.AffecterRevision(dto);
	        return ResponseEntity.ok("Publication affectée à un réviseur avec succès");
	    } catch (Exception e) {
	        log.error("Erreur lors de l'affectation :", e);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur lors de l'affectation : " + e.getMessage());
	    }
	}
	@PutMapping("/revisions/{idRevision}")
    public ResponseEntity<?> updateRevision(@PathVariable Long idRevision, @RequestBody RevisionsoumetDto dto) {
        try {
            // Vérifie que le DTO contient l'ID correspondant
            dto.setIdRevision(idRevision);

            // Appelle le service pour effectuer la mise à jour
            revisionService.SoumettreRevisionPublication(dto);

            return ResponseEntity.ok("Révision mise à jour avec succès");
        } catch (AccessDeniedException e) {
            // Retourne un code 403 si l'utilisateur n'a pas les permissions
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            // Retourne un code 400 si l'entrée est invalide
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Retourne un code 500 pour les erreurs inattendues
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur interne du serveur");
        }
    }
@GetMapping("/revision/reviseurPublication")
public ResponseEntity<List<Revision>> getMyRevisionsReviseur() {
    List<Revision> revisions = revisionService.getRevisionForCurrentUser();
    return ResponseEntity.ok(revisions);
}
@GetMapping("/revisiontrue/all")
public ResponseEntity<List<Revision>> getAllRevisionTrue() {
    
    return ResponseEntity.ok(revisionService.getRevisionAllTrue());
}
@GetMapping("/revisionfalse/all")
public ResponseEntity<List<Revision>> getAllRevisionFasle() {
    
    return ResponseEntity.ok(revisionService.getRevisionAllFalse());
}
@GetMapping("/en-revision")
public ResponseEntity<List<Revision>> getRevisionsEnRevision() {
    try {
        // Appel au service pour récupérer les révisions filtrées
        List<Revision> revisions = revisionService.getRevisionCurrentUser();

        // Vérification si des révisions sont présentes
        if (revisions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(revisions);
        }

        // Retour des révisions dans la réponse
        return ResponseEntity.ok(revisions);
    } catch (Exception e) {
        // En cas d'erreur, renvoyer une réponse avec un code d'erreur
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
@GetMapping("/effectuees-validees")
public ResponseEntity<List<Revision>> getRevisionsEffectueesValidees() {
    try {
        // Appel au service pour récupérer les révisions filtrées
        List<Revision> revisions = revisionService.getRevisionForCurrentUserValide();

        // Vérification si des révisions sont présentes
        if (revisions.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(revisions);
        }

        // Retour des révisions dans la réponse
        return ResponseEntity.ok(revisions);
    } catch (Exception e) {
        // En cas d'erreur, renvoyer une réponse avec un code d'erreur
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
@PutMapping("/valider/{idRevision}")
public ResponseEntity<Map<String, Object>> validerPublication(@PathVariable Long idRevision) {
    Map<String, Object> response = new HashMap<>();
    try {
        String message = revisionService.validerPublication(idRevision);
        response.put("message", message);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    } catch (IllegalArgumentException | IllegalStateException e) {
        response.put("message", e.getMessage());
        response.put("status", "error");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}


@GetMapping("/validated")
public ResponseEntity<List<Revision>> getValidatedRevisions() {
    List<Revision> revisions = revisionService.getValidatedRevisionsForCurrentUserchercheur();
    return ResponseEntity.ok(revisions);
}
@GetMapping("/revisionterminer")
public ResponseEntity<List<Revision>> getCompletedRevisions() {
    try {
        // Récupérer le username du réviseur connecté
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // Récupérer les révisions effectuées
        List<Revision> completedRevisions = revisionService.getRevisionsByTerminer(username);

        // Vérifier si la liste est vide
        if (completedRevisions.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content
        }
        return ResponseEntity.ok(completedRevisions); // 200 OK avec les données
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
    }
}
}
