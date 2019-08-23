package com.zzz.my.shop.web.admin.dao;

import com.zzz.my.shop.commons.persistence.BaseDao;
import com.zzz.my.shop.domain.TbContent;
import org.springframework.stereotype.Repository;

@Repository
public interface TbContentDao extends BaseDao<TbContent> {

    /**
     * 根据类目 ID 删除内容
     * @param categoryIds
     */
    public void deleteByCategoryId(String[] categoryIds);
}
