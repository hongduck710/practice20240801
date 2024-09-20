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
		<div class="uploadResult"><ul></ul></div>
	</form>
	
	<div class="uploadDiv"><input type="file" name="uploadFile" multiple="multiple" /></div>
	
	<div class="bigPictureWrapper"><div class="bigPicture"></div></div>

</div>

<script type="text/javascript">
$(document).ready(function(){
	
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
		} else if(operation === 'modify'){
			console.log("서밋 클릭(submit clicked)");
			let str ="";
			$(".uploadResult ul li").each(function(i, obj){
				let jobj = $(obj);
				console.dir(jobj);
				
				str += "<input type='hidden' name='attachList["+i+"].fileName' value='" + jobj.data("filename") + "' />";
				str += "<input type='hidden' name='attachList["+i+"].uuid' value='" + jobj.data("uuid") + "' />";
				str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='" + jobj.data("path") + "' />";
				str += "<input type='hidden' name='attachList["+i+"].fileType' value='" + jobj.data("type") + "' />";
			}); // $(".uploadResult ul li") 닫음
			formObj.append(str).submit();
		} // if else문 닫음
		formObj.submit();
	}); // button click이벤트 닫음
		
});
</script>

<script type="text/javascript">

$(document).ready(function(){
	
	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	let maxSize = 5242880; // 5MB
	
	function checkExtension (fileName, fileSize) {
		if(fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
			return false;
		}
		
		return true;
	} // checkExtension 닫음
	
	function showUploadResult(uploadResultArr) {
		if(!uploadResultArr || uploadResultArr.length == 0) { return; }
		let uploadUL = $(".uploadResult ul");
		let str = "";
		
		$(uploadResultArr).each(function(i, obj) {
			// image type
			if(obj.image) {
				let fileCallPath = encodeURIComponent( obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName );
				str += "<li data-path='" + obj.uploadPath + "'";
				str += "data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'";
				str += " ><div>";
				str += "<span>" + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class=''><i class='fa fa-times'></i></button><br />";
				str += "<img src='/display?fileName=" + fileCallPath + "' alt='첨부 이미지' />";
				str += "</div>";
				str += "</li>";
			} else {
				let fileCallPath = encodeURIComponent( obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName );
				let fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");
				
				str += "<li "; /* 이렇게 쓸 경우 li 뒤에 공백 남겨두는 거 기억하기! */
				str += "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'><div>";
				str += "<span>" + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file'><i class='fa fa-times'></i></button><br />";
				str += "<img src='/resources/img/clip-icon.png' alt='📎' />";
				str += "</div>";
				str += "</li>";
			}
		}); //$(uploadResultArr) 닫음
		uploadUL.append(str);
	} // showUploadResult 닫음
	
	$("input[type='file']").change(function(e){
		let formData = new FormData();
		let inputFile = $("input[name='uploadFile']");
		let files = inputFile[0].files;
		
		for(let i = 0; i < files.length; i++) {
			if( !checkExtension(files[i].name, files[i].size) ) {
				return false;
			}
			formData.append("uploadFile", files[i]);
		} // input[type='file'] 닫음
		
		$.ajax({
			url : "/uploadAjaxAction",
			processData : false,
			contentType : false,
			data : formData,
			type : "POST",
			dataType : "json",
			success : function(result){
				console.log(result);
				showUploadResult(result); //업로드 결과 처리 함수
			}
		}); // $.ajax 닫음
		
	}); // input[type='file'] 닫음
	
	(function(){
		let bno = '<c:out value = "${board.bno}" />';
		$.getJSON("/board/getAttachList", {bno : bno}, function(arr) {
			console.log(arr);
			let str = "";
			$(arr).each(function(i, attach){
				
				// image type
				if(attach.fileType) {
					let fileCallPath = encodeURIComponent(attach.uploadPath + "/s_" + attach.uuid + "_" + attach.fileName);
					str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'><div>";
					str += "<span>" + attach.fileName + "</span>";
					str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image'><i class='fa fa-times'></i></button>";
					str += "<img src='/display?fileName=" + fileCallPath + "' />";
					str += "</div>";
					str += "</li>";
				} else {
					let fileCallPath = encodeURIComponent(attach.uploadPath + attach.uuid + "_" + attach.fileName); /* 20240920 - 교재 587페이지에 fileCallPath정의 부분이 누락되어 있어서 register.jsp페이지 참고 후 임의로 작성함 */
					str += "<li data-path='" + attach.uploadPath + "' data-uuid='" + attach.uuid + "' data-filename='" + attach.fileName + "' data-type='" + attach.fileType + "'><div>";
					str += "<span>" + attach.fileName + "</span><br />";
					str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file'><i class='fa fa-times'></i></button>";
					str += "<img src = '/resources/img/clip-icon.png' alt='📎' />";
					str += "</div>";
					str += "</li>";
				} 
			}); // $(arr).each 닫음
			$(".uploadResult ul").html(str);
		}); // $.getJSON 닫음
	})(); // 즉시실행함수 닫음
	/* 20240920 - 즉시실행함수 (function(){})() 맨 뒤에 괄호가 누락이 되어서 첨부파일이 보이지 않았음. 즉시 실행함수: (function(){})(); */
	$(".uploadResult").on("click", "button", function(e){
		console.log("파일삭제(delete file)");
		if(confirm("정말로 삭제하시겠어요?(Are you sure to remove this file?)")){
			let targetLi = $(this).closest("li");
			targetLi.remove();
		}
	});

});

</script>
<%@ include file="/WEB-INF/views/include/footer.jspf" %>