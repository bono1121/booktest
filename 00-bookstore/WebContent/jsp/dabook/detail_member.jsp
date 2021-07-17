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

/** 새로운 글을 화면에 추가하기 위한 함수
 *  - commentid: 글 번호
 *  - writer : 작성자 이름
 *  - contnet : 덧글 내용
 *  - datetime : 작성일시 */
 
/* 익스플로우 : 도구>일반>설정>임시인터넷파일>웹페이지열때마다 체크 해야만 댓글 update가 바로바로 됨  */
 
function addNewItem(commentid, writer, content, datetime, bookid) {


	 // 새로운 글이 추가될 li태그 객체
	var new_li = $("<li class='list-group-item'>");
	new_li.attr("commentid", commentid);//삭제시 필요

	// 작성자 정보가 지정될 <p>태그
 	var writer_p = $("<p>");  	

	// 작성자 정보의 이름
	var name_span = $("<span>");
	name_span.html(writer + "님");

	// 작성일시
	var date_span = $("<span>");
	date_span.html(" / " + datetime + " ");
	
	// 삭제하기 버튼
	var del_input = $("<button>");
	del_input.attr({"type" : "button"});
	del_input.addClass("btn btn-info");//bootstrap
	del_input.addClass("delete_btn");//삭제시 필요
	del_input.html("삭제");
	
	// 내용
	var content_p = $("<p>");
	content_p.html(content);
	
	// 조립하기
	if(writer=="${member.id}"){
		writer_p.append(name_span).append(date_span).append(del_input);
		new_li.append(writer_p).append(content_p);
	}else{	
		writer_p.append(name_span).append(date_span);
		new_li.append(writer_p).append(content_p);
	}
	
	$("#comment_list").prepend(new_li);
}

$(function() {
	
    /** 페이징 처리 원할때 넣는 파라미터 requestPage*/
    /* "requestPage" : 1 추가 */
    
    var bookid = ${book.bookid};
    
	/** 기본 덧글 목록 불러오기 */ /*익스플로우에서 안되고 크롬에서는 되고 다른걸로 대체 해보자.  */
	//$.getJSON("comment_search", {
    $.get("comment_search", {
        "bookid" : bookid       
    }, function(json) { 
 
    	$("#message").html(json.message);
    	    	
    	if(json.result){
    		
			var item = json.commentItem;			
	    	
			for(var i=0; i<item.length; i++) {
	    		addNewItem(item[i].commentid, item[i].writer, item[i].content, item[i].datetime, item[i].bookid);
	    	}			
    	}
    }).fail(function() {
        // 데이터 연동에 실패한 경우 (404, 500에러)
        alert("데이터 조회에 실패했습니다. 잠시후에 시도해 주세요.");
    }).always(function() {
        // 에러 여부에 상관없이 통신이 종료되면, 상태값을 다시 변경한다.
    });
	
	/** 덧글 내용 저장 이벤트 */
	$("#comment_form").submit(function() {

		// 내용에 대한 입력여부 검사
		if (!$("#comment").val()) {
			alert("내용을 입력하세요.");
			$("#comment").focus();
			return false;
		}

		var url = "comment_save";

		/** 글 저장을 위한 Post 방식의 Ajax 연동 처리 */
		$.post(url, $(this).serialize(), function(json) {

 	    	$("#message").html(json.message);
	    	
	    	if(json.result){
	    		
				var item = json.comment;	
				
		    	addNewItem(item.commentid, item.writer, item.content, item.datetime, item.bookid);
		    	
		    	$("#comment").val('').focus();
	    	} 
			
	    	
		}).fail(function() {
			alert("댓글 작성에 실패했습니다. 잠시 후에 다시 시도해 주세요.");
		});

		return false;
	});
	
	/** 삭제 버튼 클릭시에 항목 삭제하도록 "미리" 지정 */
	$(document).on("click", ".delete_btn", function() {
		if (confirm("정말 선택하신 항목을 삭제하시겠습니까?")) {

			// 덧글 삭제
			var url = "comment_delete";

			// 글번호 얻기
			var commentid = $(this).parents("li").attr("commentid");
			
			// 삭제 제거 대상 --> 클릭된 버튼을 기준으로 "comment_item"이라는 클래스를 갖는 상위 요소
			var target = $(this).parents("li");
			

			/** 글 번호를 통하여 삭제를 요청함 */
			$.post(url, {				
				"commentid" : commentid				
			}, function(json) {
				
				$("#message").html(json.message);
		    					
				// 삭제 성공을 의미할 경우, 삭제 대상 요소를 화면에서 제거
				if (json.result) {
					target.remove();
				}
			}).fail(function() {
				alert("댓글 삭제에 실패했습니다. 잠시 후에 다시 시도해 주세요.");
			});
		}
	});	
});
</script>
</head>
<body>
	<div class="container">
		<h2>도서상세보기</h2>

		<div class="row">
			<form action="addBook_toCart" method="post">
				<div class="col-sm-5 col-md-6 col-lg-4">
					<div class="thumbnail">
						<img src="${book.imagepath}" id="preview" name="preview"
							alt="이미지 추가하려면 클릭" style="color: lightgray; width: 100%" />
					</div>
				</div>
				<div class="col-sm-7 col-md-6 col-lg-8">
					<div class="form-group">
						<input name="bookid" value="${book.bookid}" hidden="hidden" /> <label
							for="bookid">책아이디:</label>
						<p type="text" class="form-control input-lg" id="bookid"
							readonly="readonly">${book.bookid}</p>
					</div>
					<div class="form-group">
						<label for="bookname">책이름:</label>
						<p type="text" class="form-control input-lg" id="bookname"
							name="bookname" readonly="readonly">${book.bookname}</p>
					</div>
					<div class="form-group">
						<label for="publisher">출판사:</label>
						<p type="text" class="form-control input-lg" id="publisher"
							name="publisher" readonly="readonly">${book.publisher }</p>
					</div>
					<div class="form-group">
						<label for="price">가격:</label>
						<p type="text" class="form-control input-lg" id="price"
							name="price" readonly="readonly">${book.price }</p>
					</div>

					<div class="form-group">
						<label for="description">설명:</label>
						<p class="form-control input-lg" rows="5" id="description"
							name="description" readonly="readonly">${book.description }</p>
					</div>

					<div class="form-group">
						<label for="quantity">수량:</label> <input type="text"
							class="form-control input-lg" id="quantity" name="quantity"
							placeholder="수량을 입력하세요.">
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary btn-lg">장바구니</button>
					</div>
				</div>
			</form>
		</div>
	</div>
	<!-- 목록이 출력될 요소 -->
	<div class="container">
		<h2>댓글</h2>
		<c:choose>
			<c:when test="${member== null}">
				<div class="form-group">
					<label>Comment : 댓글을 입력하려면 로그인이 필요합니다.</label>
				</div>
			</c:when>
			<c:when test="${member!= null}">
				<form id="comment_form">
					<input name="bookid" value="${book.bookid}" hidden="hidden" />
					<div class="form-group">
						<label for="comment">Comment:</label>
						<textarea class="form-control" rows="5" id="comment"
							name="content"></textarea>
					</div>
					<div class="form-group">
						<button type="submit" class="btn btn-primary btn-lg">추가</button>
					</div>
				</form>
			</c:when>
		</c:choose>
		<div class="form-group">
			<p id="message"></p>
		</div>
		<ul id="comment_list" class="list-group">
		</ul>
	</div>
</body>
</html>

