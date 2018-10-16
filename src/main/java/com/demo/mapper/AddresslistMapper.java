package com.demo.mapper;

import com.demo.model.Addresslist;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface AddresslistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Addresslist record);

    int insertSelective(Addresslist record);

    Addresslist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Addresslist record);

    int updateByPrimaryKey(Addresslist record);

    /**
     * Mybatis3.x提供的新特性(@Select、@Update、@Delete、@Insert )
     * @Select("SELECT * FROM addresslist")
     * @return
     */
    List<Addresslist> selectAddress();
}