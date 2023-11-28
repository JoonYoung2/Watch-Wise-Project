const updateClick = (id, requestIp) => {
    let idId = document.getElementById("id");
    let updateFormId = document.getElementById("updateForm");
    let requestIpId = document.getElementById("requestIp");

    updateFormId.style.display = "";
    idId.value = id;
    requestIpId.value = requestIp;
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