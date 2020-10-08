package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.CheckResult;
import com.github.health.check.mapper.CheckResultMapper;
import com.github.health.check.service.CheckResultService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class CheckResultServiceImpl extends ServiceImpl<CheckResultMapper, CheckResult> implements CheckResultService {

    @Resource
    private CheckResultMapper checkResultMapper;

    @Override
    public CheckResult getNewestBeat(String projectName, String checkName) {
        return checkResultMapper.getNewestBeat(projectName, checkName);
    }

    @Override
    public IPage getCheckResultPage(int page, String name) {
        Page<CheckResult> checkResultPage = new Page<>(page,20);
        if (!StringUtils.isEmpty(name)) {
            QueryWrapper<CheckResult> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("check_name",name);
            return checkResultMapper.selectPage(checkResultPage, queryWrapper);
        }
        return checkResultMapper.selectPage(checkResultPage, null);
    }
}
