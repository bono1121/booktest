/* <-- jquery 요청방법 : https://api.jquery.com/jquery.post/  --> */
$(function() {
	/** id속성이 "checkid"인 요소에 대한 "click"이벤트 정의 */
	$("#checkid").click(
		function() {
			// 사용자 입력값 얻어오기
			var input_value = $("input[name='id']").val();

			//alert(input_value);

			// 입력여부 검사
			if (!input_value) {
				alert("아이디를 입력하세요.");
				$("input[name='id']").focus();
				return false;
			}

			/** 데이터파일의 URL설정 */
			var url = "idcheck";

			/** get방식 ajax 연동 */
			$.get(
				url,
				{
					"id" : input_value
				},
				function(data) {
					/** XML 데이터를 읽어왔을 때, 이 함수의 파라미터는 XML-DOM형태이며,
					 *  데이터를 추출하는 방법은 $.ajax()함수와 동일하다.*/
					// 데이터 로드에 성공한 경우 XML에서 "result"태그의 값을 추출
					var result_text = $(data).find(
							"result").text();
					// "true" 혹은 "false"라는 문자열이므로, eval함수를 사용하여 boolean값으로 변경
					var result = eval(result_text);

					// 결과 출력
					//result = false:아이디 사용 불가능
					//result = true :아이디 사용 가능
					if (result) {
						$(".console")
								.html(
										"<span style='color:blue'>사용할 수 있는 아이디 입니다.</span>");
					} else {
						$(".console")
								.html(
										"<span style='color:red'>사용할 수 없는 아이디 입니다.</span>");
					}
				});
		});
});	