package com.xiupeilian.carpart.controller;

import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.sysconstant.SysConstant;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private UserService userService;
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login/login";
    }

    @RequestMapping("/login")
    public void login(LoginVo vo, HttpServletRequest request, HttpServletResponse response) throws Exception {
          //首先 判断验证码是否正确
        //.toUpperCase()是把 验证码统一转换成大写
        String code =(String) request.getSession().getAttribute(SysConstant.VALIDATE_CODE);
//        生成的 验证码 和 session传过来的验证码 进行比较
        if (vo.getValidate().toUpperCase().equals(code.toUpperCase())){
            vo.setPassword(SHA1Util.encode(vo.getPassword()));
            SysUser user = userService.findUserByLoginNameAndPassword(vo);

            if (null==user){
                response.getWriter().write("2");
            }else {
                request.getSession().setAttribute("user",user);
                response.getWriter().write("3");
            }

    }else{
            response.getWriter().write("1");
        }
    }
    @RequestMapping("/noauth")
    public String noauth(){
        return "exception/noauth";
    }
    @RequestMapping("/forgetPassword")
    public String forgetPassword(){
        return "login/forgetPassword";
    }
    @RequestMapping("/getPassword")
    public void getpassword(HttpServletResponse response,LoginVo vo)throws  Exception{
//        查看  邮箱 账号  是否 匹配
        SysUser user =userService.findUserByLoginNameAndEmail(vo);
        if (null==user){
           response.getWriter().write("1");

        }else{
//            邮箱 账号匹配  ,给用户 生成 新密码
            String newpassword =new Random().nextInt(899999)+100000+"";

//            然后 修改 数据库
//            首先 把密码进行加密
            user.setPassword(SHA1Util.encode(newpassword));
            userService.updateUser(user);
//            发送 邮件 到邮箱

            SimpleMailMessage mailMessage = new SimpleMailMessage();
//              发信人  是谁
            mailMessage.setFrom("j15839227503@sina.com");
//            收信人是谁
            mailMessage.setTo(user.getEmail());
//            邮件主题
            mailMessage.setSubject("密码找回");
//            内容
            mailMessage.setText("您的新密码是"+newpassword);
            //    将 同步流程 异步化    通过 多线程实现  采用  线程池 的技术
//  创建一个 任务  交给 线程池
            MailTask mailTask = new MailTask(mailSender,mailMessage);
// 让线程池 去执行该任务

            
            executor.execute(mailTask);
            response.getWriter().write("2");
        }

    }
}
