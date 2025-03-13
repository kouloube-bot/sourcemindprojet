// script.js

document.addEventListener('DOMContentLoaded', function () {
    const revisionTableBody = document.getElementById('revisionTableBody');
    const noDataMessage = document.getElementById('noDataMessage');

    // Fonction pour récupérer les révisions via l'API
    async function fetchCompletedRevisions() {
        const token = localStorage.getItem('token'); // Assurez-vous que le token est stocké
        if (!token) {
            alert('Vous devez vous connecter.');
            window.location.href = 'login.html';
            return;
        }

        try {
            const response = await fetch('http://localhost:8090/revisionterminer', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });

            if (response.ok) {
                const revisions = await response.json();
                displayRevisions(revisions);
            } else if (response.status === 204) {
                // Pas de contenu
                noDataMessage.style.display = 'block';
                revisionTableBody.innerHTML = '';
            } else {
                const errorData = await response.json();
                alert(`Erreur: ${errorData.message || 'Erreur inconnue'}`);
            }
        } catch (error) {
            console.error('Erreur:', error);
            alert('Erreur lors de la récupération des révisions.');
        }
    }

    // Fonction pour afficher les révisions dans la table
    function displayRevisions(revisions) {
        noDataMessage.style.display = revisions.length === 0 ? 'block' : 'none';
        revisionTableBody.innerHTML = '';

        revisions.forEach(revision => {
            const row = document.createElement('tr');
            row.innerHTML = `
                <td>${revision.publication.titre}</td>
                 <td>${revision.publication.type}</td>
                 <td>${revision.publication.resume}</td>
                  <td>${revision.publication.status}</td>
                <td>
                            ${revision.publication.fichierPDF ? 
                                `<a href="${revision.publication.fichierPDF}" target="_blank">
                                    <i class="bi bi-file-pdf"></i> Voir le PDF
                                </a>` : 
                                "Non disponible"
                            }
                        </td>
                 <td>${revision.effectuee}</td>
                <td>${revision.commentaires}</td>
                <td>${revision.note}</td>
            `;
            revisionTableBody.appendChild(row);
        });
    }

    // Charger les révisions au chargement de la page
    fetchCompletedRevisions();
});
