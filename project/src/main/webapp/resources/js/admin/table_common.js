const rowNumUpdate = (tableName, order) => {
    console.log(tableName);
    let rowNumId = document.getElementById("rowNum");
    let rowNum = rowNumId.value;
    let tableNm = tableName;
	let orderByColumn = order;
    $.ajax({
		url: "/updateRowNum",
		method: "GET",
		data: {
			rowNum, tableNm, orderByColumn
		},
		success: (response) =>{
			location.reload();
		},
		error: ()=>{
			console.log(error);
		}
	});
}

const insertForm = () => {
    let insertFormId = document.getElementById("insertForm");
	let bodyId = document.getElementById("body");
    insertFormId.style.display = "";
	bodyId.style.backgroundColor = "rgba(0, 0, 0, 0.1)";
}

const insertClose = () => {
	let insertFormId = document.getElementById("insertForm");
	let bodyId = document.getElementById("body");
    insertFormId.style.display = "none";
	bodyId.style.backgroundColor = "";
}

const updateForm = (cnt) => {
	let content = document.querySelectorAll(".content-values" + cnt);
	let updateInput = document.querySelectorAll(".update-input");

	for(var i = 0; i < content.length; i++){
		updateInput[i].value = content[i].value;
	}
    let updateFormId = document.getElementById("updateForm");
	let bodyId = document.getElementById("body");
    updateFormId.style.display = "";
	bodyId.style.backgroundColor = "rgba(0, 0, 0, 0.1)";
}

const updateClose = () => {
	let updateFormId = document.getElementById("updateForm");
	let bodyId = document.getElementById("body");
    updateFormId.style.display = "none";
	bodyId.style.backgroundColor = "";
}

const search = (event, tableNm, orderByColumn) => {
	let queryId = document.getElementById("query").value;
	if (event.key === "Enter") {
        queryId = queryId.replaceAll("'", "''");
        location.href = "/admin/"+tableNm+"/"+orderByColumn+"/1?query=" + queryId;
        return;
    }
}