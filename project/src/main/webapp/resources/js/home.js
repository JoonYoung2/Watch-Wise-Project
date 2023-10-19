let dailyLeftBtnId = document.getElementById("dailyLeftBtn");
let weeklyLeftBtn0Id = document.getElementById("weeklyLeftBtn0");
let weeklyLeftBtn1Id = document.getElementById("weeklyLeftBtn1");
let weeklyLeftBtn2Id = document.getElementById("weeklyLeftBtn2");
let upcomingLeftBtnId = document.getElementById("upcomingLeftBtn");

let dailyRightBtnId = document.getElementById("dailyRightBtn");
let weeklyRightBtn0Id = document.getElementById("weeklyRightBtn0");
let weeklyRightBtn1Id = document.getElementById("weeklyRightBtn1");
let weeklyRightBtn2Id = document.getElementById("weeklyRightBtn2");
let upcomingRightBtnId = document.getElementById("upcomingRightBtn");

let dailyTopClass = document.querySelectorAll(".dailyTop");
let weeklyTop0Class = document.querySelectorAll(".weeklyTop0");
let weeklyTop1Class = document.querySelectorAll(".weeklyTop1");
let weeklyTop2Class = document.querySelectorAll(".weeklyTop2");
let upcomingClass = document.querySelectorAll(".upcoming");

const dailyLeftBtn = () => {
    dailyLeftBtnId.style.display = "none";
    dailyRightBtnId.style.display = "";
    for(var i = 0; i < dailyTopClass.length; i++){
        if(dailyTopClass[i].style.display == ""){
            dailyTopClass[i].style.display = "none";
        }else{
            dailyTopClass[i].style.display = "";
        }
    }
}

const dailyRightBtn = () => {
    dailyLeftBtnId.style.display = "";
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
    weeklyRightBtn0Id.style.display = "";
    for(var i = 0; i < weeklyTop0Class.length; i++){
        if(weeklyTop0Class[i].style.display == ""){
            weeklyTop0Class[i].style.display = "none";
        }else{
            weeklyTop0Class[i].style.display = "";
        }
    }
}

const weeklyRightBtn0 = () => {
    weeklyLeftBtn0Id.style.display = "";
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
    weeklyRightBtn1Id.style.display = "";
    for(var i = 0; i < weeklyTop1Class.length; i++){
        if(weeklyTop1Class[i].style.display == ""){
            weeklyTop1Class[i].style.display = "none";
        }else{
            weeklyTop1Class[i].style.display = "";
        }
    }
}

const weeklyRightBtn1 = () => {
    weeklyLeftBtn1Id.style.display = "";
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
    weeklyRightBtn2Id.style.display = "";
    for(var i = 0; i < weeklyTop2Class.length; i++){
        if(weeklyTop2Class[i].style.display == ""){
            weeklyTop2Class[i].style.display = "none";
        }else{
            weeklyTop2Class[i].style.display = "";
        }
    }
}

const weeklyRightBtn2 = () => {
    weeklyLeftBtn2Id.style.display = "";
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
    upcomingLeftBtnId.style.display = "none";
    upcomingRightBtnId.style.display = "";
    for(var i = 0; i < upcomingClass.length; i++){
        if(upcomingClass[i].style.display == ""){
            upcomingClass[i].style.display = "none";
        }else{
            upcomingClass[i].style.display = "";
        }
    }
}

const upcomingRightBtn = () => {
    upcomingLeftBtnId.style.display = "";
    upcomingRightBtnId.style.display = "none";
    for(var i = 0; i < upcomingClass.length; i++){
        if(upcomingClass[i].style.display == ""){
            upcomingClass[i].style.display = "none";
        }else{
            upcomingClass[i].style.display = "";
        }
    }
}