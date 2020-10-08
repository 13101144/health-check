package com.github.health.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.health.check.domain.entity.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

public interface ProjectMapper extends BaseMapper<Project> {

    @Select("SELECT * FROM Project WHERE owner = #{owner} AND name = #{project} AND code = #{code}")
    Project queryProject(String owner, String project, String code);
}
