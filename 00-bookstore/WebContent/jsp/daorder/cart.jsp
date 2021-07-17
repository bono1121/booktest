<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../mainMenu.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>다락방서점</title>

<!-- 전체선택 버튼 기능 스크립트  -->
<!--아이디 중복 체크 스크립트 -->
<script src="/08-bookstore/js/checkedAll.js"></script>
</head>
<body>
	<div class="container">
		<h2>장바구니</h2>
		<form action="orderBook">
			<table class="table table-hover">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkedAll"> 전체</th>
						<th>책이름</th>
						<th>출판사</th>
						<th>가격</th>
						<th>수량</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="shoppingItem" items="${cart}">
						<tr>
							<td><input type="checkbox" class="checkSingle"
								name="shoppingItemInedx"
								value="${shoppingItem.shoppingItemInedx}"></td>
							<td id="bookname">${shoppingItem.book.bookname}</td>
							<td id="publisher">${shoppingItem.book.publisher}</td>
							<td id="price">${shoppingItem.book.price}</td>
							<td id="quantity">${shoppingItem.quantity}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<div class="form-group">
				<input name="amount" value="${totalPrice}" hidden="hidden" /> <label
					for="bookid">총합계액:</label>
				<p type="text" class="form-control input-lg" id="bookid"
					readonly="readonly">${totalPrice}</p>
			</div>
			<div class="form-group">
				<!-- 선택상품이  1개이상일때 버튼 활성화 -->
				<%-- 				<c:if test="${fn:length(cart) gt 0}">
					<button id="buyButton" type="submit" class="btn btn-primary btn-lg">선택상품구매하기</button>
				</c:if> --%>
				<!-- 선택상품이  1개미만일때 버튼 비활성화 -->
				<%-- 				<c:if test="${fn:length(cart) eq 0}">
					<button type="submit" class="btn btn-primary btn-lg" disabled="disabled">선택상품구매하기</button>
				</c:if> --%>

				<button id="buyButton" type="submit" class="btn btn-primary btn-lg">선택상품구매하기</button>
			</div>
		</form>

	</div>
</html>