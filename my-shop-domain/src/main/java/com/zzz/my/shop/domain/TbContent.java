package com.zzz.my.shop.domain;

import com.zzz.my.shop.commons.persistence.BaseEntity;
import javafx.scene.shape.Mesh;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * 文章内容管理
 */
@Data
public class TbContent extends BaseEntity {
    @Length(min = 1,max = 20,message = "标题长度介于 1 - 20 个字符之间")
    private String title;

    @Length(min = 1,max = 20,message = "子标题长度必须介于 1 - 20 之间")
    private String subTitle;

    @Length(min = 1,max = 50,message = "标题描述长度必须介于 1 - 50 之间")
    private String titleDesc;


    private String url;
    private String pic;
    private String pic2;

    @Length(min = 1,message = "内容不能为空")
    private String content;

    @NotNull(message = "文章所属类别不能为空")
    private TbContentCategory tbContentCategory;
}
