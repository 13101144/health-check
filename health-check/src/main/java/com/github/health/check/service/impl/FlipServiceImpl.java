package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.Flip;
import com.github.health.check.mapper.FlipMapper;
import com.github.health.check.service.FlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FlipServiceImpl extends ServiceImpl<FlipMapper, Flip> implements FlipService {

    @Resource
    FlipMapper flipMapper;

    @Override
    public List<Flip> getUnhandleFlips() {
        QueryWrapper queryWrapper = new QueryWrapper<>().eq("status","N");
        List<Flip> flips = flipMapper.selectList(queryWrapper);
        return flips;
    }
}
