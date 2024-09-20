
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
	<div class="file-attach-wrap">
		<div class="form-group uploadDiv">
			<input type="file" name="uploadFile"  multiple />
		</div>
		<div class="uploadResult">
			<ul></ul>
		</div>
	</div> <!-- file-attach-wrap 닫음 -->
</div>



<script type="text/javascript">
$(document).ready(function(e){

	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	let maxSize = 5242880; // 5MB
	
	function checkExtension (fileName, fileSize) {
		if(fileSize >= maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if(regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
		}
		
		return true;
	} // checkExtension 닫음

	function showUploadResult(uploadResultArr) {
		if(!uploadResultArr || uploadResultArr.length == 0){ return; }
		let uploadUL = $(".uploadResult ul");
		let str = "";

		$(uploadResultArr).each(function(i, obj){
			// image type
			if(obj.image){
				let fileCallPath = encodeURIComponent( obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName );
				str += "<li data-path='"+ obj.uploadPath +"'";
				str += "data-uuid='"+ obj.uuid +"' data-filename='" + obj.fileName + "' data-type='" + obj.image + "'";
				str += "><div>";
				str += "<span>" + obj.fileName + "</span>";	
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='image' class=''><i class='fa fa-times'></i></button><br/>"; // 조금 더 안정적으로 코드를 작성하기 위해서는 \'로 작성하는 것이 좋음.(작은 따옴표 이스케이프 처리)
				str += "<img src='/display?fileName=" + fileCallPath + "' />";
				str += "</div>";
				str += "</li>";	

			} else {
				let fileCallPath = encodeURIComponent( obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName ); 
				let fileLink = fileCallPath.replace(new RegExp(/\\/g), "/");

				str += "<li "; /* 20240919: 태그를 이런 식으로 줄바꿈 하면서 분리할 경우 띄어쓰기에 유의!!!! 예를 들면 <li data=''></il>가 되어야 하는데 <lidata=''></li>이렇게 li와 data가 붙어서 데이터가 전달되지 않는 경우가 생김 */
				str += "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "' ><div> ";
				str += "<span>" + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file'><i class='fa fa-times'></i></button><br/>";
				str += "<img src='/resources/img/clip-icon.png' alt='📎' />";
				str += "</div>";
				str += "</li>";		

				/*
				str += "<li";
				str += "data-path='" + obj.uploadPath + "' data-uuid='" + obj.uuid + "' data-filename='" + obj.fileName + "' data-type='" + obj.image + "' ><div>";
				str += "<span>" + obj.fileName + "</span>";
				str += "<button type='button' data-file=\'" + fileCallPath + "\' data-type='file' class=''><i class='fa fa-times'></i></button><br/>";
				str += "<img src='/resources/img/clip-icon.png' alt='📎' />";
				str += "</div>";
				str += "</li>";
				*/
			
			}
		}); //$(uploadResultArr) 닫음
		uploadUL.append(str);
	} // showUploadResult 닫음
	
	$("input[type='file']").change(function(e){
		let formData = new FormData();
		let inputFile = $("input[name='uploadFile']");
		let files = inputFile[0].files;
		
		for(let i = 0; i < files.length; i++) {
			if( !checkExtension(files[i].name, files[i].size) ){
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
				showUploadResult(result); // 업로드 결과 처리 함수
			}
		}); // $.ajax 닫음
		
	});
	
	let formObj = $("form[role='form']");
	$("button[type='submit']").on("click", function(e){
		e.preventDefault();
		console.log("submit clicked");

		let str = "";

		$(".uploadResult ul li").each(function(i, obj){
			let jobj = $(obj);
			console.dir(jobj);

			str += "<input type='hidden' name='attachList["+i+"].fileName' value='" + jobj.data("filename") + "' />";
			str += "<input type='hidden' name='attachList["+i+"].uuid' value='" + jobj.data("uuid") + "' />";
			str += "<input type='hidden' name='attachList["+i+"].uploadPath' value='" + jobj.data("path") + "' />";
			str += "<input type='hidden' name='attachList["+i+"].fileType' value='" + jobj.data("type") + "' />";
		});
		formObj.append(str).submit();
	}); // formObj 닫음


	$(".uploadResult").on("click", "button", function(e){
		console.log("파일 삭제(delete file)");
		
		let targetFile = $(this).data("file");
		let type = $(this).data("type");

		let targetLi = $(this).closest("li");

		$.ajax({
			url : "/deleteFile",
			data : {fileName: targetFile, type: type},
			dataType : "text",
			type : "POST",
			success : function(result){
				alert(result);
				targetLi.remove();
			}
		}); // $.ajax 닫음
	});

});
</script>

<%@ include file="/WEB-INF/views/include/footer.jspf" %>