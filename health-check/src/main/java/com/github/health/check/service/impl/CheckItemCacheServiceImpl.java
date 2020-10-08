package com.github.health.check.service.impl;

import com.github.health.check.domain.entity.CheckItem;
import com.github.health.check.mapper.CheckItemMapper;
import com.github.health.check.service.CheckItemCacheService;
import com.github.health.check.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CheckItemCacheServiceImpl implements CheckItemCacheService {

    private static Map<String, CheckItem> checkItemCache = new ConcurrentHashMap<>();

    @Resource
    CheckItemMapper checkItemMapper;


    @Override
    @PostConstruct
    public void init() {
        checkItemCache = loadCheckItemInfo();
    }

    private Map<String, CheckItem> loadCheckItemInfo() {
        Map<String, CheckItem> map = new ConcurrentHashMap<>();
        List<CheckItem> list = checkItemMapper.selectList(null);
        if (list == null) {
            return map;
        }
        Iterator<CheckItem> iterator = list.iterator();
        while (iterator.hasNext()) {
            CheckItem checkItem = iterator.next();
            String key = checkItem.getProjectName()+"-"+checkItem.getName();
            map.put(key, checkItem);
        }
        return map;
    }

    public CheckItem get(String appkey) {
        return checkItemCache.get(appkey);
    }


    @Override
    public void remove(String key) {
        checkItemCache.remove(key);
    }

    @Override
    public void add(String key, CheckItem checkItem) {
        checkItemCache.put(key, checkItem);
    }
}
