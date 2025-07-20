const container = document.getElementById("add-variant-column");
let variantIndex = 0;

function addValue(value) {
    return `value="${value}"`;
}

document.getElementById("add-variant").addEventListener("click", () => addVariant());

function addVariant(id, color, storage, stock, price, salePrice, salePercentage, saleExpireDate) {
    function createInputSpan(label, type, name, className, placeholder, attributes) {
        return `<span>${label}<input type="${type}" name="${name}" class="${className}" placeholder="${placeholder}" ${attributes}/></span>`;
    }

    const wrapper = document.createElement("div");
    wrapper.classList.add("item");
    if (variantIndex === 0) wrapper.classList.add("first");
    variantIndex++;

    wrapper.innerHTML = `
        <div class="variant-label-box">
            <label>${variantTitle} ${variantIndex}</label>
            <button type="button" class="remove-btn"></button>
        </div>
        <div class="variant-inputs">
            ${createInputSpan(colorLabel, "color", "variantColor[]", "", "", color ? addValue(color) : "")}
            ${createInputSpan(storageLabel, "number", "variantStorage[]", "storage", "128 GB", storage !== null ? addValue(storage) : "min=1")}
            <div class="form-error error-storage"><p>${storageError}</p></div>
            ${createInputSpan(stockLabel, "number", "variantStock[]", "stock", "10 pz", 'min="1"' + (stock !== null ? addValue(stock) : ""))}
            <div class="form-error error-stock"><p>${stockError}</p></div>
            ${createInputSpan(priceLabel, "number", "variantPrice[]", "price", "999,99€", 'step="0.01" min="0.00"' + (price !== null ? addValue(price) : ""))}
            <div class="form-error error-price"><p>${priceError}</p></div>
            ${createInputSpan(salePercLabel, "number", "variantSalePercentage[]", "sale-percentage", "20%", 'step="1" min="0" max="100"' + (salePercentage !== null ? addValue(salePercentage) : ""))}
            <div class="form-error error-sale-perc"><p>${percError}</p></div>
            ${createInputSpan(salePriceLabel, "number", "variantSalePrice[]", "sale-price", "999,99€", 'step="0.01" min="0.00"' + (salePrice !== null ? addValue(salePrice) : ""))}
            <div class="form-error error-sale-price"><p>${priceError}</p></div>
            ${createInputSpan(saleExpDateLabel, "datetime-local", "variantSaleDate[]", "sale-date", "", saleExpireDate !== null ? addValue(saleExpireDate) : "")}
            <div class="form-error error-sale-date"><p>${dateError}</p></div>
            ${id ? `<input type="hidden" name="variantId[]" value="${id}" />` : ""}
        </div>
    `;

    wrapper.querySelector(".remove-btn").addEventListener("click", () => removeVariant(wrapper));

    const storageInput = wrapper.querySelector(".storage");
    const stockInput = wrapper.querySelector(".stock");
    const priceInput = wrapper.querySelector(".price");
    const salePercInput = wrapper.querySelector(".sale-percentage");
    const salePriceInput = wrapper.querySelector(".sale-price");
    const saleDateInput = wrapper.querySelector(".sale-date");

    const storageErrorDiv = wrapper.querySelector(".error-storage");
    const stockErrorDiv = wrapper.querySelector(".error-stock");
    const priceErrorDiv = wrapper.querySelector(".error-price");
    const salePercErrorDiv = wrapper.querySelector(".error-sale-perc");
    const salePriceErrorDiv = wrapper.querySelector(".error-sale-price");
    const saleDateErrorDiv = wrapper.querySelector(".error-sale-date");

    variantFields.push(
        storageInput, storageErrorDiv,
        stockInput, stockErrorDiv,
        priceInput, priceErrorDiv,
        salePercInput, salePercErrorDiv,
        salePriceInput, salePriceErrorDiv,
        saleDateInput, saleDateErrorDiv
    );

    storageInput.addEventListener("input", () => checkStorageStock(storageInput, storageErrorDiv));
    stockInput.addEventListener("input", () => checkStorageStock(stockInput, stockErrorDiv));
    priceInput.addEventListener("input", () => checkPrice(priceInput, priceErrorDiv));
    salePercInput.addEventListener("input", () => checkPercentage(salePercInput, salePercErrorDiv));
    salePriceInput.addEventListener("input", () => checkPrice(salePriceInput, salePriceErrorDiv));
    saleDateInput.addEventListener("input", () => checkDate(saleDateInput, saleDateErrorDiv));

    container.appendChild(wrapper);
}

function removeVariant(wrapper) {
    if (variantIndex <= 1) return showPopup(popUpTitle, popUpMessageNoVariants);
    wrapper.remove();
    container.querySelector("div.item").classList.add("first");
    let counter = 1;
    container.querySelectorAll("div.item label").forEach(el => el.textContent = `${variantTitle} ${counter++}`);
    variantIndex = counter - 1;
}

function cleanAllVariants() {
    container.innerHTML = "";
    variantIndex = 0;
    variantFields = [];
}