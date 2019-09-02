package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Dymsn;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Notice;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.DymsnService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.util.SHA1Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 首页、导航、消息公告相关
 * @Author: Tu Xu
 * @CreateDate: 2019/8/21 15:19
 * @Version: 1.0
 **/
@Controller
@RequestMapping("/index")
public class IndexController {
    @Autowired
    private UserService userService;
    @Autowired
    private DymsnService dymsnService;
    /**
     * @Description: 首页展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/index")
    public String index(){
        return "index/index";
    }
    /**
     * @Description: 首页top区域展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/top")
    public String top(HttpServletRequest request){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String now=sdf.format(new Date());
        request.setAttribute("now",now);
        return "index/top";
    }
    /**
     * @Description: 导航区域展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/navigation")
    public String navigation(HttpServletRequest request){
        //取到用户id
        SysUser user=(SysUser) request.getSession().getAttribute("user");
        int id=user.getId();
        //查询到登录用户的所有导航菜单
        List<Menu> menuList= userService.findMenusById(id);
        request.setAttribute("menuList",menuList);
        return "index/navigation";
    }
    
    /**
     * @Description: 动态消息展示
     * @Author:      Administrator
     * @Param:       []
     * @Return       java.lang.String
      **/
    @RequestMapping("/dymsn")
    public String dymsn(HttpServletRequest request){
        List<Dymsn> list=dymsnService.findDymsns();
        request.setAttribute("list",list);
        return "index/dymsn";
    }

    @RequestMapping("/notice")
    public String notice(Integer pageSize,Integer pageNum,HttpServletRequest request){
        pageSize=pageSize==null?10:pageSize;
        pageNum=pageNum==null?1:pageNum;
        PageHelper.startPage(pageNum,pageSize);
        //查询全部
        List<Notice> list=dymsnService.findNotice();
        PageInfo<Notice> page=new PageInfo<>(list);
        request.setAttribute("page",page);
        return "index/notice";
    }

    @RequestMapping("/toChangePassword")
    public String toChangePassword() {

        return "index/changePassword";
    }

    @RequestMapping("/toPassword")
    public void toChangePassword(HttpServletResponse response,String oldPwd,String newPwd,HttpServletRequest request) throws IOException {

        HttpSession session =request.getSession(false);
        SysUser user=(SysUser) request.getSession().getAttribute("user");
        System.out.println("userid"+ oldPwd+"userpassword:"+newPwd);
        if(user.getPassword().equals(SHA1Util.encode(oldPwd))){
            user.setPassword(SHA1Util.encode(newPwd));
            userService.updateUser(user);
            response.getWriter().write("2");

        }else{
            response.getWriter().write("1");
        }

    }


}
