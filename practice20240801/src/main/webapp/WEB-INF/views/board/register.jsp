
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
    
<%@ include file="/WEB-INF/views/include/header.jspf" %>	

<div class="board register">

	<form role="form" id="reg_form" action="/board/register" method="post">
		
			
			<label>타이틀<input class="form_control" id="title" name="title" /></label>
	
		
			
			<label>글쓰는 곳<textarea class="form_control" rows="18"  id="content" name="content"></textarea></label>
		
		
			<label>작성자 누구?<input class="form_control" id="writer" name="writer" /></label>
			<!-- name속성은 BoardVO클래스의 변수와 일치시켜준다. -->
		
			<div class="buttons">
				<button type="reset">리셋</button>
				<button type="submit">글쓰기 버튼</button>
			</div>
			
	</form>
</div>

<script type="text/javascript">	
</script>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>