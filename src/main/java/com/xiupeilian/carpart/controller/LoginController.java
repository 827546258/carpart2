package com.xiupeilian.carpart.controller;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.xiupeilian.carpart.model.*;
import com.xiupeilian.carpart.service.BrandService;
import com.xiupeilian.carpart.service.CityService;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.sysconstant.SysConstant;
import com.xiupeilian.carpart.task.MailTask;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private RedisTemplate jedis;

    @Autowired
    private ThreadPoolTaskExecutor executor;

    @Autowired
    private JavaMailSenderImpl mailSender;
    @Autowired
    private UserService userService;
    @Autowired
    private CityService cityService;

    @Autowired
    private  BrandService brandService;
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
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token =new UsernamePasswordToken(vo.getLoginName(),vo.getPassword());
            try {
//                这一步 会去  UserRealm 中的登陆认证方法
                subject.login(token);

            }catch (Exception e){
//                捕获到异常  说明 用户名 密码错误
//                e.gettmessage 是 2
                response.getWriter().write(e.getMessage());
//                用户名密码错误 直接 return 后面的不执行
                return;
            }
//            走到 这一步 说明 登陆成功
//            取到 UserRealm 中 登陆成功 存储的用户信息
            SysUser user =(SysUser) SecurityUtils.getSubject().getPrincipal();
//            存到  spring 中的 session 中
            request.getSession().setAttribute("user",user);
            response.getWriter().write("3");

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

//            dd
            executor.execute(mailTask);
            response.getWriter().write("2");
        }

    }
//    q去往 注册页面
@RequestMapping("/toRegister")
   public String toRegister(HttpServletRequest request){
    //初始化数据  汽车品牌、配件种类、精品种类
    List<Brand> brandList=brandService.findBrandAll();
    List<Parts> partsList=brandService.findPartsAll();
    List<Prime> primelist=brandService.findPrimeAll();
    request.setAttribute("brandList",brandList);
    request.setAttribute("partsList",partsList);
    request.setAttribute("primeList",primelist);
     return "login/register";
   }
  @RequestMapping("/checkLoginName")
    public void checkLoginName(String loginName,HttpServletResponse response) throws IOException {
        SysUser user = userService.findUserByLoginName(loginName);
        if (null==user){
            response.getWriter().write("1");
        }else {
            response.getWriter().write("2");
        }
  }
  @RequestMapping("/checkPhone")
    public void checkPhone(String telnum,HttpServletResponse response) throws IOException {
        SysUser user=userService.findUserByPhone(telnum);
      if(null==user){
          response.getWriter().write("1");
      }else{
          response.getWriter().write("2");
      }
  }
  @RequestMapping("/checkEmail")
    public void checkEmail(HttpServletResponse response,String email) throws IOException {
      SysUser user=userService.findUserByEmail(email);
      if(null==user){
          response.getWriter().write("1");
      }else{
          response.getWriter().write("2");
      }
  }
//  检查 企业名称  是否被 注册过
  @RequestMapping("/checkCompanyname")
    public void checkCompanyname(HttpServletResponse response,String companyname) throws IOException {
      Company company = userService.findCompanyByName(companyname);
      if(null==company){
          response.getWriter().write("1");
      }else{
          response.getWriter().write("2");
      }
  }
//  获取 省市县 根据 父ID 查询 所有 子 节点
//    返回 一个 josn 用 responsebody
    @RequestMapping("/getCity")
    public @ResponseBody List<City> getCity(Integer parentId){
        parentId=parentId==null?SysConstant.CITY_CHINA_ID:parentId;
        List<City> cityList=cityService.findCitiesByParentId(parentId);
        return cityList;
    }
//  点击 注册
    @RequestMapping("/register")
    public String register(RegisterVo vo){
        //先插入企业表，再插入用户表,需要在一个事务进行控制
        userService.addRegsiter(vo);
        //       重定向 到  登陆页面  一个包下  不需要 包路径
        return "redirect:toLogin";
    }
    @RequestMapping("/tosms")
    public String tosms(){
        return "login/sms";
    }
    @RequestMapping("/smsControllter")
    public void smsControllter(String phone){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAICvhI2oyM59Qb", "T85kTo894T5Y21ePfWQxc0sWd7ztKw");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "汽配连");
        request.putQueryParameter("TemplateCode", "SMS_172884133");
        String code =new Random().nextInt(899999)+100000+"";
        request.putQueryParameter("TemplateParam",  "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            jedis.boundValueOps(phone).set(code);
            jedis.expire(phone,5, TimeUnit.MINUTES);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping("/checkCode")
    public void checkCode(HttpServletResponse response,String phone,String code) throws IOException {
        String telPhone=(String) jedis.boundValueOps(phone).get();
        if (null==telPhone){
            response.getWriter().write("1");
        }
        if (telPhone!=code){
            response.getWriter().write("2");
        }
    }


}
