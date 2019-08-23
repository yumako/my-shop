package com.zzz.my.shop.web.admin.web.controller;

import com.zzz.my.shop.commons.dto.BaseResult;
import com.zzz.my.shop.commons.dto.PageInfo;
import com.zzz.my.shop.domain.TbUser;
import com.zzz.my.shop.web.admin.service.TbUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private TbUserService tbUserService;

    /**
     *新增和编辑用户表单错误时自动保存已输入信息
     * @param
     * @return com.zzz.my.shop.domain.TbUser
     * @author yumako
     * @date 2019/8/5
     */
    @ModelAttribute
    public TbUser getTbUser(Long id){
        TbUser tbUser = null;
        //如果id不为空，则从数据库获取信息
        if (id != null){
             tbUser = tbUserService.getById(id);
        }else {
            tbUser = new TbUser();
        }
        return tbUser;
    }

    /**
     *跳转到用户列表页
     * @param
     * @return java.lang.String
     * @author yumako
     * @date 2019/8/5
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
//        List<TbUser> tbUsers = tbUserService.selectAll();
//        model.addAttribute("tbUsers",tbUsers);
        return "user_list";
    }

    /**
     * 跳转用户表单页
     * @return
     */
    @RequestMapping(value = "/form",method = RequestMethod.GET)
    public String form(){
        return "user_form";
    }

    /**
     * 保存用户信息
     * 使用 RedirectAttributes 重定向传值
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(TbUser tbUser, Model model, RedirectAttributes redirectAttributes){
        BaseResult baseResult = tbUserService.save(tbUser);

        if (baseResult.getStatus() == 200){
            redirectAttributes.addFlashAttribute("baseResult", baseResult);
            return "redirect:/user/list";
        }else{
            model.addAttribute("baseResult",baseResult);
            return "user_form";
        }
    }

    /**
     * 批量删除功能
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public BaseResult delete(String ids){
        BaseResult baseResult = null;
        if (!StringUtils.isBlank(ids)){
            String[] idArray = ids.split(",");
            tbUserService.deleteMulti(idArray);
            baseResult = BaseResult.success("删除用户成功");
        }
        else {
            baseResult = BaseResult.fail("删除用户失败");
        }
        return baseResult;
    }

    /**
     * 分页查询功能
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    public PageInfo<TbUser> page(HttpServletRequest request,TbUser tbUser){
        String strDraw = request.getParameter("draw");
        String strStart = request.getParameter("start");
        String strLength = request.getParameter("length");

        int draw = strDraw == null ? 0 :Integer.parseInt(strDraw);
        int start = strStart == null ? 0 :Integer.parseInt(strStart);
        int length = strLength == null ? 0 :Integer.parseInt(strLength);

        //封装dataTables 需要的结果
        PageInfo<TbUser> pageInfo = tbUserService.page(start, length, draw, tbUser);

        return pageInfo;
    }

    /**
     * 查看详情信息
     * @param tbUser
     * @return
     */
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(TbUser tbUser){
        return "user_detail";
    }
}
