package com.github.health.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.health.check.domain.entity.Flip;

import java.util.List;

public interface FlipService extends IService<Flip> {

    List<Flip> getUnhandleFlips();

}
