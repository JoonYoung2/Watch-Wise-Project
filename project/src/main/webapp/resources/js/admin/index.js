let liveSearchContent = document.querySelectorAll(".live-search-content");
let liveSearchRightBtnId = document.getElementById("live-search-right-btn");
let liveSearchLeftBtnId = document.getElementById("live-search-left-btn");
let liveSearchPage = 1;
let liveSearchEndPage = 0;
let liveSearchStart = 0;
let liveSearchEnd = 0;
let liveSearchCntClass = document.querySelectorAll(".chart-live-search-cnt");
let liveSearchContentClass = document.querySelectorAll(".chart-live-search-content");
window.onload = () => {
    if(liveSearchContent.length > 10){
        for(var i = 0; i < 10; i++){
            liveSearchContent[i].style.display="";
        }
    }else{
        for(var i = 0; i < liveSearchContent.length; i++){
            liveSearchContent[i].style.display="";
        }
    }

    if(liveSearchContent.length % 10 != 0){
        liveSearchEndPage = Math.ceil(liveSearchContent.length / 10);
    }else{
        liveSearchEndPage = (liveSearchContent.length / 10)
    }
}

const liveSearchRightBtn = () => {
    liveSearchPage++;
    if(liveSearchPage == liveSearchEndPage){
        liveSearchRightBtnId.style.display="none";
        liveSearchLeftBtnId.style.display="flex";
    }
    liveSearchLeftBtnId.style.display="flex";
    liveSearchEnd = liveSearchPage * 10;
    liveSearchStart = liveSearchEnd - 10;
    for(var i = 0; i < liveSearchContent.length; i++){
        if(i >= liveSearchStart && i < liveSearchEnd){
            liveSearchContent[i].style.display="";
        }else{
            liveSearchContent[i].style.display="none";
        }
    }
}

const liveSearchLeftBtn = () => {
    liveSearchPage--;
    if(liveSearchPage == 1){
        liveSearchRightBtnId.style.display="flex";
        liveSearchLeftBtnId.style.display="none";
    }
    liveSearchRightBtnId.style.display="flex";
    liveSearchEnd = liveSearchPage * 10;
    liveSearchStart = liveSearchEnd - 10;
    for(var i = 0; i < liveSearchContent.length; i++){
        if(i >= liveSearchStart && i < liveSearchEnd){
            liveSearchContent[i].style.display="";
        }else{
            liveSearchContent[i].style.display="none";
        }
    }
}

