const variants = document.querySelectorAll(".variant");
const mainPrice = document.getElementById("main-view-price");
const discountPerc = document.getElementById("discount-perc");
const actualPrice = document.getElementById("actual-price");
const actualWrapper = document.querySelector(".actual-price-wrapper");
const stockInfoMain = document.getElementById("stock-info-main");
const stockInfoCheckout = document.getElementById("stock-info-checkout");
const checkoutPrice = document.getElementById("checkout-view-price");
const qtySelect = document.getElementById("qty");
const pVarIdInput = document.querySelector("#p-var-id");

function formatPrice(price) {
    const parts = price.toFixed(2).split('.');
    let integerPart = parts[0];
    const decimalPart = parts[1];

    integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

    return '€' + integerPart + ',' + decimalPart;
}

variants.forEach((v) => v.addEventListener("click", () => {
    variants.forEach((el) => el.classList.remove("selected"));
    v.classList.add("selected");

    pVarIdInput.value = v.dataset.id;
    console.log(pVarIdInput.value);

    const price = parseFloat(v.dataset.price);
    const salePrice = parseFloat(v.dataset.salePrice);
    const discount = v.dataset.discount === "true";
    const discountValue = v.dataset.discountPerc;
    const stock = parseInt(v.dataset.stock);

    if (discount) {
        discountPerc.style.display = "";
        discountPerc.textContent = `-${discountValue}%`;
        actualWrapper.style.display = "";
        actualPrice.textContent = formatPrice(price);
        mainPrice.textContent = formatPrice(salePrice);
        checkoutPrice.textContent = formatPrice(salePrice);
    } else {
        discountPerc.style.display = "none";
        actualWrapper.style.display = "none";
        mainPrice.textContent = formatPrice(price);
        checkoutPrice.textContent = formatPrice(price);
    }

    const stockText = stock > 10 ? available : availableOnly + stock;
    stockInfoMain.textContent = stockText;
    stockInfoCheckout.textContent = stockText;

    stockInfoMain.classList.toggle("oos", stock < 1);
    stockInfoCheckout.classList.toggle("oos", stock < 1);

    qtySelect.innerHTML = "";
    if (stock < 1) {
        qtySelect.innerHTML = `<option value="0">0</option>`;
    } else {
        for (let i = 1; i <= stock; i++) {
            const opt = document.createElement("option");
            opt.value = i;
            opt.textContent = i;
            qtySelect.appendChild(opt);
        }
    }
}));

// Parte checkout
const inputAction = document.querySelector("#post-action");

document.querySelector("#add-to-cart").addEventListener("click", () => inputAction.value = "addToCart");
document.querySelector("#buy-now").addEventListener("click", () => inputAction.value = "buyNow");