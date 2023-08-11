package com.project.bo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserBO {

	private String userName;

	private String password;

	private UserType type;
	
}
