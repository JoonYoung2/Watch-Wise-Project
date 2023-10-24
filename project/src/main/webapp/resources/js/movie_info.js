let postersLeftBtnId = document.getElementById("postersLeftBtn");

let postersRightBtnId = document.getElementById("postersRightBtn");

let postersClass = document.querySelectorAll(".posters");

let postersPageNum = 1;

let postersCnt = 0;

const postersLeftBtn = () => {
    postersPageNum--;
    if(postersPageNum == 1){
        postersLeftBtnId.style.display = "none";
        postersRightBtnId.style.display = "block";
        for(var i = 0; i < postersClass.length; i++){
            if(i >= postersPageNum*5-5 && i < postersPageNum*5){
                postersClass[i].style.display = "";
            }else{
                postersClass[i].style.display = "none";
            }
        }
    }else{
        postersLeftBtnId.style.display = "block";
        postersRightBtnId.style.display = "block";
        for(var i = 0; i < postersClass.length; i++){
            if(i >= postersPageNum*5-5 && i < postersPageNum*5){
                postersClass[i].style.display = "";
            }else{
                postersClass[i].style.display = "none";
            }
        }
    }
}

const postersRightBtn = (cnt) => {
    postersCnt = Number(cnt);
    let pageNum = Math.ceil(postersCnt / 5);
    postersPageNum++;
    if(postersPageNum == pageNum){
        postersLeftBtnId.style.display = "block";
        postersRightBtnId.style.display = "none";
        for(var i = 0; i < postersClass.length; i++){
            if(i >= postersPageNum*5-5 && i < postersPageNum*5){
                postersClass[i].style.display = "";
            }else{
                postersClass[i].style.display = "none";
            }
        }
    }else{
        postersLeftBtnId.style.display = "block";
        postersRightBtnId.style.display = "block";
        for(var i = 0; i < postersClass.length; i++){
            if(i >= postersPageNum*5-5 && i < postersPageNum*5){
                postersClass[i].style.display = "";
            }else{
                postersClass[i].style.display = "none";
            }
        }
    }
}