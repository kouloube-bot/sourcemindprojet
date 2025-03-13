document.addEventListener('DOMContentLoaded', function() {
    // Éléments du DOM
    const openModalBtn = document.getElementById('openModalBtn');
    const addPublicationModal = document.getElementById('addPublicationModal');
    const closeModalBtns = document.querySelectorAll('.close-btn');
    const addPublicationForm = document.getElementById('addPublicationForm');
    const editPublicationModal = document.getElementById('editPublicationModal');
    const editPublicationForm = document.getElementById('editPublicationForm');
    const publicationList = document.getElementById('publicationList');

    // Variables globales
    let publications = [];
    let currentStatus = 'soumises';
        // recupere les données de l'utilisateur connecté
        async function fetchAndDisplayStats() {
            const token = localStorage.getItem('token');
            if (!token) {
                console.error('Token manquant');
                return;
            }
    
            try {
                const response = await fetch('http://localhost:8090/stats', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
    
                if (!response.ok) {
                    throw new Error(`Erreur HTTP: ${response.status}`);
                }
    
                const stats = await response.json();
                console.log('Statistiques reçues:', stats); //
                 // Mise à jour des cartes statistiques
            const statCards = document.querySelectorAll('.stat-card');
            if (statCards.length >= 4) {
                statCards[0].querySelector('.number strong').textContent = stats.valides;
                statCards[1].querySelector('.number strong').textContent = stats.total;
                statCards[2].querySelector('.number strong').textContent = stats.enRevision;
                statCards[3].querySelector('.number strong').textContent = stats.soumis;
            }
        } catch (error) {
            console.error('Erreur lors de la récupération des statistiques:', error);
        }
    }
    fetchAndDisplayStats();
    
    // Mettre à jour les statistiques toutes les 30 secondes
    setInterval(fetchAndDisplayStats, 30000);


        async function getUserInfo() {
            const token = localStorage.getItem('token');
            try {
                const response = await fetch('http://localhost:8090/info/utilisateur', {
                    headers: {
                        'Authorization': `Bearer ${token}`
                    }
                });
                if (response.ok) {
                    const userData = await response.json();
                    const title = userData.prenom ? 
                        `${userData.nom} ${userData.prenom}` : 
                        userData.nom;
                    document.querySelector('.profile-section h5').textContent = title;
                }
            } catch (error) {
                console.error('Erreur:', error);
            }
        }
    
        getUserInfo();  
    
       
    // styles pour les onglets
    const styles = `
        .tabs-container {
            margin: 20px 0;
        }
        .tabs {
            display: flex;
            gap: 10px;
            margin-bottom: 20px;
        }
        .tab-button {
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: all 0.3s ease;
            background-color: #f0f0f0;
        }
        .tab-button.active {
            background-color: #0d6efd;
            color: white;
            transform: translateY(-2px);
        }
        .status-badge {
            padding: 5px 10px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: bold;
        }
        .status-SOUMISE { background-color: #e3f2fd; color: #0d47a1; }
        .status-VALIDEE { background-color: #e8f5e9; color: #1b5e20; }
        .status-EN_REVISION { background-color: #fff3e0; color: #e65100; }
    `;

    // Ajouter les styles
    const styleSheet = document.createElement("style");
    styleSheet.innerText = styles;
    document.head.appendChild(styleSheet);

    // Ajouter les onglets
    const tabsContainer = document.createElement("div");
    tabsContainer.className = "tabs-container";
    
    const table = document.querySelector("table");
    table.parentNode.insertBefore(tabsContainer, table);

    // Gestionnaires d'événements modaux
    openModalBtn.addEventListener('click', () => {
        addPublicationModal.style.display = 'flex';
    });

    closeModalBtns.forEach(button => {
        button.addEventListener('click', () => {
            addPublicationModal.style.display = 'none';
            editPublicationModal.style.display = 'none';
        });
    });

    // Ajout d'une nouvelle publication
    addPublicationForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const formData = new FormData(addPublicationForm);
        const token = localStorage.getItem('token');
        
        if (!token) {
            alert('Token manquant. Veuillez vous reconnecter.');
            window.location.href = 'login.html';
            return;
        }

        try {
            const response = await fetch('http://localhost:8090/api/publications', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                body: formData
            });

            if (response.ok) {
                alert('Publication ajoutée avec succès');
                addPublicationModal.style.display = 'none';
                addPublicationForm.reset();
                await fetchPublicationsByStatus(currentStatus);
            } else {
                const errorData = await response.json();
                alert(`Erreur: ${errorData.message || 'Erreur inconnue'}`);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue');
        }
    });

    // Modification d'une publication
    function openEditModal(publicationId) {
        const publication = publications.find(pub => pub.idPublication === publicationId);
        if (!publication) return;

        document.getElementById('editPublicationId').value = publication.idPublication;
        document.getElementById('editTitle').value = publication.titre;
        document.getElementById('editSummary').value = publication.resume;
        document.getElementById('editType').value = publication.type;
        editPublicationModal.style.display = 'flex';
    }

    editPublicationForm.addEventListener('submit', async function(e) {
        e.preventDefault();
        const idPublication = document.getElementById('editPublicationId').value;
        const formData = new FormData(editPublicationForm);
        
        const publicationData = {
            titre: formData.get('titre'),
            resume: formData.get('resume'),
            type: formData.get('type')
        };

        const token = localStorage.getItem('token');
        if (!token) {
            alert('Token manquant. Veuillez vous reconnecter.');
            window.location.href = 'login.html';
            return;
        }

        try {
            const response = await fetch(`http://localhost:8090/publicationmod/${idPublication}`, {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(publicationData)
            });

            if (response.ok) {
                alert('Publication mise à jour');
                editPublicationModal.style.display = 'none';
                await fetchPublicationsByStatus(currentStatus);
            } else {
                const errorData = await response.json();
                alert(errorData.message || 'Erreur de modification');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur serveur');
        }
    });

    // Suppression d'une publication
    async function deletePublication(idPublication) {
        if (!confirm('Êtes-vous sûr de vouloir supprimer cette publication ?')) return;

        const token = localStorage.getItem('token');
        if (!token) {
            alert('Token manquant. Veuillez vous reconnecter.');
            window.location.href = 'login.html';
            return;
        }

        try {
            const response = await fetch(`http://localhost:8090/publicationdele/${idPublication}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                alert('Publication supprimée avec succès');
                await fetchPublicationsByStatus(currentStatus);
            } else {
                const errorData = await response.json();
                alert(errorData.message || 'Erreur lors de la suppression');
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Une erreur est survenue');
        }
    }
    //afficher publication  validée pour un chercheur
    async function fetchPublicationsValidees() {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Token manquant');
            window.location.href = 'login.html';
            return;
        }
    
        try {
            const response = await fetch('http://localhost:8090/validated/pubm', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const publications = await response.json();
                displayPublications(publications);
            } else {
                const errorData = await response.json();
                alert(`Erreur: ${errorData.message}`);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la récupération');
        }
    }
     //afficher les publication en rvivision pour un chercheur
     async function fetchPublicationsEnRevision() {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Token manquant');
            window.location.href = 'login.html';
            return;
        }
    
        try {
            const response = await fetch('http://localhost:8090/publications/en-revision', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const publications = await response.json();
                displayPublications(publications);
            } else {
                const errorData = await response.json();
                alert(`Erreur: ${errorData.message}`);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la récupération');
        }
    }


    // Récupération et affichage des publications
    async function fetchPublicationsByStatus(status) {
        const token = localStorage.getItem('token');
        if (!token) {
            alert('Token manquant. Veuillez vous reconnecter.');
            window.location.href = 'login.html';
            return;
        }

        try {
            const response = await fetch('http://localhost:8090/mypublication', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                publications = await response.json();
                const filteredPublications = publications.filter(pub => {
                    switch(status) {
                        case 'soumises':
                            return pub.status === 'SOUMISE';
                        case 'validees':
                            return  fetchPublicationsValidees();
               
                        case 'revision':
                            return  fetchPublicationsEnRevision();
                        default:
                            return true;
                    }
                });
                displayPublications(filteredPublications);
            } else {
                const errorData = await response.json();
                alert(`Erreur: ${errorData.message || 'Erreur inconnue'}`);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la récupération des publications');
        }
    }
       function displayPublications(publications) {
        publicationList.innerHTML = '';
        const actionsHeader = document.querySelector('th:last-child'); 
         // Filtrer les publications avec statut SOUMISE
    const publicationsWithActions = publications.filter(pub => pub.status === 'SOUMISE');
    
    // Masquer la colonne Actions si aucune publication n'a le statut SOUMISE
    actionsHeader.style.display = publicationsWithActions.length > 0 ? '' : 'none';
        publications.forEach(publication => {
            const row = document.createElement('tr');
           
            const actionButtons = publication.status === 'SOUMISE' ? `
            <button class="edit-btn" data-id="${publication.idPublication}">
                <i class="fas fa-edit"></i> 
            </button>
            <button class="delete-btn" data-id="${publication.idPublication}">
                <i class="bi bi-trash"></i>
            </button>
        ` : '';
    
            row.innerHTML = `
                <td>${publication.titre}</td>
                <td>${publication.resume}</td>
                <td>${publication.fichierPDF ? '<a href="#">Voir fichier</a>' : 'Aucun document'}</td>
                <td><span class="status-badge status-${publication.status}">${publication.status}</span></td>
                <td>${publication.type}</td>
                 <td>${actionButtons}</td>
                  
            `;
            publicationList.appendChild(row);
            
            
            if (publication.status === 'SOUMISE') {
                row.querySelector('.edit-btn').addEventListener('click', () => openEditModal(publication.idPublication));
                row.querySelector('.delete-btn').addEventListener('click', () => deletePublication(publication.idPublication));
            }
        });
    }

    
    // Gestionnaire d'événements pour les onglets
    document.querySelectorAll('.tab-button').forEach(button => {
        button.addEventListener('click', (e) => {
            document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
            e.target.classList.add('active');
            currentStatus = e.target.dataset.status;
            fetchPublicationsByStatus(currentStatus);
        });
    });

    // Chargement initial
    fetchPublicationsByStatus(currentStatus);
});