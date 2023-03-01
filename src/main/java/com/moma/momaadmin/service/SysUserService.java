package com.moma.momaadmin.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.RegisterBody;
import com.moma.momaadmin.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author litenghao
 * @description 针对表【sys_user】的数据库操作Service
 * @createDate 2023-02-22 17:24:24
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getUserByName(String username);

    boolean checkUserNameUnique(String username);

    boolean userRegister(RegisterBody registerBody);
}
