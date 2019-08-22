package com.xiupeilian.carpart.service;

import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;

import java.util.List;

public interface UserService {
    public SysUser findUserByLoginNameAndPassword(LoginVo vo);

    List<Menu> findMenusById(int userid);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);

    void updateUser(SysUser user);
}
