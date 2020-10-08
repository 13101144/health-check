package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.Difference;
import com.github.health.check.mapper.DifferenceMapper;
import com.github.health.check.service.DifferenceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DifferenceServiceImpl extends ServiceImpl<DifferenceMapper, Difference> implements DifferenceService {

    @Resource
    DifferenceMapper differenceMapper;

    @Override
    public Difference getJobDValue(String jobName, String jobGroup) {
        return differenceMapper.getJobDValue(jobName,jobGroup);
    }

    @Override
    public Boolean removeJobDValue(String jobName, String jobGroup) {
        return differenceMapper.removeJobDValue(jobName, jobGroup);
    }
}
