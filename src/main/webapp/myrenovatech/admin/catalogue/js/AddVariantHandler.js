const container = document.getElementById("add-variant-column");
let variantIndex = 0;

document.getElementById("add-variant").addEventListener("click", () => addVariant());

function addVariant(color, storage, stock, price) {
    const wrapper = document.createElement("div");
    wrapper.classList.add("item");
    if (variantIndex === 0)
        wrapper.classList.add("first");

    variantIndex++;

    wrapper.innerHTML = `
        <label>${variantTitle} ${variantIndex}</label>
        <div class="variant-inputs">
            <input type="color" name="variantColor[]" ${color ? addValue(color) : ''} />
            <input type="number" name="variantStorage[]" placeholder="128 GB" min="1" ${storage ? addValue(storage) : ''} />
            <input type="number" name="variantStock[]" placeholder="10 pz" min="1" ${stock ? addValue(stock) : ''} />
            <input type="number" name="variantPrice[]" placeholder="999,99€" step="0.01" min="0.01" ${price ? addValue(price) : ''} />
            <button type="button" class="remove-btn"></button>
        </div>
    `;

    wrapper.querySelector(".remove-btn").addEventListener("click", () => removeVariant(wrapper));

    container.appendChild(wrapper);
}

function addValue(value) {
    return `value="${value}"`;
}

function removeVariant(wrapper) {
    if (variantIndex <= 1) {
        showPopup(popUpTitleNoVariants, popUpMessageNoVariants);
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
    addVariant();
}

document.addEventListener("DOMContentLoaded", () => {
    addVariant();
});