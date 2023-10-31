// Paging Method START

let memberCommendLeftBtnId = document.getElementById("memberCommendLeftBtn");
let memberCommend2LeftBtnId = document.getElementById("memberCommend2LeftBtn");

let memberCommendRightBtnId = document.getElementById("memberCommendRightBtn");
let memberCommend2RightBtnId = document.getElementById("memberCommend2RightBtn");

let memberCommendClass = document.querySelectorAll(".memberCommend");
let memberCommend2Class = document.querySelectorAll(".memberCommend2");

let memberCommendPageNum = 1;
let memberCommend2PageNum = 1;

let memberCommendCnt = 0;
let memberCommend2Cnt = 0;

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

const memberCommend2LeftBtn = () => {
    memberCommend2PageNum--;
    if(memberCommend2PageNum == 1){
        memberCommend2LeftBtnId.style.display = "none";
        memberCommend2RightBtnId.style.display = "block";
        for(var i = 0; i < memberCommend2Class.length; i++){
            if(i >= memberCommend2PageNum*5-5 && i < memberCommend2PageNum*5){
                memberCommend2Class[i].style.display = "";
            }else{
                memberCommend2Class[i].style.display = "none";
            }
        }
    }else{
        memberCommend2LeftBtnId.style.display = "block";
        memberCommend2RightBtnId.style.display = "block";
        for(var i = 0; i < memberCommend2Class.length; i++){
            if(i >= memberCommend2PageNum*5-5 && i < memberCommend2PageNum*5){
                memberCommend2Class[i].style.display = "";
            }else{
                memberCommend2Class[i].style.display = "none";
            }
        }
    }
}

const memberCommend2RightBtn = (cnt) => {
    memberCommend2Cnt = Number(cnt);
    let pageNum = Math.ceil(memberCommend2Cnt / 5);
    memberCommend2PageNum++;
    if(memberCommend2PageNum == pageNum){
        memberCommend2LeftBtnId.style.display = "block";
        memberCommend2RightBtnId.style.display = "none";
        for(var i = 0; i < memberCommend2Class.length; i++){
            if(i >= memberCommend2PageNum*5-5 && i < memberCommend2PageNum*5){
                memberCommend2Class[i].style.display = "";
            }else{
                memberCommend2Class[i].style.display = "none";
            }
        }
    }else{
        memberCommend2LeftBtnId.style.display = "block";
        memberCommend2RightBtnId.style.display = "block";
        for(var i = 0; i < memberCommend2Class.length; i++){
            if(i >= memberCommend2PageNum*5-5 && i < memberCommend2PageNum*5){
                memberCommend2Class[i].style.display = "";
            }else{
                memberCommend2Class[i].style.display = "none";
            }
        }
    }
}

// Paging Method END