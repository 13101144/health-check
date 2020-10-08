package com.github.health.check.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.health.check.domain.entity.Project;
import com.github.health.check.service.ProjectService;
import com.github.health.check.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(tags = "项目管理")
@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    /**
     * 通过ID查询项目信息
     * @param id ID
     * @return 项目信息
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询项目信息")
    public R getProjectById(@PathVariable long id) {
        return R.success(projectService.getById(id));
    }

    /**
     * 根据项目名查询项目信息
     * @param projectname 项目名
     * @return
     */
    @GetMapping("/details/{projectname}")
    @ApiOperation(value = "根据项目名查询项目信息")
    public R getUserByProjectName(@PathVariable String projectname) {
        Project project = projectService.getOne(Wrappers.<Project>query().lambda().eq(Project::getName, projectname));
        return R.success(project);
    }

    /**
     * 删除项目信息
     * @param id ID
     * @return R
     */
    @DeleteMapping("remove")
    @ApiOperation(value = "删除项目信息")
    public R deleteProject(@RequestParam long id) {
        return R.success(projectService.removeById(id));
    }

    /**
     * 添加项目信息
     * @param project 项目信息
     * @return success/false
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加项目信息")
    public R addProject(@Valid @RequestBody  Project project) {
        Project existProject = projectService.getOne(Wrappers.<Project>query().lambda().eq(Project::getName, project.getName()));
        if (existProject != null) {
            return R.failed(String.format("检查项名已经存在 %s", project.getName()));
        }
        return R.success(projectService.saveProject(project));
    }

    /**
     * 更新项目信息
     * @param project 项目信息
     * @return R
     */
    @PutMapping("/edit")
    @ApiOperation(value = "更新项目信息")
    public R updateUser(@Valid @RequestBody Project project) {
        return R.success(projectService.updateProject(project));
    }

    /**
     * 分页查询项目
     * @param page 参数集
     * @return 项目集合
     */
    @GetMapping("/listpage")
    @ApiOperation(value = "分页查询项目")
    public R getProjectPage(@RequestParam Integer page, @RequestParam(required = false) String name) {
        return R.success(projectService.getProjectPage(page, name));
    }


}
