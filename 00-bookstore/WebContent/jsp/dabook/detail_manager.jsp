<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
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
		<h2>도서상세보기</h2>

		<div class="row">
			<form action="book_update" enctype="multipart/form-data"
				method="post">
				<div class="col-sm-5 col-md-6 col-lg-4">
					<div class="thumbnail">
						<img src="${book.imagepath}" id="preview" name="preview"
							alt="이미지 추가하려면 클릭" style="color: lightgray; width: 100%" />
						<div class="caption">
							<input type="file" id="filename" name="filename" />
						</div>
					</div>
				</div>
				<div class="col-sm-7 col-md-6 col-lg-8">
					<div class="form-group">
						<input name="bookid" value="${book.bookid}" hidden="hidden" /> <label
							for="bookid">책아이디:</label>
						<p type="text" class="form-control input-lg" id="bookid"
							name="bookid" readonly="readonly">${book.bookid}</p>
					</div>
					<div class="form-group">
						<label for="bookname">책이름:</label> <input type="text"
							class="form-control input-lg" id="bookname" name="bookname"
							placeholder="책이름을 입력하세요." value="${book.bookname}">
					</div>
					<div class="form-group">
						<label for="publisher">출판사:</label> <input type="text"
							class="form-control input-lg" id="publisher" name="publisher"
							placeholder="출판사를 입력하세요." value="${book.publisher }">
					</div>
					<div class="form-group">
						<label for="price">가격:</label> <input type="text"
							class="form-control input-lg" id="price" name="price"
							placeholder="가격을 입력하세요." value="${book.price }">
					</div>

					<div class="form-group">
						<label for="description">설명:</label>
						<textarea class="form-control input-lg" rows="5" id="description"
							name="description" placeholder="도서에 대한 자세한 정보를 입력하세요.">${book.description }</textarea>
					</div>
					<div class="form-group">
						<button type="submit" name="update" value="update"
							class="btn btn-primary btn-lg">수정</button>
						<button type="submit" name="delete" value="delete"
							class="btn btn-primary btn-lg">삭제</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>