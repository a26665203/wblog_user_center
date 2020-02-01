package com.wblog.user.rpo;

import com.wblog.user.constant.WblogResult;
import com.wblog.user.pojo.WblogUser;
import com.wblog.user.query.WblogUserQuery;

import java.util.concurrent.ExecutionException;

public interface WblogUserRpo {
    WblogResult getWblogUserByAccount(WblogUserQuery query) throws ExecutionException;
    WblogResult insertWblogUser(WblogUser user);
    WblogResult updateWblogUser(WblogUser user);
}
