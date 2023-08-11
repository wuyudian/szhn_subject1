package com.project.servicer.impl;

import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.bo.UserLoginBO;
import com.project.bo.UserType;
import com.project.bo.UserBO;
import com.project.mapper.UserInfoMapper;
import com.project.po.UserInfoPO;
import com.project.servicer.UserServicer;

@Service
public class UserServicerImpl implements UserServicer {

	@Autowired
	public UserInfoMapper userInfoMapper;

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final Logger logger= Logger.getLogger("UserServicerImpl.class");
	
	@Override
	public String userCreate(UserBO userBO) {
		if (StringUtils.isAnyBlank(userBO.getUserName(), userBO.getPassword())) {
			return "用户名和密码不能为空";
		}
		UserType type = userBO.getType();
		if (null == type) {
			return "用户名类型不能为空";
		}
		if (type.equals(UserType.ADMIN) && type.equals(UserType.STUDENT) 
				&& type.equals(UserType.TEACHER)) {
			return "用户名类型不正确";
		}
		
		QueryWrapper<UserInfoPO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", userBO.getUserName());
		UserInfoPO userInfoPO = userInfoMapper.selectOne(queryWrapper);
		if (null == userInfoPO) {
			// 保存用户信息
			userInfoPO = new UserInfoPO();
			UUID uuid = UUID.randomUUID();
			userInfoPO.setUserId(uuid.toString());
			userInfoPO.setUserName(userBO.getUserName());
			userInfoPO.setPassword(userBO.getPassword());
			userInfoPO.setType(userBO.getType().toString());
			userInfoPO.setCreateDate(new Date());
			userInfoMapper.insert(userInfoPO);
			// 缓存用户信息，key为用户名
			redisTemplate.opsForValue().set(userBO.getUserName(), userInfoPO);
		} else {
			return "用户名已存在";
		}
		
		logger.info("userCreate success");
		
		return "用户创建成功";
	}
	
	@Override
	public String userLogin(UserLoginBO loginBO) {
		if (StringUtils.isAnyBlank(loginBO.getUserName(), loginBO.getPassword())) {
			return "用户名和密码不能为空";
		}
		// 查缓存
		UserInfoPO userInfo = (UserInfoPO)redisTemplate.opsForValue().get(loginBO.getUserName());
		if (null != userInfo && StringUtils.equals(userInfo.getPassword(), loginBO.getPassword())) {
			return "登录成功";
		}
		// 查数据库
		QueryWrapper<UserInfoPO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", loginBO.getUserName());
		queryWrapper.eq("password", loginBO.getPassword());
		UserInfoPO userInfoPO = userInfoMapper.selectOne(queryWrapper);
		if (null == userInfoPO) {
			return "登录失败";
		}
		
		logger.info("userLogin success");
		
		return "登录成功";
	}
	
}
