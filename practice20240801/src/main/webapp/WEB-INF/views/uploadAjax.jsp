<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>    
<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transactional//EN" "http://www.w3.org/TR/html4/loose.dtd" >-->
<html>
<head>
<meta name="viewport"  content="width=device-width, initial-scale=1.0"/>
<meta charset="UTF-8" />
<title>업로드 AJAX</title>
</head>
<body>
	<h1>UPLOAD WITH AJAX</h1>
	<div class="uploadDiv">
		<input type="file" name="uploadFile" multiple />
	</div>	
	<button id="uploadBtn">UPLOAD</button>
	
	
	
	
<!-- 	<script src="https://code.jquery.com/jquery-3.7.1.min.js"  type="text/javascript"></script> --><!--  20240911: header.jspf에 있는 제이쿼리 CDN을 첨부하였더니 ajax is not a function오류 사라짐, slim버전이 아닌 CDM을 사용하였더니 ajax is not a function오류 사라짐-->
	<script
			  src="https://code.jquery.com/jquery-3.7.1.min.js"
			  integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
			  crossorigin="anonymous">
	</script><!-- 20240911: integrity, crossoorigin 지우지 않으면 image파일 여부 true, false에 영향이 있는지 없는 지 확인하기 위해 지우지 않고 제이쿼리 첨부함 -->
	<script>
	
	let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	let maxSize = 5242880; // 5MB
	
	function checkExtension(fileName, fileSize) {
		
		if(fileSize > maxSize) {
			alert("파일 사이즈 초과");
			return false;
		}
		
		if(regex.test(fileName)) {
			alert("해당 종류의 파일은 업로드 할 수 없습니다.");
			return false;
		}
		
		return true;
	}
	
	$(document).ready(function(){
		$("#uploadBtn").on("click", function(e){
			let formData = new FormData(); /* 202409011 정리:FormData객체는 가상의 <form>태그와 같다고 생각하면 됨. Ajax를 이용하는 파일 업로드는 FormData를 이용해서 필요한 파라미터를 담아서 전송하는 방식(FormData객체는 브라우저의 제약이 있으므로 주의) */
			let inputFile = $("input[name='uploadFile']");
			let files = inputFile[0].files;
			
			console.log(files);
			
			// add File Data to formData
			for(let i = 0; i < files.length; i++) {
				
				if( !checkExtension(files[i].name, files[i].size) ) {
					return false;
				}
				
				formData.append("uploadFile", files[i]);
			}
			
			$.ajax({
				url : '/uploadAjaxAction',
				processData : false,
				contentType : false,
				data : formData,
				type : 'POST',
				dadaType: 'json',
				success : function(result) {
					console.log(result);
				} 
			}); // ajax 닫음
			
		}); // #uploadBtn클릭 닫음
	});	
	</script>
</body>
</html>    