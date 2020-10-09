package com.github.health.check.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.CheckResult;

public interface CheckResultService extends IService<CheckResult> {

    CheckResult getNewestBeat(String projectName, String checkName);

    IPage getCheckResultPage(int page, String name);

}
