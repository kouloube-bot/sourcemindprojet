
// Fonction pour ajouter un département
async function ajouterDepartement() {
    const token = localStorage.getItem("token");

    // Vérifiez si le token est présent dans le localStorage
    if (!token) {
        alert("Token manquant. Veuillez vous reconnecter.");
        window.location.href = "login.html";
        return;
    }

    // Récupérer les valeurs du formulaire
    const libelle = document.getElementById("libelle").value;
    const description = document.getElementById("description").value;

    // Vérifiez que les champs ne sont pas vides
    if (!libelle || !description) {
        alert("Veuillez remplir tous les champs.");
        return;
    }

    try {
        // Requête POST vers le backend avec await
        const response = await fetch("http://localhost:8090/departement", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Token JWT
            },
            body: JSON.stringify({
                libelle: libelle,
                description: description
            })
        });

        // Vérifier si la réponse est valide
        if (!response.ok) {
            if (response.status === 403) {
                throw new Error("Vous n'êtes pas autorisé à ajouter un département.");
            }
            throw new Error("Erreur lors de l'ajout du département.");
        }

        // Traiter la réponse JSON
        const result = await response.json();
        console.log("Département ajouté avec succès :", result);

        alert("Département ajouté avec succès !");
        window.location.href = "listeDepartement.html"; 
    } catch (error) {
        console.error("Erreur :", error.message);
        alert("Erreur : " + error.message);
    }
}

// Ajouter un écouteur d'événement sur le bouton
document.getElementById("ajouter-btn").addEventListener("click", ajouterDepartement);


//listedepartement


//ajouter role

// URL de l'API
const URL = "http://localhost:8090/role/add";

// Fonction pour ajouter un rôle
async function ajouterRole(event) {
    event.preventDefault(); // Empêche le rechargement de la page

    const token = localStorage.getItem("token"); // Récupérer le token JWT
    if (!token) {
        alert("Vous devez vous reconnecter. Token manquant.");
        window.location.href = "login.html";
        return;
    }

    // Récupérer la valeur du formulaire
    const roleName = document.getElementById("roleName").value.trim();

    // Validation simple côté frontend
    if (!roleName) {
        afficherMessage("Veuillez remplir le nom du rôle.", "danger");
        return;
    }

    try {
        // Requête POST vers le backend
        const response = await fetch(URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}` // Ajout du token JWT
            },
            body: JSON.stringify({ roleName: roleName }) // Données envoyées
        });

        // Vérifier la réponse
        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Erreur lors de l'ajout du rôle.");
        }

        // Succès
        const result = await response.json();
        console.log("Rôle ajouté avec succès :", result);
        afficherMessage("Rôle ajouté avec succès !", "success");

        // Rediriger vers la liste des rôles après 2 secondes
        setTimeout(() => {
            window.location.href = "listeRoles.html";
        }, 2000);

    } catch (error) {
        console.error("Erreur :", error.message);
        afficherMessage(`Erreur : ${error.message}`, "danger");
    }
}

// Fonction pour afficher un message dans la zone dédiée
function afficherMessage(message, type) {
    const messageDiv = document.getElementById("message");
    messageDiv.innerHTML = `<div class="alert alert-${type}" role="alert">${message}</div>`;
}

// Ajouter un écouteur d'événement pour le formulaire
document.getElementById("form-ajout-role").addEventListener("submit", ajouterRole);

