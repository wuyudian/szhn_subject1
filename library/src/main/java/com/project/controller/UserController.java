package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.bo.UserLoginBO;
import com.project.bo.UserBO;
import com.project.servicer.UserServicer;

import io.swagger.annotations.ApiOperation;

@Controller
public class UserController {

	@Autowired
	public UserServicer userServicer;
	
	@PostMapping("/user/create")
	@ApiOperation("创建用户接口")
	@ResponseBody
	public String userCreate(@RequestBody UserBO userBO) {	
		return userServicer.userCreate(userBO);
	}
	
	@PostMapping("/user/login")
	@ApiOperation("用户登录接口")
	@ResponseBody
	public String userLogin(@RequestBody UserLoginBO loginBO) {	
		    return userServicer.userLogin(loginBO);
	}
	
}
