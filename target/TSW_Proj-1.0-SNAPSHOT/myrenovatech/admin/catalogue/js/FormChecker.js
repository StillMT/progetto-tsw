let variantFields = [];

const brandInput = document.getElementById("brand");
const modelInput = document.getElementById("model");
const brandError = document.getElementById("brand-error");
const modelError = document.getElementById("model-error");

const brandRegex = /^[\p{L}\p{N} .,'\-_&+!()]{1,30}$/u;
const modelRegex = /^[\p{L}\p{N} .,'\-_&+!()]{1,50}$/u;

brandInput.addEventListener("input", () => checkGeneral(brandInput, brandError, brandRegex));
modelInput.addEventListener("input", () => checkGeneral(modelInput, modelError, modelRegex));

function checkGeneral(input, errorDiv, regex) {
    const isValid = regex.test(input.value);
    errorDiv.style.display = isValid ? "none" : "block";
    return isValid;
}

function checkStorageStock(input, errorDiv) {
    return checkGeneral(input, errorDiv, /^[0-9]+$/);
}

function checkPrice(input, errorDiv) {
    return checkGeneral(input, errorDiv, /^\d+(\.\d{1,2})?$/);
}

function checkPercentage(input, errorDiv) {
    return checkGeneral(input, errorDiv, /^(100|\d{1,2})$/);
}

function checkDate(input, errorDiv) {
    const isValid = input.value.trim() !== "";
    errorDiv.style.display = isValid ? "none" : "block";
    return isValid;
}

function checkAllFields() {
    let result = true;
    for (let i = 0; i < variantFields.length; i += 2) {
        const input = variantFields[i];
        const error = variantFields[i + 1];
        switch (input.name) {
            case "variantStorage[]":
            case "variantStock[]":
                result = checkStorageStock(input, error) && result;
                break;
            case "variantPrice[]":
            case "variantSalePrice[]":
                result = checkPrice(input, error) && result;
                break;
            case "variantSalePercentage[]":
                result = checkPercentage(input, error) && result;
                break;
            case "variantSaleDate[]":
                result = checkDate(input, error) && result;
                break;
        }
    }
    result = checkGeneral(brandInput, brandError, brandRegex) && result;
    result = checkGeneral(modelInput, modelError, modelRegex) && result;
    return result;
}