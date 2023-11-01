// Paging Method START
let memberCommendLeftBtnId = document.getElementById("memberCommendLeftBtn");
let searchMoviesLeftBtnId = document.getElementById("searchMoviesLeftBtn");

let memberCommendRightBtnId = document.getElementById("memberCommendRightBtn");
let searchMoviesRightBtnId = document.getElementById("searchMoviesRightBtn");

let memberCommendClass = document.querySelectorAll(".memberCommend");
let searchMoviesClass = document.querySelectorAll(".searchMovies");

let searchPeopleMoviesPageNum = {};
let searchListSize = 0

try{
    searchListSize = Number(document.getElementById("searchListSize").value);
    for(var i = 0; i < searchListSize; i++){
        searchPeopleMoviesPageNum[i] = 1;
    }
}catch(error){
    
}


let memberCommendPageNum = 1;
let searchMoviesPageNum = 1;


let memberCommendCnt = 0;
let searchMoviesCnt = 0;
let searchPeopleMoviesCnt = 0;

// 인물 search 연관영화

const searchPeopleMoviesLeftBtn = (movieCnt) => {
    let searchPeopleMoviesLeftBtnId = document.getElementById("searchPeopleMoviesLeftBtn" + movieCnt);
    let searchPeopleMoviesRightBtnId = document.getElementById("searchPeopleMoviesRightBtn" + movieCnt);
    let searchPeopleMoviesClass = document.querySelectorAll(".searchPeopleMovies" + movieCnt);

    searchPeopleMoviesPageNum[movieCnt]--;
    if(searchPeopleMoviesPageNum[movieCnt] == 1){
        searchPeopleMoviesLeftBtnId.style.display = "none";
        searchPeopleMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchPeopleMoviesClass.length; i++){
            if(i >= searchPeopleMoviesPageNum[movieCnt]*5-5 && i < searchPeopleMoviesPageNum[movieCnt]*5){
                searchPeopleMoviesClass[i].style.display = "";
            }else{
                searchPeopleMoviesClass[i].style.display = "none";
            }
        }
    }else{
        searchPeopleMoviesLeftBtnId.style.display = "block";
        searchPeopleMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchPeopleMoviesClass.length; i++){
            if(i >= searchPeopleMoviesPageNum[movieCnt]*5-5 && i < searchPeopleMoviesPageNum[movieCnt]*5){
                searchPeopleMoviesClass[i].style.display = "";
            }else{
                searchPeopleMoviesClass[i].style.display = "none";
            }
        }
    }
}

const searchPeopleMoviesRightBtn = (cnt, movieCnt) => {
    let searchPeopleMoviesLeftBtnId = document.getElementById("searchPeopleMoviesLeftBtn" + movieCnt);
    let searchPeopleMoviesRightBtnId = document.getElementById("searchPeopleMoviesRightBtn" + movieCnt);
    let searchPeopleMoviesClass = document.querySelectorAll(".searchPeopleMovies" + movieCnt);

    searchPeopleMoviesCnt = Number(cnt);
    let pageNum = Math.ceil(searchPeopleMoviesCnt / 5);
    searchPeopleMoviesPageNum[movieCnt]++;
    if(searchPeopleMoviesPageNum[movieCnt] == pageNum){
        searchPeopleMoviesLeftBtnId.style.display = "block";
        searchPeopleMoviesRightBtnId.style.display = "none";
        for(var i = 0; i < searchPeopleMoviesClass.length; i++){
            if(i >= searchPeopleMoviesPageNum[movieCnt]*5-5 && i < searchPeopleMoviesPageNum[movieCnt]*5){
                searchPeopleMoviesClass[i].style.display = "";
            }else{
                searchPeopleMoviesClass[i].style.display = "none";
            }
        }
    }else{
        searchPeopleMoviesLeftBtnId.style.display = "block";
        searchPeopleMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchPeopleMoviesClass.length; i++){
            if(i >= searchPeopleMoviesPageNum[movieCnt]*5-5 && i < searchPeopleMoviesPageNum[movieCnt]*5){
                searchPeopleMoviesClass[i].style.display = "";
            }else{
                searchPeopleMoviesClass[i].style.display = "none";
            }
        }
    }
}

// 인물 search 연관영화 EMD


// 영화 search 연관영화 START
const searchMoviesLeftBtn = () => {
    searchMoviesPageNum--;
    if(searchMoviesPageNum == 1){
        searchMoviesLeftBtnId.style.display = "none";
        searchMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }else{
        searchMoviesLeftBtnId.style.display = "block";
        searchMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }
}

const searchMoviesRightBtn = (cnt) => {
    searchMoviesCnt = Number(cnt);
    let pageNum = Math.ceil(searchMoviesCnt / 5);
    searchMoviesPageNum++;
    if(searchMoviesPageNum == pageNum){
        searchMoviesLeftBtnId.style.display = "block";
        searchMoviesRightBtnId.style.display = "none";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }else{
        searchMoviesLeftBtnId.style.display = "block";
        searchMoviesRightBtnId.style.display = "block";
        for(var i = 0; i < searchMoviesClass.length; i++){
            if(i >= searchMoviesPageNum*5-5 && i < searchMoviesPageNum*5){
                searchMoviesClass[i].style.display = "";
            }else{
                searchMoviesClass[i].style.display = "none";
            }
        }
    }
}
// 영화 search 연관영화 END

const memberCommendLeftBtn = () => {
    memberCommendPageNum--;
    if(memberCommendPageNum == 1){
        memberCommendLeftBtnId.style.display = "none";
        memberCommendRightBtnId.style.display = "block";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }else{
        memberCommendLeftBtnId.style.display = "block";
        memberCommendRightBtnId.style.display = "block";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }
}

const memberCommendRightBtn = (cnt) => {
    memberCommendCnt = Number(cnt);
    
    let pageNum = Math.ceil(memberCommendCnt / 5);
    memberCommendPageNum++;
    
    if(memberCommendPageNum == pageNum){
        memberCommendLeftBtnId.style.display = "block";
        memberCommendRightBtnId.style.display = "none";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }else{
        memberCommendLeftBtnId.style.display = "block";
        memberCommendRightBtnId.style.display = "block";
        for(var i = 0; i < memberCommendClass.length; i++){
            if(i >= memberCommendPageNum*5-5 && i < memberCommendPageNum*5){
                memberCommendClass[i].style.display = "";
            }else{
                memberCommendClass[i].style.display = "none";
            }
        }
    }
}

// Paging Method END

// Actor 더 보기 기능

let actorInfoAddPageNum = 1;
let actorInfoAddPageCount = 0;
let actorInfoAddId = document.getElementById("actorInfoAdd");
const actorInfoAdd = (pageCnt) => {
    let pageCount = Number(pageCnt);
    if(pageCount % 3 == 0){
        actorInfoAddPageCount = pageCount/3;
    }else{
        actorInfoAddPageCount = Math.ceil(pageCount/3);
    }
    actorInfoAddPageNum++;
    console.log(actorInfoAddPageCount);
    if(actorInfoAddPageNum == actorInfoAddPageCount){
        actorInfoAddId.style.display="none";
    }
    let pageCntClass = document.querySelectorAll(".pageCnt");
    for(var i = 0; i < pageCount; i++){
        if(i < actorInfoAddPageNum * 3 && i > actorInfoAddPageNum - 3){
            pageCntClass[i].style.display = "block";
        }
    }
}

// Actor 더 보기 기능