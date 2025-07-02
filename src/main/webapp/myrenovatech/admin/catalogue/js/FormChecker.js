const brandInput = document.getElementById("brand");
const modelInput = document.getElementById("model");
const brandError = document.getElementById("brand-error");
const modelError = document.getElementById("model-error");

brandInput.addEventListener("input", () => checkGeneral(brandInput, brandError, /^[\p{L}\p{N} .,'\-_&+!()]{1,30}$/u));
modelInput.addEventListener("input", () => checkGeneral(modelInput, modelError, /^[\p{L}\p{N} .,'\-_&+!()]{1,50}$/u));

function checkGeneral(input, errorDiv, regex) {
    if (regex.test(input.value))
        errorDiv.style.display = "none";
    else
        errorDiv.style.display = "block";
}

function checkStorageStock(input, errorDiv) {
    checkGeneral(input, errorDiv, /^[0-9]+$/);
}

function checkPrice(input, errorDiv) {
    checkGeneral(input, errorDiv, /^\d+(\.\d{1,2})?$/);
}