<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta charset="UTF-8" />
<title>ACCESS ERROR</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
</head>
<body>
	<section class="text-center">
		<h1>💫 엑세스 제한 페이지 💫</h1>
		<h1>🪐 ACCESS DENIED PAGE🪐 </h1>
		
		<h2><c:out value="${SPRING_SECURITY_403_EXCEPTION.getMessage()}" /></h2>
		<h2><c:out value="${msg}" /></h2>
	</section>	
</body>
</html>