// 비밀번호 보기 토글
function togglePassword(inputId) {
  const pwInput = document.getElementById(inputId);
  pwInput.type = pwInput.type === "password" ? "text" : "password";
}

// 오류 메시지 알림
function showError(message) {
  alert(message);
}

// 페이지 준비 시 초기 실행 (예: 에러 메시지 자동 실행)
document.addEventListener("DOMContentLoaded", () => {
  const errorFlag = document.getElementById("error-flag");
  if (errorFlag && errorFlag.value === "true") {
    showError("입력정보가 올바르지 않습니다.");
  }
});
