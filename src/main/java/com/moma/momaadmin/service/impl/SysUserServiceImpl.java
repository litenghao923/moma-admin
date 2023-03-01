package com.moma.momaadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.mapper.SysUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public boolean userRegister(SysUser user) {
        if (user.getUsername() != null && baseMapper.selectCount(new QueryWrapper<SysUser>().eq("username", user.getUsername())) > 0) {
            return false;
        } else {
            boolean result = baseMapper.insert(user) > 0;
            if (result){
                //添加默认角色;
            }
            return result;
        }
    }
}




