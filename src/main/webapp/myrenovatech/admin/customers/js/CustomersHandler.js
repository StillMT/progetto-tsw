const noEls = document.createElement("div");
noEls.classList.add("no-products-message");
noEls.textContent = noProductsInList;

const customerServletUrl = "/myrenovatech/admin/customers/customerServlet";

const customersList = document.getElementById("customers-list");
const ordersList = document.getElementById("orders-list");

const removeButton = document.getElementById("active-view-close-btn");
const activeView = document.getElementById("active-view");
const activeViewUser = document.getElementById("active-view-username");

const orderState = {
    "CANCELED" : canceled,
    "TO_SHIP" : toShip,
    "SHIPPED" : shipped,
    "DELIVERED" : delivered
}

let custJson = null;

document.addEventListener("DOMContentLoaded", async () => {
    addNoEls(customersList);
    addNoEls(ordersList);

    removeButton.addEventListener("click", () => {
        activeView.style.display = removeButton.style.display = "none";
        cleanList(ordersList);
        addNoEls(ordersList);
    })

    await refreshCustList();

    document.getElementById("cust-refresh-list").addEventListener("click", async () => await refreshCustList());

    document.getElementById("search-input").addEventListener("input", e => populateCustList(custJson, e.currentTarget.value));
});

async function refreshCustList() {
    try {
        const json = await (await fetch(`${customerServletUrl}?action=getCustomers`)).json();

        custJson = json;

        populateCustList(json);
    } catch (e) {
        console.error("Error:", e);
    }
}

function populateCustList(json, searchInput) {
    cleanList(customersList);

    let count = 0;
    json.forEach(e => {
        if (searchInput) {
            const lowerInput = searchInput.toLowerCase();
            if (String(e.id).includes(lowerInput) || e.fullName.toLowerCase().includes(lowerInput) ||
                e.username.toLowerCase().includes(lowerInput) || e.email.toLowerCase().includes(lowerInput) ||
                e.role.toLowerCase() === lowerInput) {
                addCustomerRow(e.id, e.fullName, e.username, e.email, e.role, e.isLimited);
                count++;
            }
        } else {
            addCustomerRow(e.id, e.fullName, e.username, e.email, e.role, e.isLimited);
            count++;
        }
    });

    if (count === 0)
        addNoEls(customersList);
}

function addOrderRow(nr, date, total, address, state, tracking) {
    const row = document.createElement("div");
    row.className = "row";
    row.innerHTML = `
            <span>${nr}</span>
            <span>${date}</span>
            <span>${total.toFixed(2)}</span>
            <span>${address}</span>
            <span>${state}</span>
            <span>
                <a href="#?nr=${nr}" target="_blank"><button class="view-order">${viewOrderButton}</button></a>
                ${tracking ? `<a href="https://t.17track.net/${lang}#nums=${tracking}" target="_blank"><button class="view-order">${trackOrder}</button></a>` : ""}
                ${state === orderState.TO_SHIP || state === orderState.SHIPPED ? `<div class="cancel-order-wrapper">
                    <button class="cancel-order" data-nr="${nr}">${cancelOrder}</button>
                    <button class="remove-btn"></button>
                </div>` : "" }
            </span>
            `;
    ordersList.appendChild(row);

    const cancelOrderButton = row.querySelector(".cancel-order");
    if (cancelOrderButton) {
        const wrapper = cancelOrderButton.closest(".cancel-order-wrapper");
        const closeBtn = cancelOrderButton.parentElement.querySelector("button.remove-btn");

        cancelOrderButton.addEventListener("click", async e => {
            if (cancelOrderButton.style.backgroundColor !== "red") {
                cancelOrderButton.style.backgroundColor = "red";
                wrapper.classList.add("active");
                closeBtn.style.display = "block";

                closeBtn.addEventListener("click",
                    () => resetLimitationControls(closeBtn, cancelOrderButton, wrapper), {once: true});
            } else {
                toggleButtons(ordersList);

                try {
                    const json = await (await fetch(`${customerServletUrl}?action=cancelOrder&orderNr=${e.currentTarget.dataset.nr}`)).json();

                    if (!json.result) {
                        showPopup(popUpTitle, popUpMessageCancelOrderFailure);
                        resetLimitationControls(closeBtn, cancelOrderButton, wrapper);
                    }
                    else {
                        row.children[4].textContent = orderState.CANCELED;
                        wrapper.remove();
                    }
                } catch (e) {
                    resetLimitationControls(closeBtn, cancelOrderButton, wrapper);
                    showPopup(popUpTitle, popUpMessageCancelOrderFailure);
                    console.error("Error:", e);
                }

                toggleButtons(ordersList);
            }
        });
    }
}

