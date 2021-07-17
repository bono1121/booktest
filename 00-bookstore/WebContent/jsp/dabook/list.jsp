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

			location.href = "book_detail?bookid=" + bookId+"&div="+1;//div=1는 managerview
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

		<!-- 페이징 처리 -->
		<ul class="pagination pagination-lg">

			<c:if test="${pageGroupResult.beforePage}">
				<li><a
					href="book_search?reqPage=${pageGroupResult.groupStartNumber-1}">before</a></li>
			</c:if>

			<c:forEach var="index" begin="${pageGroupResult.groupStartNumber}"
				end="${pageGroupResult.groupEndNumber}">

				<c:choose>

					<c:when test="${pageGroupResult.selectPageNumber==index}">
						<li><a href="book_search?reqPage=${index}"
							class="list-group-item active">${index}</a></li>
					</c:when>

					<c:otherwise>
						<li><a href="book_search?reqPage=${index}"
							class="list-group-item">${index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>

			<c:if test="${pageGroupResult.afterPage}">
				<li><a
					href="book_search?reqPage=${pageGroupResult.groupEndNumber+1}">after</a></li>
			</c:if>
		</ul>

	</div>
</html>


<%-- 참고: 주석땜시 오류가 나길래 주석 단건 아래에 다시 복사해 놓은거임 : 다시 테스트 해보자.
		<!-- 페이징 처리 -->
		<ul class="pagination pagination-lg">
			<!-- 앞구간으로 이동 -->
			<c:if test="${pageGroupResult.beforePage}">
				<li><a
					href="book_search?reqPage=${pageGroupResult.groupStartNumber-1}">before</a></li>
			</c:if>
			<!-- 현구간 -->
			<c:forEach var="index" begin="${pageGroupResult.groupStartNumber}"
				end="${pageGroupResult.groupEndNumber}">
				<!-- 선택된 페이지  active 클래스 추가-->
				<c:choose>
					<!-- 현재 선택된 페이지 처리-->
					<c:when test="${pageGroupResult.selectPageNumber==index}">
						<li><a href="book_search?reqPage=${index}"
							class="list-group-item active">${index}</a></li>
					</c:when>
					<!-- 선택되지 않은 페이지 처리  -->
					<c:otherwise>
						<li><a href="book_search?reqPage=${index}"
							class="list-group-item">${index}</a></li>
					</c:otherwise>
				</c:choose>
			</c:forEach>
			<!-- 뒷구간으로 이동 -->
			<c:if test="${pageGroupResult.afterPage}">
				<li><a
					href="book_search?reqPage=${pageGroupResult.groupEndNumber+1}">after</a></li>
			</c:if>
		</ul> --%>
