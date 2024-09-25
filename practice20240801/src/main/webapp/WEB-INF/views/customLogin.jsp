<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta charset="UTF-8" />
	<title>로그인 LOGIN</title>
</head>
<body>
	<h1>🔒 로그인 페이지</h1>
	<h2><c:out value="${error}" /></h2>
	<h2><c:out value="${logout}" /></h2>
	
	<form role="form" method="post" action="/login">
		<fieldset>
			<label><input type="text" name="username" placeholder="아이디를 입력해주세요." autofocus /></label>
			<label><input type="password" name="password" placeholder="비밀번호를 입력해주세요." value="" /></label>
			<label><input type="checkbox" name="remember-me" />로그인 유지(Remember Me)</label>
			<a href="index.html" class="btn-success">로그인</a>
		</fieldset>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	</form>
	<script
		src="https://code.jquery.com/jquery-3.7.1.min.js"
		integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
		crossorigin="anonymous">
	</script>
	<script>
	$(document).ready(function(){
		$(".btn-success").on("click",function(e){
			e.preventDefault();
			$("form").submit();
		});
	});	
	</script>
</body>
</html>