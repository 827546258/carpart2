package com.xiupeilian.carpart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/staff")
public class StaffController {
@RequestMapping("/staffList")
    public void staff(HttpServletResponse response) throws Exception{
        response.getWriter().write("normal success");
    }
}
