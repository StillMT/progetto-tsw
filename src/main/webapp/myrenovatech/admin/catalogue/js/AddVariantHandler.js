const container = document.getElementById("add-variant-column");
let variantIndex = 0;

document.getElementById("add-variant").addEventListener("click", () => addVariant());

function addVariant(id, color, storage, stock, price) {
    function addValue(value) {
        return `value="${value}"`;
    }

    const wrapper = document.createElement("div");
    wrapper.classList.add("item");
    if (variantIndex === 0)
        wrapper.classList.add("first");

    variantIndex++;

    wrapper.innerHTML = `
        <div class="variant-label-box">
            <label>${variantTitle} ${variantIndex}</label>
            <button type="button" class="remove-btn"></button>
        </div>
        <div class="variant-inputs">
            <input type="color" name="variantColor[]" ${color ? addValue(color) : ''} />
            <input type="number" id="storage-${variantIndex}" name="variantStorage[]" placeholder="128 GB" min="1" ${storage ? addValue(storage) : ''} />
            <div class="form-error" id="error-storage-${variantIndex}">
                <p>${storageError}</p>
            </div>
            
            <input type="number" id="stock-${variantIndex}" name="variantStock[]" placeholder="10 pz" min="1" ${stock ? addValue(stock) : ''} />
            <div class="form-error" id="error-stock-${variantIndex}">
                <p>${stockError}</p>
            </div>
            
            <input type="number" id="price-${variantIndex}" name="variantPrice[]" placeholder="999,99€" step="0.01" min="0.01" ${price ? addValue(price) : ''} />
            <div class="form-error" id="error-price-${variantIndex}">
                <p>${priceError}</p>
            </div>
            
            ${id ? `<input type="hidden" name="variantId[]" value="${id}" />` : ''}
        </div>
    `;

    wrapper.querySelector(".remove-btn").addEventListener("click", () => removeVariant(wrapper));

    const storageInput = wrapper.querySelector(`#storage-${variantIndex}`);
    const stockInput = wrapper.querySelector(`#stock-${variantIndex}`);
    const priceInput = wrapper.querySelector(`#price-${variantIndex}`);
    const storageErrorDiv = wrapper.querySelector(`#error-storage-${variantIndex}`);
    const stockErrorDiv = wrapper.querySelector(`#error-stock-${variantIndex}`);
    const priceErrorDiv = wrapper.querySelector(`#error-price-${variantIndex}`);

    storageInput.addEventListener("input", () => checkStorageStock(storageInput, storageErrorDiv));
    stockInput.addEventListener("input", () => checkStorageStock(stockInput, stockErrorDiv));
    priceInput.addEventListener("input", () => checkPrice(priceInput, priceErrorDiv));

    container.appendChild(wrapper);
}

function removeVariant(wrapper) {
    if (variantIndex <= 1) {
        showPopup(popUpTitle, popUpMessageNoVariants);
        return;
    }

    wrapper.remove();
    container.querySelector("div.item").classList.add("first");

    let counter = 1;
    container.querySelectorAll("div.item label").forEach(el => el.textContent = variantTitle + " " + counter++);
    variantIndex = counter - 1;
}

function cleanAllVariants() {
    container.innerHTML = "";
    variantIndex = 0;
}

document.addEventListener("DOMContentLoaded", () => {
    addVariant();
});