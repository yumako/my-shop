package com.zzz.my.shop.web.admin.dao;

import com.zzz.my.shop.commons.persistence.BaseDao;
import com.zzz.my.shop.domain.TbContentCategory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TbContentCategoryDao extends BaseDao<TbContentCategory> {

    /**
     * 根据 父级节点id partent_id 查询所有子节点
     * @param pid
     * @return
     */
    public List<TbContentCategory> selectByPid(Long pid);

    public void deleteCategory(String[] ids);
}
