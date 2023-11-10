const rowNumUpdate = (tableName) => {
    console.log(tableName);
    let rowNumId = document.getElementById("rowNum");
    let rowNum = rowNumId.value;
    let tableNm = tableName;
    $.ajax({
		url: "/updateRowNum",
		method: "GET",
		data: {
			rowNum, tableNm
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
    insertFormId.style.display = "";
}

// const updateForm = () => {
//     let updateFormId = document.getElementById("updateForm");
//     updateFormId.style.display = "";
// }