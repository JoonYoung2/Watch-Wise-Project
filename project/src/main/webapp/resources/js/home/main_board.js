let colors = ["#FF69B4", "#ADD8E6", "#90EE90", "#FFC0CB", "#FFA500", "#FF4500", "#FF6347", 
    "#1E90FF", "#FFD700", "#008000", "#FF1493", "#00BFFF", "#FF0000", "#4682B4", "#FFFACD", 
    "#00FF7F", "#F0E68C", "#FFA50080", "#FF450070", "#FF634780"];
let chartName = "";
let chartTitle = "";
let pageWidthClass = document.querySelectorAll(".main-page-width");
let menusClass = document.querySelectorAll(".menus");

// 인기 영화 관련
let movieLikeNumClass = document.querySelectorAll(".movie-like-num");
let movieNmClass = document.querySelectorAll(".movie-nm");
let moviePosterClass = document.querySelectorAll(".movie-poster");
// let movieRightBtnId = document.getElementById("movie-right-btn");
// let movieLeftBtnId = document.getElementById("movie-left-btn");
let movieContent = [];
let movieCnt = [];
let movieColor = [];

// 인기 배우 관련
let actorLikeNumClass = document.querySelectorAll(".actor-like-num");
let actorNmClass = document.querySelectorAll(".actor-nm");
let actorProfileClass = document.querySelectorAll(".actor-profile");
// let actorRightBtnId = document.getElementById("actor-right-btn");
// let actorLeftBtnId = document.getElementById("actor-left-btn");
let actorContent = [];
let actorCnt = [];
let actorColor = [];

// 회원 동향 관련

window.onload = () => {
    // pageWidthClass[0].style.display="flex";
    // menusClass[0].style.backgroundColor="rgba(0, 0, 0, 0.1)";
    // menusClass[0].style.fontWeight="bold";


    // 인기영화 차트표
    if(movieLikeNumClass.length <= 10){
        for(var i = 0; i < movieLikeNumClass.length; i++){
            let x = Math.floor(Math.random() * 20);

            moviePosterClass[i].style.display="block";
            movieContent[i] = movieNmClass[i].value;
            movieCnt[i] = Number(movieLikeNumClass[i].value);
            movieColor[i] = colors[x];
        }
    }else{
        // movieRightBtnId.style.display="flex";
        for(var i = 0; i < 10; i++){
            let x = Math.floor(Math.random() * 20);

            moviePosterClass[i].style.display="block";
            movieContent[i] = movieNmClass[i].value;
            movieCnt[i] = Number(movieLikeNumClass[i].value);
            movieColor[i] = colors[x];
        }
    }
    
    //바 차트 인기 영화
    chartName = "bar-chart-movie";
    chartTitle = "좋아요 수 ";
    barChart(chartName, chartTitle, movieContent, movieColor, movieCnt);

    // 인기배우 차트표
    if(actorLikeNumClass.length <= 10){
        for(var i = 0; i < actorLikeNumClass.length; i++){
            let x = Math.floor(Math.random() * 20);

            actorProfileClass[i].style.display="block";
            actorContent[i] = actorNmClass[i].value;
            actorCnt[i] = Number(actorLikeNumClass[i].value);
            actorColor[i] = colors[x];
        }
    }else{
        // actorRightBtnId.style.display="flex";
        for(var i = 0; i < 10; i++){
            let x = Math.floor(Math.random() * 20);

            actorProfileClass[i].style.display="block";
            actorContent[i] = actorNmClass[i].value;
            actorCnt[i] = Number(actorLikeNumClass[i].value);
            actorColor[i] = colors[x];
        }
    }

    //바 차트 인기 배우
    chartName = "bar-chart-actor";
    chartTitle = "좋아요 수 ";
    barChart(chartName, chartTitle, actorContent, actorColor, actorCnt);
}

const barChart = (cartId, title, content, color, cnt) => {
    new Chart(document.getElementById(cartId), {
        type: 'bar',
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