package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bo.BookBO;
import com.project.bo.BookUpdateBO;
import com.project.servicer.BookServicer;
import com.project.vo.BookVO;

import io.swagger.annotations.ApiOperation;

@Controller
public class BookController {

	@Autowired
	public BookServicer bookServicer;
	
	@PostMapping("/book/add")
	@ApiOperation("新增图书接口")
	@ResponseBody
	public String bookCreate(@RequestBody BookBO bookBO, @RequestParam String userName) {	
		return bookServicer.libraryBookAdd(bookBO, userName);
	}
	
	@PostMapping("/book/delete")
	@ApiOperation("删除图书接口")
	@ResponseBody
	public String bookDelete(@RequestParam Integer bookId, @RequestParam String userName) {	
		return bookServicer.libraryBookDelete(bookId, userName);
	}
	
	@PostMapping("/book/update")
	@ApiOperation("更新图书接口")
	@ResponseBody
	public String bookUpdate(@RequestBody BookUpdateBO bookUpdateBO, @RequestParam String userName) {	
		return bookServicer.libraryBookUpdate(bookUpdateBO, userName);
	}
	
	@PostMapping("/book/query")
	@ApiOperation("查询图书接口")
	@ResponseBody
	public List<BookVO> bookQuery(@RequestBody BookBO bookBO) {	
		return bookServicer.libraryBookQuery(bookBO);
	}
	
}
