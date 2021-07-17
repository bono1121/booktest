package service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.Part;

public class FileManager {
	
	private static final String FILE_SERVER_PATH = "c:/hanbit/FileServer/";
		

	public static String getFileServerPath() {
		return FILE_SERVER_PATH;
	}

	private static String getNewFilename(Part part) {
        
		
		String contentDispositionHeader = 
                part.getHeader("content-disposition");

        String[] elements = contentDispositionHeader.split(";");
        
        for (String element : elements) {  
        	
        	//element.trim().startsWith("filename") : filename이란 엘리먼트가 존재하고 
        	//element.length()>12 : 그 값이 존재하는 경우 (filename=""가 아닌 경우)
        	//참고 : 원래는 element 값이 filename="c:\.....\x.jpg" 이런형식으로 들어올 때가 정상      	
            if (element.trim().startsWith("filename") && element.length()>12) {
           	
            	/* 파일명 만들기 1 => 경로에서 파일명만 리턴시키기 */
            	//element ==> filename="c:\.....\x.jpg"에서 파일명 빼내기
            	//File file = new File(element.substring(element.indexOf('=') + 1, element.length()-1).trim());
            	//return file.getName();
            	
               	
            	/* 파일명 만들기 2 => 유일한 파일명 리턴시키기 */            	
            	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmSSS");
               	Date date = new Date();

            	String newFilename = sdf.format(date);
            	//element ==> filename="c:\.....\x.jpg"에서 jpg만 빼내기
                String extension = element.substring(element.indexOf('.') , element.length()-1).trim();

            	//파일명 : 저장날짜시간+확장자
            	return newFilename+extension;               
            }
        }
        return null;  
    }
    
    public static String saveImageToFileServer(Part part) throws IOException{
    	
        String newFileName = getNewFilename(part);
    	
        if (newFileName != null && !newFileName.isEmpty()) {
            part.write(FILE_SERVER_PATH + newFileName);

            return newFileName;
        }
        return null;
                
    }
    
    public static void deleteFile(String filename){
    
		//ServletContext context = getServletContext();	
		//String path = context.getRealPath("upload");	
		//System.out.println("path : " + path);	
		//System.out.println("pictureUrl : " + pictureUrl);	
		//System.out.println(path + "\\" + pictureUrl);

		File f = new File(FILE_SERVER_PATH+filename);		
	
		if(f.exists()){
			f.delete();	
			System.out.println("파일 삭제됨");	
		}else{	
			System.out.println("파일 없음");	
		}
    }
}
