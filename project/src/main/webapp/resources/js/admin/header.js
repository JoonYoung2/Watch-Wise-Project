const menuTitleMouseOver = (cnt) => {
    let count = Number(cnt);
    let headerMenuDivClass = document.querySelectorAll(".header-menu-div");
    headerMenuDivClass[count].style.display="block";
}

const menuTitleMouseOut = (cnt) => {
    let count = Number(cnt);
    let headerMenuDivClass = document.querySelectorAll(".header-menu-div");
    headerMenuDivClass[count].style.display="none";
}

const allUpdate = () => {
    alert("모든 정보 업데이트가 진행중입니다.");
    $.ajax({
        url: "http://localhost:8081/allUpdate",
        method: "GET",
        data: {
            
        },
        success: function (response) {
            alert("모든 정보 업데이트가 완료되었습니다.");
            return;
        },
        error: function (xhr, status, error) {
            alert("Cors 설정 후 다시 실행해주세요.");
            return;
        }
    });
}

const liveSearchUpdate = () => {
    alert("실시간 검색어 업데이트가 진행중입니다.");
    $.ajax({
        url: "http://localhost:8081/liveSearchUpdate",
        method: "GET",
        data: {
            
        },
        success: function (response) {
            alert("실시간 검색어 업데이트가 완료되었습니다.");
            return;
        },
        error: function (xhr, status, error) {
            alert("Cors 설정 후 다시 실행해주세요.");
            return;
        }
    });
}