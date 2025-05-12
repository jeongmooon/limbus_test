const navHomeBtn = document.querySelector("#nav-home-btn");
const toggleBtn = document.getElementById('menu-toggle');
const menu = document.getElementById('menu');

function navHomeEvent(){
    window.location.href = "/main";
}

function toggleEvent(){
    menu.classList.toggle('hidden');
}

navHomeBtn.addEventListener('click',navHomeEvent);
toggleBtn.addEventListener('click',toggleEvent);