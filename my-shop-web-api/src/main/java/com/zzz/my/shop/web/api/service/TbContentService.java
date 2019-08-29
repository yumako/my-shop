package com.zzz.my.shop.web.api.service;

import com.zzz.my.shop.domain.TbContent;

import java.util.List;

public interface TbContentService {

    /**
     * 根据 categoryId 查询内容列表
     * @param tbContent
     * @return
     */
    List<TbContent> selectByCategoryId(Long categoryId);
}
