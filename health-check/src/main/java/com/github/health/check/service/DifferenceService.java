package com.github.health.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.Difference;

public interface DifferenceService extends IService<Difference> {

    Difference getJobDValue(String jobName, String jobGroup);

    Boolean removeJobDValue(String jobName, String jobGroup);

}
