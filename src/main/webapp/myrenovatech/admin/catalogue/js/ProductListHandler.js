document.getElementById("refresh-list").addEventListener("click", async function () {
    try {
        const res = await fetch("/myrenovatech/admin/products-handler?getList");
        if (!res.ok) throw new Error("Call error");

        const products = await res.json();
        const container = document.querySelector(".product-list-container");

        container.querySelectorAll(".product-row").forEach(el => el.remove());

        products.forEach(p => {
            const row = document.createElement("div");
            row.className = "product-row";
            row.innerHTML = `
          <span data-label="Nome">${p.name}</span>
          <span data-label="Prezzo">${p.price}€</span>
          <span data-label="Categoria">${p.category}</span>
          <span data-label="Azioni">
            <button>Modifica</button>
            <button>Elimina</button>
          </span>
        `;
            container.appendChild(row);
        });

    } catch (err) {
        console.error("AJAX error:", err);
    }
});