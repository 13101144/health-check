package com.github.health.check.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.dto.ProjectDto;
import com.github.health.check.domain.entity.Project;
import org.springframework.web.bind.annotation.RequestParam;

public interface ProjectService extends IService<Project> {

    Boolean saveProject(Project project);

    Boolean updateProject(Project project);

    IPage getProjectPage(int page, String name);
}
