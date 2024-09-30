<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@include file="include/header.jspf"%>

<section class="wrap">
	
	<div class="main_banner">
		<img src="${pageContext.request.contextPath}/resources/img/commi1.jpg" alt="컴미" />
		
	</div>

	<div class="youtube_responsive">
		<iframe  src="https://www.youtube.com/embed/fTlqaJKy5H4" title="요정컴미" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
	</div>
	<div class="youtube_responsive">
	  	<iframe  src="https://www.youtube.com/embed/TbiN9D-cY_w" title="지구방위대 후뢰시맨 고화질 오프닝 국내판" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share" referrerpolicy="strict-origin-when-cross-origin" allowfullscreen></iframe>
	</div>
	<div class="guide">
		<h1>프로젝트 실행 가이드라인</h1>
		<ol>
			<li> /WEB-INF/sql/COMMI.sql에 기입된 명령문을 실행시켜서 테이블을 생성합니다.</li>
			<li>src/test/java 패키지폴더에 있는 org.zerock.security에 있는 MemberTest.java파일을 열고, 열린 파일 창에 마우스 오른쪽 버튼 클릭하면 나오는 메뉴에서 Run As(녹색 동그라이 모양 실행 아이콘) 안에 있는 메뉴에서 JUnit테스트 메뉴를 눌러서 테스트를 실행하면 아이디가 user, manager, admin인 회원들이 만들어집니다.(이러한 실행에서 첫 번째 실행시에는 tbl_memeber테이블에 회원 정보들은 만들어지지만 tbl_member_auth테이블은 두 번째 실행을 해야 만들어지는 것을 확인했으므로 첫 번째 실행에서 tbl_member_auth테이블에 정보가 만들어지지 않았으면 두 번째 테스트 실행을 해보고 확인합니다.)</li>
		</ol> 
	</div>
	<h1 class="clock">00:00:00</h1>
</section>
<script src="${pageContext.request.contextPath}/resources/js/clock.js"></script>
<%@include file="include/footer.jspf"%>
