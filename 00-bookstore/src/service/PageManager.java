package service;

import dao.PageDAO;
import dao.impl.PageDAOImpl;
import model.page.PageGroupResult;
import model.page.PageInfo;
import model.page.PageRowResult;

public class PageManager {

	private int requestPage;
	
	public PageManager(){
	
	}
			
	public PageManager(int requestPage) {
		super();
		this.requestPage = requestPage;
	}

	public PageRowResult getPageRowResult(){
	
		PageRowResult pageRowResult = new PageRowResult();
		//row 시작번호
		pageRowResult.setRowStartNumber((requestPage * PageInfo.ROW_COUNT_PER_PAGE) - (PageInfo.ROW_COUNT_PER_PAGE-1));
		//row 마지막 번호
		pageRowResult.setRowEndNumber((pageRowResult.getRowStartNumber() + PageInfo.ROW_COUNT_PER_PAGE) - 1);

		return pageRowResult;
	}
	
	public PageGroupResult getPageGroupResult(String sql){
		
		PageGroupResult pageGroupResult = new PageGroupResult();
		
		//요청페이지를 SHOW_PAGE_COUNT를 나누면 현재페이지 그룹넘버를 구할 수 있다.
		int requestPageGroupNumber = (int)Math.ceil((double)requestPage/PageInfo.SHOW_PAGE_COUNT);
		
		//그룹 시작 넘버 		
		pageGroupResult.setGroupStartNumber((requestPageGroupNumber*PageInfo.SHOW_PAGE_COUNT)-(PageInfo.SHOW_PAGE_COUNT-1));
		//그룹 마지막 넘버
		pageGroupResult.setGroupEndNumber(requestPageGroupNumber*PageInfo.SHOW_PAGE_COUNT);
		
		PageDAO pageDAO = new PageDAOImpl();		
		int totalPageNumber=0;
		
		totalPageNumber = (int)Math.ceil((double)pageDAO.getCount(sql)/PageInfo.ROW_COUNT_PER_PAGE);			
				
		//last 페이지수 확인
		if(pageGroupResult.getGroupEndNumber()>totalPageNumber){
			pageGroupResult.setGroupEndNumber(totalPageNumber);
		}		
		
		//before, after 유무
		if(pageGroupResult.getGroupStartNumber()==1){
			pageGroupResult.setBeforePage(false);
		}
		
		if(pageGroupResult.getGroupEndNumber()==totalPageNumber){
			pageGroupResult.setAfterPage(false);
		}
		
		//선택페이지 set : list.jsp 에서 active 표시를 위해 필요
		pageGroupResult.setSelectPageNumber(requestPage);
		
		return pageGroupResult;
	}
	
/*	public static void main(String[] args) {
		PageManager pm = new PageManager(5, PageInfo.BOOK);
		PageRowResult pageRowResult= pm.getPageRowResult();
		PageGroupResult pageGroupResult = pm.getPageGroupResult();
		System.out.println("rowStartNumber:"+pageRowResult.getRowStartNumber());
		System.out.println("rowEndNumber:"+pageRowResult.getRowEndNumber());
		System.out.println("pageStartNumber:"+pageGroupResult.getGroupStartNumber());
		System.out.println("pageEndNumber"+pageGroupResult.getGroupEndNumber());
	}*/
}

/*
SELECT * FROM (SELECT ROWNUM rn, books.* FROM (SELECT * FROM book ORDER BY bookid DESC) books)
WHERE rn BETWEEN 1 AND 2;
 */