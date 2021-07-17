<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../mainMenu.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>다락방서점</title>

<script type="text/javascript">
	$(document).ready(function() {
		$("tbody tr").click(function() {
			$('.selected').removeClass('selected');
			$(this).addClass("selected");
			
			var bookId = $('#bookid', this).html();	
			
			location.href = "book_detail?bookid="+bookId+"&div="+2;//div=2는 memberview
		});
	});
	
</script>
</head>
<body>
	<div class="container">
		<h2>책 목록</h2>

		<table class="table table-hover">
			<thead>
				<tr>
					<th>책아이디</th>
					<th>책이름</th>
					<th>출판사</th>
					<th>가격</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="book" items="${books}">
					<tr>
						<td id="bookid">${book.bookid}</td>
						<td id="bookname">${book.bookname}</td>
						<td id="publisher">${book.publisher}</td>
						<td id="price">${book.price}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</html>