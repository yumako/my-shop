package com.zzz.my.shop.web.ui.api;

import com.zzz.my.shop.commons.utils.HttpClientUtils;
import com.zzz.my.shop.commons.utils.JsonUtils;
import com.zzz.my.shop.web.ui.dto.TbContent;

import java.util.List;

/**
 * 内容管理接口
 * <p>Title: ContentsApi</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2019/8/26 21:57
 */
public class ContentsApi {

    /**
     * 首页轮播图
     * @return
     */
    public static List<TbContent> ppt(){
        List<TbContent> tbContents = null;
        String result = HttpClientUtils.doGet(API.API_CONTENTS_PPT + "100");
        try {
            tbContents = JsonUtils.json2listByTree(result, "data", TbContent.class);
            return tbContents;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbContents;
    }
}
