let menuEvent = document.getElementById("menuList");

menuEvent.addEventListener("mouseover", function (event) {
    event.target.style.color = "#ff7952";
}, false);


menuEvent.addEventListener("mouseout", function(event){
    event.target.style.color = "#363c41";
}, false)