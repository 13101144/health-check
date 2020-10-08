package com.github.health.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.health.check.domain.entity.CheckResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface CheckResultMapper extends BaseMapper<CheckResult> {

    @Select("select * from check_result where project_name = #{projectName} and check_name = #{checkName} order by beat_created desc limit 1")
    public CheckResult getNewestBeat(String projectName, String checkName);


}
