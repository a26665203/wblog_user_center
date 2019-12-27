package com.wblog.user.mapper;

import com.wblog.user.pojo.WblogUser;
import com.wblog.user.query.WblogUserFriendQuery;
import com.wblog.user.query.WblogUserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WblogUserMapper {
    List<WblogUser> findByCondition(WblogUserQuery query);
    WblogUser findById(Integer id);
    List<WblogUser> findByConditionByPage(WblogUserQuery query);
    int insertUser(WblogUser user);
    int updateUser(WblogUser user);
}
