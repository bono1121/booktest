<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="../mainMenu.jsp" />
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>다락방서점</title>
<link rel="stylesheet" href="/08-bookstore/css/imageDisplay.css">
<script src="/08-bookstore/js/imagePreview.js"></script>
</head>
<body>
	<div class="container">
		<h2>도서추가</h2>
		<c:if test="${errors!=null }">
			<p class="text-danger">에러!!!
			<ul>
				<c:forEach var="error" items="${errors}">
					<li>${error}</li>
				</c:forEach>
			</ul>
			</p>
		</c:if>
		<form action="book_save" enctype="multipart/form-data" method="post">
			<div class="thumbnail">
				<img id="preview" alt="이미지 추가하려면 클릭" style="color: lightgray;" />
				<div class="caption">
					<input type="file" id="filename" name="filename" />
				</div>
			</div>
			<div class="form-group">
				<label for="bookname">책이름:</label><span class="text-danger">
					${bookError.booknameErr}</span> <input type="text"
					class="form-control input-lg" id="bookname" name="bookname"
					value="${bookForm.bookname}" placeholder="책이름을 입력하세요.">
			</div>
			<div class="form-group">
				<label for="publisher">출판사:</label><span class="text-danger">
					${bookError.publisherErr}</span> <input type="text"
					class="form-control input-lg" id="publisher" name="publisher"
					value="${bookForm.publisher}" placeholder="출판사를 입력하세요.">
			</div>
			<div class="form-group">
				<label for="price">가격:</label><span class="text-danger">
					${bookError.priceErr}</span> <input type="text"
					class="form-control input-lg" id="price" name="price"
					value="${bookForm.price}" placeholder="가격을 입력하세요.">
			</div>

			<div class="form-group">
				<label for="description">설명:</label><span class="text-danger">
					${bookError.descriptionErr}</span>
				<textarea class="form-control input-lg" rows="5" id="description"
					name="description" placeholder="도서에 대한 자세한 정보를 입력하세요.">${bookForm.description}</textarea>
			</div>
			<div class="form-group">
				<button type="submit" class="btn btn-primary btn-lg">추가</button>
			</div>
		</form>
	</div>
</body>
</html>