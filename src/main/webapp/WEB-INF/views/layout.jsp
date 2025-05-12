<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/common/navbar.jsp" %>
<%
    String body = (String) request.getAttribute("body");
    String pageToInclude = body + ".jsp";
%>
<body class="pt-16 bg-gray-950 text-white" style="margin-bottom:40px;margin-left:40px;margin-right:40px;">
    <div class="max-w-8.5xl mx-auto px-2">
        <!-- 여기에 모든 콘텐츠 넣기 -->
        <jsp:include page="<%= pageToInclude %>" />
    </div>
</body>
