package com.wblog.user.rpo;

import com.wblog.user.constant.WblogResult;
import com.wblog.user.dto.WblogUserInfo;
import com.wblog.user.pojo.WblogUser;
import com.wblog.user.query.WblogUserQuery;

import java.util.concurrent.ExecutionException;

public interface WblogUserRpo {
    WblogResult<WblogUserInfo> getWblogUserByAccount(WblogUserQuery query) throws ExecutionException;
    WblogResult<Integer> insertWblogUser(WblogUser user);
    WblogResult<Integer> updateWblogUser(WblogUser user);
}
