let colors = ["#FF69B4", "#ADD8E6", "#90EE90", "#FFC0CB", "#FFA500", "#FF4500", "#FF6347", 
    "#1E90FF", "#FFD700", "#008000", "#FF1493", "#00BFFF", "#FF0000", "#4682B4", "#FFFACD", 
    "#00FF7F", "#F0E68C", "#FFA50080", "#FF450070", "#FF634780"];
let redColor = "red";
let blueColor = "blue";
let pinkColor = "#FFB6C1";
let chartName = "";
let chartTitle = "";
let typeName = "";
let pageWidthClass = document.querySelectorAll(".page-width");
let menusClass = document.querySelectorAll(".menus");

// 인기 검색어 관련
let liveSearchContent = document.querySelectorAll(".live-search-content");
let liveSearchRightBtnId = document.getElementById("live-search-right-btn");
let liveSearchLeftBtnId = document.getElementById("live-search-left-btn");
let liveSearchPage = 1;
let liveSearchEndPage = 0;
let liveSearchStart = 0;
let liveSearchEnd = 0;

let liveSearchCntClass = document.querySelectorAll(".chart-live-search-cnt");
let liveSearchContentClass = document.querySelectorAll(".chart-live-search-content");
let searchContent = [];
let searchCnt = [];
let searchColor = [];

// 인기 영화 관련
let movieLikeNumClass = document.querySelectorAll(".movie-like-num");
let movieNmClass = document.querySelectorAll(".movie-nm");
let moviePosterClass = document.querySelectorAll(".movie-poster");
let movieRightBtnId = document.getElementById("movie-right-btn");
let movieLeftBtnId = document.getElementById("movie-left-btn");
let movieContent = [];
let movieCnt = [];
let movieColor = [];

// 인기 배우 관련
let actorLikeNumClass = document.querySelectorAll(".actor-like-num");
let actorNmClass = document.querySelectorAll(".actor-nm");
let actorProfileClass = document.querySelectorAll(".actor-profile");
let actorRightBtnId = document.getElementById("actor-right-btn");
let actorLeftBtnId = document.getElementById("actor-left-btn");
let actorContent = [];
let actorCnt = [];
let actorColor = [];

// 회원 동향 관련
let memberTrendDateClass = document.querySelectorAll(".member-trend-date");
let memberTrendCountClass = document.querySelectorAll(".member-trend-count");
let memberTrendContent = [];
let memberTrendCnt = [];
let memberTrendColor = [];

