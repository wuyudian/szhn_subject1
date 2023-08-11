package com.project.servicer;

import java.util.List;

import com.project.bo.BookBO;
import com.project.bo.BookUpdateBO;
import com.project.vo.BookVO;

public interface BookServicer {

	String libraryBookAdd(BookBO bookBO, String userName);
	
	String libraryBookDelete(Integer bookId, String userName);
	
	String libraryBookUpdate(BookUpdateBO bookUpdateBO, String userName);
	
	List<BookVO> libraryBookQuery(BookBO bookBO);
	
}
