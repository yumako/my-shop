package com.zzz.my.shop.web.ui.api;

import com.zzz.my.shop.commons.utils.HttpClientUtils;
import com.zzz.my.shop.commons.utils.JsonUtils;
import com.zzz.my.shop.web.ui.dto.TbUser;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员管理接口
 * <p>Title: UsersApi</p>
 * <p>Description: </p>
 *
 * @author 111
 * @version 1.0.0
 * @date 2019/8/29 21:57
 */
public class UsersApi {

    /**
     * 登录
     * @param tbUser
     * @return
     * @throws Exception
     */
    public static TbUser login(TbUser tbUser) throws Exception {
        List<BasicNameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("username", tbUser.getUsername()));
        params.add(new BasicNameValuePair("password", tbUser.getPassword()));

        String json = HttpClientUtils.doPost(API.API_USER_LOGIN, params.toArray(new BasicNameValuePair[params.size()]));
        TbUser user = JsonUtils.json2pojoByTree(json, "data", TbUser.class);

        return user;
    }
}
