<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<meta charset="UTF-8" />
<title>파일업로드</title>
</head>
<body>
	
	<form action="/sample/exUploadPost" method="post" enctype="multipart/form-data">
		<div><input type="file" name="files" /></div>
		<div><input type="file" name="files" /></div>
		<div><input type="file" name="files" /></div>
		<div><input type="file" name="files" /></div>
		<div><input type="file" name="files" /></div>
		<div><input type="submit" /></div>						
	</form>
	
</body>
</html>