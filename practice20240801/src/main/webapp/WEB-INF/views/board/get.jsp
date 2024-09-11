
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
	
    
<%@ include file="/WEB-INF/views/include/header.jspf" %>	

<div class="board get">

	<div>
			<label>글 번호<input name="bno" value="<c:out value='${board.bno}' />"  readonly="readonly" /></label>
			
			<label>타이틀<input name="title" value="<c:out value='${board.title}' />"  readonly="readonly" /></label>
	
			<label>내용<textarea name="content"  rows="18"   readonly="readonly"><c:out value='${board.content}' /></textarea></label>
		
			<label>글쓴이 누구?<input name="writer" value="${board.writer}"  readonly="readonly" /></label>
			
			<label>등록일<input name="regDate"  value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm'  value='${board.regdate}' />"  readonly="readonly"/></label>
			
			<label>수정일<input name="updateDate"  value="<fmt:formatDate pattern='yyyy-MM-dd HH:mm' value='${board.updateDate}' />"  readonly="readonly"/></label>
			<!-- name속성은 BoardVO클래스의 변수와 일치시켜준다. -->
		
			<div class="buttons">
				<button data-oper="modify" >수정</button><!-- onclick="location.href='/board/modify?bno=<c:out value="${board.bno}" />'" -->
				<button data-oper="list">목록</button><!--  onclick="location.href='/board/list'" -->
			</div>
			
			<form id="oper_form" action="/board/modify" method="get">
				<input type="hidden" id="bno" name="bno" value="<c:out value='${board.bno}' />" />
				<input type="hidden" name="pageNum" value="<c:out value='${cri.pageNum}' />" />
				<input type="hidden" name="amount" value="<c:out value='${cri.amount}' />" />
				<input type="hidden" name="keyword" value="<c:out value='${cri.keyword}' />" />
				<input type="hidden" name="type" value="<c:out value='${cri.type}' />" />
			</form>
			
			<div class="reply">
						<!-- 댓글 팝업 -->
			<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
				<div class="modal-header">댓글 팝업창
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
				</div>
				<div class="modal-body">
					<label>댓글작성자<input class="form-control" name="replyer" value="replyer" /></label>
					<label>댓글 작성<input class="form-control" name="reply" value="New Reply!!!!" /></label>
					<label>댓글 작성일<input class="form-control" name="replyDate" value="" /></label>
				</div>
				<div class="modal-footer">
					<button id="modalRegisterBtn" type="button">등록</button>
					<button id="modalModBtn" type="button">수정</button>
					<button id="modalRemoveBtn" type="button">삭제</button>
					<button id="modalCloseBtn" type="button" data-dismiss="modal">닫기</button>
				</div>
			
			</div><!--.modal  -->
				<h1>
					<strong><i class="fa fa-comments fa-fw"></i>댓글</strong>
					<button id="addReplyBtn">댓글 달기</button>
				</h1>
				
				<ul class="chat"><!-- li 하나가 댓글 -->
					 <li data-rno="55">
						<div>
							<strong>user00</strong>
							<span>2024-09-05 -9:59</span>
						</div>
						<p>내용 🪐🪐🪐🪐🪐🪐🪐🪐</p>					
					</li>
				</ul>
			</div><!-- .reply -->
			<div class="panel-footer">
			
			</div>
			
	</div>
</div>





<script type="text/javascript">

$(document).ready(function(){
	let operForm = $("#oper_form");
	
	$("button[data-oper='modify']").on("click", function(e){
		operForm.attr("action", "/board/modify").submit();
	});
	
	$("button[data-oper='list']").on("click", function(e){
		operForm.find("#bno").remove();
		operForm.attr("action", "/board/list");
		operForm.submit();
	});
});

</script>

<script type="text/javascript" src="/resources/js/reply.js"></script>
<script>
console.log("🌼🌼🌼🌼🌼🌼🌼🌼");
console.log("🌼 JS 테스트 🌼");


