package com.github.health.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.health.check.domain.entity.CheckItem;
import org.apache.ibatis.annotations.Select;

public interface CheckItemMapper extends BaseMapper<CheckItem> {

    @Select("SELECT * FROM check_item where project_name = #{projectName} and check_name = #{checkName}")
    CheckItem getCheckItem(String projectName, String checkName);
}
