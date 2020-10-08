package com.github.health.check.service;

import com.github.health.check.domain.entity.CheckItem;

public interface CheckItemCacheService {

    void init();

    CheckItem get(String key);

    void remove(String key);

    void add(String key, CheckItem checkItem);
}
