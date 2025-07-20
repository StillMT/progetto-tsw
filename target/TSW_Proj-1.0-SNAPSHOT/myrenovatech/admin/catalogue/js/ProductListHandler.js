const actionType = document.getElementById("action");
const noEls = document.createElement("div");
noEls.id = "no-products-message";
noEls.textContent = noProductsInList;

const form = document.querySelector("form");
const header = document.querySelector(".add-product-header");
const title = document.querySelector(".add-product-title");
const descInput = document.getElementById("description");
const categoryInput = document.getElementById("category");
const submitBtn = form.querySelector('input[type="submit"]');

let hidden;

document.addEventListener("DOMContentLoaded", () => {
    addVariant();
    document.querySelector(".product-list-container").appendChild(noEls);
});

document.getElementById("refresh-list").addEventListener("click", refreshProductList);

async function refreshProductList() {
    const container = document.querySelector(".product-list-container");
    container.querySelectorAll(".product-row").forEach(el => el.remove());
    const divNoEls = container.querySelector("#no-products-message");
    if (divNoEls) divNoEls.remove();

    try {
        const res = await fetch("/myrenovatech/admin/products-handler?action=getList");
        const { products } = await res.json();

        if (products.length === 0) {
            container.appendChild(noEls);
            return;
        }

        products.forEach(p => container.appendChild(createProductRow(p)));
    } catch (err) {
        console.error("AJAX error:", err);
    }
}

function createProductRow(p) {
    const row = document.createElement("div");
    row.className = "product-row";

    const variantHTML = p.variants.map(v => `
        <div class="variant-pill" style="background-color: ${v.color}; color: ${getTextColor(v.color)}">
            ${v.storage} GB – ${v.stock} pz. – €${v.price.toFixed(2)}
        </div>
    `).join('');

    row.innerHTML = `
        <span>${escapeHtml(p.brand)}</span>
        <span>${escapeHtml(p.model)}</span>
        <span class="truncated-description" title="${escapeHtml(p.description)}">${escapeHtml(p.description)}</span>
        <span class="variant-boxes">${variantHTML}</span>
        <span class="btns-wrapper">
            <a href="/products/?prodId=${p.id}" target="_blank"><button class="view-btn">${viewBtn}</button></a>
            <button class="edit-btn" data-id="${p.id}"
                data-brand="${escapeHtml(p.brand)}"
                data-model="${escapeHtml(p.model)}"
                data-description="${escapeHtml(p.description)}"
                data-category="${p.id_category}"
                data-variants='${encodeURIComponent(JSON.stringify(p.variants))}'>
                ${headerTitleEdit}
            </button>
            <button data-id="${p.id}" class="del-btn">${deletePhrase}</button>
        </span>
    `;

    row.querySelector(".edit-btn").addEventListener("click", function () {
        editProduct(this.dataset);
    });

    row.querySelector(".del-btn").addEventListener("click", el => handleDelete(el.currentTarget));

    return row;
}

function escapeHtml(str) {
    return str.replace(/&/g, "&amp;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
}

function getTextColor(bgColor) {
    const r = parseInt(bgColor.slice(1, 3), 16);
    const g = parseInt(bgColor.slice(3, 5), 16);
    const b = parseInt(bgColor.slice(5, 7), 16);
    const luminance = 0.299 * r + 0.587 * g + 0.114 * b;
    return luminance > 140 ? "#000000" : "#ffffff";
}

function editProduct(data) {
    document.querySelectorAll(".form-error").forEach(el => el.style.display = "none");
    brandInput.value = data.brand;
    modelInput.value = data.model;
    descInput.value = data.description;
    categoryInput.value = data.category;

    cleanAllVariants();
    const variants = JSON.parse(decodeURIComponent(data.variants));
    variants.forEach(v => addVariant(v.id, v.color, v.storage, v.stock, v.price, v.salePrice, v.salePercentage, v.saleExpireDate));
console.log(variants);
    submitBtn.value = title.textContent = `${headerTitleEdit} ${headerTitleProduct}`;

    let closeBtn = document.getElementById("closeEditMode");
    if (!closeBtn) {
        closeBtn = document.createElement("button");
        closeBtn.id = "closeEditMode";
        closeBtn.classList.add("remove-btn");
        closeBtn.addEventListener("click", () => closeEdit(closeBtn));
        header.appendChild(closeBtn);
    }

    hidden = document.getElementById("productIdHidden") || document.createElement("input");
    hidden.type = "hidden";
    hidden.id = "productIdHidden";
    hidden.name = "productId";
    hidden.value = data.id;
    form.appendChild(hidden);
    actionType.value = "edit";

    loadExistingImages(data.id);
}

function closeEdit(callBtn) {
    if (hidden) form.removeChild(hidden);
    header.removeChild(callBtn);
    submitBtn.value = title.textContent = `${headerTitleAdd} ${headerTitleProduct}`;
    brandInput.value = modelInput.value = descInput.value = "";
    categoryInput.selectedIndex = 0;
    cleanAllVariants();
    addVariant();
    cleanFileList();
    actionType.value = "add";
}

function handleDelete(btn) {
    const row = btn.closest(".product-row");
    const editBtn = row.querySelector(".edit-btn");

    async function deleteProduct(id) {
        try {
            const res = await fetch(`/myrenovatech/admin/products-handler?action=deleteProduct&id=${id}`);
            const json = await res.json();
            if (json.result) row.remove();
            else resetControls();
        } catch (err) {
            console.error("AJAX error:", err);
            resetControls();
        }
    }

    function resetControls() {
        if (editBtn) editBtn.disabled = false;
        btn.disabled = false;
        btn.removeAttribute("style");
        const extraDelBtn = btn.parentElement.querySelector(".remove-btn");
        if (extraDelBtn) extraDelBtn.remove();
    }

    if (btn.style.backgroundColor === "red") {
        btn.disabled = true;
        if (editBtn) editBtn.disabled = true;
        const rmBtn = btn.parentElement.querySelector(".remove-btn");
        if (rmBtn) rmBtn.disabled = true;
        deleteProduct(btn.dataset.id);
    } else {
        const wrapper = btn.closest("span");
        btn.style.backgroundColor = "red";
        const confirmBtn = document.createElement("input");
        confirmBtn.type = "button";
        confirmBtn.classList.add("remove-btn");
        confirmBtn.addEventListener("click", ev => {
            wrapper.removeChild(ev.currentTarget);
            btn.removeAttribute("style");
        });
        wrapper.appendChild(confirmBtn);
    }
}