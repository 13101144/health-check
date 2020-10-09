package com.github.health.check.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.dto.CheckDto;
import com.github.health.check.domain.entity.CheckItem;

import java.util.List;

public interface CheckItemService extends IService<CheckItem> {

    Boolean saveCheck(CheckItem check) throws Exception;

    Boolean updateCheck(CheckItem CheckItem) throws Exception;

    IPage getCheckPage(int page, String name);

    List<CheckItem> listCheck();

    CheckItem getCheckItem(String projectName, String checkName);

}
