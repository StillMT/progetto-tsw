const cancelBtn = document.getElementById("cancel-order");

if (cancelBtn) {
    let alreadyClicked = false;

cancelBtn.addEventListener("click", e => {
        if (alreadyClicked)
            return;

        showPopup(popUpTitle, popUpMessageSureToCancel);
        e.preventDefault();
        alreadyClicked = true;
    });
}