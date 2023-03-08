package com.moma.momaadmin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moma.momaadmin.common.security.JwtConstant;
import com.moma.momaadmin.entity.RegisterBody;
import com.moma.momaadmin.entity.SysMenu;
import com.moma.momaadmin.entity.SysRole;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.mapper.SysMenuMapper;
import com.moma.momaadmin.mapper.SysRoleMapper;
import com.moma.momaadmin.service.SysUserRoleService;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.mapper.SysUserMapper;
import com.moma.momaadmin.util.DateUtil;
import com.moma.momaadmin.util.RedisUtil;
import com.moma.momaadmin.util.SecurityUtil;
import com.moma.momaadmin.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author litenghao
 * @description 针对表【sys_user】的数据库操作Service实现
 * @createDate 2023-02-22 17:24:24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private RedisUtil redisUtil;

    @Override
    public SysUser getUserByName(String username) {
        return getOne(new QueryWrapper<SysUser>().eq("username", username));
    }

    @Override
    public boolean checkUserNameUnique(String username) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<SysUser>();
        wrapper.eq("username", username);
        long count = count(wrapper);
        return count <= 0;
    }


    @Transactional
    @Override
    public boolean userRegister(RegisterBody registerBody) {
        SysUser regUser = new SysUser();
        regUser.setUsername(registerBody.getUsername());
        regUser.setPassword(SecurityUtil.encryptPassword(registerBody.getPassword()));
        regUser.setNickname("默认昵称" + StringUtil.getRandomString(4));
        regUser.setPhone(registerBody.getPhone());
        regUser.setEmail(registerBody.getEmail());
        regUser.setCreateTime(new Date());
        regUser.setStatus("1");
        return save(regUser);
    }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        StringBuffer authority = new StringBuffer();
        String auth_key = "moma_admin_";
        if (redisUtil.hasKey(auth_key + userId)) {
            System.out.println("Redis有缓存");
            authority.append(redisUtil.get("moma_admin_",String.valueOf(userId)));
        }else {
            System.out.println("Redis没缓存");
            List<SysRole> roleList=sysRoleMapper.selectList(new QueryWrapper<SysRole>().inSql("id","SELECT role_id FROM sys_user_role WHERE user_id="+userId));
            if (roleList.size()>0){
                String roleCodeStr=roleList.stream().map(r->"ROLE_"+r.getCode()).collect(Collectors.joining(","));
                authority.append(roleCodeStr);
            }

            //获取菜单权限
            Set<String> menuCodeSet=new HashSet<>();
            for (SysRole sysRole:roleList){
                List<SysMenu> menuList=sysMenuMapper.selectList(new QueryWrapper<SysMenu>().inSql("id","SELECT menu_id FROM sys_role_menu WHERE role_id="+sysRole.getId()));
                for (SysMenu sysMenu:menuList){
                    String perms=sysMenu.getPerms();
                    if (StringUtil.isNotEmpty(perms)){
                        menuCodeSet.add(perms);
                    }
                }
            }
            if (menuCodeSet.size()>0){
                authority.append(",");
                String menuCodeStr=menuCodeSet.stream().collect(Collectors.joining(","));
                authority.append(menuCodeStr);
            }
            redisUtil.set(auth_key,String.valueOf(userId),authority, JwtConstant.JWT_TTL);
        }
        return authority.toString();
    }
}




