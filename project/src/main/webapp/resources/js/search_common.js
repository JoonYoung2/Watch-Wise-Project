// 연관검색어 관련
let searchLength = 0;
let keyUpAndDown = -1; // -1로 초기화

// 초기검색어 관련
let removieCheck = 0;
let popularStartLength = 0;
let popularEndLength = document.querySelectorAll(".relatedSearches").length;
let popularKeyUpAndDown = -1;

let searchesCheck = 0;

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

const searchesKeyup = (event) => {
    let query = document.getElementById("query").value;
    let searchesId = document.getElementById("searches");
    let searches2Id = document.getElementById("searches2");

    if (event.key === "Enter") {
        if (query.length === 0) {
            // location.href="/";
            return;
        }
        query = query.replaceAll("'", "''");
        location.href = "search?query=" + query;
        return;
    }
    // 연관 검색어 관련 ArrowUp OR ArrowDown 눌렀을 시
    if (query.length > 0) {
        if(searchesCheck == 1){
            if (event.key === "ArrowUp") {
                let relatedClass = document.querySelectorAll(".relatedClass");
                let relatedClassValue = document.querySelectorAll(".relatedClassValue");
                let queryId = document.getElementById("query");
                if(keyUpAndDown == 0){
                    keyUpAndDown = searchLength-1;
                    relatedClass[0].style.backgroundColor = "";
                    relatedClass[keyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                    queryId.value = relatedClassValue[keyUpAndDown].value;
                    return;
                }
    
                if(keyUpAndDown == -1){
                    keyUpAndDown = searchLength-1;
                    relatedClass[keyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                    queryId.value = relatedClassValue[keyUpAndDown].value;
                    return;
                }
    
                if (keyUpAndDown >= 0) {
                    relatedClass[keyUpAndDown].style.backgroundColor = "";
                }
                if (keyUpAndDown > 0) {
                    keyUpAndDown--;
                }
                if (keyUpAndDown >= 0) {
                    relatedClass[keyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                    queryId.value = relatedClassValue[keyUpAndDown].value;
                }
                return;
            }
    
            if (event.key === "ArrowDown") {
                let relatedClass = document.querySelectorAll(".relatedClass");
                let relatedClassValue = document.querySelectorAll(".relatedClassValue");
                let queryId = document.getElementById("query");
    
                if(keyUpAndDown == -1){
                    keyUpAndDown = 0;
                    relatedClass[0].style.backgroundColor = "rgba(0,0,0,0.1)";
                    return;
                }
    
                if (keyUpAndDown >= 0) {
                    relatedClass[keyUpAndDown].style.backgroundColor = "";
                }
    
                if(keyUpAndDown == searchLength-1){
                    relatedClass[keyUpAndDown].style.backgroundColor = "";
                    relatedClass[0].style.backgroundColor = "rgba(0,0,0,0.1)";
                    queryId.value = relatedClassValue[0].value;
                    keyUpAndDown = 0;
                    return;
                }
    
                if (keyUpAndDown < searchLength - 1) {
                    keyUpAndDown++;
                }
                if (keyUpAndDown >= 0) {
                    relatedClass[keyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                    queryId.value = relatedClassValue[keyUpAndDown].value;
                }
                return;
            }
        }
    }

    if(query.length == 0){
        searchesCheck = 0;
    }
    // 최근검색 AND 인기검색어 관련
    if (searchesCheck === 0) {
        searchesId.style.display = 'block';
        searches2Id.style.display = 'none';
        if (event.key === "ArrowUp") {
            let relatedSearches = document.querySelectorAll(".relatedSearches");
            let relatedSearchesValue = document.querySelectorAll(".relatedSearchesValue");
            let queryId = document.getElementById("query");
            if(popularKeyUpAndDown == popularStartLength){
                popularKeyUpAndDown = popularEndLength-1;
                relatedSearches[popularStartLength].style.backgroundColor = "";
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                queryId.value = relatedSearchesValue[popularKeyUpAndDown].value;
                return;
            }
        
            if(popularKeyUpAndDown == -1){
                popularKeyUpAndDown = popularEndLength-1;
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                queryId.value = relatedSearchesValue[popularKeyUpAndDown].value;
                return;
            }
        
            if (popularKeyUpAndDown >= 0) {
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "";
            }
            if (popularKeyUpAndDown > 0) {
                popularKeyUpAndDown--;
            }
            if (popularKeyUpAndDown >= 0) {
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                queryId.value = relatedSearchesValue[popularKeyUpAndDown].value;
            }
            return;
        }

        if (event.key === "ArrowDown") {
            let relatedSearches = document.querySelectorAll(".relatedSearches");
            let relatedSearchesValue = document.querySelectorAll(".relatedSearchesValue");
            let queryId = document.getElementById("query");

            if(popularKeyUpAndDown == -1){
                popularKeyUpAndDown = popularStartLength;
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                return;
            }
        
            if (popularKeyUpAndDown >= popularStartLength) {
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "";
            }
        
            if(popularKeyUpAndDown == popularEndLength-1){
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "";
                relatedSearches[popularStartLength].style.backgroundColor = "rgba(0,0,0,0.1)";
                queryId.value = relatedSearchesValue[popularStartLength].value;
                popularKeyUpAndDown = popularStartLength;
                return;
            }
        
            if (popularKeyUpAndDown < popularEndLength-1) {
                popularKeyUpAndDown++;
            }
            if (popularKeyUpAndDown >= 0) {
                relatedSearches[popularKeyUpAndDown].style.backgroundColor = "rgba(0,0,0,0.1)";
                queryId.value = relatedSearchesValue[popularKeyUpAndDown].value;
            }
            return;
        }
    }

    // 검색어 추가 시
    if (query.length > 0) {
        searchesId.style.display = 'none';
        searches2Id.style.display = 'block';
        
        query = query.replaceAll("'", "''");

        let relatedSearch = query;
        $.ajax({
            url: "http://localhost:8080/relatedSearch",
            method: "GET",
            data: { query: relatedSearch },
            success: function (response) {
                let msg = "";
                msg += "<div style='padding:10px; color:red;'>연관 검색어</div>";
                for (let i = 0; i < response.length; i++) {
                    
                    if(response[i].searchType == 1){
                        msg += "<a style='all:unset; cursor:pointer;' onmousedown='searchStartMouseDown(\"" + response[i].content + "\")'>";
                        msg += "<div class='relatedClass' style='padding:5px 10px; background-color:;'><img style='width:15px; padding-right:10px;' src='/resources/img/past.png'>"+response[i].content+"</div></a>";
                    }else{
                        msg += "<a style='all:unset; cursor:pointer;' onmousedown='searchStartMouseDown(\"" + response[i].content + "\")'>";
                        msg += "<div class='relatedClass' style='padding:5px 10px; background-color:;'>"+response[i].content+"</div></a>";
                    }
                    msg += "<input type='hidden' class='relatedClassValue' value=\"" + response[i].content.replace(/"/g, '&quot;') + "\">";
                }

                relatedSearchesClass = document.querySelectorAll(".relatedSearches");
                for(var i = popularStartLength; i < popularEndLength; i++){
                    if(relatedSearchesClass[i].style.backgroundColor!=""){
                        relatedSearchesClass[i].style.backgroundColor = "";
                    }
                }
                msg += "</div>";
                searches2Id.innerHTML = msg;
                searchLength = response.length;
                keyUpAndDown = -1; // -1로 초기화
                searchesCheck = 1;
                popularKeyUpAndDown = -1;
            },
            error: function (xhr, status, error) {
                console.error("오류 발생: " + error);
            }
        });
    }
}

const searchAllMove = (num) => {
    let count = Number(num);
    
    $.ajax({
        url: "http://localhost:8080/searchAllMove",
        method: "GET",
        data:{},
        success: function (response) {
            let recentDivId = document.getElementById("recentDiv");
            let queryId = document.getElementById("query");
            recentDivId.innerText = "";
            popularEndLength = popularEndLength-count;
            relatedSearchesClass = document.querySelectorAll(".relatedSearches");
            for(var i = popularStartLength; i < popularEndLength; i++){
                if(relatedSearchesClass[i].style.backgroundColor!=""){
                    relatedSearchesClass[i].style.backgroundColor = "";
                }
            }
            popularKeyUpAndDown = -1;
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

// Live Searches

let locationDivId = document.getElementById("locationDiv");
let liveSearchListId = document.getElementById("liveSearchList");
const liveSearchMouseOver = () => {
    locationDivId.innerText = "△";
    liveSearchListId.style.display="block";
}

const liveSearchMouseOut = () => {
    locationDivId.innerText = "▽";
    liveSearchListId.style.display="none";
}

const liveSearchClick = (content) => {
    content = content.replaceAll("'", "''");
    location.href = "search?query=" + content;
    return;
}

// Live Searches