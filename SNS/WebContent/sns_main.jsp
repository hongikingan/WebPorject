<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/styles.css" type="text/css" media="screen" />
<link rel="stylesheet" href="http://code:jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="http://code.jquery.com/jquery-1.9.1.js"></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My SNS</title>
<script>
	function newuser(){
		window.open("new_user.jsp", "newuser", "titlebar=no, location=no, scrollbars=no, resizeable=no, menubar=no, toolbar=no, width=300, height=240");
		
	}
</script>
</head>
<body>

<!-- 상단 배너 -->
<header></header>

<!-- 배너 아래 메인메뉴 -->
<nav>

<div class="menu">
	<ul>
	<li><a href="#">Home</a></li>
	<li><a href="javascript:newuser()">New User</a></li>
	<li><sns:login /></li>
	</ul>
</div>
</nav>

<!-- 메인 콘텐츠 구성 섹션 -->
<section>
	<!--  게시글 메인 섹션 -->
	<section></section>
	<div id="accordion">
		<h3>김프리 :: [좋아요 6 | 댓글 4]</h3>
		<div>
			<p>어제는 나홀로 영화를 보았습니다. ^^ 사람들이 이상하다고 해도 나는 즐거워요..</p>
			<p>[삭제] [좋아요] / 2013.7.8.14:00에 작성된 글입니다.</p>
			<ul class="reply">
			<li>홍길동 :: 저랑 똑같네요... - 2013.7.8:14:00 <a href="">삭제</a></li>
			<li>아무개 :: 이런일은 있어서는 안되지요.. 파이팅~~~ - 2013.7.8:14:00 </a></li>
			<li>김사랑 :: 전화하지 그랬니.... 친구 좋다는 것이 ㅋㅋㅋ - 2013.7.8:14:00 </a></li>
			</ul>
			<form action="" class="">
				댓글달기 <input type="text" name="" size="60">
			</form>
		</div>
	</div>
	<!-- 중앙 좌측 회원목록, 기타 메뉴 섹션 -->
	<aside></aside>
</section>

<!-- 본문 하단 메뉴 영역 -->
<footer>
	<!-- 하단 메뉴별 영역 -->
	<aside></aside>
</footer>

</body>
</html>