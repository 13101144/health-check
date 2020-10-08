package com.github.health.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.BeatInfo;

import java.util.Date;

public interface BeatInfoService extends IService<BeatInfo> {

    void createBeat(BeatInfo beatInfo);

    BeatInfo getLastBeatInfo(String projectName, String checkName, Date date);

    BeatInfo getLastBeatInfoWithoutTime(String projectName, String checkName);

}
