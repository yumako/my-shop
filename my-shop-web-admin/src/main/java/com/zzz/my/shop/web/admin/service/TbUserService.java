package com.zzz.my.shop.web.admin.service;

import com.zzz.my.shop.commons.persistence.BaseService;
import com.zzz.my.shop.domain.TbUser;

public interface TbUserService extends BaseService<TbUser> {
    /**
     * 用户登录
     * @param email
     * @param password
     * @return
     */
    public TbUser login(String email, String password);
}
