package com.zzz.my.shop.web.admin.service;

import com.zzz.my.shop.commons.persistence.BaseService;
import com.zzz.my.shop.domain.TbContent;

public interface TbContentService extends BaseService<TbContent> {

    /**
     * 根据类目 ID 删除内容
     * @param categoryIds
     */
    public void deleteByCategoryId(String[] categoryIds);

}
