package com.moma.momaadmin.controller;

import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.RestResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysUserService userService;

    @GetMapping("info")
    @PreAuthorize("hasAnyAuthority('ROLE_admin')")
    public RestResult userInfo(Principal principal){
        System.out.println(principal.toString());
        Map<String,Object> resultMap=new HashMap<>();
        SysUser user=userService.getUserByName(principal.getName());
        resultMap.put("currentUser:",user);
        return RestResult.ok(resultMap);
    }
}
