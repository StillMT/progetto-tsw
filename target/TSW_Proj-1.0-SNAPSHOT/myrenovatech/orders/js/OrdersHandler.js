(async function () {
    const requestUri = "/myrenovatech/orders/orderServlet";

    const orderState = {
        "CANCELED" : canceled,
        "TO_SHIP" : toShip,
        "SHIPPED" : shipped,
        "DELIVERED" : delivered
    };

    function formatPrice(price) {
        const parts = price.toFixed(2).split('.');
        let integerPart = parts[0];
        const decimalPart = parts[1];

        integerPart = integerPart.replace(/\B(?=(\d{3})+(?!\d))/g, '.');

        return '€' + integerPart + ',' + decimalPart;
    }

    const noEls = document.createElement("div");
    noEls.classList.add("no-products-message");
    noEls.textContent = noElements;

    const list = document.getElementById("list");

    const json = await (await fetch(requestUri + "?action=getOrders")).json();

    populateList();

    document.getElementById("search-bar").addEventListener("input", e => populateList(e.currentTarget.value));

    function populateList(searchInput) {
        cleanList();

        let count = 0;
        json.forEach(o => {
            if (searchInput) {
                const lowerInput = searchInput.toLowerCase();
                if (o.nr.includes(lowerInput) || o.date.includes(lowerInput) || String(o.totalPrice).includes(lowerInput)
                    || o.address.includes(lowerInput) || orderState[o.order_state].includes(lowerInput) || o.tracking.includes(lowerInput)) {
                    addRow(o);
                    count++;
                }
            } else {
                addRow(o);
                count++;
            }
        });

        if (count === 0)
            list.appendChild(noEls.cloneNode(true));
    }

    function addRow(el) {
        const row = document.createElement("div");
        row.className = "row";
        row.innerHTML = `
            <span>${el.nr}</span>
            <span>${el.date}</span>
            <span>${formatPrice(el.totalPrice)}</span>
            <span>${el.address}</span>
            <span>${orderState[el.order_state]}</span>
            <span>
                <a href="view?nr=${el.nr}"><button class="view-order">${viewOrder}</button></a>
                ${el.tracking ? `<a href="https://t.17track.net/${lang}#nums=${el.tracking}" target="_blank"><button class="view-order">${trackOrder}</button></a>` : ""}
            </span>
        `;
        list.appendChild(row);
    }

    function cleanList() {
        list.querySelectorAll(".row").forEach((e) => e.remove());
        const noElsDiv = list.querySelector(".no-products-message");
        if (noElsDiv)
            noElsDiv.remove();
    }

})();