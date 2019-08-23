package com.zzz.my.shop.web.admin.web.controller;

import com.zzz.my.shop.commons.dto.BaseResult;
import com.zzz.my.shop.domain.TbContentCategory;
import com.zzz.my.shop.web.admin.service.TbContentCategoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/content/category")
public class ContentCategoryController {

    @Autowired
    private TbContentCategoryService tbContentCategoryService;

    /**
     * 新增和编辑文章内容表单错误时自动保存已输入信息
     * @param
     * @return com.zzz.my.shop.domain.TbContentCategory
     * @author yumako
     * @date 2019/8/19
     */
    @ModelAttribute
    public TbContentCategory getTbContentCategory(Long id){
        TbContentCategory tbContentCategory = null;
        //如果id不为空，则从数据库获取信息
        if(id != null){
            tbContentCategory = tbContentCategoryService.getById(id);
        }else {
            tbContentCategory = new TbContentCategory();
        }
        return tbContentCategory;
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        List<TbContentCategory> sourceList = tbContentCategoryService.selectAll();
        List<TbContentCategory> targetList = new ArrayList<>();

        //排序, 调用方法，0L 为约定好的根节点
        sortList(sourceList, targetList, 0L);

        model.addAttribute("tbContentCategories", targetList);
        return "content_category_list";
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult delete(String ids) {
        BaseResult baseResult = null;
        if (StringUtils.isNotBlank(ids)) {
            tbContentCategoryService.delete(Long.parseLong(ids));
            baseResult = BaseResult.success("删除分类及其子类及其全部内容成功");
        } else {
            baseResult = BaseResult.fail("删除分类失败");
        }

        return baseResult;
    }

    /**
     * 文章类别树形结构
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/tree/data", method = RequestMethod.POST)
    public List<TbContentCategory> selectByPid(Long id) {
        if (id == null) {
            id = 0L;
        }
        return tbContentCategoryService.selectByPid(id);

    }

    /**
     * 跳转到表单页面
     *
     * @return
     */
    @RequestMapping(value = "/form", method = RequestMethod.GET)
    public String form(TbContentCategory tbContentCategory) {
        return "content_category_form";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(TbContentCategory tbContentCategory, Model model, RedirectAttributes redirectAttributes){
        BaseResult baseResult = tbContentCategoryService.save(tbContentCategory);
        if (baseResult.getStatus() == 200){
            redirectAttributes.addFlashAttribute("baseResult",baseResult);
            return "redirect:/content/category/list";
        }
        else {
            model.addAttribute("baseResult",baseResult);
        }
        return form(tbContentCategory);
    }
    /**
     * 递归排序
     *
     * @param sourceList 数据源集合
     * @param targetList 排序后的集合
     * @param parentId   父节点id
     */
    public void sortList(List<TbContentCategory> sourceList, List<TbContentCategory> targetList, Long parentId) {
        for (TbContentCategory tbContentCategory : sourceList) {
            if (tbContentCategory.getParentId().equals(parentId)) {
                targetList.add(tbContentCategory);

                //判断有没有子节点，有则继续追加
                if (tbContentCategory.getIsParent()) {
                    for (TbContentCategory contentCategory : sourceList) {
                        if (contentCategory.getParentId().equals(tbContentCategory.getId())) {
                            sortList(sourceList, targetList, tbContentCategory.getId());
                            break;
                        }
                    }
                }
            }
        }
    }

}
