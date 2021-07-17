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
		<h3>주문 정보</h3>

		<!--배송지 -->
		<table class="table">
			<tr>
				<td colspan="3">주문일 : ${orderdate}</td>
			</tr>
			<c:forEach var="orderInfo" items="${orderInfos}">
				<tr>
					<td><img width="50px" height="50px" alt=""
						src="${orderInfo.imagepath}"></td>
					<td><h4>${orderInfo.bookname}</h4></td>
					<td><h4>
							${orderInfo.price } / <small>수량 ${orderInfo.quantity }개</small></td>
				</tr>
			</c:forEach>
			<tr>
				<th>총합계액</th>
				<td colspan="2"><p type="text" class="form-control input-lg"
						readonly="readonly">${amount}</p></td>
			</tr>
		</table>
		<h3>배송지 정보</h3>
		<table class="table">
			<tr>
				<th>주문고객</th>
				<td colspan="2">${member.name }</td>

			</tr>
			<tr>
				<th>연락처</th>
				<td colspan="2">${member.phone }</td>
			</tr>
			<tr>
				<th>배송지</th>
				<td colspan="2">${member.address }${member.address2}</td>
			</tr>
		</table>
	</div>
</html>