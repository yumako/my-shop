package com.zzz.my.shop.domain;

import com.zzz.my.shop.commons.persistence.BaseEntity;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 内容分类实体类
 * @param
 * @return
 * @author yumako
 * @date 2019/8/12
 */
@Data
public class TbContentCategory extends BaseEntity {

    private Long id;
    private Long parentId;

    @Length(min = 1,max = 20,message = "父级目录必须在 1-20 个字符之间")
    private String name;

    private Integer status;
    private Integer sortOrder;
    private Boolean isParent;
    private TbContentCategory parent;
}
