package com.zzz.my.shop.web.admin.service;

import com.zzz.my.shop.commons.persistence.BaseService;
import com.zzz.my.shop.domain.TbContentCategory;

import java.util.List;

public interface TbContentCategoryService extends BaseService<TbContentCategory> {
    /**
     * 根据 父级节点id partent_id 查询所有子节点
     * @param id
     * @return
     */
    public List<TbContentCategory> selectByPid(Long id);

    public void deleteCategory(String[] ids);
}
