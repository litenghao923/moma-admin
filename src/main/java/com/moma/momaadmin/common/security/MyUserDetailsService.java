package com.moma.momaadmin.common.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        SysUser user = userService.getOne(wrapper);
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        } else if ("0".equals(user.getStatus())) {
            throw new LockedException("账号已冻结");
        }
        return new User(user.getUsername(), user.getPassword(), getUserAuthority(user.getId()));
    }

    /**
     * 获取用户权限信息 包括角色 菜单权限信息
     * @param userId
     * @return
     */
    public List<GrantedAuthority> getUserAuthority(Long userId) {
        System.out.println("获取用户权限信息 包括菜单 菜单权限信息");
        String authority=userService.getUserAuthorityInfo(userId);
        System.out.println("auth="+authority);
        return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
