package com.github.health.check.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.health.check.domain.entity.User;
import com.github.health.check.enums.ErrorCode;
import com.github.health.check.exception.BusinessException;
import com.github.health.check.service.UserService;
import com.github.health.check.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过ID查询用户信息
     * @param id ID
     * @return 用户信息
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询用户信息")
    public R getUserById(@PathVariable long id) {
        return R.success(userService.getById(id));
    }

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return
     */
    @GetMapping("/details/{username}")
    @ApiOperation(value = "根据用户名查询用户信息")
    public R getUserByUserName(@PathVariable String username){
        User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, username));
        if (user == null) {
            throw new BusinessException(ErrorCode.USER_NOT_FOUND.getCode(), ErrorCode.USER_NOT_FOUND.getMsg());
        }
        return R.success();
    }

    /**
     * 删除用户信息
     * @param id ID
     * @return R
     */
    @DeleteMapping("remove")
    @ApiOperation(value = "删除用户信息")
    public R deleteUser(@RequestParam long id) {
        return R.success(userService.removeById(id));
    }

    /**
     * 添加用户
     * @param user 用户信息
     * @return success/false
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加用户")
    public R addUser(@Valid @RequestBody User user) {
        User existUser = userService.getOne(Wrappers.<User>query().lambda().eq(User::getUsername, user.getUsername()));
        if (existUser != null) {
            throw new BusinessException(ErrorCode.USERNAME_HAS_EXIST.getCode(), ErrorCode.USERNAME_HAS_EXIST.getMsg());
        }
        return R.success(userService.saveUser(user));
    }



    /**
     * 更新用户信息
     * @param user 用户信息
     * @return R
     */
    @PutMapping("/edit")
    @ApiOperation(value = "更新用户信息")
    public R updateUser(@Valid @RequestBody User user) {
        return R.success(userService.updateUser(user));
    }

    /**
     * 分页查询用户
     * @param page 参数集
     * @return 用户集合
     */
    @GetMapping("/listpage")
    @ApiOperation(value = "分页查询用户")
    public R getUserPage(@RequestParam Integer page, @RequestParam(required = false) String name) {
        return R.success(userService.getUserPage(page, name));
    }

    @GetMapping("/list")
    @ApiOperation(value = "分页查询用户")
    public R list() {
        return R.success(userService.list());
    }
}
