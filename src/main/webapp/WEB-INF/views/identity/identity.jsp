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
    <form id="identity-form" enctype="multipart/form-data">
        <div class="mb-4">
          <label class="block text-sm text-gray-200 mb-1">소속 캐릭터 (Sinner)</label>
          <select name="sinnerId" class="w-full p-2 rounded bg-gray-800 text-white" required>
            <c:forEach var="sinner" items="${sinnerList}">
              <option value="${sinner.id}">${sinner.name}</option>
            </c:forEach>
          </select>
        </div>

        <div class="mb-4">
            <label class="block text-sm text-gray-200 mb-1">이름</label>
            <input type="text" name="name" class="w-full p-2 rounded bg-gray-800 text-white" required />
        </div>

        <div class="mb-4">
            <label class="block text-sm text-gray-200 mb-1">시즌</label>
            <input type="number" name="season" class="w-full p-2 rounded bg-gray-800 text-white" required />
        </div>

        <div class="mb-4">
            <label class="block text-sm text-gray-200 mb-1">등급</label>
            <input type="number" name="grade" class="w-full p-2 rounded bg-gray-800 text-white" required />
        </div>

        <div class="mb-4">
            <label class="block text-sm text-gray-200 mb-1">인격 이미지</label>
            <input type="file" name="file" accept="image/*" class="w-full text-white" required />
        </div>

        <button type="submit" class="bg-yellow-400 text-black px-4 py-2 rounded hover:bg-yellow-300">추가하기</button>
    </form>
    <script src="/js/identity/identity.js"></script>
</body>
</html>
