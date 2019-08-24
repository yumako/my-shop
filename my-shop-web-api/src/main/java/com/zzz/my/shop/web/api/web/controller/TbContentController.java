package com.zzz.my.shop.web.api.web.controller;

import com.zzz.my.shop.domain.TbContent;
import com.zzz.my.shop.web.api.service.TbContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "content")
public class TbContentController {

    @Autowired
    private TbContentService tbContentService;

    public TbContent getTbContent(Long id){
        TbContent tbContent = null;
        if (id == null){
            tbContent = new TbContent();
        }
        return tbContent;
    }

    @RequestMapping(value = "findTbcontentByCategoryId",method = RequestMethod.GET)
    public List<TbContent> findTbcontentByCategoryId(Long categortId){
        return tbContentService.selectByCategoryId(categortId);
    }

}
