<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>회원가입</title>

  <!-- Tailwind 및 공통 스타일 -->
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="/static/css/style.css" />
  <script src="/static/js/auth/register.js"></script>
</head>
<div class="flex items-center justify-center min-h-screen">

  <div class="login-box w-[420px] p-8 rounded-xl text-center">
    <h1 class="text-3xl text-yellow-300 mb-6 tracking-widest">회원가입</h1>

    <form class="space-y-5" id="registerForm" method="POST" action="/register">
      <div>
        <label class="block mb-1 text-sm text-gray-400 text-left">관리자 ID</label>
        <input type="text" class="input-field w-full py-2 px-4 rounded-md" id="userId" name="userId" placeholder="아이디 입력" value="${userCreateRequest.userId}" />
      </div>

      <div>
        <label class="block mb-1 text-sm text-gray-400 text-left">비밀번호</label>
        <div class="relative">
          <input type="password" id="password" class="input-field w-full py-2 px-4 rounded-md" name="pass" placeholder="비밀번호 입력" />
          <button type="button" onclick="togglePassword('password')" class="absolute right-3 top-2 text-sm text-gray-400">👁</button>
        </div>
      </div>

      <div>
        <label class="block mb-1 text-sm text-gray-400 text-left">비밀번호 확인</label>
        <div class="relative">
          <input type="password" id="confirmPassword" class="input-field w-full py-2 px-4 rounded-md" name="confirmPass" placeholder="비밀번호 재입력" />
          <button type="button" onclick="togglePassword('confirmPassword')" class="absolute right-3 top-2 text-sm text-gray-400">👁</button>
        </div>
      </div>

      <div>
        <label class="block mb-1 text-sm text-gray-400 text-left">게임 코드</label>
        <input type="text" class="input-field w-full py-2 px-4 rounded-md" id="limbusGameCode" name="limbusGameCode" placeholder="sdfr4en" value="${userCreateRequest.limbusGameCode}" />
      </div>

      <button type="submit" class="glow-btn w-full py-3 mt-4 rounded-md">가입하기</button>
      <button type="button" class="glow-btn w-full py-3 mt-4 rounded-md" onclick="location.href='/login'">로그인으로</button>

      <p class="text-xs text-gray-500 mt-3">ⓒ Limbus Co. - 모든 권한 보유.</p>
    </form>
  </div>

    <!-- 알림용 히든 값 -->
    <input type="hidden" id="error-flag" value="${result}" />
</div>
</html>
