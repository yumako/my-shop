package com.zzz.my.shop.web.api.service;

import com.zzz.my.shop.domain.TbUser;

/**
 * 会员管理
 * <p>Title: TbUserService</p>
 * <p>Description: </p>
 *
 * @author 111
 * @version 1.0.0
 * @date 2019/8/28 9:12
 */
public interface TbUserService {

    /**
     * 登录
     * @param tbUser
     * @return
     */
    TbUser login(TbUser tbUser);
}
