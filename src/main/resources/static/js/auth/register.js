function togglePassword(id) {
    const field = document.getElementById(id);
    if (field.type === 'password') {
        field.type = 'text';
    } else {
        field.type = 'password';
    }
}

// 오류 메시지 알림
function showError(message) {
    alert(message);
}

document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');

    const validateField = (value, fieldName, maxLength = 40, isKorean = false) => {
        if (value === '') {
            return `${fieldName}를 입력하세요.`;
        }
        if (value.length > maxLength) {
            return `${fieldName}는 ${maxLength}자 이하로 입력해주세요.`;
        }
        if (isKorean && /[ㄱ-ㅎㅏ-ㅣ가-힣]/.test(value)) {
            return `${fieldName}에는 한글을 사용할 수 없습니다.`;
        }
        return null;
    };

    if (form) {
        form.addEventListener('submit', function (e) {
            const userId = document.getElementById('userId').value;
            const limbusGameCode = document.getElementById('limbusGameCode').value;
            const pw = document.getElementById('password').value;
            const confirmPw = document.getElementById('confirmPassword')?.value;

            // 유효성 검사
            let message = validateField(userId, '아이디', 40, true) ||
                          validateField(pw, '비밀번호') ||
                          validateField(limbusGameCode, '게임 코드');

            // 비밀번호 확인 체크
            if (!message && confirmPw !== undefined && pw !== confirmPw) {
                message = '비밀번호가 일치하지 않습니다.';
            }

            if (message) {
                alert(message);
                return e.preventDefault();
            }
        });
    }

    document.getElementById("registerForm").addEventListener("submit", async function(e) {
        e.preventDefault();

        const formData = {
            userId: document.getElementById("userId").value,
            pass: document.getElementById("password").value,
    		limbusGameCode : document.getElementById("limbusGameCode").value
        };

        fetchUse("/user/register", "POST", "application/json", formData, "회원가입 중입니다...")
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
        });
    });
});

