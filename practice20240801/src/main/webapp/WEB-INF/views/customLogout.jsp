<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta charset="UTF-8" />
	<title>로그아웃</title>
</head>
<body>
	<h1>로그아웃 페이지</h1>
	<form role="form" action="/customLogout" method="post">
		<fieldset>
			<a href="index.html" class="btn-success">로그아웃</a>
		</fieldset>
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<!-- <button>로그아웃</button> -->
	</form>
	
	
	<script
	src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous">
	</script>
	<script>
		$(".btn-success").on("click", function(e){
			e.preventDefault();
			$("form").submit();
		});
	</script>
	<c:if test="${param.logout != null}">
		<script>
		$(document).ready(function(){
			alert("로그아웃하였습니다.");
		});
		/* 20240926 - 교재에서는 customLogout.jsp에 로그아웃 표시 alert코드가 
		기입되어 있었으나 로그아웃 후 alert창 표시가 뜨지않아 customLogtin.jsp파일
		에 코드를 기입하니 alert창 표시가 정상적으로 표시됨을 확인 */
		</script>
	</c:if>
</body>

</html>