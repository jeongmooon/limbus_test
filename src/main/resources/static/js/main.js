const mainCards = document.querySelectorAll(".main-card");

function mainCardClickEvent(){
    hrefUrl(this.dataset["url"]);
}
mainCards.forEach(mainCard=>{
    mainCard.addEventListener('click',mainCardClickEvent);
});