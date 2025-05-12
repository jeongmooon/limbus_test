<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Limbus Character Codex</title>

  <!-- Tailwind & 공통 스타일 -->
  <script src="https://cdn.tailwindcss.com"></script>
  <link rel="stylesheet" href="/static/css/style.css" />
</head>
<body class="p-8">

    <div class="flex items-center justify-between mb-8">
      <h1 class="text-4xl text-yellow-400">📖 캐릭터 도감</h1>
      <button class="text-sm text-yellow-300 border border-grey-300 px-3 py-1 rounded hover:bg-yellow-300 hover:text-black transition edit-identity-button">
        ✏️ 수정
      </button>
    </div>
    <div class="px-6" id="container">
      <!-- 도감 달성률 바 -->
      <div class="mb-8 dictionary-bar">
        <p class="text-yellow-300 text-lg mb-2">
          📘 ${userId}님의 도감 달성률
          <span id="identity-dictionary-text" class="text-xs text-gray-300 mt-1">(${prisonerGuid}) ${rate}%</span>
        </p>
        <div class="w-full h-3 bg-gray-700 rounded-full overflow-hidden">
          <div id="identity-dictionary-rate" class="h-full bg-yellow-400" style="width: ${rate}%"></div>
        </div>
      </div>
    </div>

  <script src="/js/userIdentity/userIdentity.js"></script>
</body>
</html>
