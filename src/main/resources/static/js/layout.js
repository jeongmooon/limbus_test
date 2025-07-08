function hrefUrl(url){
    const loading = document.getElementById("loading-href");
    loading.style.display = "flex";

    setTimeout(() => {
        window.location.href = url;
        loading.style.display = "none";
    }, 300); // 0.3초 정도 여유
}

function fetchUse(url, method, type = null, body = null, loadingMessage = "") {
    const loading = document.getElementById("loading");
    loading.querySelector(".loading-text").textContent = loadingMessage || "로딩 중...";
    loading.style.display = "flex";

    const options = {
        method: method,
        headers: {},
    };

    if (type) {
        options.headers["Content-Type"] = type;
    }

    if (body) {
        options.body = JSON.stringify(body);
    }

    return fetch(url, options);
}