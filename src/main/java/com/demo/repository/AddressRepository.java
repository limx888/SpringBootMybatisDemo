package com.demo.repository;

import com.demo.model.Addresslist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Addresslist, Integer> {
    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名
     * @return 查询结果
     */
    List<Addresslist> findAllByName(String name);
}
