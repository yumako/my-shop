package com.zzz.my.shop.web.admin.service.impl;

import com.zzz.my.shop.commons.dto.BaseResult;
import com.zzz.my.shop.commons.dto.PageInfo;
import com.zzz.my.shop.commons.validator.BeanValidator;
import com.zzz.my.shop.domain.TbContentCategory;
import com.zzz.my.shop.web.admin.dao.TbContentCategoryDao;
import com.zzz.my.shop.web.admin.dao.TbContentDao;
import com.zzz.my.shop.web.admin.service.TbContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TbContentCategoryServiceImpl implements TbContentCategoryService {

    @Autowired
    private TbContentCategoryDao tbContentCategoryDao;
    @Autowired
    private TbContentDao tbContentDao;

    @Override
    public TbContentCategory getById(Long id) {
        return tbContentCategoryDao.getById(id);
    }

    @Override
    public void deleteMulti(String[] ids) {
        tbContentCategoryDao.deleteMulti(ids);
    }

    @Override
    public PageInfo<TbContentCategory> page(int start, int length, int draw, TbContentCategory entity) {
        return null;
    }

    @Override
    public int count(TbContentCategory tbContentCategory) {
        return tbContentCategoryDao.count(tbContentCategory);
    }

    @Override
    public List<TbContentCategory> selectAll() {
        return tbContentCategoryDao.selectAll();
    }

    @Override
    public BaseResult save(TbContentCategory entity) {
        String validator = BeanValidator.validator(entity);
        if (validator != null) {
            return BaseResult.fail(validator);
        } else {
            TbContentCategory parent = entity.getParent();
            // 如果没有选择父级节点则默认设置为根目录
            if (parent == null || parent.getId() == null) {
                // 0 代表根目录
                parent.setId(0L);
            }

            entity.setUpdated(new Date());
            // 新增
            if (entity.getId() == null) {
                entity.setCreated(new Date());
                entity.setIsParent(false);

                // 判断当前新增的节点有没有父级节点
                if (parent.getId() != 0L) {
                    TbContentCategory currentCategoryParent = getById(parent.getId());
                    if (currentCategoryParent != null) {
                        // 为父级节点设置 isParent 为 true
                        currentCategoryParent.setIsParent(true);
                        update(currentCategoryParent);
                    }
                }

                // 父级节点为 0 ，表示为根目录
                else {
                    // 根目录一定是父级目录
                    entity.setIsParent(true);
                }

                tbContentCategoryDao.insert(entity);
            }

            // 修改
            else {
                update(entity);
            }
            return BaseResult.success("保存分类信息成功");
        }
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void delete(Long id) {
        List<String> targetArray = new ArrayList<>();
        findAllChild(targetArray, id);

        String[] categoryIds = targetArray.toArray(new String[targetArray.size()]);
        //删除类目及其子类目
        tbContentCategoryDao.deleteCategory(categoryIds);
        //删除类目下所有内容
        tbContentDao.deleteByCategoryId(categoryIds);
    }

    /**
     * 查找出所有子节点
     * @param targetList
     * @param parentId
     */
    public void findAllChild(List<String> targetList,Long parentId){
        targetList.add(String.valueOf(parentId));

        List<TbContentCategory> tbContentCategories = selectByPid(parentId);
        for (TbContentCategory tbContentCategory : tbContentCategories) {
            findAllChild(targetList, tbContentCategory.getId());
        }
    }
    @Override
    public void update(TbContentCategory entity) {
        tbContentCategoryDao.update(entity);

    }

    /**
     * 根据 父级节点id partent_id 查询所有子节点
     *
     * @param pid
     * @return
     */
    @Override
    public List<TbContentCategory> selectByPid(Long pid) {
        return tbContentCategoryDao.selectByPid(pid);
    }

    @Override
    public void deleteCategory(String[] ids) {

    }
}
