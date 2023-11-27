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