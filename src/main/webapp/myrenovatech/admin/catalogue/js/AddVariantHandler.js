const container = document.getElementById("add-variant-column");
let variantIndex = 0;

document.getElementById("add-variant").addEventListener("click", () => addVariant());

function addVariant() {
    const wrapper = document.createElement("div");
    wrapper.classList.add("item");
    if (variantIndex === 0)
        wrapper.classList.add("first");

    variantIndex++;
    wrapper.innerHTML = '<label>Variante ' + variantIndex + '</label>' +
        '<input type="color" name="variantColor[]" />' +
        '<input type="number" name="variantStorage[]" placeholder="128 GB" min="1" />' +
        '<input type="number" name="variantStock[]" placeholder="10 pz" min="1" />' +
        '<input type="number" name="variantPrice[]" placeholder="999,99€" step="0.01" min="0.01" />';

    container.appendChild(wrapper);
}

document.addEventListener("DOMContentLoaded", () => {
    addVariant();
});