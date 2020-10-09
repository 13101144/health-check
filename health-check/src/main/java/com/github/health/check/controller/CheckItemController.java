package com.github.health.check.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.health.check.domain.dto.CheckDto;
import com.github.health.check.domain.entity.CheckItem;
import com.github.health.check.service.CheckItemCacheService;
import com.github.health.check.service.CheckItemService;
import com.github.health.check.util.JobUtil;
import com.github.health.check.util.KeyGenerator;
import com.github.health.check.util.R;
import com.sun.tools.javac.comp.Check;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Api(tags = "检查项管理")
@RestController
@RequestMapping("/check")
public class CheckItemController {

    @Autowired
    private CheckItemService checkItemService;

    @Autowired
    private CheckItemCacheService checkItemCacheService;

    @Autowired
    JobUtil jobUtil;

    /**
     * 通过ID查询检查项
     * @param id ID
     * @return 检查项
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "通过ID查询检查项")
    public R getCheckById(@PathVariable long id) {
        return R.success(checkItemService.getById(id));
    }

    /**
     * 根据检查名查询检查项信息
     * @param checkname 项目名
     * @return
     */
    @GetMapping("/details/{checkname}")
    @ApiOperation(value = "根据检查名查询检查项信息")
    public R getUserByCheckName(@PathVariable String checkname) {
        CheckItem check = checkItemService.getOne(Wrappers.<CheckItem>query().lambda().eq(CheckItem::getName, checkname));
        return R.success(check);
    }

    /**
     * 删除检查项信息
     * @param id ID
     * @return R
     */
    @DeleteMapping("remove")
    @ApiOperation(value = "删除检查项信息")
    public R deleteCheck(@RequestParam long id) throws Exception{
        CheckItem checkItem = checkItemService.getById(id);
        String key = KeyGenerator.generateKey(checkItem.getProjectName(), checkItem.getName());
        checkItemCacheService.remove(key);
        checkItemService.removeById(id);
        if (checkItem != null) {
            jobUtil.removeJob(checkItem);
        }
        return R.success();
    }

    /**
     * 添加检查项信息
     * @param checkItem 检查项信息
     * @return success/false
     */
    @PostMapping("/add")
    @ApiOperation(value = "添加检查项信息")
    public R addCheck(@Valid @RequestBody  CheckItem checkItem) throws Exception{
        CheckItem existCheck = checkItemService.getOne(Wrappers.<CheckItem>query().lambda().eq(CheckItem::getName, checkItem.getName()));
        if (existCheck != null) {
            return R.failed(String.format("检查项名已经存在 %s", checkItem.getName()));
        }
        return R.success(checkItemService.saveCheck(checkItem));
    }

    /**
     * 更新检查项信息
     * @param checkItem 检查项信息
     * @return R
     */
    @PutMapping("/edit")
    @ApiOperation(value = "更新检查项信息")
    public R updateCheck(@Valid @RequestBody CheckItem checkItem) throws Exception{
        return R.success(checkItemService.updateCheck(checkItem));
    }

    /**
     * 分页查询检查项
     * @param page 参数集
     * @return 检查项集合
     */
    @GetMapping("/listpage")
    @ApiOperation(value = "分页查询检查项")
    public R getCheckPage(@RequestParam Integer page, @RequestParam(required = false) String name) {
        return R.success(checkItemService.getCheckPage(page, name));
    }

    /**
     * 分页查询检查项
     * @param
     * @return 检查项集合
     */
    @GetMapping("/list")
    @ApiOperation(value = "分页查询检查项")
    public R lisr() {
        return R.success(checkItemService.listCheck());
    }
}
