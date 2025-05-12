<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<nav class="fixed top-0 left-0 w-full bg-gray-900 text-white shadow z-50">
  <div class="flex justify-between items-center px-6 py-3">
    <!-- 로고 -->
    <h1 class="text-xl font-bold cursor-pointer" id="nav-home-btn">Limbus Company</h1>

    <!-- 햄버거 버튼 (모바일 전용) -->
    <button id="menu-toggle" class="md:hidden focus:outline-none">
      <svg class="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24"
           xmlns="http://www.w3.org/2000/svg">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
              d="M4 6h16M4 12h16M4 18h16"></path>
      </svg>
    </button>

    <!-- 메뉴 항목들 -->
    <div id="menu" class="hidden md:flex flex-col md:flex-row md:items-center md:space-x-4 absolute md:static top-full left-0 w-full md:w-auto bg-gray-900 md:bg-transparent shadow md:shadow-none z-40">
      <c:forEach var="menu" items="${menuList}">
        <a href="${menu.url}" class="block px-2 py-1 hover:text-yellow-400">${menu.name}</a>
      </c:forEach>
    </div>
  </div>
</nav>
<script src="/js/common/nav.js"></script>
