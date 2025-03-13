let selectedPublicationId = null; // ID de la publication sélectionnée
let selectedReviseurId = null;    // ID du réviseur sélectionné

document.addEventListener('DOMContentLoaded', function () {
    fetchPublications();  // Charger les publications
    fetchReviseurs();     // Charger les réviseurs
});

// Fonction pour afficher les publications dans le modal
function fetchPublications() {
    const token = localStorage.getItem('token');
    if (!token) {
        console.error("Token d'authentification manquant");
        return;
    }

    fetch('http://localhost:8090/soumis/all', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json'
        }
    })

    .then(response => response.json())
    .then(publications => {
        const publicationSelect = document.getElementById('publication-select');
        publicationSelect.innerHTML = '';  // Réinitialiser le contenu

        publications.forEach(pub => {
            const option = document.createElement('option');
            option.value = pub.id;
            option.textContent = pub.titre;
            publicationSelect.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Erreur lors du chargement des publications:', error);
    });
}

// Fonction pour afficher les réviseurs dans le modal
function fetchReviseurs() {
    const token = localStorage.getItem('token');
    if (!token) {
        console.error("Token d'authentification manquant");
        return;
    }

    fetch('http://localhost:8090/reviseurs/all'
        , {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + token,
                'Content-Type': 'application/json'
            }
        })
    .then(response => response.json())
    .then(reviseurs => {
        const reviseurSelect = document.getElementById('reviseur-select');
        reviseurSelect.innerHTML = '';  // Réinitialiser le contenu

        // Vérification des données des réviseurs
        if (reviseurs && reviseurs.length > 0) {
            reviseurs.forEach(rev => {
                const option = document.createElement('option');
                option.value = rev.id;
                option.textContent = rev.email;
                reviseurSelect.appendChild(option);
            });
        } else {
            const option = document.createElement('option');
            option.textContent = 'Aucun réviseur disponible';
            reviseurSelect.appendChild(option);
        }
    })
    .catch(error => {
        console.error('Erreur lors du chargement des réviseurs:', error);
    });
}

// Ouvrir le modal
function openModal() {
    document.getElementById('affecter-modal').style.display = 'block';
}

// Fermer le modal
function closeModal() {
    document.getElementById('affecter-modal').style.display = 'none';
}

// Lorsque l'utilisateur choisit une publication, mettez à jour l'ID sélectionné
document.getElementById('publication-select').addEventListener('change', function (event) {
    selectedPublicationId = event.target.value;
});

// Lorsque l'utilisateur choisit un réviseur, mettez à jour l'ID sélectionné
document.getElementById('reviseur-select').addEventListener('change', function (event) {
    selectedReviseurId = event.target.value;
});

// Affecter la publication à un réviseur
function affecterPublication() {
    if (!selectedPublicationId || !selectedReviseurId) {
        alert("Veuillez sélectionner une publication et un réviseur.");
        return;
    }

    const token = localStorage.getItem('token');
    if (!token) {
        console.error("Token d'authentification manquant");
        return;
    }
   
    fetch('http://localhost:8090/revision/affecterReviseur', {
        method: 'POST',
        headers: {
            'Authorization': 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            idPublication: selectedPublicationId,
            idUtilisateur: selectedReviseurId
        })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`Erreur lors de l'affectation : ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => {
        alert("Réviseur affecté avec succès !");
        closeModal();  // Fermer le modal après l'affectation
        fetchPublications();  // Recharger les publications si nécessaire
    })
    .catch(error => {
        alert(`Erreur : ${error.message}`);  // Afficher un message d'erreur à l'utilisateur
        console.error('Erreur lors de l\'affectation du réviseur:', error);
    });
}