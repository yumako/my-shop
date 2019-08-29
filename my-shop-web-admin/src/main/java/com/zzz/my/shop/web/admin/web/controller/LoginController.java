package com.zzz.my.shop.web.admin.web.controller;

import com.zzz.my.shop.commons.constant.ConstantUtils;
import com.zzz.my.shop.domain.TbUser;
import com.zzz.my.shop.web.admin.service.TbUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Autowired
    private TbUserService tbUserService;

    /**
     *跳转到登录页
     * @param
     * @return
     * @author yumako
     * @date 2019/8/1
     */
    @RequestMapping(value = {"","login"},method = RequestMethod.GET)
    public String login(){
        return "login";
    }

    /**
     *登录逻辑
     * @param
     * @return
     * @author yumako
     * @date 2019/8/1
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public String login(@RequestParam(required = true) String email, @RequestParam(required = true) String password, HttpServletRequest request, Model model){
        TbUser tbUser = tbUserService.login(email, password);
        if (tbUser == null){
            model.addAttribute("message","用户名或密码输入错误，请重新输入！");
            return login();
        }else {
            //将登录信息放入会话
            request.getSession().setAttribute(ConstantUtils.SESSION_USER, tbUser);
            return "redirect:/main";
        }
    }

    //注销
    @RequestMapping(value = "logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        return login();
    }
}