$(document).ready(function(){
	let bnoValue = '<c:out value=" ${board.bno}" />';
	let replyUL = $(".chat");
	
	let pageNum = 1;
	let replyPageFooter = $(".panel-footer");
	
	function showReplyPage(replyCnt){
		let endNum = Math.ceil(pageNum / 10.0) * 10;
		let startNum = endNum - 9;
		
		let prev = startNum != 1;
		let next = false;
		
		let realEnd =  Math.ceil(replyCnt/10.0);
		
		
		if(endNum * 10 >= replyCnt) {
			endNum = Math.ceil(replyCnt/10.0);
		}
		
		if(endNum * 10 < replyCnt) {
			next = true;
		}
		
		let str = "<ul class='pagination'>";
		
		if(prev) {
			str += "<li class='page-item'><a class='page-link' href='" + 1 + "'><i class='fa fa-angle-double-left'></i></a></li>"; /*20240906 - 맨앞으로 가기, 맨 뒤로 가기 구현  문제시 삭제*/
			str += "<li class='page-item'><a class='page-link' href='" + (startNum - 1) + "'><i class='fa fa-angle-left'></i></a></li>";
		}
		
		for(let i = startNum; i <= endNum; i++) {
			
			let active = pageNum == i ? "active" : "";
			str += "<li class='page-item "+active+" '><a class='page-link' href='"+i+"'>" + i + "</a></li>";	
		}
		
		if(next) {
			str += "<li class='page-item'><a class='page-link' href='" + (endNum + 1) + "'><i class='fa fa-angle-right'></i></a></li>";
			str += "<li class='page-item'><a class='page-link' href='" + realEnd + "'><i class='fa fa-angle-double-right'></i></a></li>"; /*20240906 - 맨앞으로 가기, 맨 뒤로 가기 구현  문제시 삭제*/
		}
		
		str += "</ul>";
		
		console.log("페이지네이션: " + str);
		
		replyPageFooter.html(str);
		
	} //showReplyPage
	
	replyPageFooter.on("click", "li a", function(e){
		e.preventDefault();
		console.log("페이저 클릭");
		
		let targetPageNum = $(this).attr("href");
		
		console.log("타겟PageNum" + targetPageNum);
		
		pageNum = targetPageNum;
		showList(pageNum);
	});
	
	
	
	
	function showList(page){
		
		console.log("show list🎈🎈: " + page);
		
		replyService.getList({bno:bnoValue, page:page || 1}, 
				function(replyCnt, list){
			console.log("replyCnt🎈🎈: " + replyCnt);
			console.log("list🎈🎈: " + list);
			console.log(list);
			
			if(page == -1){
				pageNum = Math.ceil(replyCnt/10.0);
				showList(pageNum);
				return;
			}
			
			let str = "";
			
			if(list == null || list.length == 0){
				return;
			}
			
			for (let i = 0, len = list.length || 0; i < len; i++){
				str +="<li class='' data-rno='"+list[i].rno+"'>";
				str += "<div><strong>["
					+list[i].rno+"] " + list[i].replyer + "</strong>";
				str += "<span>" + replyService.displayTime(list[i].replyDate) + "</span></div>";
				str += "<p>" + list[i].reply + "</p></li>";
			}
			replyUL.html(str);
			showReplyPage(replyCnt);
		}); //end function
		
	} // end showList
	
	
	
	let modal = $(".modal");
	let modalInputReply = modal.find("input[name='reply']");
	let modalInputReplyer = modal.find("input[name='replyer']");
	let modalInputReplyDate = modal.find("input[name='replyDate']");
	
	let modalModBtn = $("#modalModBtn");
	let modalRemoveBtn = $("#modalRemoveBtn");
	let modalRegisterBtn = $("#modalRegisterBtn");
	
	$("#addReplyBtn").on("click", function(e){
		modal.find("input").val("");
		modalInputReplyDate.closest("label").hide();
		modal.find("button[id != 'modalCloseBtn']").hide();
		
		modalRegisterBtn.show();
		
		//$(".modal").modal("show");
	});
	
	$(".chat").on("click", "li", function(e){
		let rno = $(this).data("rno");
		replyService.get(rno, function(reply){
			modalInputReply.val(reply.reply);
			modalInputReplyer.val(reply.replyer);
			modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
			modal.data("rno", reply.rno);
			
			modal.find("button[id !='modalCloseBtn']").hide();
			modalModBtn.show();
			modalRemoveBtn.show();
			
			//#(".modal").modal("show");
			
		});
	});
	
	modalRegisterBtn.on("click", function(e){
		let reply = {
			reply : modalInputReply.val(),
			replyer : modalInputReplyer.val(),
			bno : bnoValue
		};
		replyService.add(reply, function(result){
			alert(result);
			modal.find("input").val("");
			//modal.hide("hide");
			
			//showList(1);
			showList(-1);
		});
	});
	
	modalModBtn.on("click", function(e){
		let reply = { rno : modal.data("rno"), reply: modalInputReply.val() };
	
		replyService.update(reply, function(result){
			alert(result);
			//modal.modal("hide");
			showList(pageNum);
		});	
	});

	modalRemoveBtn.on("click", function(e){
		let rno = modal.data("rno");
		replyService.remove(rno, function(result){
			alert(result);
			//modal.modal("hide");
			showList(pageNum);
		});
		
	});
	
	
	showList(pageNum);  /*20240906 -  이 함수에 임의의 숫자 55를 매개변수로 넣은 채로 실행시키니 댓글 리스트가 바로 나오지 않았음.*/


});




/*
replyService.getList({bno:bnoValue, page:1},function(list){
	
	for(let i = 0, len = list.length || 0; i < len; i++){
		console.log(list[i]);
	}
	
});
*/
// for replyService add test
/*
replyService.add(
	{reply:"JS Test", replyer: "commi테스트", bno:bnoValue}
	// 2040904 replyer를 replier로 작성해서 에러가 나타났다! 사전상의미는 replier가 맞음 ㅎ
	,
	function(result){
		alert("🌼🌼result: " + result);
	}
);
*/

//예: 37번 댓글 삭제 하려면 숫자 37 기입
/*
replyService.remove(54, function(count){
		console.log(count);
		
		if(count === "success"){
			alert("🌼 삭제 완료 🌼");
		}
}, function(err){
	alert("에러!!");
});
*/
/*
replyService.update({
	rno : 55,
	bno : bnoValue,
	reply : "🌼 댓글 수정 🌼"
}, function(result){
	alert("🌼 수정 완료 🌼");
});
*/
/*
replyService.get(55, function(data){
	alert("🐬조회 완료🐬");
	console.log(data);
});
*/
</script>	
	
<script type="text/javascript">
$(document).ready(function(){
	console.log(replyService);
});
</script>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>