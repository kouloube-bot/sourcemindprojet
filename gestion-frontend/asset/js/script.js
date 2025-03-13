// auth-service.js

// Configuration de l'URL de l'API
const API_URL = "http://localhost:8090";

// Fonction pour afficher les toasts
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    const toastMessage = document.getElementById('toast-message');
    const toastTitle = document.getElementById('toast-title');
    const toastInstance = new bootstrap.Toast(toast);
    
    // Définir les classes de couleur selon le type
    toast.classList.remove('bg-success', 'bg-danger', 'bg-warning', 'text-white');
    switch(type) {
        case 'success':
            toast.classList.add('bg-success', 'text-white');
            toastTitle.textContent = 'Succès';
            break;
        case 'error':
            toast.classList.add('bg-danger', 'text-white');
            toastTitle.textContent = 'Erreur';
            break;
        case 'warning':
            toast.classList.add('bg-warning');
            toastTitle.textContent = 'Avertissement';
            break;
    }
    
    // Définir le message
    toastMessage.textContent = message;
    
    // Afficher le toast
    toastInstance.show();
}

// Fonction de vérification d'authentification
function checkAuth() {
    const token = localStorage.getItem("token");
    const role = localStorage.getItem("role");

    if (!token || !role) {
        showToast("Session expirée. Veuillez vous reconnecter.", "warning");
        setTimeout(() => {
            window.location.href = "login.html";
        }, 1500);
        return false;
    }
    return true;
}

// Gestion de la soumission du formulaire de connexion
if (document.getElementById("login-form")) {
    document.getElementById("login-form").addEventListener("submit", async function (e) {
        e.preventDefault();

        const username = document.getElementById("email").value;
        const password = document.getElementById("password").value;
        const user = { username, password };

        try {
            const response = await fetch(`${API_URL}/authenticate`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user),
            });

            if (!response.ok) {
                throw new Error("Échec de la connexion. Veuillez vérifier vos informations.");
            }

            const data = await response.json();

            if (data.token && data.role) {
                localStorage.setItem("token", data.token);
                localStorage.setItem("role", data.role);

                showToast("Connexion réussie !", "success");
                
                setTimeout(() => {
                    switch (data.role) {
                        case "ADMIN":
                            window.location.href = "menuadmin.html";
                            break;
                        case "CHERCHEUR":
                            window.location.href = "menuChercheur.html";
                            break;
                        case "REVISEUR":
                            window.location.href = "menuReviseur.html";
                            break;
                        default:
                            showToast("Rôle inconnu!", "error");
                            window.location.href = "login.html";
                    }
                }, 1500);
            } else {
                showToast("Erreur lors de la connexion. Rôle ou token manquant.", "error");
            }
        } catch (error) {
            console.error("Erreur :", error);
            showToast(error.message, "error");
        }
    });
}

// Gestion de l'inscription
if (document.getElementById("signup-form")) {
    document.getElementById("signup-form").addEventListener("submit", async function (e) {
        e.preventDefault();

        const username = document.getElementById("username").value;
        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const user = { username, email, password };

        try {
            const response = await fetch(`${API_URL}/users/add`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(user),
            });

            if (!response.ok) {
                throw new Error("Une erreur s'est produite lors de l'inscription.");
            }

            const data = await response.json();
            showToast("Inscription réussie !", "success");
            setTimeout(() => {
                window.location.href = "login.html";
            }, 1500);
        } catch (error) {
            console.error("Erreur :", error);
            showToast(error.message, "error");
        }
    });
}

// Gestion de l'inscription des chercheurs
if (document.getElementById("chercheur-form")) {
    document.addEventListener("DOMContentLoaded", async function() {
        try {
            const response = await fetch(`${API_URL}/departements`);
            if (!response.ok) {
                throw new Error("Erreur lors de la récupération des départements");
            }
            
            const departements = await response.json();
            const departementSelect = document.getElementById("departement");
            
            departements.forEach(departement => {
                const option = document.createElement("option");
                option.value = departement.id;
                option.textContent = departement.libelle;
                departementSelect.appendChild(option);
            });
        } catch (error) {
            console.error("Erreur:", error);
            showToast("Erreur lors du chargement des départements.", "error");
        }
    });

    document.getElementById("chercheur-form").addEventListener("submit", async function(e) {
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
            const response = await fetch(`${API_URL}/chercheuradd`, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(enseignantChercheur)
            });

            if (!response.ok) {
                throw new Error("Erreur lors de l'ajout de l'enseignant-chercheur.");
            }

            const data = await response.json();
            showToast("Enseignant-Chercheur ajouté avec succès !", "success");
            setTimeout(() => {
                window.location.href = "success.html";
            }, 1500);
        } catch (error) {
            console.error("Erreur:", error);
            showToast(error.message, "error");
        }
    });
}

// Gestion des départements
if (document.getElementById("menu-gestion-departements")) {
    document.getElementById("menu-gestion-departements").addEventListener("click", function () {
        document.getElementById("form-ajout-departement").style.display = "block";
    });
}

if (document.getElementById("departementForm")) {
    document.getElementById("departementForm").addEventListener("submit", function (e) {
        e.preventDefault();
        
        const token = localStorage.getItem("token");
        if (!token) {
            showToast("Token manquant. Veuillez vous reconnecter.", "error");
            setTimeout(() => {
                window.location.href = "login.html";
            }, 1500);
            return;
        }

        const libelle = document.getElementById("libelle").value;
        const description = document.getElementById("description").value;

        fetch(`${API_URL}/departement`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ libelle, description })
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Erreur lors de l'ajout du département.");
            }
            return response.json();
        })
        .then(data => {
            showToast("Département ajouté avec succès !", "success");
            setTimeout(() => {
                window.location.href = "listeDepartement.html";
            }, 1500);
        })
        .catch(error => {
            console.error("Erreur :", error);
            showToast(error.message, "error");
        });
    });
}

// Vérifier l'authentification pour les pages sécurisées
if (window.location.pathname === "/menuadmin.html" || 
    window.location.pathname === "/menuChercheur.html" || 
    window.location.pathname === "/menuReviseur.html") {
    checkAuth();
}

// Gestion de la déconnexion
if (document.getElementById("logout-link")) {
    document.getElementById("logout-link").addEventListener("click", function(e) {
        e.preventDefault();
        localStorage.removeItem("token");
        localStorage.removeItem("role");
        showToast("Déconnexion réussie !", "success");
        setTimeout(() => {
            window.location.href = "login.html";
        }, 1500);
    });
}