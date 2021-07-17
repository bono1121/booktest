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
		<h2>주문 내역 확인</h2>

		<!--배송지 -->
		<table class="table">

			<c:forEach var="order" items="${orders}">
				<tr>
					<td>주문일자 : ${order.orderdate}</td>
					<td class="text-right"><a
						href="order_detail?orderid=${order.orderid}&orderdate=${order.orderdate}">주문상세조회<span
							class="glyphicon glyphicon-menu-right"></span></a></td>
				</tr>
				<tr>
					<td colspan="2">주문금액 : ${order.amount}</td>
				</tr>
				<tr>
					<td colspan="2">결제방법 : <c:choose>
							<c:when test="${order.payoption==1}">
								카드결제
							</c:when>
							<c:when test="${order.payoption==2}">
								통장이체
							</c:when>
						</c:choose>
					</td>
				</tr>
				<tr>
					<c:choose>
						<c:when test="${order.sendYN=='N'}">
							<td colspan="2" class="text-center"><button type="button"
									class="btn btn-primary btn-lg">주문취소</button></td>
						</c:when>
						<c:when test="${order.sendYN=='Y'}">
							<td colspan="2" class="text-center"><button type="button"
									class="btn btn-primary btn-lg" disabled="disabled">배송완료</button></td>
						</c:when>
					</c:choose>
				</tr>
			</c:forEach>
		</table>
	</div>
</html>