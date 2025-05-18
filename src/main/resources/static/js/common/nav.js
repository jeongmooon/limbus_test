const navHomeBtn = document.querySelector("#nav-home-btn");
const toggleBtn = document.getElementById('menu-toggle');
const navMenuBtn = document.querySelectorAll(".nav-menu");
const menu = document.getElementById('menu');

function navHomeEvent(){
    hrefUrl("/main");
}

function navMenuClickEvent(){
    hrefUrl(this.dataset["url"]);
}

function toggleEvent(){
    menu.classList.toggle('hidden');
}
window.addEventListener('DOMContentLoaded', function() {
    navHomeBtn.addEventListener('click',navHomeEvent);
    toggleBtn.addEventListener('click',toggleEvent);
    navMenuBtn.forEach(navMenu=>{
        navMenu.addEventListener('click',navMenuClickEvent);
    });
});