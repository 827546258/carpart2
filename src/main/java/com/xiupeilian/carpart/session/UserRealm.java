package com.xiupeilian.carpart.session;/*
@Description TODO
@Author jiang xiaoyu
@Date 2019/8/29 14:58
@Version 1.0
*/

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Role;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.util.SHA1Util;
import com.xiupeilian.carpart.vo.LoginVo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
    @Override
    /*
    * 授权的方法 当subject 第一次访问 需要权限 访问的资源的时候 就会调用
    * 该方法。
    * */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
       SysUser user =(SysUser)principalCollection.getPrimaryPrincipal();
// 查询出  该用户的 所有的 角色信息 和 权限 信息  先 查询 角色 信息
        Role role=userService.findRoleByRoleId(user.getRoleId());
        List<String> roleList=new ArrayList();//一个 用户 可以有 多个 身份
//        这个 角色 拥有的 所有的英文角色名称 集合
        roleList.add(role.getRoleEnglishName());
//查用户的权限信息(菜单)
        List<Menu> menuList=userService.findMenusById(user.getId());
        List<String> permisstionList=new ArrayList<>();
        for(Menu menu :menuList){
            permisstionList.add(menu.getMenuKey());
        }
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        info.addRoles(roleList);
        info.addStringPermissions(permisstionList);
        return info;
    }

    @Override
/*
* 登陆认证的方法   当 调用 subject.login（token）就会进来
* */
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token =(UsernamePasswordToken) authenticationToken;
        LoginVo vo = new LoginVo();
        vo.setLoginName(token.getUsername());
        vo.setPassword(SHA1Util.encode(new String(token.getPassword())));
//        查询
        SysUser user =userService.findUserByLoginNameAndPassword(vo);
        if (null==user){
            throw  new AccountException("2");
        }else {
            AuthenticationInfo info = new SimpleAuthenticationInfo(user,token.getPassword(),getName());
            return info;
        }

    }
}
