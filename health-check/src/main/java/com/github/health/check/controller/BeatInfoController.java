package com.github.health.check.controller;

import com.github.health.check.domain.entity.BeatInfo;
import com.github.health.check.domain.entity.CheckItem;
import com.github.health.check.domain.entity.Flip;
import com.github.health.check.domain.entity.Notification;
import com.github.health.check.service.*;
import com.github.health.check.util.DateUtil;
import com.github.health.check.util.KeyGenerator;
import com.github.health.check.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jnlp.FileOpenService;
import javax.validation.Valid;
import java.util.Date;

@Api(tags = "心跳处理")
@RestController
public class BeatInfoController {

    @Autowired
    private BeatInfoService beatInfoService;

    @Autowired
    private CheckItemCacheService checkItemCacheService;

    @Autowired
    private FlipService flipService;

    @Autowired
    private CheckItemService checkItemService;

    /**
     * 注意：使用内部缓存，集群部署时存在不一致的问题，后期会改为使用分布式缓存，或者增加一个检测超时的定时任务
     * @param beatInfo
     * @return
     */
    @PostMapping("/clientBeat")
    @ApiOperation(value = "接收并处理客户端心跳")
    public R clientBeat(@Valid @RequestBody BeatInfo beatInfo) {
        String projectName = beatInfo.getName();
        String checkName = beatInfo.getServiceName();
        String key = KeyGenerator.generateKey(checkName, projectName);
        CheckItem checkItem = checkItemCacheService.get(key);
        if (checkItem == null) {
            checkItem = checkItemService.getCheckItem(projectName, checkName);
            if (checkItem != null) {
                checkItemCacheService.add(key, checkItem);
            }
        }
        if (checkItem != null) {
            long diff = DateUtil.sub(beatInfo.getEndTime(), beatInfo.getBeginTime());
            if (diff > checkItem.getMaxExecTime()) {
                String content = buildNoticeContent(projectName, checkName, diff);
                Flip flip = new Flip();
                flip.setProjectName(checkItem.getProjectName());
                flip.setCheckName(checkItem.getName());
                flip.setCreated(new Date());
                flip.setStatus("N");
                flip.setContent(content);
                flipService.save(flip);
            }
        }

        beatInfoService.createBeat(beatInfo);
        return R.success();
    }

    private String buildNoticeContent(String projectName, String checkName, long intervals) {
        StringBuilder sb = new StringBuilder();
        sb.append("项目")
                .append(projectName)
                .append(",")
                .append("检查项")
                .append(checkName)
                .append(",执行了").append(intervals).append("分钟，超过了最大任务执行时间");
        return sb.toString();
    }


}
