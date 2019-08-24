package com.zzz.my.shop.web.api.service;

import com.zzz.my.shop.domain.TbContent;

import java.util.List;

public interface TbContentService {

    List<TbContent> selectByCategoryId(Long categoryId);
}
