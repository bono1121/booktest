<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<jsp:include page="../mainMenu.jsp" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>다락방서점</title>

<!--아이디 중복 체크 스크립트 -->
<script src="/08-bookstore/js/checkid.js"></script>

<!-- 우편번호찾기 : http://postcode.map.daum.net/guide -->
<!-- 다음 우편번호 서비스 open api 사용 -->
<script src="http://dmaps.daum.net/map_js_init/postcode.v2.js"></script>
<script src="/08-bookstore/js/daumPostcode.js"></script>
</head>
<body>
	<div class="container">
		<h2>회원가입</h2>
		<form action="customer_save" method="post">
			<div class="form-group form-inline">
				<div class="form-group">
					<input type="text" class="form-control input-lg" name="id"
						placeholder="아이디를 입력하세요.">
				</div>

				<div class="form-group">
					<button id="checkid" type="button" class="btn btn-primary btn-lg">중복체크</button>
				</div>

				<!--id 중복체크 결과 나오는 곳  -->
				<div class="form-group console input-lg"></div>
			</div>
			<div class="form-group">
				<input type="password" class="form-control input-lg" name="pwd"
					placeholder="비밀번호를 입력하세요.">
			</div>
			<div class="form-group">
				<input type="password" class="form-control input-lg" name="pwd"
					placeholder="확인 비밀번호를 입력하세요.">
			</div>
			<div class="form-group">
				<input type="text" class="form-control input-lg" name="name"
					placeholder="이름을 입력하세요.">
			</div>

			<!-- 			<div class="form-group">
				<input type="text" class="form-control input-lg" name="addr"
					placeholder="주소를 입력하세요.">
			</div> -->

			<!-- 다음 우편번호 서비스 open api 사용 -->
			<div class="form-group form-inline">
				<div class="form-group">
					<input type="text" class="form-control input-lg"
						id="sample6_postcode" name="postcode" placeholder="우편번호">
				</div>
				<div class="form-group">
					<input type="button" class="btn btn-primary btn-lg"
						onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
				</div>
			</div>

			<div class="form-group">
				<input type="text" class="form-control input-lg"
					id="sample6_address" name="address" placeholder="주소">
			</div>
			<div class="form-group">
				<input type="text" class="form-control input-lg"
					id="sample6_address2" name="address2" placeholder="상세주소를 입력하세요">
			</div>

			<div class="form-group">
				<input type="tel" class="form-control input-lg" name="phone"
					placeholder="폰번호 입력하세요.">
			</div>

			<div class="form-group">
				<input type="email" class="form-control input-lg" name="email"
					placeholder="이메일을 입력하세요.">
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-lg">가입</button>
			</div>
		</form>
	</div>
</body>
</html>