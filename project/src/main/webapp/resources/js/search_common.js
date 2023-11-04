const query = () => {
    let query = document.getElementById("query").value;
    if(query.length == 0){
        // location.href="/";
        return;
    }
    location.href="search?query=" + query;
}

const searchesClick = () => {
    let query = document.getElementById("query").value;
    if(query.length == 0){
        let searchesId = document.getElementById("searches");
        searchesId.style.display='block';
    }else{
        let searches2Id = document.getElementById("searches2");
        searches2Id.style.display='block';
    }
}

const searchesBlur = () => {
    let searchesId = document.getElementById("searches");
    let searches2Id = document.getElementById("searches2");
    searchesId.style.display='none';
    searches2Id.style.display='none';
}

let searchLength = 0;
let keyUpAndDown = 0;
const searchesKeyup = (event) => {
    let query = document.getElementById("query").value;
    let searchesId = document.getElementById("searches");
    let searches2Id = document.getElementById("searches2");

    if (event.key === "Enter") {
        if(query.length == 0){
            // location.href="/";
            return;
        }
        location.href="search?query=" + query;
        return;
    }

    if(query.length > 0){
        if(event.key === "ArrowUp"){
            console.log("up");
        }
    
        if(event.key === "ArrowDown"){
            console.log(keyUpAndDown);
            let relatedClass = document.querySelectorAll(".relatedClass");
            if(keyUpAndDown == 0){
                relatedClass[keyUpAndDown].style.backgroundColor="rgba(0,0,0,0.1)";
            }else if(keyUpAndDown > 0 && keyUpAndDown != searchLength){
                relatedClass[keyUpAndDown-1].style.backgroundColor="";
                relatedClass[keyUpAndDown].style.backgroundColor="rgba(0,0,0,0.1)";
            }
            if(searchLength != keyUpAndDown){
                keyUpAndDown++;
            }
            console.log("down");
            return;
        }
    }

    if(query.length == 0){
        searchesId.style.display='block';
        searches2Id.style.display='none';
        return;
    }

    if(query.length > 0){
        searchesId.style.display='none';
        searches2Id.style.display='block';
        let relatedSearch = query;
        $.ajax({
            url: "http://localhost:8080/relatedSearch",
            method: "GET",
            data:{query : relatedSearch},
            success: function (response) {
                let msg = "";
                msg += "<div style='padding:10px; color:red;'>연관 검색어</div>";
                for(var i = 0; i < response.length; i++){
                    msg += "<a style='all:unset; cursor:pointer;' onmousedown='searchStartMouseDown(\""+response[i].content+"\")'><div class='relatedClass' style='padding:5px 10px; background-color:;'>"+response[i].content+"</div></a>";
                }
                msg += "</div>";
                searches2Id.innerHTML = msg;
                searchLength = response.length;
                keyUpAndDown = 0;
            },
            error: function (xhr, status, error) {
                console.error("오류 발생: " + error);
            }
        });
    }
}

const searchAllMove = () => {
    $.ajax({
        url: "http://localhost:8080/searchAllMove",
        method: "GET",
        data:{},
        success: function (response) {
            let recentDivId = document.getElementById("recentDiv");
            let queryId = document.getElementById("query");
            recentDivId.innerText = "";
            queryId.focus();
            searchesClick();
        },
        error: function (xhr, status, error) {
            console.error("오류 발생: " + error);
        }
    });
}

const searchStartMouseDown = (query) => {
    location.href="search?query=" + query;
}

const searchMouseOver = () => {

}

const searchMouseOut = () => {
    
}