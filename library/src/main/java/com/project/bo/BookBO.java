package com.project.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BookBO {

	private String name;
	
	private String author;
	
	private BookCategory category;
	
}
