package com.zzz.my.shop.web.admin.service.impl;

import com.zzz.my.shop.commons.dto.BaseResult;
import com.zzz.my.shop.commons.dto.PageInfo;
import com.zzz.my.shop.commons.utils.RegexpUtils;
import com.zzz.my.shop.domain.TbUser;
import com.zzz.my.shop.web.admin.dao.TbUserDao;
import com.zzz.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TbUserServiceImpl implements TbUserService {

    @Autowired
    private TbUserDao tbUserDao;

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<TbUser> selectAll() {
        return tbUserDao.selectAll();
    }

    @Override
    public TbUser getById(Long id) {
        return tbUserDao.getById(id);
    }

    @Override
    public void update(TbUser entity) {

    }

    @Override
    public void delete(Long id) {

    }

    /**
     * 根据邮箱密码登录后台
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public TbUser login(String email, String password) {
        TbUser tbUser = tbUserDao.getByEmail(email);
        if (tbUser != null) {
            String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
            if (md5password.equals(tbUser.getPassword())) {
                return tbUser;
            }
        }
        return null;
    }

    /**
     * 新增和编辑用户信息
     *
     * @param
     * @return void
     * @author yumako
     * @date 2019/8/5
     */
    @Override
    public BaseResult save(TbUser tbUser) {
        //通过非空验证后才能进行新增和编辑工作
        BaseResult baseResult = checkTbUser(tbUser);
        if (baseResult.getStatus() == BaseResult.STATUS_SUCCESS) {
            tbUser.setUpdated(new Date());
            //新增用户
            if (tbUser.getId() == null) {
                //密码加密处理
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                tbUser.setCreated(new Date());
                tbUserDao.insert(tbUser);
            }
            //编辑用户
            else {
                tbUser.setPassword(DigestUtils.md5DigestAsHex(tbUser.getPassword().getBytes()));
                tbUserDao.update(tbUser);
            }
            baseResult.setMessage("保存用户信息成功");
        }
        return baseResult;
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deleteMulti(String[] ids) {
        tbUserDao.deleteMulti(ids);
    }

    /**
     * 分页查询和搜索功能
     *
     * @param start
     * @param length
     * @return
     */
    @Override
    public PageInfo<TbUser> page(int start, int length, int draw, TbUser tbUser) {
        int count = tbUserDao.count(tbUser);

        Map<String, Object> params = new HashMap<>();
        params.put("start", start);
        params.put("length", length);
        params.put("tbUser", tbUser);

        PageInfo<TbUser> pageInfo = new PageInfo<>();
        pageInfo.setDraw(draw);
        pageInfo.setRecordsTotal(count);
        pageInfo.setRecordsFiltered(count);
        pageInfo.setData(tbUserDao.page(params));

        return pageInfo;
    }

    /**
     * 查询用户总数
     *
     * @param tbUser
     * @return
     */
    @Override
    public int count(TbUser tbUser) {
        return tbUserDao.count(tbUser);
    }

    /**
     * 用户信息的有效性验证
     *
     * @param tbUser
     */
    private BaseResult checkTbUser(TbUser tbUser) {
        BaseResult baseResult = BaseResult.success();
        //非空验证
        if (StringUtils.isBlank(tbUser.getUsername())) {
            baseResult = BaseResult.fail("用户名不能为空，请输入用户名");
        } else if (StringUtils.isBlank(tbUser.getPassword())) {
            baseResult = BaseResult.fail("密码不能为空，请输入密码");
        } else if (StringUtils.isBlank(tbUser.getEmail())) {
            baseResult = BaseResult.fail("邮箱不能为空，请输入邮箱");
        }
        //正则验证
        else if (!RegexpUtils.checkEmail(tbUser.getEmail())) {
            baseResult = BaseResult.fail("邮箱格式不正确，请重新输入");
        } else if (StringUtils.isBlank(tbUser.getPhone())) {
            baseResult = BaseResult.fail("手机号不能为空，请输入手机号");
        } else if (!RegexpUtils.checkPhone(tbUser.getPhone())) {
            baseResult = BaseResult.fail("手机号码格式不正确，请重新输入");
        }
        return baseResult;
    }

}

