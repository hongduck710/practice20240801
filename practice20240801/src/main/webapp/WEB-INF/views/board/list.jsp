<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
<%@ include file="/WEB-INF/views/include/header.jspf" %>	

<div class="board list">
	<h1>컴미 게시판</h1>
	<div class="btn_wrap">
	<!-- <a href="/board/register">글쓰기 ✍</a> -->
		<button id="reg_btn" type="button">글쓰기 ✍</button>
	</div>
	<table>
		<thead>
			<tr>
				<th>🔢 번호</th>
				<th>🖊 제목</th>
				<th>✍ 작성자</th>
				<th>📆 작성일</th>
				<th>🗓 수정일</th>
			</tr>
		</thead>
		<c:forEach items="${list}" var="board">
			<tr>
				<td><a href="<c:out value='${board.bno}' />" class="move"><c:out value="${board.bno}" /></a></td>
				<td><a href="<c:out value='${board.bno}' />" class="move">
							<c:out value="${board.title}" />
							<strong class="reply-count"><c:out value="${board.replyCnt}" /></strong>	
						</a></td>
				<td><c:out value="${board.writer}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${board.regdate}" /></td>
				<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${board.updateDate}" /></td>
			</tr>
		</c:forEach>
	</table>
	
	<form id="searchForm" action="/board/list" method="get">

		<select name='type'>
			<option value=""
				<c:out value="${pageMaker.cri.type == null?'selected':''}" />>🔍🔍🔍🔍🔍🔍🔍🔍</option>
			<option value="T"
				<c:out value="${pageMaker.cri.type eq 'T'?'selected':''}" />>제목</option>
			<option value="C"
				<c:out value="${pageMaker.cri.type eq 'C'?'selected':''}" />>내용</option>
			<option value="W"
				<c:out value="${pageMaker.cri.type eq 'W'?'selected':''}" />>작성자</option>
			<option value="TC"
				<c:out value="${pageMaker.cri.type eq 'TC'?'selected':''}" />>제목 or 내용</option>
			<option value="TW"
				<c:out value="${pageMaker.cri.type eq 'TW'?'selected':''}" />>제목 or 작성자</option>
			<option value="TWC"
				<c:out value="${pageMaker.cri.type eq 'TWC'?'selected':''}" />>제목 or 내용 or 작성자</option>
		</select>
		<input type='text' name='keyword' 
			value='<c:out value="${pageMaker.cri.keyword}" />' />	
		<input type='hidden' name='pageNum' 
			value='<c:out value="${pageMaker.cri.pageNum}"/>' />
		<input type='hidden' name='amount' 
			value='<c:out value="${pageMaker.cri.amount}" />' />
		
		<button>Search</button>	
	</form>
	
	<ul class="pagination">
		<!-- /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제 - start -->
		<c:if test="${pageMaker.prev}">
			<li class="paginate_button last"><a href="${pageMaker.init}"><i class="fa fa-angle-double-left" aria-hidden="true"></i>
</a></li>
		</c:if>	
		<!-- /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제 - end -->
		<c:if test="${pageMaker.prev}">
			<li class="paginate_button prev"><a href="${pageMaker.startPage - 1}"><i class="fa fa-angle-left" aria-hidden="true"></i>
</a></li>
		</c:if>
		
		<c:forEach var="num" begin="${pageMaker.startPage}" end="${pageMaker.endPage}">
			<li class="paginate_button ${pageMaker.cri.pageNum == num ? 'active' : '' }"><a href="${num}">${num}</a></li>
		</c:forEach>
		
		<c:if test="${pageMaker.next}">
			<li class="paginate_button next"><a href="${pageMaker.endPage + 1}"><i class="fa fa-angle-right" aria-hidden="true"></i>
</a></li>
		</c:if>
		<!-- /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제 - start -->
		<c:if test="${pageMaker.next}">
			<li class="paginate_button prev"><a href="${pageMaker.fin}"><i class="fa fa-angle-double-right" aria-hidden="true"></i>
