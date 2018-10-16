package com.demo.mapper;

import com.demo.model.Addresslist;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

public interface MybatisMapper extends BaseMapper<Addresslist> {
    /**
     * 根据用户名统计（TODO 假设它是一个很复杂的SQL）
     *
     * @param name 用户名
     * @return 统计结果
     */
    @Select("SELECT count(1) FROM addresslist WHERE name = #{name}")
    int countByName(String name);
}
