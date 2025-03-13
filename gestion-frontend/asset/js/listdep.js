document.addEventListener("DOMContentLoaded", async function() {
    const token = localStorage.getItem("token");

    if (!token) {
        alert("Vous devez vous reconnecter.");
        window.location.href = "login.html";  // Rediriger vers la page de login si le token est manquant
        return;
    }

    try {
        const response = await fetch("http://localhost:8090/departemen/all", {
            method: "GET",
            headers: {
                "Authorization": `Bearer ${token}`  // Ajouter le token dans l'en-tête pour l'authentification
            }
        });

        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des départements.");
        }

        const departements = await response.json();  // Convertir la réponse en JSON
        const tbody = document.querySelector("#departement-table tbody");
        tbody.innerHTML = "";  // Réinitialiser le tableau avant d'ajouter les nouvelles données

        departements.forEach(departement => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${departement.id}</td>
                <td>${departement.libelle}</td>
                <td>${departement.description}</td>
            `;
            tbody.appendChild(tr);  // Ajouter une nouvelle ligne au tableau
        });
    } catch (error) {
        console.error("Erreur :", error.message);
        alert("Erreur : " + error.message);
    }
});
