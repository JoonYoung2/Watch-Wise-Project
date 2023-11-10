const updateClick = (id, tableNm, columns, orderByColumn, rowNum) => {
    let idId = document.getElementById("id");
    let updateFormId = document.getElementById("updateForm");
    let tableNmId = document.getElementById("tableNm");
    let columnsId = document.getElementById("columns");
    let orderByColumnId = document.getElementById("orderByColumn");
    let rowNumId = document.getElementById("rowNum");

    updateFormId.style.display = "";
    idId.value = id;
    tableNmId.value=tableNm;
    columnsId.value=columns;
    orderByColumnId.value=orderByColumn;
    rowNumId.value=rowNum;
}

const registerClick = () => {
    let registerFormId = document.getElementById("registerForm");
    registerFormId.style.display = "";
}