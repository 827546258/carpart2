package com.xiupeilian.carpart.mapper;

import com.xiupeilian.carpart.base.BaseMapper;
import com.xiupeilian.carpart.model.SysUser;
import com.xiupeilian.carpart.vo.LoginVo;

public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser findUserByLoginNameAndPassword(LoginVo vo);

    SysUser findUserByLoginNameAndEmail(LoginVo vo);
}