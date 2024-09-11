<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport"  content="width=device-width, initial-scale=1.0"/>
<meta charset="UTF-8" />
<title>업로드 폼</title>
</head>
<body>
	<form action="uploadFormAction" method="post" enctype="multipart/form-data"> 
		
		<input type="file" name="uploadFile" multiple />
		<button>SUBMIT</button>
		
	</form>
	<!-- 20240911:  파일 업로드에서 enctype의 속성값을 multipart/form-data로 지정해야 함, multiple속성: 한꺼번에 여러 개의 파일 업로드  -->
</body>
</html>    