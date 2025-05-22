let countrySelected = false;

const countrySelect = document.querySelector("select");
if (countrySelect) {
    function countryToggler() {
        countrySelected = true;
        countrySelect.removeEventListener("change", countryToggler);
    }

    countrySelect.addEventListener("change", countryToggler);
}

document.getElementById("register").addEventListener("submit", function (ev) {
    let formOk = true;

    for (const el of document.querySelectorAll(".form-error"))
        if (el.style.display === "block") {
            ev.preventDefault();
            formOk = false;
            showPopup("Attenzione", "Alcuni campi sono invalidi! Controlla e riprova.");
            break;
        }

    if (!countrySelected && formOk) {
        showPopup("Attenzione", "Sei sicuro di aver selezionato il paese giusto?");
        countrySelected = true;
        ev.preventDefault();
    }
});

document.querySelectorAll("#register input").forEach(input => {
    input.addEventListener('input', function () {
        switch (input.name) {
            case 'fullName': validateName(input); break;
            case 'username': validateUsername(input); break;
            case 'pass': validatePass(input); break;
            case 'rep-pass': validateRepPass(input); break;
            case 'email': validateEmail(input); break;
            case 'cell': validateCell(input); break;
        }
    });
});

function validateName(input) {
    const error = document.getElementById("fullNameError");
    const regex = /^(?=.{1,50}$)\S+(?:\s+\S+)+$/;
    error.style.display = regex.test(input.value) ? "none" : "block";
}

function validateUsername(input) {
    const error = document.getElementById("usernameError");
    const availabilityError = document.getElementById('usernameAlreadyExistError');
    const regex = /^[a-zA-Z0-9_-]{3,16}$/;

    let reg = regex.test(input.value.trim());
    error.style.display = reg ? "none" : "block";
    if (reg)
        fetch(`/check-username?username=${encodeURIComponent(input.value)}`)
            .then(res => res.text())
            .then(res => {availabilityError.style.display = (res === "true" ? "none" : "block")});
    else
        availabilityError.style.display = "none";
}

function validatePass(input) {
    const error = document.getElementById("passwordError");
    const regex = /^(?=.*[!@#$%^&*(),.?":{}|<>_])[a-zA-Z0-9!@#$%^&*(),.?":{}|<>_]{8,16}$/;
    error.style.display = regex.test(input.value.trim()) ? "none" : "block";
}

function validateRepPass(input) {
    const error = document.getElementById("repPasswordError");
    const original = document.getElementById("password");
    error.style.display = (input.value === original.value ? "none" : "block");
}

function validateEmail(input) {
    const error = document.getElementById("emailError");
    const availabilityError = document.getElementById('emailAlreadyExistError');
    const regex = /^(?=.{1,254}$)[a-zA-Z0-9](?!.*?[.]{2})[a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9](?!.*--)[a-zA-Z0-9.-]{0,253}\.[a-zA-Z]{2,}$/;

    let reg = regex.test(input.value.trim());
    error.style.display = reg ? "none" : "block";
    if (reg)
        fetch(`/check-email?email=${encodeURIComponent(input.value)}`)
            .then(res => res.text())
            .then(res => {availabilityError.style.display = (res === "true" ? "none" : "block")});
    else
        availabilityError.style.display = "none";
}

function validateCell(input) {
    const error = document.getElementById("cellError");
    const regex = /^\+?[0-9]{9,15}$/;
    error.style.display = regex.test(input.value) ? "none" : "block";
}