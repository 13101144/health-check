package com.github.health.check.task;

import com.github.health.check.constant.CommonConstants;
import com.github.health.check.domain.entity.BeatInfo;
import com.github.health.check.domain.entity.CheckResult;
import com.github.health.check.domain.entity.Difference;
import com.github.health.check.domain.entity.Flip;
import com.github.health.check.service.BeatInfoService;
import com.github.health.check.service.CheckResultService;
import com.github.health.check.service.DifferenceService;
import com.github.health.check.service.FlipService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

public class CheckJob extends QuartzJobBean {

    @Autowired
    BeatInfoService beatInfoService;

    @Autowired
    CheckResultService checkResultService;

    @Autowired
    FlipService flipService;

    @Autowired
    DifferenceService differenceService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String checkName = (String) jobDataMap.get("checkName");
        String projectName = (String) jobDataMap.get("projectName");
        Integer grace = (Integer) jobDataMap.get("grace");

        BeatInfo beatInfo = null;
        Boolean isFirstJudge = false;
        Boolean isSuccessedjudge = false;

        CheckResult checkResult = checkResultService.getNewestBeat(projectName,checkName);

        if (checkResult == null) {
            isFirstJudge = true;
        }

        // 如果是第一次判定，先去查询使用有该检查项的心跳
        if (isFirstJudge) {
            beatInfo = beatInfoService.getLastBeatInfoWithoutTime(projectName, checkName);
            // 未收到心跳
            if (beatInfo == null) {
                isSuccessedjudge = false;
            } else {
                isSuccessedjudge = true;
            }

        } else {
            // 最新的心跳创建时间必须大于上次判定的时间
            beatInfo = beatInfoService.getLastBeatInfo(projectName, checkName, checkResult.getCreated());
            if (beatInfo == null) {
                Difference difference = differenceService.getJobDValue(checkName, projectName);
                long now = System.currentTimeMillis();
                long lastReceived = checkResult.getCreated().getTime();
                if (hasReachGraceTime(now, lastReceived, grace*1000*60,  difference.getDValue() )) {
                    long intervals = now - lastReceived;
                    String content =  buildNoticeContent(projectName, checkName, intervals);
                    Flip flip = new Flip();
                    flip.setProjectName(projectName);
                    flip.setCheckName(checkName);
                    flip.setCreated(new Date());
                    flip.setStatus(CommonConstants.UN_HANDLE_STATUS);
                    flip.setContent(content);
                    flipService.save(flip);
                }
            } else {
                isSuccessedjudge = true;
            }
        }

        CheckResult checkResultNew = new CheckResult();
        if (isSuccessedjudge) {
            checkResultNew.setBeatId(beatInfo.getId());
            checkResultNew.setProjectName(projectName);
            checkResultNew.setCheckName(checkName);
            checkResultNew.setBeatCreated(beatInfo.getCreated());//
            checkResultNew.setAddress(beatInfo.getAddress());
            checkResultNew.setStatus(CommonConstants.SUCCESS_HANDLE_STATUS);
            checkResultNew.setCreated(new Date());

        } else {
            checkResultNew.setBeatId(0L);
            checkResultNew.setProjectName(projectName);
            checkResultNew.setCheckName(checkName);
            checkResultNew.setStatus(CommonConstants.UN_HANDLE_STATUS);
            checkResultNew.setCreated(new Date());
            if (isFirstJudge) {
                checkResultNew.setBeatCreated(new Date());
            } else {
                checkResultNew.setBeatCreated(checkResult.getBeatCreated());//
            }
        }
        checkResultService.save(checkResultNew);

    }

    /**
     *  计算是否到达当前时间戳
     * @param now 当前时间戳
     * @param lastReceived 上此心跳达到时间戳
     * @param grace 最大容忍延迟时间单位为ms
     * @param dValue 两次定时任务执行间隔 单位为ms
     * @return
     */
    private boolean hasReachGraceTime(long now, long lastReceived, long grace, long dValue) {
         return  now - lastReceived - grace > dValue;
    }

    private String buildNoticeContent(String projectName, String checkName, long intervals) {
        long minute = intervals/1000*60;
        StringBuilder sb = new StringBuilder();
        sb.append("项目")
            .append(projectName)
            .append(",")
            .append("检查项")
            .append(checkName)
            .append(",超过").append(minute).append("分钟未上报心跳，该定时任务可能发生故障");
        return sb.toString();
    }
}
