<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ include file="/WEB-INF/views/include/header.jspf" %>	

<div class="board modify">
	<form role="form" action="/board/modify" method="post" id="board_form">
		
		<input type="hidden" name="pageNum" value="<c:out value='${cri.pageNum}' /> "/>
		<input type="hidden" name="amount" value="<c:out value='${cri.amount}' />"/>
		<input type="hidden" name="type" value="<c:out value='${cri.type}' />" />
		<input type="hidden" name="keyword" value="<c:out value='${cri.keyword}' />" />
		
		<label>글번호<input name="bno"  value="<c:out value='${board.bno}' />" readonly="readonly"/></label>
		
		<label>타이틀<input name="title" value="<c:out value='${board.title}' />"  /></label>
	
		<label>내용<textarea name="content" rows="18" ><c:out value="${board.content}" /></textarea></label>
		
		<label>글쓴이 누구?<input name="writer" value="<c:out value='${board.writer}' />"  /></label>
		<!-- 
		<label>등록일<input name="regDate" value="<fmt:formatDate pattern = 'yyyy-MM-dd hh:mm'  value='${board.regdate}'/>"  readonly="readonly"/></label>
		<label>수정일<input name="updateDate" value="<fmt:formatDate pattern='yyyy-MM-dd hh:mm'  value='${board.updateDate}'/>"  readonly="readonly"/></label>
		modify.jsp파일에 글 수정 코드가 있으면 에러가 생김
		 -->

		<div class="buttons">
			<button type="submit" data-oper="modify">글 수정</button>
			<button type="submit" data-oper="remove">글 삭제</button>
			<button type="submit" data-oper="list">목록</button>
		</div>
	</form>
</div>

<script>
(function ($) {
	
	let formObj = $("#board_form");
	
	$('button').on("click", function(e){
		
		e.preventDefault();
		
		let operation = $(this).data("oper");
		
		console.log(operation);
		
		if(operation === 'remove'){
			formObj.attr("action", "/board/remove");
		} else if(operation === 'list'){
			// move to list
			formObj.attr("action", "/board/list").attr("method", "get");
			
			let pageNumTag = $("input[name='pageNum']").clone();
			let amountTag = $("input[name='amount']").clone();
			let keywordTag = $("input[name='keyword']").clone();
			let typeTag = $("input[name='type']").clone();
			
			formObj.empty();
			
			formObj.append(pageNumTag);
			formObj.append(amountTag);
			formObj.append(keywordTag);
			formObj.append(typeTag);
		}
		formObj.submit();
	});
})(jQuery);

</script>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>