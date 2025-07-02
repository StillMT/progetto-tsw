const fileInput = document.getElementById("images");
const imageList = document.getElementById("image-list");
const hiddenInputs = document.getElementById("hidden-image-inputs");
const hiddenKeptInputs = document.getElementById("hidden-kept-images");
const form2 = fileInput.closest("form");

const MAX_IMAGES = 6;

let selectedFiles = [];
let keptOldImages = [];

fileInput.addEventListener("change", (e) => {
    const newFiles = Array.from(e.target.files);
    const total = selectedFiles.length + keptOldImages.length + newFiles.length;

    if (total > MAX_IMAGES) {
        showPopup(popUpTitle, popUpMessageImageLimit);

        fileInput.value = "";
        return;
    }

    selectedFiles.push(...newFiles);
    updateFileList();
    fileInput.value = "";
});

function updateFileList() {
    imageList.innerHTML = "";

    keptOldImages.forEach((fileName, index) => {
        const li = document.createElement("li");
        li.className = "file-item";

        const span = document.createElement("span");
        span.className = "file-name";
        span.textContent = fileName;

        const button = document.createElement("button");
        button.type = "button";
        button.className = "remove-btn";
        button.addEventListener("click", () => removeOldImage(index));

        li.appendChild(span);
        li.appendChild(button);
        imageList.appendChild(li);
    });

    selectedFiles.forEach((file, index) => {
        const li = document.createElement("li");
        li.className = "file-item";

        const span = document.createElement("span");
        span.className = "file-name";
        span.textContent = file.name;

        const button = document.createElement("button");
        button.type = "button";
        button.className = "remove-btn";
        button.addEventListener("click", () => removeSelectedFile(index));

        li.appendChild(span);
        li.appendChild(button);
        imageList.appendChild(li);
    });
}

function removeOldImage(index) {
    keptOldImages.splice(index, 1);
    updateFileList();
}

function removeSelectedFile(index) {
    selectedFiles.splice(index, 1);
    updateFileList();
}

function loadExistingImages(productId) {
    fetch(`/products/imgs/getFileList?&productId=${productId}`)
        .then(response => response.json())
        .then(data => {
            keptOldImages = data.products;
            updateFileList();
        })
        .catch(error => console.error("AJAX error:", error));
}

function cleanFileList() {
    selectedFiles = [];
    keptOldImages = [];
    updateFileList();
    hiddenInputs.innerHTML = "";
    hiddenKeptInputs.innerHTML = "";
}

form2.addEventListener("submit", (e) => {
    let hasErrors = false;
    document.querySelectorAll(".form-error").forEach(e => {
        if (e.style.display === "block")
            hasErrors = true;
    });

    if (hasErrors) {
        e.preventDefault();
        showPopup(popUpTitle, popUpMessageFieldErrors);
        return;
    }

    hiddenInputs.innerHTML = "";
    hiddenKeptInputs.innerHTML = "";

    const dt = new DataTransfer();
    selectedFiles.forEach(file => dt.items.add(file));

    const uploadInput = document.createElement("input");
    uploadInput.type = "file";
    uploadInput.name = "uploadedImages[]";
    uploadInput.multiple = true;
    uploadInput.files = dt.files;
    uploadInput.style.display = "none";
    hiddenInputs.appendChild(uploadInput);

    keptOldImages.forEach(name => {
        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "keptImages[]";
        input.value = name;
        hiddenKeptInputs.appendChild(input);
    });
});