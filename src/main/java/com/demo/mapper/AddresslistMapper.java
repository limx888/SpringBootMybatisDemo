package com.demo.mapper;

import com.demo.model.Addresslist;

public interface AddresslistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Addresslist record);

    int insertSelective(Addresslist record);

    Addresslist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Addresslist record);

    int updateByPrimaryKey(Addresslist record);
}