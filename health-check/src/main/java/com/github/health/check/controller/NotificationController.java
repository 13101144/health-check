package com.github.health.check.controller;

import com.github.health.check.service.CheckResultService;
import com.github.health.check.service.NotificationService;
import com.github.health.check.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "判定结果")
@RestController
@RequestMapping("/notice")
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    /**
     * 判定结果分页查询
     * @param page 参数集
     * @return 项目集合
     */
    @GetMapping("/listpage")
    @ApiOperation(value = "通知信息分页查询")
    public R getProjectPage(@RequestParam Integer page, @RequestParam(required = false) String name) {
        return R.success(notificationService.getNotificationPage(page, name));
    }
}
