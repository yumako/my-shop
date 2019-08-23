package com.zzz.my.shop.commons.persistence;

import java.util.List;
import java.util.Map;

public interface BaseDao<T extends BaseEntity> {
    /**
     * 查询所有
     * @return
     */
    public List<T> selectAll();

    /**
     * 新增
     * @param entity
     */
    public void insert(T entity);

    /**
     * 删除
     * @param id
     */
    public void delete(Long id);

    /**
     * 根据 ID 查询信息
     * @param id
     * @return
     */
    public T getById(Long id);

    /**
     * 更新
     * @param entity
     */
    public void update(T entity);

    /**
     * 批量删除
     * @param ids
     */
    public void deleteMulti(String[] ids);

    /**
     * 分页查询
     * @param parmas，需要两个参数，start/记录开始的位置 length/每页记录数
     * @return
     */
    public List<T> page(Map<String,Object> parmas);

    /**
     * 查询总数
     * @return
     */
    int count(T entity);
}
