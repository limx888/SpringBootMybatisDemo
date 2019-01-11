package com.demo.service;

import com.demo.mapper.AddresslistMapper;
import com.demo.model.Addresslist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    private AddresslistMapper addresslistMapper;

    @Cacheable(value = "addressList", key = "#id")
    @Override
    public Addresslist getAddressById(int id) {
        log.info("根据userId获取地址信息");
        return addresslistMapper.selectByPrimaryKey(id);
    }

    @CachePut(value = "addresslist", key = "#addresslist.id", condition = "#addresslist.name.length() < 10")
    @Override
    public int addAddress(Addresslist addresslist) {
        log.info("添加地址信息");
        return addresslistMapper.insert(addresslist);
    }

    @CachePut(value = "addresslist", key = "#addresslist.id")
    @Override
    public int updateAddress(Addresslist addresslist) {
        log.info("更新地址信息");
        return addresslistMapper.updateByPrimaryKey(addresslist);
    }

    /**
     * allEntries：是否清空所有缓存内容，缺省为 false，如果指定为 true，则方法调用后将立即清空所有缓存（如：@CacheEvict(value = "user", key = "#id", allEntries = true)）
     * beforeInvocation：是否在方法执行前就清空，缺省为 false，如果指定为 true，则在方法还没有执行的时候就清空缓存，缺省情况下，如果方法执行抛出异常，则不会清空缓存（如：@CacheEvict(value = "user", key = "#id", beforeInvocation = true)）
     * @param id
     * @return
     */
    @CacheEvict(value = "addressList", key = "#id", allEntries = false, beforeInvocation = true)
    @Override
    public int deleteById(int id) {
        log.info("根据userId删除地址信息");
        return addresslistMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<Addresslist> selectAddress() {
        log.info("获取所有地址信息");
        return addresslistMapper.selectAddress();
    }
}
