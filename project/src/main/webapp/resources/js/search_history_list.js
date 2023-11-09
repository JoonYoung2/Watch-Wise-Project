let selectDivId = document.getElementById("selectDiv");

const allChoose = () => {
    let checkboxClass = document.querySelectorAll(".checkboxClass");
    let msg = "";
    for(var i = 0; i < checkboxClass.length; i++){
        if(checkboxClass[i].checked == false){
            checkboxClass[i].checked = true;
        }
    }
    
    msg = "<div style='cursor:pointer;' onclick='allCancel();'>취소</div>";
    selectDivId.innerHTML = msg;
}

const allCancel = () => {
    let checkboxClass = document.querySelectorAll(".checkboxClass");
    let msg = "";
    for(var i = 0; i < checkboxClass.length; i++){
        if(checkboxClass[i].checked == true){
            checkboxClass[i].checked = false;
        }
    }
    
    msg = "<div style='cursor:pointer;' onclick='allChoose();'>모두</div>";
    selectDivId.innerHTML = msg;
}

const checkboxClick = () => {
    let checkboxClass = document.querySelectorAll(".checkboxClass");
    let cnt = 0;
    let msg = "";
    for(var i = 0; i < checkboxClass.length; i++){
        if(checkboxClass[i].checked == false){
            cnt++;
            break;
        }
    }

    if(cnt == 1){
        msg = "<div style='cursor:pointer;' onclick='allChoose();'>모두</div>";
    }else{
        msg = "<div style='cursor:pointer;' onclick='allCancel();'>취소</div>";
    }

    selectDivId.innerHTML = msg;
}

const deleteSearchHistory = () => {
    let checkboxClass = document.querySelectorAll(".checkboxClass");
    let ids = "";
    for(var i = 0; i < checkboxClass.length; i++){
        if(checkboxClass[i].checked == true){
            ids += checkboxClass[i].value + ",";
        }
    }
    ids = ids.substring(0, ids.length-1);
    location.href="deleteSearchHistory?ids=" + ids;
    return;
}