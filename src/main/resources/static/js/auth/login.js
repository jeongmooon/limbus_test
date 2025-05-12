// 비밀번호 보기 토글
function togglePassword(inputId) {
    const pwInput = document.getElementById(inputId);
    pwInput.type = pwInput.type === "password" ? "text" : "password";
}

// 페이지 준비 시 초기 실행 (예: 에러 메시지 자동 실행)
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("loginForm").addEventListener("submit", async function(e) {
        e.preventDefault(); // ⛔ 기본 제출 막기

        const formData = {
            userId: this[name="userId"].value,
            pass: this[name="pass"].value
        };

        fetch("/user/login", {
            method: "POST",
            headers: {
              "Content-Type": "application/json"
            },
            body: JSON.stringify(formData)
        })
        .then(response => {
            if(!response.ok){
                return response.text().then(msg=>{
                    throw new Error(msg);
                });
            }
            return response.text();
        })
        .then(result => {
            location.href = "/main";
        })
        .catch(error =>{
            alert(JSON.parse(error.message).message);
        });
    });
});


