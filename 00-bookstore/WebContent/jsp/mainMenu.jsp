<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
<head>
<title>다락방서점</title>

<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="/08-bookstore/index.jsp">다락방서점</a>
			</div>

			<!-- 책 검색 -->
			<form action="book_search_name" class="navbar-form navbar-left">
				<div class="input-group">
					<input type="text" class="form-control" placeholder="Search"
						name="bookname">
					<div class="input-group-btn">
						<button class="btn btn-default" type="submit">
							<i class="glyphicon glyphicon-search"></i>
						</button>
					</div>
				</div>
			</form>

			<!-- 장바구니 / 회원가입 / 로그인 / 관리자 -->
			<ul class="nav navbar-nav navbar-right">
				<!-- 장바구니 -->
				<li><a href="cartView_LOGCK"><span
						class="glyphicon glyphicon-shopping-cart"></span>장바구니<span
						class="badge">${fn:length(cart
						)}</span></a></li>

				<c:choose>
					<c:when test="${member==null}">
						<!-- 회원가입 -->
						<li><a href="customer_input"><span
								class="glyphicon glyphicon-user"></span>회원가입</a></li>
						<!-- 로그인 -->
						<li><a href="login_input"><span
								class="glyphicon glyphicon-log-in"></span>로그인</a></li>
					</c:when>
					<c:otherwise>
						<!-- myMenu -->
						<li class="dropdown"><a class="dropdown-toggle"
							data-toggle="dropdown" href="#"> <span
								class="glyphicon glyphicon-user "></span> myMenu <span
								class="caret"></span>
						</a>
							<ul class="dropdown-menu">
								<li><a href="order_list">주문내역확인</a></li>
								<li><a href="customer_update">회원정보수정</a></li>
							</ul></li>

						<!-- 로그아웃 -->
						<li><a href="logout"><span
								class="glyphicon glyphicon-log-in"></span>로그아웃</a></li>
					</c:otherwise>
				</c:choose>

				<!-- 관리자 -->
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#"> <span
						class="glyphicon glyphicon-cog "></span> 관리자 <span class="caret"></span>
				</a>
					<ul class="dropdown-menu">
						<li><a href="book_input">도서추가</a></li>
						<li><a href="book_search?reqPage=1">도서리스트</a></li>
					</ul></li>
			</ul>
		</div>
	</nav>
</body>
</html>
