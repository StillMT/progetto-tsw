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
    if (regex.test(input.value)) {
        errorDiv.style.display = "none";
        return true;
    }
    else {
        errorDiv.style.display = "block";
        return false;
    }
}

function checkStorageStock(input, errorDiv) {
    return checkGeneral(input, errorDiv, /^[0-9]+$/);
}

function checkPrice(input, errorDiv) {
    return checkGeneral(input, errorDiv, /^\d+(\.\d{1,2})?$/);
}

function checkAllFields() {
    let result = true;

    for (let i = 0; i < variantFields.length; i += 2) {

        switch (variantFields[i].name) {
            case "variantStorage[]":
            case "variantStock[]":
                result = checkStorageStock(variantFields[i], variantFields[i + 1]);
                break;

            case "variantPrice[]":
                result = checkPrice(variantFields[i], variantFields[i + 1]);
                break;

            default:
                result = false;
                break;
        }
    }

    if (!checkGeneral(brandInput, brandError, brandRegex))
        result = false;
    if (!checkGeneral(modelInput, modelError, modelRegex))
        result = false;

    return result;
}