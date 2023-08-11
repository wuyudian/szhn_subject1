package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.po.LibraryBookPO;

@Mapper
public interface LibraryBookMapper extends BaseMapper<LibraryBookPO> {

}
