$(document).ready(function() {
	
	$("#buyButton").prop("disabled", true);
	
	$("#checkedAll").change(function() {
		if (this.checked) {
			$(".checkSingle").each(function() {
				this.checked = true;
				$("#buyButton").prop("disabled", false);
			})
		} else {
			$(".checkSingle").each(function() {
				this.checked = false;
				$("#buyButton").prop("disabled", true);
			})
		}
	});

	$(".checkSingle").click(function() {
		//아이템이 선택했을때
		if ($(this).is(":checked")) { 
			
			//구매버튼 활성화
			$("#buyButton").prop("disabled", false);
			
			//checkedAll를 체크해야할지 안해야할지 체크
			var isAllChecked = 1; //모두 체크가 됐다고 가정하고..		
			
			$(".checkSingle").each(function() {//아이템 모두 돌아가면서 모두 체크됐는지 확인
				if (!this.checked)//한개라도 체크가 안된게 있으면
					isAllChecked = 0;//0으로 값을 바꾼다.
			})
			
			if (isAllChecked == 1) {//모두 체크된 경우처리
				$("#checkedAll").prop("checked", true);
				$("#buyButton").prop("disabled", false);
			}
	    
	    //아이템이 선택취소하는 경우
		} else {
			$("#checkedAll").prop("checked", false);					
			$("#buyButton").prop("disabled", true);			
			
			//한개라도 선택된게 있는지 체크		
			var isBuyButtonisabled = 0; 
			
			$(".checkSingle").each(function() {
				if (this.checked)
					isBuyButtonisabled = 1;
			})
			
			if (isBuyButtonisabled == 1) {
				$("#buyButton").prop("disabled", false);
			}
		}
	});
});