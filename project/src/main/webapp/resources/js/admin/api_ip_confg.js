const updateClick = (id, allowedOrigins) => {
    let idId = document.getElementById("id");
    let updateFormId = document.getElementById("updateForm");
    let allowedOriginsId = document.getElementById("allowed-origins");

    updateFormId.style.display = "";
    idId.value = id;
    allowedOriginsId.value = allowedOrigins;
}

const registerClick = () => {
    let registerFormId = document.getElementById("registerForm");
    registerFormId.style.display = "";
}

const registerClose = () => {
    let registerFormId = document.getElementById("registerForm");
    registerFormId.style.display = "none";
}

const updateClose = () => {
    let updateFormId = document.getElementById("updateForm");
    updateFormId.style.display = "none";
}