</a></li>
		</c:if>
		<!-- /*2024. 08. 09 맨첫페이지 맨끝페이지를 위해 개인적으로 임의로 추가한 코드. - 문제 발생시 삭제 - end -->

		
	</ul>
	
	<form id="action_form" action="/board/list" method="get">
		<input type="hidden" name="pageNum" value="${pageMaker.cri.pageNum}" />
		<input type="hidden" name="amount" value="${pageMaker.cri.amount}" />
		<input type="hidden" name="type" value="<c:out value='${pageMaker.cri.type}'/>" />
		<input type="hidden" name="keyword" value="<c:out value='${pageMaker.cri.keyword}' />" />
	</form>
	
	<div class="modal" id="my_modal" role="dialog" aria-hidden="true" tabindex="-1" aria-labelledby="modal_label">
		
		<div class="modal_body">
			<h4 class="modal_title" id="my_modal_label">처리가 완료되었습니다.</h4>
			<button class="close_btn" type="button" data-dismiss="modal">닫기</button>
		</div>
		
	</div>
		
</div>


<script type="text/javascript">
(function ($) {
	
	let result = '<c:out value="${result}" />';
	
	checkModal(result);
	
	history.replaceState({}, null, null);
	
 	function checkModal(result) {
		if (result === '' || history.state) {
			return;
		}
		
		if(parseInt(result) > 0){
			
 			$('#my_modal .modal_body h4').html(
				parseInt(result) + '번 게시글 등록 완료 ✅'
			); 
			
		}
		
		$('#my_modal').addClass('showing');
		
	}
 	
 	$('#my_modal .modal_body button.close_btn').click(function(){
 		$('#my_modal').removeClass('showing');
 	});

	$("#reg_btn").on("click", function(){
		self.location = "/board/register";
	});
	
	let actionForm = $("#action_form");
	
	$(".paginate_button a").on("click", function(e){
		
		e.preventDefault();
		
		console.log('click');
		
		actionForm.find("input[name='pageNum']").val($(this).attr("href"));
		actionForm.submit();
	});
	
	$(".move").on("click", function(e){
		
		e.preventDefault();
		actionForm.append("<input type='hidden' name='bno' value='" + 
				$(this).attr("href") + "'>");
		actionForm.attr("action","/board/get");
		actionForm.submit();
		
	});
	
	let searchForm = $("#searchForm");
	
	$("#searchForm button").on("click", function(){
		
		if(!searchForm.find("option:selected").val()){
			alert("검색종류를 선택하세요.");
			return false;
		}
		
		if(!searchForm.find("input[name='keyword']").val()){
			alert("키워드를 입력하세요.");
			return false;
		}
		
		searchForm.find("input[name='pageNum']").val("1");
		e.preventDefault();
		
		searchForm.submit();
		
	});
	
})(jQuery);
</script>

<!-- 바닐라 자바스크립트
<script type="text/javascript">

	'use strict';
	
	const modal = document.querySelector("#my_modal");
	const modal_alert = document.querySelector("#my_modal .modal_body h4");
	const modal_close = document.querySelector("#my_modal .modal_body button.close_btn");
	const reg_btn = document.querySelector("#reg_btn");
	
	
	let result = '<c:out value="${result}" />';
	
	//modal.style.display = "none";
	
	checkModal(result);
	
	history.replaceState({}, null, null);
	
	function checkModal(result){
		if(result === '' || history.state){
			return;
		}
		
		if(parseInt(result) > 0){
		
			//modal_alert.append(parseInt(result) + "번 게시글 등록 완료 ✅"); 
			/* 기존 텍스트는 유지하면서 기존 텍스트 뒤에 텍스트 추가할 경우 append() 사용*/
			modal_alert.innerText = parseInt(result) + "번 게시글 등록 완료 ✅";

		}
		
		modal.classList.add("showing");
		/*alert창을 쓸 경우*/
		//alert(result + "번 r게시글 등록 완료 ✅");
			
	}
	
	function closeModal(){
		modal.classList.remove("showing");
	}
	
	function init(){
		modal_close.addEventListener("click",  closeModal);
	}
	
	reg_btn.addEventListener("click", function(){
		self.location = "/board/register";
	});
	
	init();
</script>
 -->


<%@ include file="/WEB-INF/views/include/footer.jspf" %>
