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

<div class="px-6" id="container">
  <div class="mb-8">
    <div class="flex items-end justify-between gap-4 mb-8">
      <div id="top-label">
        <p class="text-yellow-300 text-lg">
          🧩 ${userId}님의 덱 빌딩
          <span class="text-sm text-gray-400 ml-2">(${prisonerGuid})</span>
        </p>


          <label for="deck-code" class="block text-sm text-gray-200 mt-2 mb-1">덱 코드</label>
          <select id="deck-code" name="deck-code" class="w-64 p-2 rounded bg-gray-800 text-white">
            <c:if test="${!deckListCode.isEmpty()}">
                <c:forEach var="deckCode" items="${deckListCode}">
                  <option value="${deckCode}">${deckCode}</option>
                </c:forEach>
            </c:if>
            <c:if test="${deckListCode.isEmpty()}">
                <option value="">새로운 덱</option>
            </c:if>
          </select>
      </div>
      <div>
          <button class="text-sm text-yellow-300 border border-grey-300 px-4 py-2 rounded hover:bg-yellow-300 hover:text-black transition new-deck-list h-fit">
            ✏️ 추가
          </button>
          <button class="text-sm text-yellow-300 border border-grey-300 px-4 py-2 rounded hover:bg-yellow-300 hover:text-black transition save-deck h-fit">
            ✏️ 저장
          </button>
      </div>
    </div>


    <!-- 20칸 그리드 -->
<div class="grid gap-2 mb-8" style="grid-template-columns: repeat(20, minmax(0, 1fr));">
  <c:forEach var="sinner" items="${sinnerList}">
    <c:forEach var="identity" items="${sinner.userIdentities}">
      <div
        class="w-full aspect-square bg-gray-800 rounded overflow-hidden"
        draggable="true"
        ondragstart="handleDragStart(event)"
        data-id="${identity.id}"
        data-sinner="${sinner.id}"
        data-img="/image/prisoner/${sinner.id}/${identity.imgPath}.webp"
      >
        <img
          src="/image/prisoner/${sinner.id}/${identity.imgPath}.webp"
          alt="${identity.name}"
          class="object-cover w-full h-full hover:scale-105 transition-transform duration-200"
        />
      </div>
    </c:forEach>
  </c:forEach>
</div>

<button id="deck-add-btn" class="mb-4 px-4 py-2 bg-yellow-400 text-black rounded hover:bg-yellow-300">
  ➕ 덱 추가
</button>

<div id="deck-zones-container" class="space-y-4"></div>
<script src="/js/deck/deck.js"></script>
</body>
</html>
