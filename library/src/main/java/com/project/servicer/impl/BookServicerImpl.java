package com.project.servicer.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.project.bo.BookBO;
import com.project.bo.BookCategory;
import com.project.bo.BookUpdateBO;
import com.project.bo.UserType;
import com.project.mapper.LibraryBookMapper;
import com.project.mapper.UserInfoMapper;
import com.project.po.LibraryBookPO;
import com.project.po.UserInfoPO;
import com.project.servicer.BookServicer;
import com.project.vo.BookVO;

@Service
public class BookServicerImpl implements BookServicer {

	@Autowired
	public UserInfoMapper userInfoMapper;
	
	@Autowired
	public LibraryBookMapper libraryBookMapper;
	
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	
	private static final Logger logger= Logger.getLogger("BookServicerImpl.class");
	
	@Override
	public String libraryBookAdd(BookBO bookBO, String userName) {
		String userType = getUserType(userName);
		if (!StringUtils.equals(UserType.ADMIN.name(), userType)) {
			return "没有新增图书权限";
		}
		if (StringUtils.isAnyBlank(bookBO.getName(), bookBO.getAuthor())) {
			return "书名和作者不能为空";
		}
		BookCategory category = bookBO.getCategory();
		if (null == category) {
			return "图书类别不能为空";
		}
		if (category.equals(BookCategory.NOVEL) && category.equals(BookCategory.HISTORY) 
				&& category.equals(BookCategory.SCIENCE) && category.equals(BookCategory.OTHER)) {
			return "图书类别不正确";
		}
		
		QueryWrapper<LibraryBookPO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", bookBO.getName());
		queryWrapper.eq("author", bookBO.getAuthor());
		queryWrapper.eq("category", bookBO.getCategory().name());
		LibraryBookPO libraryBookPO = libraryBookMapper.selectOne(queryWrapper);
		if (null == libraryBookPO) {
			// 保存图书信息
			libraryBookPO = new LibraryBookPO();
			libraryBookPO.setName(bookBO.getName());
			libraryBookPO.setAuthor(bookBO.getAuthor());
			libraryBookPO.setCategory(bookBO.getCategory().name());
			libraryBookPO.setCreateDate(new Date());
			libraryBookMapper.insert(libraryBookPO);
		} else {
			return "图书已存在";
		}
		
		logger.info("libraryBookAdd success");
		
		return "图书新增成功";
	}

	@Override
	public String libraryBookDelete(Integer bookId, String userName) {
		String userType = getUserType(userName);
		if (!StringUtils.equals(UserType.ADMIN.name(), userType)) {
			return "没有删除图书权限";
		}
		if (null != bookId) {
			libraryBookMapper.deleteById(bookId); 
		}
		
		logger.info("libraryBookDelete success");
		
		return "图书删除成功";
	}
	
	@Override
	public String libraryBookUpdate(BookUpdateBO bookUpdateBO, String userName) {
		String userType = getUserType(userName);
		if (!StringUtils.equals(UserType.ADMIN.name(), userType)) {
			return "没有更新图书权限";
		}
		if (null == bookUpdateBO.getBookId()) {
			return "书id不能为空";
		}
		LibraryBookPO libraryBookPO = libraryBookMapper.selectById(bookUpdateBO.getBookId());
		if (null == libraryBookPO) {
			return "该图书不存在";
		}
		
		if (StringUtils.isNotBlank(bookUpdateBO.getName())) {
			libraryBookPO.setName(bookUpdateBO.getName());
		}
		if (StringUtils.isNotBlank(bookUpdateBO.getAuthor())) {
			libraryBookPO.setAuthor(bookUpdateBO.getAuthor());
		}
		if (null != bookUpdateBO.getCategory()) {
			libraryBookPO.setCategory(bookUpdateBO.getCategory().name());
		}		
		libraryBookPO.setUpdateDate(new Date());
		libraryBookMapper.updateById(libraryBookPO);
		
		logger.info("libraryBookUpdate success");
		
		return "更新成功";
	}
	
	@Override
	public List<BookVO> libraryBookQuery(BookBO bookBO) {
		QueryWrapper<LibraryBookPO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("name", bookBO.getName());
		queryWrapper.eq("author", bookBO.getAuthor());
		queryWrapper.eq("category", bookBO.getCategory().name());
		List<LibraryBookPO> bookList = libraryBookMapper.selectList(queryWrapper);
		
		List<BookVO> resultList = new ArrayList<>();
		bookList.forEach(param -> {
			BookVO bookVO = new BookVO();
			bookVO.setBookId(param.getBookId());
			bookVO.setAuthor(param.getAuthor());
			bookVO.setCategory(param.getCategory());
			resultList.add(bookVO);
		});
		
		logger.info("libraryBookQuery success");
		
		return resultList;
	}
	
	private String getUserType(String userName) {
		// 查缓存
		UserInfoPO userInfo = (UserInfoPO)redisTemplate.opsForValue().get(userName);
		if (null != userInfo) {
			return userInfo.getType();
		}
		// 查数据库
		QueryWrapper<UserInfoPO> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("user_name", userName);
		UserInfoPO userInfoPO = Optional.ofNullable(userInfoMapper.selectOne(queryWrapper))
				.orElse(new UserInfoPO());
		return userInfoPO.getType();
	}
	
}
