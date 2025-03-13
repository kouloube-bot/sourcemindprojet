document.addEventListener("DOMContentLoaded", async function () {
    try {
        // Effectue la requête pour récupérer les départements
        const response = await fetch("http://localhost:8090/departements");

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des départements");
        }

        // Convertit la réponse en JSON
        const departements = await response.json();

        // Trouverl'élément <select> dans le DOM
        const departementSelect = document.getElementById("departement");

        // Remplit la liste déroulante avec les départements
        departements.forEach(departement => {
            const option = document.createElement("option");
            option.value = departement.id; 
            option.textContent = departement.libelle; 
            departementSelect.appendChild(option);
        });

    } catch (error) {
        console.error("Erreur:", error);
        alert("Une erreur est survenue lors du chargement des départements.");
    }
});

// Soumission du formulaire
document.getElementById("chercheur-form").addEventListener("submit", async function (e) {
    e.preventDefault();

    const nom = document.getElementById("nom").value;
    const prenom = document.getElementById("prenom").value;
    const email = document.getElementById("email").value;
    const departement = document.getElementById("departement").value;  
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    const enseignantChercheur = {
        nom,
        prenom,
        email,
        idDepartement: departement, 
        username,
        password
    };

    try {
        const response = await fetch("http://localhost:8090/chercheuradd", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(enseignantChercheur)
        });

        if (!response.ok) {
            throw new Error("Erreur lors de l'ajout de l'enseignant-chercheur.");
        }

        const data = await response.json();
        alert("Enseignant-Chercheur ajouté avec succès !");
        // Redirige l'utilisateur vers la page de connexion
        window.location.href = "login.html"; 
    } catch (error) {
        console.error("Erreur:", error);
        alert("Une erreur est survenue. Veuillez réessayer.");
    }
});