function addCustomerRow(id, fullName, username, email, role, isLimited) {
    const row = document.createElement("div");
    row.className = "row";
    row.innerHTML = `
        <span>${id}</span>
        <span>${fullName}</span>
        <span>${username}</span>
        <span>${email}</span>
        <span>${role}</span>
        <span>
            <button class="view-order" data-id="${id}" data-name="${username}">${viewOrdersButton}</button>
            ${role === "USER" ? `<div class="limit-user-wrapper">
                <button class="limit-user" data-id="${id}">${isLimited ? unlockUser : limitUser}</button>
                <button class="remove-btn"></button>
            </div>` : ""}
        </span>
    `;
    customersList.appendChild(row);

    row.querySelector("button.view-order").addEventListener("click", async e => {
        try {
            const id = e.currentTarget.dataset.id;
            const name = e.currentTarget.dataset.name;

            cleanList(ordersList);
            addNoEls(ordersList);

            toggleButtons(customersList);

            const json = await (await fetch(`${customerServletUrl}?action=getOrders&userId=${id}`)).json();
            cleanList(ordersList);
            json.forEach((el) => addOrderRow(el.nr, el.date, el.totalPrice, el.address, orderState[el.order_state], el.tracking));
            if (json.length === 0)
                addNoEls(ordersList);

            activeView.style.display = "inline";
            removeButton.style.display = "block";
            activeViewUser.textContent = name;
            toggleButtons(customersList);
        } catch (e) {
            console.error("Error:", e);
        }
    });

    const limitBtn = row.querySelector("button.limit-user");
    if (limitBtn) {
        const wrapper = limitBtn.closest("div.limit-user-wrapper");
        const closeBtn = limitBtn.parentElement.querySelector("button.remove-btn");

        limitBtn.addEventListener("click", async () => {
            if (limitBtn.style.backgroundColor !== "red") {
                limitBtn.style.backgroundColor = "red";
                wrapper.classList.add("active");
                closeBtn.style.display = "block";

                closeBtn.addEventListener("click",
                    () => resetLimitationControls(closeBtn, limitBtn, wrapper), {once: true});
            } else {
                toggleButtons(customersList);

                try {
                    const json = await (await fetch(`${customerServletUrl}?action=toggleLimitation&userId=${id}`)).json();

                    if (!json.result)
                        showPopup(popUpTitle, popUpMessageToggleLimitationFailure);
                    else
                        limitBtn.textContent = json.unlockedNow ? limitUser : unlockUser;
                } catch (e) {
                    showPopup(popUpTitle, popUpMessageToggleLimitationFailure);
                    console.error("Error:", e);
                }

                resetLimitationControls(closeBtn, limitBtn, wrapper);
                toggleButtons(customersList);
            }
        });
    }
}

function resetLimitationControls(closeBtn, limitBtn, wrapper) {
    closeBtn.style.display = limitBtn.style.backgroundColor = "";
    wrapper.classList.remove("active");
}

function toggleButtons(list) {
    list.querySelectorAll(".row button").forEach(e => e.disabled = !e.disabled);
}

function addNoEls(list) {
    list.appendChild(noEls.cloneNode(true));
}

function cleanList(list) {
    list.querySelectorAll(".row").forEach((e) => e.remove());
    const noElsDiv = list.querySelector(".no-products-message");
    if (noElsDiv)
        noElsDiv.remove();
}