package com.demo;

import com.demo.mapper.AddresslistMapper;
import com.demo.mapper.MybatisMapper;
import com.demo.model.Addresslist;
import com.demo.repository.AddressRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    private static final Logger log = LoggerFactory.getLogger(DemoApplicationTests.class);

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    AddresslistMapper addresslistMapper;

    @Autowired
    MybatisMapper mybatisMapper;

    @Test
    public void contextLoads() {

    }

    /**
     * 整合SpringDataJpa
     * 下面的几个操作中，findAllByName，其它的都是继承自JpaRepository接口中的方法，更关键的是分页及排序是如此的简单实例化一个Pageable即可…
     */
    public void JpaRepositoryTest() {
        final Addresslist addresslist = addressRepository.save(new Addresslist("u1", "11122222222"));
        log.info("[添加成功] - [{}]", addresslist.toString());
        final List<Addresslist> u1 = addressRepository.findAllByName("u1");
        log.info("[条件查询] - [{}]", u1.size());
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("name")));
        final Page<Addresslist> addresslists = addressRepository.findAll(pageable);
        log.info("[分页+排序+查询所有] - [{}]", addresslists.getContent().get(0).getId());
        addressRepository.findById(addresslists.getContent().get(0).getId()).ifPresent(addresslist1 -> log.info("[主键查询] - [{}]", addresslist1.getId()));
        final Addresslist edit = addressRepository.save(new Addresslist(addresslist.getId(), "修改后的ui", "修改后的p1"));
        log.info("[修改成功] - [{}]", edit.toString());
        addressRepository.deleteById(edit.getId());
        log.info("[删除主键为 {} 成功] - [{}]", edit.getId());
    }

    /**
     * 整合Mybatis
     * @throws Exception
     */
    public void mybatisTest() throws Exception {
        final int row1 = addresslistMapper.insert(new Addresslist("A1","2222222222"));
        log.info("[添加结果] - [{}]", row1);
        final List<Addresslist> addresslists = addresslistMapper.selectAddress();
        log.info("[查询所有] - [{}]", addresslists.size());
    }

    /**
     * 通用Mapper与分页插件的集成
     * @throws Exception
     */
    @Test
    public void mybatisPageHelperTest() throws Exception {
        final PageInfo<Object> pageInfo = PageHelper.startPage( 1, 10 ).setOrderBy( "id desc" ).doSelectPageInfo( () -> this.mybatisMapper.selectAll());
        log.info( "[lambda写法] - [分页信息] - [{}]", pageInfo.toString() );

        PageHelper.startPage( 2, 10 ).setOrderBy( "id desc" );
        final PageInfo<Addresslist> userPageInfo = new PageInfo<Addresslist>(this.mybatisMapper.selectAll());
        log.info( "[普通写法] - [{}]", userPageInfo );
    }

}
