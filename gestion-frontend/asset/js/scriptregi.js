const API_URL = "http://localhost:8090";

document.getElementById("signup-form").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const user = { username, email, password };

    fetch(`${API_URL}/users/add`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user),
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Une erreur s'est produite lors de l'inscription.");
            }
            return response.json();
        })
        .then((data) => {
            alert("Inscription réussie !");
            window.location.href = "login.html";
        })
        .catch((error) => {
            console.error("Erreur :", error);
            alert("Une erreur est survenue. Veuillez réessayer.");
        });
});