window.onload = () => {
    let maxValue = 0;
    pageWidthClass[0].style.display="flex";
    menusClass[0].style.backgroundColor="rgba(0, 0, 0, 0.1)";
    menusClass[0].style.fontWeight="bold";

    // 실시간 인기검색어 순위표
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

    // 실시간 인기검색어 차트표
    if(liveSearchCntClass.length < 10){
        for(var i = 0; i < liveSearchCntClass.length; i++){
            // let x = Math.floor(Math.random() * 20);

            searchContent[i] = liveSearchContentClass[i].value;
            searchCnt[i] = Number(liveSearchCntClass[i].value);
            searchColor[i] = blueColor;
            maxValue = Math.max(maxValue, searchCnt[i]);
        }
    }else{
        for(var i = 0; i < 10; i++){
            // let x = Math.floor(Math.random() * 20);

            searchContent[i] = liveSearchContentClass[i].value;
            searchCnt[i] = Number(liveSearchCntClass[i].value);
            searchColor[i] = pinkColor;
            maxValue = Math.max(maxValue, searchCnt[i]);
        }
    }

    for(var i = 0; i < searchCnt.length; i++){
        if(maxValue == searchCnt[i]){
            searchColor[i] = redColor;
        }
    }
    maxValue = 0;
    chartName = "bar-chart-live-search";
    chartTitle = "검색 수 ";
    typeName = "bar";
    //바 차트 실시간 검색어
    barChart(chartName, typeName, chartTitle, searchContent, searchColor, searchCnt);

    // 인기영화 차트표
    if(movieLikeNumClass.length <= 10){
        for(var i = 0; i < movieLikeNumClass.length; i++){
            // let x = Math.floor(Math.random() * 20);

            moviePosterClass[i].style.display="block";
            movieContent[i] = movieNmClass[i].value;
            movieCnt[i] = Number(movieLikeNumClass[i].value);
            movieColor[i] = pinkColor;
            maxValue = Math.max(maxValue, movieCnt[i]);
        }
    }else{
        // movieRightBtnId.style.display="flex";
        for(var i = 0; i < 10; i++){
            // let x = Math.floor(Math.random() * 20);

            moviePosterClass[i].style.display="block";
            movieContent[i] = movieNmClass[i].value;
            movieCnt[i] = Number(movieLikeNumClass[i].value);
            movieColor[i] = pinkColor;
            maxValue = Math.max(maxValue, movieCnt[i]);
        }
    }
    
    for(var i = 0; i < movieCnt.length; i++){
        if(maxValue == movieCnt[i]){
            movieColor[i] = redColor;
        }
    }

    maxValue = 0;
    //바 차트 인기 영화
    chartName = "bar-chart-movie";
    chartTitle = "좋아요 수 ";
    typeName = "bar";
    barChart(chartName, typeName, chartTitle, movieContent, movieColor, movieCnt);

    // 인기배우 차트표
    if(actorLikeNumClass.length <= 10){
        for(var i = 0; i < actorLikeNumClass.length; i++){
            // let x = Math.floor(Math.random() * 20);

            actorProfileClass[i].style.display="block";
            actorContent[i] = actorNmClass[i].value;
            actorCnt[i] = Number(actorLikeNumClass[i].value);
            actorColor[i] = pinkColor;
            maxValue = Math.max(maxValue, actorCnt[i]);
        }
    }else{
        // actorRightBtnId.style.display="flex";
        for(var i = 0; i < 10; i++){
            // let x = Math.floor(Math.random() * 20);

            actorProfileClass[i].style.display="block";
            actorContent[i] = actorNmClass[i].value;
            actorCnt[i] = Number(actorLikeNumClass[i].value);
            actorColor[i] = pinkColor;
            maxValue = Math.max(maxValue, actorCnt[i]);
        }
    }

    for(var i = 0; i < actorCnt.length; i++){
        if(maxValue == actorCnt[i]){
            actorColor[i] = redColor;
        }
    }

    maxValue = 0;
    //바 차트 인기 배우
    chartName = "bar-chart-actor";
    chartTitle = "좋아요 수 ";
    typeName = "bar";
    barChart(chartName, typeName, chartTitle, actorContent, actorColor, actorCnt);

    //회원 동향 차트표
    let totalCnt = 0;
    for(var i = 0; i < memberTrendCountClass.length; i++){
        // let x = Math.floor(Math.random() * 20);
        let memberTrendDateValue = memberTrendDateClass[i].value.substring(5, 10).replaceAll("-", ".");
        memberTrendContent[i] = memberTrendDateValue;
        memberTrendCnt[i] = Number(memberTrendCountClass[i].value);
        totalCnt += memberTrendCnt[i];
        memberTrendColor[i] = pinkColor;
        maxValue = Math.max(memberTrendCnt[i], maxValue);
    }

    for(var i = 0; i < memberTrendCnt.length; i++){
        if(maxValue == memberTrendCnt[i]){
            memberTrendColor[i] = redColor;
        }
    }

    maxValue = 0;
    document.getElementById("total-cnt").innerHTML = "총 가입자 수 : <span style='color:red;'>" + totalCnt + "</span>";
    //바 차트 회원 동향
    chartName = "bar-chart-member";
    chartTitle = "가입 수  ";
    typeName = "line";
    barChart(chartName, typeName, chartTitle, memberTrendContent, memberTrendColor, memberTrendCnt);
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

const barChart = (cartId, typeName, title, content, color, cnt) => {
    new Chart(document.getElementById(cartId), {
        type: typeName,
        data: {
          labels: content,
          datasets: [
            {
              label: title,
              backgroundColor: color,
              data: cnt
            }
          ]
        },
        options: {
          legend: { display: false },
          title: {
            display: true,
            text: 'Predicted world population (millions) in 2050'
          }
        }
    });
}

const menuClick = (cnt) => {
    let count = Number(cnt);
    for(var i = 0; i < pageWidthClass.length; i++){
        pageWidthClass[i].style.display="none";
        menusClass[i].style.backgroundColor="white";
        menusClass[i].style.fontWeight="";
    }

    pageWidthClass[count].style.display="flex";
    menusClass[count].style.backgroundColor="rgba(0, 0, 0, 0.1)";
    menusClass[count].style.fontWeight="bold";
}