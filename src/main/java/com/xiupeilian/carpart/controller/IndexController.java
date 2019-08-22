package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Notice;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.DymsnService;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private DymsnService dymsnService;
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String index(){
        return "index/index";
    }
    @RequestMapping("/top")
    public String top(HttpServletRequest request){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String now =simpleDateFormat.format(new Date());
        request.setAttribute("now",now);
        return"index/top";
    }
    @RequestMapping("/navigation")
    public String navigation(HttpServletRequest request){
        //取到用户 的 ID  因为 用户的权限不同  看到的导航栏也不同
        SysUser user =(SysUser) request.getSession().getAttribute("user");
        int userid = user.getId();
     //查询 到 登陆 用户的所有的导航菜单
        List<Menu> menuList = userService.findMenusById(userid);
        request.setAttribute("menuList",menuList);
        return "index/navigation";
    }
    /*动态消息展示
    **/
    @RequestMapping("/dymsn")
    public String dymsn(HttpServletRequest request){
        List<Dymsn> list=dymsnService.findDymsns();
        request.setAttribute("list",list);
        return "index/dymsn";
    }

    @RequestMapping("/notice")
    public String notice(Integer pageSize,Integer pageNum,HttpServletRequest request){
        System.err.println("jinlaile   notice");
       pageSize=pageSize==null?10:pageNum;
       pageNum=pageNum==null?1:pageNum;
       PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<Notice> list=dymsnService.findNotice();
        PageInfo<Notice> page=new PageInfo<>(list);
        System.err.println("page"+page);
        request.setAttribute("page",page);
        return "index/notice";
    }
}
