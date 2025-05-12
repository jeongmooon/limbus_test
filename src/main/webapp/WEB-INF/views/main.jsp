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
    <script>
        tailwind.config = {
            theme: {
                    extend: {},
                },
                plugins: [tailwindcssAspectRatio],
        };
    </script>
    <script src="https://cdn.tailwindcss.com?plugins=aspect-ratio"></script>
    </body>
</head>
<body class="p-8">

    <div class="max-w-screen-xl mx-auto space-y-12">
        <!-- 유튜브 영상 -->
        <div class="aspect-[16/9] w-full rounded-lg overflow-hidden shadow-lg">
            <iframe
                src="https://www.youtube.com/embed/${videoId}"
                title="YouTube video player"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                allowfullscreen
                class="w-full h-full"
            ></iframe>
        </div>

<div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-6">
  <!-- 카드 예시 -->
  <div class="relative h-40 overflow-hidden rounded-lg shadow-md group cursor-pointer main-card" data-url="/user-identity">
    <!-- 배경 이미지 줌업 -->
    <div class="absolute inset-0 bg-center bg-cover transition-transform duration-500 scale-100 group-hover:scale-110"
         style="background-image: url('/static/image/main/card1.webp');"></div>

    <!-- 어두운 오버레이 -->
    <div class="absolute inset-0 bg-black bg-opacity-40"></div>

  <!-- 텍스트 + 밑줄 효과 -->
  <div class="relative z-10 flex items-center justify-center h-full">
    <span class="text-2xl font-extrabold text-white relative after:absolute after:left-0 after:bottom-0 after:h-[3px] after:bg-white after:w-0 group-hover:after:w-full after:transition-all after:duration-300">
      캐릭터 도감
    </span>
  </div>
  </div>

  <!-- 다른 카드들도 동일하게 반복 -->
  <div class="relative h-40 overflow-hidden rounded-lg shadow-md group cursor-pointer main-card" data-url="/deck">
    <div class="absolute inset-0 bg-center bg-cover transition-transform duration-500 scale-100 group-hover:scale-110"
         style="background-image: url('/static/image/main/card2.webp');"></div>
    <div class="absolute inset-0 bg-black bg-opacity-40"></div>
  <!-- 텍스트 + 밑줄 효과 -->
  <div class="relative z-10 flex items-center justify-center h-full">
    <span class="text-2xl font-extrabold text-white relative after:absolute after:left-0 after:bottom-0 after:h-[3px] after:bg-white after:w-0 group-hover:after:w-full after:transition-all after:duration-300">
      덱 빌딩
    </span>
  </div>
  </div>

  <div class="relative h-40 overflow-hidden rounded-lg shadow-md group cursor-pointer main-card" data-url="/dashboard/identity">
    <div class="absolute inset-0 bg-center bg-cover transition-transform duration-500 scale-100 group-hover:scale-110"
         style="background-image: url('/static/image/main/card3.webp');"></div>
    <div class="absolute inset-0 bg-black bg-opacity-40"></div>
  <!-- 텍스트 + 밑줄 효과 -->
  <div class="relative z-10 flex items-center justify-center h-full">
    <span class="text-2xl font-extrabold text-white relative after:absolute after:left-0 after:bottom-0 after:h-[3px] after:bg-white after:w-0 group-hover:after:w-full after:transition-all after:duration-300">
      인격 통계
    </span>
  </div>
  </div>

  <div class="relative h-40 overflow-hidden rounded-lg shadow-md group cursor-pointer main-card" data-url="">
    <div class="absolute inset-0 bg-center bg-cover transition-transform duration-500 scale-100 group-hover:scale-110"
         style="background-image: url('/static/image/main/card4.webp');"></div>
    <div class="absolute inset-0 bg-black bg-opacity-40"></div>
  <!-- 텍스트 + 밑줄 효과 -->
  <div class="relative z-10 flex items-center justify-center h-full">
    <span class="text-2xl font-extrabold text-white relative after:absolute after:left-0 after:bottom-0 after:h-[3px] after:bg-white after:w-0 group-hover:after:w-full after:transition-all after:duration-300">
      인격 도감
    </span>
  </div>
  </div>
</div>
<script src="/js/main.js"></script>
</html>
