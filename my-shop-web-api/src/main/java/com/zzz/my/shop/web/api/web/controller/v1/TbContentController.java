package com.zzz.my.shop.web.api.web.controller.v1;

import com.zzz.my.shop.commons.dto.BaseResult;
import com.zzz.my.shop.domain.TbContent;
import com.zzz.my.shop.web.api.service.TbContentService;
import com.zzz.my.shop.web.api.web.dto.TbContentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "${api.path.v1}/contents")
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

//    @RequestMapping(value = "{category_id}",method = RequestMethod.GET)
//    public List<TbContentDTO> findTbContentByCategoryId(@PathVariable(value = "category_id",required = true) Long categoryId){
//        List<TbContentDTO> tbContentDTOS = null;
//        List<TbContent> tbContents = tbContentService.selectByCategoryId(categoryId);
//
//        if (tbContents != null && tbContents.size() > 0){
//            tbContentDTOS = new ArrayList<>();
//            for (TbContent tbContent : tbContents) {
//                TbContentDTO dto = new TbContentDTO();
//                BeanUtils.copyProperties(tbContent, dto);
//                tbContentDTOS.add(dto);
//            }
//        }
//
//        return tbContentDTOS;
//    }

    /**
     * 门户首页大图轮播图接口
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "ppt/{category_id}",method = RequestMethod.GET)
    public BaseResult findPPT(@PathVariable(value = "category_id",required = true)Long categoryId){
        List<TbContentDTO> tbContentDTOS = null;
        List<TbContent> tbContents = tbContentService.selectByCategoryId(categoryId);

        if (tbContents != null && tbContents.size() > 0){
            tbContentDTOS = new ArrayList<>();
            for (TbContent tbContent : tbContents) {
                TbContentDTO dto = new TbContentDTO();
                BeanUtils.copyProperties(tbContent, dto);
                tbContentDTOS.add(dto);
            }
        }
        return BaseResult.success("首页轮播图请求成功",tbContentDTOS);
    }


}


