$(function() {
    // #user_name 요소와 #user_password 두 요소에 대한,
    // fucos, blur 이벤트 처리
    // --> bind 용법 사용함
    $("#id, #pwd").bind({
        "focus" : function() {
            $(this).addClass("active");
        },
        "blur" : function() {
            $(this).removeClass("active");
        }
    });

    // 폼에 대한 submit 이벤트 처리 --> 입력값 여부 검사를 수행한다.
    $("#login").bind("submit", function() {
        /** 입력여부 검사 */
        if (!$("#id").val()) {
            alert("아이디를 입력하세요.");
            $("#id").focus();
            return false;
        }

        if (!$("#pwd").val()) {
            alert("비밀번호를 입력하세요.");
            $("#pwd").focus();
            return false;
        }

        /** 로그인처리에 대한 JSP예시 */
        var url = "login_ajax";

        /*** ajax 로그인처리 ***/
        var ajax = $.post(url, $(this).serialize(), function(data) {
            // XML에서 result태그와 message태그의 내용 추출
            var result = $(data).find("result").text();
            var message = $(data).find("message").text();

            // result값은 boolean형으로 변환.
            var result_value = eval(result);
            
            // 결과메시지 출력
            alert(message);

            // 결과에 따른 후속 처리
            if (result_value) {
                // 로그인 성공시 처리할 부분
                location.href="index.jsp";
            }             
        }).fail(function() {
            alert("로그인에 실패하였습니다. 잠시후에 다시 시도해 주세요.");
        });
        return false;
    });
});