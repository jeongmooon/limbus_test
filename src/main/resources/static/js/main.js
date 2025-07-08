const mainCards = document.querySelectorAll(".main-card");

function mainCardClickEvent(){
    const loading = document.getElementById("loading");
    hrefUrl(this.dataset["url"]);
    loading.style.display = "none";
}
mainCards.forEach(mainCard=>{
    const loading = document.getElementById("loading");
    mainCard.addEventListener('click',mainCardClickEvent);
    loading.style.display = "none";
});