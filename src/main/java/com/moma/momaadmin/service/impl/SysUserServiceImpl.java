package com.moma.momaadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moma.momaadmin.entity.RegisterBody;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserRoleService;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.mapper.SysUserMapper;
import com.moma.momaadmin.util.DateUtil;
import com.moma.momaadmin.util.SecurityUtil;
import com.moma.momaadmin.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author litenghao
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-02-22 17:24:24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getUserByName(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public boolean checkUserNameUnique(String username) {
        QueryWrapper<SysUser> wrapper=new QueryWrapper<SysUser>();
        wrapper.eq("username",username);
        long count =count(wrapper);
        return count <= 0;
    }


    @Transactional
    @Override
    public boolean userRegister(RegisterBody registerBody) {
        SysUser regUser=new SysUser();
        regUser.setUsername(registerBody.getUsername());
        regUser.setPassword(SecurityUtil.encryptPassword(registerBody.getPassword()));
        regUser.setNickname("默认昵称"+StringUtil.getRandomString(4));
        regUser.setPhone(registerBody.getPhone());
        regUser.setEmail(registerBody.getEmail());
        regUser.setCreateTime(new Date());
        regUser.setStatus("1");

        return save(regUser);
    }
}




