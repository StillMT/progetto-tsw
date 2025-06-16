const actionType = document.getElementById("action");

document.getElementById("refresh-list").addEventListener("click", async function () {
    function getTextColor(bgColor) {
        const r = parseInt(bgColor.slice(1, 3), 16);
        const g = parseInt(bgColor.slice(3, 5), 16);            // Valori da StackOverflow
        const b = parseInt(bgColor.slice(5, 7), 16);
        const luminance = (0.299 * r + 0.587 * g + 0.114 * b);
        return luminance > 140 ? "#000000" : "#ffffff";
    }

    try {
        const res = await fetch("/myrenovatech/admin/products-handler?action=getList");
        const products = await res.json();
        const container = document.querySelector(".product-list-container");
        container.querySelectorAll(".product-row").forEach(el => el.remove());

        products.products.forEach(p => {
            const variantHTML = p.variants.map(v => `
                <div class="variant-pill" style="background-color: ${v.color}; color: ${getTextColor(v.color)}">
                    ${v.storage} GB – ${v.stock} pz. – €${v.price.toFixed(2)}
                </div>
            `).join('');

            const row = document.createElement("div");
            row.className = "product-row";
            row.innerHTML = `
                <span>${p.brand}</span>
                <span>${p.model}</span>
                <span class="truncated-description" title="${p.description}">${p.description}</span>
                <span class="variant-boxes">${variantHTML}</span>
                <span class="btns-wrapper">
                    <button class="edit-btn"
                        data-id="${p.id}"
                        data-brand="${p.brand}"
                        data-model="${p.model}"
                        data-description="${p.description}"
                        data-category="${p.id_category}"
                        data-variants='${encodeURIComponent(JSON.stringify(p.variants))}'>
                        ${headerTitleEdit}
                    </button>
                    <button data-id="${p.id}" class="del-btn">${deletePhrase}</button>
                </span>
            `;
            container.appendChild(row);

            row.querySelector(".edit-btn").addEventListener("click", function () {
                editProduct(this.dataset);
            });

            row.querySelector(".del-btn").addEventListener("click", el => handleDelete(el.currentTarget));
        });

    } catch (err) {
        console.error("AJAX error:", err);
    }
});

function handleDelete(btn) {
    const row = btn.closest(".product-row");
    const editBtn = row.querySelector(".edit-btn");

    function resetControls() {
        if (editBtn) editBtn.disabled = false;
        btn.disabled = false;
        btn.removeAttribute("style");
        const extraDelBtn = btn.parentElement.querySelector(".remove-btn");
        if (extraDelBtn) extraDelBtn.remove();
    }

    async function deleteProduct(id) {
        try {
            const res = await fetch(`/myrenovatech/admin/products-handler?action=deleteProduct&id=${id}`);
            console.log(res);
            const json = await res.json();
            console.log(json);

            if (json.result)
                row.remove();
            else
                resetControls();
        } catch (err) {
            console.error("AJAX error:", err);
            resetControls();
        }
    }

    if (btn.style.backgroundColor === "red") {
        btn.disabled = true;
        editBtn.disabled = true;
        btn.parentElement.querySelector(".remove-btn").disabled = true;

        deleteProduct(btn.dataset.id);
    } else {
        const wrapper = btn.closest("span");
        btn.style.backgroundColor = "red";

        const delBtn = document.createElement("input");
        delBtn.type = "button";
        delBtn.classList.add("remove-btn");
        wrapper.appendChild(delBtn);

        delBtn.addEventListener("click", ev => {
            wrapper.removeChild(ev.currentTarget);
            btn.removeAttribute("style");
        });
    }
}


const form = document.querySelector("form");
const header = document.querySelector(".add-product-header");
const title = document.querySelector(".add-product-title");
const brandInput = document.getElementById("brand");
const modelInput = document.getElementById("model");
const descInput = document.getElementById("description");
const categoryInput = document.getElementById("category");
const submitBtn = document.querySelector('input[type="submit"]');
const variantContainer = document.getElementById("add-variant-column");

let hidden;

function cleanAllVariants() {
    variantContainer.innerHTML = "";
    variantIndex = 0;
}

function editProduct(data) {
    function closeEdit(callBtn) {
        if (hidden)
            form.removeChild(hidden);
        header.removeChild(callBtn);
        submitBtn.value = title.textContent = `${headerTitleAdd} ${headerTitleProduct}`;
        brandInput.value = modelInput.value = descInput.value = "";
        categoryInput.selectedIndex = 0;
        cleanAllVariants();
        addVariant();
        cleanFileList();
        actionType.value = "add";
    }

    brandInput.value = data.brand;
    modelInput.value = data.model;
    descInput.value = data.description;
    categoryInput.value = data.category;

    cleanAllVariants();
    const variants = JSON.parse(decodeURIComponent(data.variants));
    variants.forEach(v => addVariant(v.id, v.color, v.storage, v.stock, v.price));

    submitBtn.value = title.textContent = `${headerTitleEdit} ${headerTitleProduct}`;

    let closeBtn = document.getElementById("closeEditMode");
    if (!closeBtn) {
        closeBtn = document.createElement("button");
        closeBtn.id = "closeEditMode";
        closeBtn.classList.add("remove-btn");
        closeBtn.addEventListener("click", () => closeEdit(closeBtn));
        header.appendChild(closeBtn);
    }

    hidden = document.getElementById("productIdHidden");
    if (!hidden) {
        hidden = document.createElement("input");
        hidden.type = "hidden";
        hidden.id = "productIdHidden";
        hidden.name = "productId";
    }
    hidden.value = data.id;
    form.appendChild(hidden);
    actionType.value = "edit";

    loadExistingImages(data.id);
}