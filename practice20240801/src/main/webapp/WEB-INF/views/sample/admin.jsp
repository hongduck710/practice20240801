<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>     
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="UTF-8">
<title>admin</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/common.css" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css" />
<style>
section{width:95%; margin:auto;}
ul{border: 4px solid #e690dc;border-radius: 21px;padding: 34px;}

</style>
</head>
<body>
	<section>
		<h2>/sample/admin 페이지</h2>
		<ul>
			<li>principal : <sec:authentication property="principal" /></li>
			<li>MemberVO : <sec:authentication property="principal.member" /></li>
			<li>사용자 아이디 : <sec:authentication property="principal.username" /></li>
			<li>사용자 권한 리스트 : <sec:authentication property="principal.member.authList" /></li>
			<li>🔮🔮MemberVO에 있는 userName은??? : <sec:authentication property="principal.member.userName" /></li>
		</ul>
		<a href="/customLogout">로그아웃(log out)</a>
	</section>	
</body>
</html>