let dailyLeftBtnId = document.getElementById("dailyLeftBtn");
let weeklyLeftBtn0Id = document.getElementById("weeklyLeftBtn0");
let weeklyLeftBtn1Id = document.getElementById("weeklyLeftBtn1");
let weeklyLeftBtn2Id = document.getElementById("weeklyLeftBtn2");
let upcomingLeftBtnId = document.getElementById("upcomingLeftBtn");
let recentlyKoLeftBtnId = document.getElementById("recentlyKoLeftBtn");

let dailyRightBtnId = document.getElementById("dailyRightBtn");
let weeklyRightBtn0Id = document.getElementById("weeklyRightBtn0");
let weeklyRightBtn1Id = document.getElementById("weeklyRightBtn1");
let weeklyRightBtn2Id = document.getElementById("weeklyRightBtn2");
let upcomingRightBtnId = document.getElementById("upcomingRightBtn");
let recentlyKoRightBtnId = document.getElementById("recentlyKoRightBtn");

let dailyTopClass = document.querySelectorAll(".dailyTop");
let weeklyTop0Class = document.querySelectorAll(".weeklyTop0");
let weeklyTop1Class = document.querySelectorAll(".weeklyTop1");
let weeklyTop2Class = document.querySelectorAll(".weeklyTop2");
let upcomingClass = document.querySelectorAll(".upcoming");
let recentlyKoClass = document.querySelectorAll(".recentlyKo");

let upcomingPageNum = 1;
let recentlyKoPageNum = 1;

let upcomingCnt = 0;
let recentlyKoCnt = 0;

const dailyLeftBtn = () => {
    dailyLeftBtnId.style.display = "none";
    dailyRightBtnId.style.display = "block";
    for(var i = 0; i < dailyTopClass.length; i++){
        if(dailyTopClass[i].style.display == ""){
            dailyTopClass[i].style.display = "none";
        }else{
            dailyTopClass[i].style.display = "";
        }
    }
}

const dailyRightBtn = () => {
    dailyLeftBtnId.style.display = "block";
    dailyRightBtnId.style.display = "none";
    for(var i = 0; i < dailyTopClass.length; i++){
        if(dailyTopClass[i].style.display == ""){
            dailyTopClass[i].style.display = "none";
        }else{
            dailyTopClass[i].style.display = "";
        }
    }
}

const weeklyLeftBtn0 = () => {
    weeklyLeftBtn0Id.style.display = "none";
    weeklyRightBtn0Id.style.display = "block";
    for(var i = 0; i < weeklyTop0Class.length; i++){
        if(weeklyTop0Class[i].style.display == ""){
            weeklyTop0Class[i].style.display = "none";
        }else{
            weeklyTop0Class[i].style.display = "";
        }
    }
}

const weeklyRightBtn0 = () => {
    weeklyLeftBtn0Id.style.display = "block";
    weeklyRightBtn0Id.style.display = "none";
    for(var i = 0; i < weeklyTop0Class.length; i++){
        if(weeklyTop0Class[i].style.display == ""){
            weeklyTop0Class[i].style.display = "none";
        }else{
            weeklyTop0Class[i].style.display = "";
        }
    }
}

const weeklyLeftBtn1 = () => {
    weeklyLeftBtn1Id.style.display = "none";
    weeklyRightBtn1Id.style.display = "block";
    for(var i = 0; i < weeklyTop1Class.length; i++){
        if(weeklyTop1Class[i].style.display == ""){
            weeklyTop1Class[i].style.display = "none";
        }else{
            weeklyTop1Class[i].style.display = "";
        }
    }
}

const weeklyRightBtn1 = () => {
    weeklyLeftBtn1Id.style.display = "block";
    weeklyRightBtn1Id.style.display = "none";
    for(var i = 0; i < weeklyTop1Class.length; i++){
        if(weeklyTop1Class[i].style.display == ""){
            weeklyTop1Class[i].style.display = "none";
        }else{
            weeklyTop1Class[i].style.display = "";
        }
    }
}

const weeklyLeftBtn2 = () => {
    weeklyLeftBtn2Id.style.display = "none";
    weeklyRightBtn2Id.style.display = "block";
    for(var i = 0; i < weeklyTop2Class.length; i++){
        if(weeklyTop2Class[i].style.display == ""){
            weeklyTop2Class[i].style.display = "none";
        }else{
            weeklyTop2Class[i].style.display = "";
        }
    }
}

