package com.wblog.user.mapper;

import com.wblog.user.pojo.WblogUser;
import com.wblog.user.pojo.WblogUserStatus;
import com.wblog.user.query.WblogUserQuery;
import com.wblog.user.query.WblogUserStatusQuery;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface WblogUserStatusMapper {
    List<WblogUserStatus> findByCondition(WblogUserStatusQuery query);
    WblogUserStatus findById(Integer id);
    List<WblogUserStatus> findByConditionByPage(WblogUserStatus query);
    int insertUserStatus(WblogUserStatus user);
    int updateUserStatus(WblogUserStatus user);
}
