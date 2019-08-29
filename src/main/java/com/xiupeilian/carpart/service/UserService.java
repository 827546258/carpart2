package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Company;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.Role;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;
import com.xiupeilian.carpart.vo.RegisterVo;

import java.util.List;

public interface UserService {
    public SysUser findUserByLoginNameAndPassword(LoginVo vo);

    List<Menu> findMenusById(int userid);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    void updateUser(SysUser user);

    List<SysUser> findUser();

    List<SysUser> findUsers(LoginVo vo);

    SysUser findUserByLoginName(String loginName);

    SysUser findUserByPhone(String telnum);

    SysUser findUserByEmail(String email);

    Company findCompanyByName(String companyname);

    void addRegsiter(RegisterVo vo);

    Role findRoleByRoleId(Integer roleId);
}
