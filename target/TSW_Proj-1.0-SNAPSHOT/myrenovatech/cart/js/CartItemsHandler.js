const SERVLET_URL = "/myrenovatech/cart/cartServlet";

initCartHandlers();
updateCartSummary();

function initCartHandlers() {
    document.querySelectorAll(".qty-cart-item").forEach(input => {
        input.addEventListener("change", async (e) => {
            const cartItem = e.target.closest(".cart-item");
            const cartId = cartItem.dataset.id;
            const newQty = parseInt(e.target.value);

            if (!isNaN(newQty) && newQty >= 0) {
                setLoading(cartItem, true);
                const res = await fetch(`${SERVLET_URL}?action=updateQty&cartId=${cartId}&qty=${newQty}`);
                const json = await res.json();
                setLoading(cartItem, false);
                if (json.result) {
                    cartItem.dataset.qty = newQty;
                    updateCartSummary();
                } else
                    console.log("Qt err:", json);
            }
        });
    });

    document.querySelectorAll(".remove-item").forEach(link => {
        link.addEventListener("click", async (e) => {
            const cartItem = e.target.closest(".cart-item");
            const cartId = cartItem.dataset.id;

            setLoading(cartItem, true);
            const res = await fetch(`${SERVLET_URL}?action=remove&cartId=${cartId}`);
            const json = await res.json();
            setLoading(cartItem, false);

            if (json.result) {
                cartItem.remove();
                updateCartSummary();
                if (document.querySelectorAll(".cart-item").length === 0)
                    document.querySelector(".cart-wrapper-items").innerHTML = `<div class="no-cart-items">${emptyCart}</div>`;
            } else
                console.log("Del prod err:", json);
        });
    });

    document.querySelectorAll(".checkbox-cart-selected").forEach(checkbox => {
        checkbox.addEventListener("change", async (e) => {
            const cartItem = e.target.closest(".cart-item");
            const cartId = cartItem.dataset.id;
            const selected = e.target.checked;

            setLoading(cartItem, true);
            const res = await fetch(`${SERVLET_URL}?action=toggleSelection&cartId=${cartId}&selected=${selected}`);
            const json = await res.json();
            setLoading(cartItem, false);

            if (json.result)
                updateCartSummary();
            else
                console.log("check err:", json);
        });
    });
}

function updateCartSummary() {
    const items = document.querySelectorAll(".cart-item");
    let total = 0;
    let count = 0;

    items.forEach(item => {
        const selected = item.querySelector(".checkbox-cart-selected").checked;
        if (selected) {
            const qty = parseInt(item.dataset.qty);
            const price = parseFloat(item.dataset.price);
            if (!isNaN(qty) && !isNaN(price)) {
                total += qty * price;
                count += qty;
            }
        }
    });

    document.getElementById("cart-total-price").textContent = formatPrice(total);
    document.querySelector(".cart-wrapper-summary span").innerHTML = `${provisionalTotal} (${count} ${articles}):`;
}

function formatPrice(price) {
    const parts = price.toFixed(2).split('.');
    let integerPart = parts[0];
    const decimalPart = parts[1];
    integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
    return '€' + integerPart + ',' + decimalPart;
}

function setLoading(cartItem, isLoading) {
    if (isLoading) {
        if (!cartItem.querySelector(".cart-item-overlay")) {
            const overlay = document.createElement("div");
            overlay.className = "cart-item-overlay";
            cartItem.appendChild(overlay);
        }
    } else {
        const overlay = cartItem.querySelector(".cart-item-overlay");
        if (overlay) overlay.remove();
    }
}