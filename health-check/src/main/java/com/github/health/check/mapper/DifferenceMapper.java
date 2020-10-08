package com.github.health.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.health.check.domain.entity.Difference;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

public interface DifferenceMapper extends BaseMapper<Difference> {

    @Select("select * from difference where job_name = #{jobName} and job_group = #{jobGroup}")
    Difference getJobDValue(String jobName, String jobGroup);

    @Delete("delete from difference where job_name = #{jobName} and job_group = #{jobGroup}")
    Boolean removeJobDValue(String jobName, String jobGroup);
}
