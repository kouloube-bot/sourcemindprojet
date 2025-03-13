// Fonction pour afficher les rôles
async function afficherRoles() {
    const token = localStorage.getItem("token");

    // Vérifier si le token est présent
    if (!token) {
        alert("Vous devez vous reconnecter.");
        window.location.href = "login.html";
        return;
    }

    try {
        // Requête GET pour récupérer les rôles
        const response = await fetch("http://localhost:8090/roles", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}` // En-tête avec le token JWT
            }
        });

        // Vérifier si la réponse est correcte
        if (!response.ok) {
            if (response.status === 403) {
                throw new Error("Vous n'avez pas les permissions pour afficher cette liste.");
            }
            throw new Error("Erreur lors du chargement des rôles.");
        }

        // Récupérer les données JSON
        const roles = await response.json();
        console.log("Données reçues :", roles);

        // Sélectionner le tableau dans le DOM
        const tbody = document.querySelector("#role-table tbody");

        if (!tbody) {
            console.error("L'élément #role-table tbody est introuvable.");
            return;
        }

        tbody.innerHTML = ""; // Vider le tableau avant d'ajouter les nouvelles données

        // Ajouter les rôles dynamiquement dans le tableau
        roles.forEach(role => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${role.id}</td>
                <td>${role.roleName}</td>
            `;
            tbody.appendChild(tr);
        });

    } catch (error) {
        console.error("Erreur :", error.message);
        alert("Erreur : " + error.message);
    }
}

// Charger les rôles après le chargement du DOM
document.addEventListener("DOMContentLoaded", afficherRoles);

// listeuser
document.addEventListener("DOMContentLoaded", async function() {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Vous devez vous reconnecter.");
        window.location.href = "login.html";  // Redirige vers la page de login si le token est manquant
        return;
    }

    try {
        const response = await fetch("http://localhost:8090/utilisateur/all", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`  // Ajout du token JWT dans l'en-tête
            }
        });

        if (!response.ok) {
            throw new Error("Erreur lors du chargement des utilisateurs.");
        }

        const users = await response.json();  // Convertir la réponse en JSON
        const tbody = document.querySelector("#user-table tbody");
        tbody.innerHTML = "";  // Réinitialiser le tableau avant d'ajouter les nouvelles données

        // Ajouter chaque utilisateur au tableau
        users.forEach(user => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${user.id}</td>
                <td>${user.email}</td>
                <td>${user.username}</td>
                <td>${user.roles.map(role => role.roleName).join(', ')}</td>  <!-- Afficher les rôles -->
            `;
            tbody.appendChild(tr);  // Ajouter une nouvelle ligne au tableau
        });
    } catch (error) {
        console.error("Erreur :", error.message);
        alert("Erreur : " + error.message);
    }
});

//liste enseignantchercheur
/// Variables pour gérer la pagination
let chercheurs = []; // Liste des chercheurs
const ITEMS_PER_PAGE = 5; // Nombre d'éléments par page
let currentPage = 1;

// Fonction pour récupérer les chercheurs via l'API
async function fetchChercheurs() {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Vous devez vous connecter.");
        window.location.href = "login.html";
        return;
    }

    try {
        const response = await fetch("http://localhost:8090/enseignat/all", {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error("Erreur lors du chargement des chercheurs.");
        }

        chercheurs = await response.json();
        afficherPage(); // Afficher la première page
    } catch (error) {
        console.error("Erreur :", error.message);
        alert("Erreur : " + error.message);
    }
}

// Fonction pour afficher une page spécifique
function afficherPage() {
    const startIndex = (currentPage - 1) * ITEMS_PER_PAGE;
    const endIndex = startIndex + ITEMS_PER_PAGE;
    const chercheursPage = chercheurs.slice(startIndex, endIndex);

    const tbody = document.querySelector("#chercheur-table tbody");
    tbody.innerHTML = ""; // Réinitialiser le tableau

    chercheursPage.forEach((chercheur) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${chercheur.id}</td>
            <td>${chercheur.nom}</td>
            <td>${chercheur.prenom}</td>
            <td>${chercheur.email}</td>
            <td>${chercheur.departement.libelle}</td>
        `;
        tbody.appendChild(row);
    });

    afficherPagination();
}

// Fonction pour mettre à jour les contrôles de pagination
function afficherPagination() {
    const totalPages = Math.ceil(chercheurs.length / ITEMS_PER_PAGE);

    const prevPage = document.getElementById("prev-page");
    const nextPage = document.getElementById("next-page");
    const currentPageElement = document.getElementById("current-page");

    // Mettre à jour le texte de la page actuelle
    currentPageElement.textContent = `Page ${currentPage} sur ${totalPages}`;

    // Activer/Désactiver les liens de pagination
    prevPage.classList.toggle("disabled", currentPage === 1);
    nextPage.classList.toggle("disabled", currentPage === totalPages);
}

// Ajouter des gestionnaires d'événements pour les liens de pagination
document.getElementById("prev-page").addEventListener("click", () => {
    if (currentPage > 1) {
        currentPage--;
        afficherPage();
    }
});

document.getElementById("next-page").addEventListener("click", () => {
    const totalPages = Math.ceil(chercheurs.length / ITEMS_PER_PAGE);
    if (currentPage < totalPages) {
        currentPage++;
        afficherPage();
    }
});

// Charger les chercheurs au chargement de la page
document.addEventListener("DOMContentLoaded", fetchChercheurs);
