package com.github.health.check.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.health.check.domain.entity.BeatInfo;
import org.apache.ibatis.annotations.Select;

public interface BeatInfoMapper extends BaseMapper<BeatInfo> {

    @Select("select * from  beat_info where name = #{projectName} and service_name = #{checkName} and status = 'N' and type = 'E' and created > #{date} order by created desc limit 1")
    BeatInfo getLastBeatInfo(String projectName, String checkName, String date);

    @Select("select * from beat_info where name = #{projectName} and service_name = #{checkName} and status = 'N' and type = 'E' order by created desc limit 1")
    BeatInfo getLastBeatInfoWithoutTime(String projectName, String checkName);
}