const weeklyRightBtn2 = () => {
    weeklyLeftBtn2Id.style.display = "block";
    weeklyRightBtn2Id.style.display = "none";
    for(var i = 0; i < weeklyTop2Class.length; i++){
        if(weeklyTop2Class[i].style.display == ""){
            weeklyTop2Class[i].style.display = "none";
        }else{
            weeklyTop2Class[i].style.display = "";
        }
    }
}

const upcomingLeftBtn = () => {
    upcomingPageNum--;
    if(upcomingPageNum == 1){
        upcomingLeftBtnId.style.display = "none";
        upcomingRightBtnId.style.display = "block";
        for(var i = 0; i < upcomingClass.length; i++){
            if(i >= upcomingPageNum*5-5 && i < upcomingPageNum*5){
                upcomingClass[i].style.display = "";
            }else{
                upcomingClass[i].style.display = "none";
            }
        }
    }else{
        upcomingLeftBtnId.style.display = "block";
        upcomingRightBtnId.style.display = "block";
        for(var i = 0; i < upcomingClass.length; i++){
            if(i >= upcomingPageNum*5-5 && i < upcomingPageNum*5){
                upcomingClass[i].style.display = "";
            }else{
                upcomingClass[i].style.display = "none";
            }
        }
    }
}

const upcomingRightBtn = (cnt) => {
    upcomingCnt = Number(cnt);
    let pageNum = Math.ceil(upcomingCnt / 5);
    upcomingPageNum++;
    if(upcomingPageNum == pageNum){
        upcomingLeftBtnId.style.display = "block";
        upcomingRightBtnId.style.display = "none";
        for(var i = 0; i < upcomingClass.length; i++){
            if(i >= upcomingPageNum*5-5 && i < upcomingPageNum*5){
                upcomingClass[i].style.display = "";
            }else{
                upcomingClass[i].style.display = "none";
            }
        }
    }else{
        upcomingLeftBtnId.style.display = "block";
        upcomingRightBtnId.style.display = "block";
        for(var i = 0; i < upcomingClass.length; i++){
            if(i >= upcomingPageNum*5-5 && i < upcomingPageNum*5){
                upcomingClass[i].style.display = "";
            }else{
                upcomingClass[i].style.display = "none";
            }
        }
    }
}

const recentlyKoLeftBtn = () => {
    recentlyKoPageNum--;
    if(recentlyKoPageNum == 1){
        recentlyKoLeftBtnId.style.display = "none";
        recentlyKoRightBtnId.style.display = "block";
        for(var i = 0; i < recentlyKoClass.length; i++){
            if(i >= recentlyKoPageNum*5-5 && i < recentlyKoPageNum*5){
                recentlyKoClass[i].style.display = "";
            }else{
                recentlyKoClass[i].style.display = "none";
            }
        }
    }else{
        recentlyKoLeftBtnId.style.display = "block";
        recentlyKoRightBtnId.style.display = "block";
        for(var i = 0; i < recentlyKoClass.length; i++){
            if(i >= recentlyKoPageNum*5-5 && i < recentlyKoPageNum*5){
                recentlyKoClass[i].style.display = "";
            }else{
                recentlyKoClass[i].style.display = "none";
            }
        }
    }
}

const recentlyKoRightBtn = (cnt) => {
    recentlyKoCnt = Number(cnt);
    let pageNum = Math.ceil(recentlyKoCnt / 5);
    recentlyKoPageNum++;
    if(recentlyKoPageNum == pageNum){
        recentlyKoLeftBtnId.style.display = "block";
        recentlyKoRightBtnId.style.display = "none";
        for(var i = 0; i < recentlyKoClass.length; i++){
            if(i >= recentlyKoPageNum*5-5 && i < recentlyKoPageNum*5){
                recentlyKoClass[i].style.display = "";
            }else{
                recentlyKoClass[i].style.display = "none";
            }
        }
    }else{
        recentlyKoLeftBtnId.style.display = "block";
        recentlyKoRightBtnId.style.display = "block";
        for(var i = 0; i < recentlyKoClass.length; i++){
            if(i >= recentlyKoPageNum*5-5 && i < recentlyKoPageNum*5){
                recentlyKoClass[i].style.display = "";
            }else{
                recentlyKoClass[i].style.display = "none";
            }
        }
    }
}