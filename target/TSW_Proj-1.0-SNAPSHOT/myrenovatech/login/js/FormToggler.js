function formToggle() {
    const login = document.getElementById("login");
    const register = document.getElementById("register");

    if (register.style.display === "none") {
        login.style.display = "none";
        register.style.removeProperty("display");
    }
    else {
        register.style.display = "none";
        login.style.removeProperty("display");
    }
}