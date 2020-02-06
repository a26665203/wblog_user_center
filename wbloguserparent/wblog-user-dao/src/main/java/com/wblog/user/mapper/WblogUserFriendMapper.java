package com.wblog.user.mapper;

import com.wblog.user.pojo.WblogUser;
import com.wblog.user.pojo.WblogUserFriend;
import com.wblog.user.query.WblogUserFriendQuery;
import com.wblog.user.query.WblogUserQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface WblogUserFriendMapper {
    List<WblogUserFriend> findByCondition(WblogUserFriendQuery query);
    WblogUserFriend findById(Integer id);
    List<WblogUserFriend> findByConditionByPage(WblogUserFriendQuery query);
    int insertUserFriend(WblogUserFriend user);
    int updateUserFriend(WblogUserFriend user);
    int deleteUserFriend(@Param("nickName") String nickName, @Param("friendName") String friendName);
}
