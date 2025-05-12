const mainCards = document.querySelectorAll(".main-card");

function mainCardClickEvent(){
    window.location.href = this.dataset["url"];
}
mainCards.forEach(mainCard=>{
    mainCard.addEventListener('click',mainCardClickEvent);
});