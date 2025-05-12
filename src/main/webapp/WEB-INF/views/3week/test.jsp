<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>login</title>

  <!-- Tailwind & 공통 스타일 -->
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="/static/css/style.css" />
  <script src="/static/js/script.js"></script>
</head>
<body class="flex items-center justify-center min-h-screen">

  <div class="login-box w-[380px] p-8 rounded-xl text-center">
    <h1 class="text-3xl text-yellow-300 mb-6 tracking-widest">NETWORK LOGIN</h1>

    <form class="space-y-5" method="POST" action="/test/login-auth-all-newline">
      <div>
        <label class="block mb-1 text-sm text-gray-400 text-left">관리자 ID</label>
        <input type="text" class="input-field w-full py-2 px-4 rounded-md" placeholder="Enter your ID" name="userId" value="${userId}">
      </div>
      <div>
        <label class="block mb-1 text-sm text-gray-400 text-left">비밀번호</label>
        <div class="relative">
          <input type="password" id="password" name="pass" class="input-field w-full py-2 px-4 rounded-md" placeholder="Enter your password">
          <button type="button" onclick="togglePassword('password')" class="absolute right-3 top-2 text-sm text-gray-400">👁</button>
        </div>
      </div>
      <button type="submit" class="glow-btn w-full py-3 mt-4 rounded-md">시스템 접속</button>
      <button type="button" class="glow-btn w-full py-3 mt-4 rounded-md" onclick="location.href='/register'">회원가입</button>

      <p class="text-xs text-gray-500 mt-3">ⓒ Co. - jeongmooon.</p>
    </form>

    <select name="chooseUrl" id="chooseUrl" class="glow-btn w-full py-3 mt-4 rounded-md">
        <option value="/test/login-auth-all">식별/인증 동시</option>
        <option value="/test/login-auth-divide">식별/인증 분리</option>
        <option value="/test/login-auth-all-newline">식별/인증 개행 동시</option>
        <option value="/test/login-auth-all-param-newline">식별/인증 파라미터 개행 동시</option>
        <option value="/test/login-auth-all-hash">식별/인증 해시 인증 동시</option>
        <option value="/test/login-auth-divide-hash">식별/인증 해시 인증 분리</option>
        <option value="/test/login-auth-all-hash-param-newline">식별/인증 해시 파라미터 개행 동시</option>
        <option value="/test/login-auth-all-hash-bcryte">식별/인증 해시 Bcryte 동시</option>
        <option value="/test/login-auth-divide-hash-bcryte">식별/인증 해시 Bcryte 분리</option>
        <option value="/test/login-auth-jwt">식별/인증 해시 Jwt</option>
        </option>
    </select>

    <!-- 에러 알림용 히든 값 -->
    <input type="hidden" id="error-flag" value="${result}" />
  </div>
  <script>
    const urlSelect = document.getElementById("chooseUrl");
    urlSelect.addEventListener("change",(e)=>{
        document.querySelector("form.space-y-5").action = e.target.value;
    });
  </script>
  <script src="/static/js/keylogger.js"></script>
</body>
</html>
