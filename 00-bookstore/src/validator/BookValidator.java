package validator;


import java.util.ArrayList;
import java.util.List;

import form.BookForm;
import formError.BookError;

public class BookValidator {
	
	public BookError validate(BookForm bookForm){		
			
		List<String> errors = new ArrayList<String>();
		BookError bookError = new BookError();
		
		String bookname= bookForm.getBookname();
		
		if(bookname==null || bookname.trim().isEmpty()){
			errors.add("책이름을 입력하세요");
			bookError.setBooknameErr("책이름을 입력하세요");
			bookError.setResult(true);
		}
		String publisher= bookForm.getPublisher();
		
		if(publisher==null || publisher.trim().isEmpty()){
			errors.add("출판사를 입력하세요");
			bookError.setPublisherErr("출판사를 입력하세요");
			bookError.setResult(true);
		}
		
		String price= bookForm.getPrice();
		
		if(price==null || price.trim().isEmpty()){
			errors.add("가격을 입력하세요");
			bookError.setPriceErr("가격을 입력하세요");
			bookError.setResult(true);
		}else{
			try{
				Integer.parseInt(price);
			}catch(NumberFormatException e){				
				errors.add("가격 값이 잘못되었습니다.");
				bookError.setPriceErr("가격 값이 잘못되었습니다.");
				bookError.setResult(true);
			}
		}
		
		String description= bookForm.getDescription();
		
		if(description==null || description.trim().isEmpty()){
			errors.add("설명을 입력하세요");
			bookError.setDescriptionErr("설명을 입력하세요");
			bookError.setResult(true);
		}
		
		return bookError;
	}
}
