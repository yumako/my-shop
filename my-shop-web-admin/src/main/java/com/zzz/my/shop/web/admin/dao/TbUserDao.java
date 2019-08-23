package com.zzz.my.shop.web.admin.dao;

import com.zzz.my.shop.commons.persistence.BaseDao;
import com.zzz.my.shop.domain.TbUser;
import org.springframework.stereotype.Repository;

@Repository
public interface TbUserDao extends BaseDao<TbUser> {
    /**
     *根据邮箱查询用户信息
     * @param
     * @return
     * @author yumako
     * @date 2019/8/4
     */
    public TbUser getByEmail(String email);

}
