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
  <script src="/static/js/auth/login.js"></script>
</head>
<div class="flex items-center justify-center min-h-screen">

  <div class="login-box w-[380px] p-8 rounded-xl text-center">
    <h1 class="text-3xl text-yellow-300 mb-6 tracking-widest">NETWORK LOGIN</h1>

    <form class="space-y-5" id="loginForm" method="POST" action="/login">
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
      <button type="button" class="glow-btn w-full py-3 mt-4 rounded-md" onclick="location.href='/user/register'">회원가입</button>

      <p class="text-xs text-gray-500 mt-3">ⓒ Co. - jeongmooon.</p>
    </form>

    <!-- 에러 알림용 히든 값 -->
    <input type="hidden" id="error-flag" value="${result}" />
  </div>

</div>
</html>
