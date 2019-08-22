package com.xiupeilian.carpart.session;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // session过滤 以及权限控制
//        首先获取 请求路径
        String path=request.getRequestURI();
//        首先 判断资源路径是不是需要登录
        System.err.println(path);
       if (path.contains("login")){
//           登陆相关的不需要进行拦截  直接放行
           return true;
       }else{
           HttpSession session = request.getSession(false);
           if (null==session){
//               如果 session为 空的话  说明用户 没有登陆过 直接 重定向到 登陆去
               response.sendRedirect(request.getContextPath()+"/login/toLogin");
               return  false;
           }else {
//               session  不 为 null  也不代表着 用户一定登陆过  要判断 session 中的user
               if (null==session.getAttribute("user")){
//                   session不为空   但是 user 对象不存在  说明 还是没有登陆
                   return  false;
//                   到此处    session 过滤 完毕
               }else{
//                   user不为空    用户 登陆
//                   权限过滤
                   SysUser user =(SysUser) session.getAttribute("user");
//                   首先查询出来 登陆 用户 的权限列表(导航菜单)
//                   查询出来 自己有权限 访问的 导航菜单列表
                   List<Menu> menuList=userService.findMenusById(user.getId());
//                   数据库中 每一个 导航都有对应的 导航路径  ，我当前的请求 也包含了  路径
                   boolean chack = false;
                     for (Menu menu:menuList){
                         //为了防止 没有权限访问 的用户 通过 url 非法 访问自己 没有权限 访问的 地址 所以 进行判断
//                         如果  用户请求的资源路径 包含了自己 所拥有的导航权限 关键字
                         if (path.contains(menu.getMenuKey())){
                           chack = true;
                         }
                     }
                     if (chack){
                         return  true;

                     }else{
//                         非法 访问
//                         重定向到  非法 访问 页面、
                         response.sendRedirect(request.getContextPath()+"/login/noauth");
                         return  false;
                     }
               }

           }
       }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
             request.setAttribute("ctx",request.getContextPath());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
