package com.zzz.my.shop.web.api.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * 会员数据传输对象
 * <p>Title: TbUserDTO</p>
 * <p>Description: </p>
 *
 * @author 111
 * @version 1.0.0
 * @date 2019/8/28 9:59
 */
@Data
public class TbUserDTO implements Serializable {
    private Long id;
    private String username;
    @JsonIgnore
    private String password;
    private String phone;
    private String email;
}
