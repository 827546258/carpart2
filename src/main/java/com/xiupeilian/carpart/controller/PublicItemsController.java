package com.xiupeilian.carpart.controller;/*
@Description TODO
@Author jiang xiaoyu
@Date 2019/8/28 17:14
@Version 1.0
*/

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Brand;
import com.xiupeilian.carpart.model.Items;
import com.xiupeilian.carpart.model.Parts;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.ItemsService;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/public")
public class PublicItemsController {
    @Autowired
    private ItemsService itemsService;
    @Autowired
    private BrandService brandService;

    @RequestMapping("/publicItems")
    public String PublicItemsController(Items items, Integer pageSize, Integer pageNo, HttpServletRequest request,String brandName){
        System.err.println("sssssss");
//        第一次 需要 初始化 分页的参数
//        如果  page==null 给初始值 10  如果 不为空  就是 page
        pageSize =pageSize==null?8:pageSize;
        pageNo =pageNo==null?1:pageNo;
//        开始 分页 查询
        PageHelper.startPage(pageNo,pageSize);
//        分页 完成
//        开始查询
        List<Items> itemsList = itemsService.findItemsByQueryVo(items);
//查完  开始 封装
        PageInfo<Items> page = new PageInfo<>(itemsList);
        request.setAttribute("page",page);
//        搜索 条件  回填
        request.setAttribute("items",items);
//        因为 页面当中 品牌列表   配件类别  需要 初始化 所以 都需要查出来
        List<Brand> brandList = brandService.findBrandAll();
        List<Parts> partsList =brandService.findPartsAll();
        request.setAttribute("brandList",brandList);
        request.setAttribute("partsList",partsList);
        request.setAttribute("brandName",brandName);
        return "public/publicItems";
    }
}
