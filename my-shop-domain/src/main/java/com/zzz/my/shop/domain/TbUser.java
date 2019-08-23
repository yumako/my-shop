package com.zzz.my.shop.domain;

import com.zzz.my.shop.commons.persistence.BaseEntity;
import lombok.Data;

import java.util.Date;

@Data
public class TbUser extends BaseEntity {
    private String username;
    private String password;
    private String phone;
    private String email;

}
