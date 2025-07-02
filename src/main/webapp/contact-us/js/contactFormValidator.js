document.addEventListener("DOMContentLoaded", function () {
    const form = document.querySelector("form");
    if (!form) return;

    const name = document.getElementById("name");
    const email = document.getElementById("email");
    const cell = document.getElementById("cell");
    const object = document.getElementById("object");
    const description = document.getElementById("description");
    const orderId = document.getElementById("orderId");
    const country = document.querySelector("select[name='country']") || document.querySelector("select#country");

    function showError(id, field = null) {
        const el = document.getElementById(id);
        if (el) el.style.display = "block";
        if (field) field.classList.add("input-error");
    }

    function hideError(id, field = null) {
        const el = document.getElementById(id);
        if (el) el.style.display = "none";
        if (field) field.classList.remove("input-error");
    }

    const regexName = /^[A-Z][a-zà-ÿ']+\s[A-Z][a-zà-ÿ']+$/;
    const regexEmail =/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;

    const regexCell = /^\d{1,11}$/;
    const regexOrder = /^\d{3}-\d{3}-\d{4}$/;

    name.addEventListener("input", () => {
        const trimmed = name.value.trim();
        if (!regexName.test(trimmed) || name.value !== trimmed) {
            showError("nameError", name);
        } else {
            hideError("nameError", name);
        }
    });

    email.addEventListener("input", () => {
        if (!regexEmail.test(email.value.trim())) {
            showError("emailError", email);
        } else {
            hideError("emailError", email);
        }
    });

    cell.addEventListener("input", () => {
        const val = cell.value.trim();
        if (val && !regexCell.test(val)) {
            showError("cellError", cell);
        } else {
            hideError("cellError", cell);
        }
    });

    orderId.addEventListener("input", () => {
        if (!regexOrder.test(orderId.value.trim())) {
            showError("orderIdError", orderId);
        } else {
            hideError("orderIdError", orderId);
        }
    });

    form.addEventListener("submit", function (e) {
        let isValid = true;

        document.querySelectorAll(".form-error").forEach(el => el.style.display = "none");

        if (!regexOrder.test(orderId.value.trim())) {
            isValid = false;
            showError("orderIdError", orderId);
        }

        const objVal = object.value.trim();
        if (!objVal || objVal.length > 20) {
            isValid = false;
            showError("objectError", object);
        }

        const descVal = description.value.trim();
        if (!descVal || descVal.length > 200) {
            isValid = false;
            showError("descriptionError", description);
        }

        if (!regexName.test(name.value.trim()) || name.value !== name.value.trim()) {
            isValid = false;
            showError("nameError", name);
        }

        if (!regexEmail.test(email.value.trim())) {
            isValid = false;
            showError("emailError", email);
        }

        const cellVal = cell.value.trim();
        if (cellVal && !regexCell.test(cellVal)) {
            isValid = false;
            showError("cellError", cell);
        }

        if (country && !country.value) {
            isValid = false;
            showError("countryError", country);
        }

        if (!isValid) e.preventDefault();
    });
});
