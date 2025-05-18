function hrefUrl(url){
    const loading = document.getElementById("loading-href");
    loading.style.display = "flex";

    setTimeout(() => {
        window.location.href = url;
    }, 300); // 0.3초 정도 여유
}

function fetchUse(url,method,type,body){
    const loading = document.getElementById("loading");
    loading.style.display = "flex";

    return fetch(url, {
               method: method,
               headers: {
                   "Content-Type": type
               },
               body: JSON.stringify(body)
           });
}