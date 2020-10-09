package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.dto.CheckDto;
import com.github.health.check.domain.entity. CheckItem;
import com.github.health.check.domain.entity.Project;
import com.github.health.check.mapper. CheckItemMapper;
import com.github.health.check.service.CheckItemCacheService;
import com.github.health.check.service.CheckItemService;
import com.github.health.check.util.JobUtil;
import com.github.health.check.util.KeyGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CheckItemServiceImpl extends ServiceImpl< CheckItemMapper,  CheckItem> implements CheckItemService {

    @Resource
    private  CheckItemMapper checkItemMapper;

    @Autowired
    CheckItemCacheService checkItemCacheService;

    @Autowired
    JobUtil jobUtil;

    @Override
    @Transactional
    public Boolean saveCheck( CheckItem checkItem) throws Exception{
        checkItem.setCreated(LocalDateTime.now());
        checkItemMapper.insert(checkItem);
        String key = KeyGenerator.generateKey(checkItem.getProjectName(),checkItem.getName());
        checkItemCacheService.add(key, checkItem);
        jobUtil.addJob(checkItem);
        return Boolean.TRUE;
    }

    @Override
    @Transactional
    public Boolean updateCheck(CheckItem checkItem) throws Exception{
        checkItem.setUpdated(LocalDateTime.now());
        String key = KeyGenerator.generateKey(checkItem.getProjectName(),checkItem.getName());
        checkItemCacheService.remove(key);
        // 检查cron表达式是否变化
        String oldSchedule = checkItemMapper.selectById(checkItem.getId()).getSchedule();
        if (!oldSchedule.equals(checkItem.getSchedule())) {
            jobUtil.modifyJob(checkItem);
        }
        return this.updateById(checkItem);
    }

    @Override
    public IPage getCheckPage(int page, String name) {
        Page<CheckItem> checkPage = new Page<>(page,20);
        if (!StringUtils.isEmpty(name)) {
            QueryWrapper<CheckItem> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            return checkItemMapper.selectPage(checkPage, queryWrapper);
        }
        return checkItemMapper.selectPage(checkPage, null);
    }

    @Override
    public List<CheckItem> listCheck() {
        return checkItemMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public CheckItem getCheckItem(String projectName, String checkName) {
        return checkItemMapper.getCheckItem(projectName, checkName);
    }
}
