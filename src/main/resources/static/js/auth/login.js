const registerBtn = document.querySelector(".register-btn");

// 비밀번호 보기 토글
function togglePassword(inputId) {
    const pwInput = document.getElementById(inputId);
    pwInput.type = pwInput.type === "password" ? "text" : "password";
}
function registerBtnClickEvent(e){
    hrefUrl("/user/register");
}

// 페이지 준비 시 초기 실행 (예: 에러 메시지 자동 실행)
document.addEventListener("DOMContentLoaded", () => {
    registerBtn.addEventListener('click', registerBtnClickEvent);
    document.getElementById("loginForm").addEventListener("submit", async function(e) {
        e.preventDefault();
        const loading = document.getElementById("loading");

        const formData = {
            userId: this[name="userId"].value,
            pass: this[name="pass"].value
        };

        fetchUse("/user/login", "POST", "application/json", formData,"로그인 중입니다...")
        .then(response => {
            if(!response.ok){
                return response.text().then(msg=>{
                    throw new Error(msg);
                });
            }
            return response.text();
        })
        .then(result => {
            hrefUrl("/main");
        })
        .catch(error =>{
            alert(JSON.parse(error.message).message);
            loading.style.display = "none";
        });
    });
});


