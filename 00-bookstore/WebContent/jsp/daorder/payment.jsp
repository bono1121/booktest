<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../mainMenu.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>다락방서점</title>

<style type="text/css">
.center {
	float: none;
	margin-left: auto;
	margin-right: auto;
}
</style>
</head>
<body>
	<div class="col-xs-7 center">
		<h2>주문 /결제</h2>

		<!--배송지 -->
		<table class="table">
			<c:forEach var="shoppingItem" items="${buy}">
				<tr>
					<td><div class="thumbnail">
							<img src="${shoppingItem.book.imagepath}" id="preview"
								name="preview" alt="이미지 추가하려면 클릭"
								style="color: lightgray; width: 50px; height: 50px" />
						</div></td>
					<td><h4>${shoppingItem.book.bookname}</h4></td>
					<td><h4>${shoppingItem.book.price}
							/ <small>수량 ${shoppingItem.quantity} 개</small></td>
				</tr>
			</c:forEach>
			<tr>
				<th>주문고객</th>
				<td colspan="2">${member.name}</td>

			</tr>
			<tr>
				<th>연락처</th>
				<td colspan="2">${member.phone}</td>
			</tr>
			<tr>
				<th>배송지</th>
				<td colspan="2">${member.address}${member.address2}</td>
			</tr>
		</table>

		<!-- 결제수단  -->
		<form action="payBook">
			<div class="form-group">
				<input name="amount" value="${totalPrice}" hidden="hidden" /> <label
					for="bookid">총합계액:</label>
				<p type="text" class="form-control input-lg" id="bookid"
					readonly="readonly">${totalPrice}</p>
			</div>
			<div class="form-group">
				<label class="radio-inline"><input type="radio"
					name="payoption" value="1">카드결제</label> <label class="radio-inline"><input
					type="radio" name="payoption" value="2">통장이체</label>
			</div>

			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-lg">결제하기</button>
			</div>
		</form>
	</div>
</html>