package com.moma.momaadmin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.moma.momaadmin.entity.RegisterBody;
import com.moma.momaadmin.entity.SysUser;
import com.moma.momaadmin.service.SysUserService;
import com.moma.momaadmin.util.RestResult;
import com.moma.momaadmin.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private SysUserService userService;
}
