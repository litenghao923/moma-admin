package com.moma.momaadmin.common.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LoginImpl implements UserDetailsService {

    @Resource
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userService.getUserByName(username);
        if (user==null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }else if ("0".equals(user.getStatus())){
            throw new LockedException("账号已冻结");
        }
        return new User(user.getUsername(),user.getPassword(),getUserAuthority());
    }

    private List<GrantedAuthority> getUserAuthority(){
        return new ArrayList<>();
    }
}
