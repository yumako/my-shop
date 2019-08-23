package com.zzz.my.shop.web.admin.service.test;

import com.zzz.my.shop.domain.TbUser;
import com.zzz.my.shop.web.admin.dao.TbUserDao;
import com.zzz.my.shop.web.admin.service.TbUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring-context.xml", "classpath:spring-context-druid.xml", "classpath:spring-context-mybatis.xml"})
public class TbUserServiceTest {

    @Autowired
    private TbUserService tbUserService;
    private TbUserDao tbUserDao;

    @Test
    public void testSelectAll(){
        List<TbUser> tbUsers = tbUserService.selectAll();
        for (TbUser tbUser:tbUsers) {
            System.out.println(tbUser.getUsername());
        }
    }

    @Test
    public void testInsert(){
        TbUser user = new TbUser();
        user.setUsername("令狐冲");
        user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        user.setEmail("lhc@163.com");
        user.setPhone("18888888888");
        user.setCreated(new Date());
        user.setUpdated(new Date());

        tbUserDao.insert(user);
    }


    @Test
    public void testGetById(){
        TbUser tbUser = tbUserService.getById(36L);
        System.out.println(tbUser.getUsername());
    }

    @Test
    public void testUpdate(){
        TbUser tbUser = tbUserService.getById(37L);
        tbUser.setUsername("田伯光");
        tbUserDao.update(tbUser);
    }

    @Test
    public void testMD5(){
        System.out.println(DigestUtils.md5DigestAsHex("123456".getBytes()));
    }
}
