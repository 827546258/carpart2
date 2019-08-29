package com.xiupeilian.carpart.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiupeilian.carpart.model.Notice;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {
@Autowired
private UserService userService;
@RequestMapping("/staffList")
    public String staff(HttpServletResponse response, HttpServletRequest request, LoginVo vo,Integer pageSize,Integer pageNum) {
    pageSize=pageSize==null?10:pageNum;
    pageNum=pageNum==null?1:pageNum;
    PageHelper.startPage(pageNum,pageSize);
    SysUser user =(SysUser) request.getSession().getAttribute("user");
    vo.setCompanyId(user.getCompanyId());
    List<SysUser> staffList = userService.findUsers(vo);
    PageInfo<SysUser> page=new PageInfo<>(staffList);
    request.setAttribute("vo",vo);
    request.setAttribute("page",page);
       return "index/staffList";
    }
}
