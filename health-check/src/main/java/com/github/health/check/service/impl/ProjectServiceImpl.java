package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.Project;
import com.github.health.check.mapper.ProjectMapper;
import com.github.health.check.service.ProjectService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public Boolean saveProject(Project project) {
        String code = UUID.randomUUID().toString().replaceAll("-", "");
        project.setCode(code);
        project.setCreated(LocalDateTime.now());
        projectMapper.insert(project);
        return Boolean.TRUE;
    }

    @Override
    public Boolean updateProject(Project project) {
        project.setUpdated(LocalDateTime.now());
        return this.updateById(project);
    }

    @Override
    public IPage getProjectPage(int page, String name) {
        Page<Project> projectPage = new Page<>(page,20);
        if (!StringUtils.isEmpty(name)) {
            QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",name);
            return projectMapper.selectPage(projectPage, queryWrapper);
        }
        return projectMapper.selectPage(projectPage, null);
    }

}
