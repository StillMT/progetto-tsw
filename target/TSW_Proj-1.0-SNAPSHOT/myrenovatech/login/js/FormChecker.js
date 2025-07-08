let countrySelected = false;

const countrySelect = document.querySelector("select");
if (countrySelect) {
    function countryToggler() {
        countrySelected = true;
        countrySelect.removeEventListener("change", countryToggler);
    }

    countrySelect.addEventListener("change", countryToggler);
}

const registerInputs = document.querySelectorAll("#register input");
registerInputs.forEach(input => {
    input.addEventListener('input', () => checkInput(input));
});

document.getElementById("register").addEventListener("submit", async function (ev) {
    ev.preventDefault();
    let formOk = true;

    for (const input of registerInputs) {
        await checkInput(input);
    }

    for (const el of document.querySelectorAll(".form-error")) {
        if (el.style.display === "block") {
            showPopup(popupTitle, popupMessageInvalidFields);
            formOk = false;
            return;
        }
    }

    if (!countrySelected) {
        showPopup(popupTitle, popupMessageCountrySure);
        countrySelected = true;
        return;
    }

    ev.target.submit();
});

async function checkInput(input) {
    switch (input.name) {
        case 'fullName': validateName(input); break;
        case 'username': await validateUsername(input); break;
        case 'pass': validatePass(input); break;
        case 'rep-pass': validateRepPass(input); break;
        case 'email': await validateEmail(input); break;
        case 'cell': await validateCell(input); break;
    }
}

function toggleVisibility(element, show) {
    element.style.display = show ? "block" : "none";
}

function validateName(input) {
    const error = document.getElementById("fullNameError");
    const regex = /^(?=.{1,50}$)\S+(?:\s+\S+)+$/;
    toggleVisibility(error, !regex.test(input.value.trim()));
}

function validatePass(input) {
    const error = document.getElementById("passwordError");
    const regex = /^(?=.*[!@#$%^&*(),.?":{}|<>_])[a-zA-Z0-9!@#$%^&*(),.?":{}|<>_]{8,16}$/;
    toggleVisibility(error, !regex.test(input.value.trim()));
}

function validateRepPass(input) {
    const error = document.getElementById("repPasswordError");
    const original = document.getElementById("password");
    toggleVisibility(error, input.value !== original.value);
}

async function validateUsername(input) {
    const error = document.getElementById("usernameError");
    const availabilityError = document.getElementById("usernameAlreadyExistError");
    const regex = /^[a-zA-Z0-9_-]{3,16}$/;

    const value = input.value.trim();
    const valid = regex.test(value);

    toggleVisibility(error, !valid);
    toggleVisibility(availabilityError, valid && !(await checkAJAXField("/check-username?username=", value)));
}

async function validateEmail(input) {
    const error = document.getElementById("emailError");
    const availabilityError = document.getElementById("emailAlreadyExistError");
    const regex = /^(?=.{1,254}$)[a-zA-Z0-9](?!.*?[.]{2})[a-zA-Z0-9._%+-]{0,63}@[a-zA-Z0-9](?!.*--)[a-zA-Z0-9.-]{0,253}\.[a-zA-Z]{2,}$/;

    const value = input.value.trim();
    const valid = regex.test(value);

    toggleVisibility(error, !valid);
    toggleVisibility(availabilityError, valid && !(await checkAJAXField("/check-email?email=", value)));
}

async function validateCell(input) {
    const error = document.getElementById("cellError");
    const availabilityError = document.getElementById("cellAlreadyExistError");
    const regex = /^\+?[0-9]{9,15}$/;

    const value = input.value.trim();
    const valid = regex.test(value);

    toggleVisibility(error, !valid);
    toggleVisibility(availabilityError, valid && !(await checkAJAXField("/check-phone?phone=", value)));
}

async function checkAJAXField(url, str) {
    try {
        const res = await fetch(`${url}${encodeURIComponent(str)}`);
        const data = await res.json();
        return data.result;
    } catch (err) {
        console.error("AJAX error:", err);
        return false;
    }
}