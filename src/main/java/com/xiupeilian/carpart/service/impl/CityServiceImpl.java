package com.xiupeilian.carpart.service.impl;

import com.xiupeilian.carpart.mapper.CityMapper;
import com.xiupeilian.carpart.model.City;
import com.xiupeilian.carpart.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityMapper cityMapper;


    @Override
    /*缓存 注解
    指定  该方法的返回值 会被spring-cache 中 的缓存管理器（cachemanager）缓存
    需要指定使用哪一个缓存
    * */
    @Cacheable(value = "redisCache")
    public List<City> findCitiesByParentId(Integer parentId) {
        return cityMapper.findCitiesByParentId( parentId);
    }
    @Override
    @CacheEvict(value="canglaoshi",allEntries = true)
    public void deleteCityById(int id) {
        cityMapper.deleteByPrimaryKey(id);
    }
}
