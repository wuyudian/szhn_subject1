package com.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.project.po.UserInfoPO;

@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoPO> {

}
