package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.MenuMapper;
import com.xiupeilian.carpart.mapper.SysUserMapper;
import com.xiupeilian.carpart.model.Menu;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.service.UserService;
import com.xiupeilian.carpart.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Override
    public SysUser findUserByLoginNameAndPassword(LoginVo vo) {
        return sysUserMapper.findUserByLoginNameAndPassword(vo);
    }

    @Override
    public List<Menu> findMenusById(int userid) {
        return menuMapper.findMenusByUserId(userid);
    }

    @Override
    public SysUser findUserByLoginNameAndEmail(LoginVo vo) {
        return sysUserMapper.findUserByLoginNameAndEmail(vo);
    }

    @Override
    public void updateUser(SysUser user) {
        sysUserMapper.updateByPrimaryKeySelective(user);
    }
}
