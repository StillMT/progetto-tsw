document.getElementById("contact-form").addEventListener("submit", function (ev) {
    for (const el of document.querySelectorAll(".form-error"))
        if (el.style.display === "block") {
            ev.preventDefault();
            break;
        }
});