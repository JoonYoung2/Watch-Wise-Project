let movieInfoLeftBtnId = document.getElementById("movieInfoLeftBtn");

let movieInfoRightBtnId = document.getElementById("movieInfoRightBtn");

let movieInfoClass = document.querySelectorAll(".movieInfo");

let movieInfoPageNum = 1;

let movieInfoCnt = 0;

const movieInfoLeftBtn = () => {
    movieInfoPageNum--;
    if(movieInfoPageNum == 1){
        movieInfoLeftBtnId.style.display = "none";
        movieInfoRightBtnId.style.display = "block";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }else{
        movieInfoLeftBtnId.style.display = "block";
        movieInfoRightBtnId.style.display = "block";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }
}

const movieInfoRightBtn = (cnt) => {
    movieInfoCnt = Number(cnt);
    let pageNum = Math.ceil(movieInfoCnt / 5);
    movieInfoPageNum++;
    if(movieInfoPageNum == pageNum){
        movieInfoLeftBtnId.style.display = "block";
        movieInfoRightBtnId.style.display = "none";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }else{
        movieInfoLeftBtnId.style.display = "block";
        movieInfoRightBtnId.style.display = "block";
        for(var i = 0; i < movieInfoClass.length; i++){
            if(i >= movieInfoPageNum*5-5 && i < movieInfoPageNum*5){
                movieInfoClass[i].style.display = "";
            }else{
                movieInfoClass[i].style.display = "none";
            }
        }
    }
}