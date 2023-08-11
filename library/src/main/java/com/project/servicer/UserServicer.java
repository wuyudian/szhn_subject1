package com.project.servicer;

import com.project.bo.UserLoginBO;
import com.project.bo.UserBO;

public interface UserServicer {
	
	String userCreate(UserBO userBO);
	
	String userLogin(UserLoginBO loginBO);
	
}
