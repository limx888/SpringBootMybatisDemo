package com.demo;

import com.demo.mapper.AddresslistMapper;
import com.demo.mapper.MybatisMapper;
import com.demo.model.Addresslist;
import com.demo.rabbit.RabbitSender;
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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    private RabbitSender rabbitSender;

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
    public void mybatisPageHelperTest() throws Exception {
        final PageInfo<Object> pageInfo = PageHelper.startPage( 1, 10 ).setOrderBy( "id desc" ).doSelectPageInfo( () -> this.mybatisMapper.selectAll());
        log.info( "[lambda写法] - [分页信息] - [{}]", pageInfo.toString() );

        PageHelper.startPage( 2, 10 ).setOrderBy( "id desc" );
        final PageInfo<Addresslist> userPageInfo = new PageInfo<Addresslist>(this.mybatisMapper.selectAll());
        log.info( "[普通写法] - [{}]", userPageInfo );
    }

    /**
     * redis缓存
     */
    public void redisTemplateTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        IntStream.range( 0, 1000 ).forEach( i ->
                executorService.execute( () -> stringRedisTemplate.opsForValue().increment( "kk", 1 ))
        );

        stringRedisTemplate.opsForValue().set( "k1","v1" );
        final String k1 = stringRedisTemplate.opsForValue().get( "k1" );
        log.info( "[字符缓存结果] - [{}]", k1 );
        // TODO 以下只演示整合，具体Redis命令可以参考官方文档，Spring Data Redis 只是改了名字而已，Redis支持的命令它都支持
        String key = "battcn:user:1";
        redisTemplate.opsForValue().set( key, new Addresslist( "Aaa", "12333333333" ) );
        // TODO 对应String（字符串）
        final Addresslist addresslist = (Addresslist) redisTemplate.opsForValue().get( key );
        log.info( "[对应缓存结果] - [{}]", addresslist );
    }

    /**
     * Rabbit Queue
     */
    public void sendAddressList() {
        Addresslist addresslist = new Addresslist();
        addresslist.setName("lmx");
        addresslist.setPhone("12345678910");
        addresslist.setEmail("email@test.com");
        rabbitSender.sendAddressList(addresslist);
    }

    /**
     * Rabbit Delay Queue
     */
    @Test
    public void sendDelayAddressList() {
        Addresslist addresslist = new Addresslist();
        addresslist.setName("lmx");
        addresslist.setPhone("12345678910");
        addresslist.setEmail("email@test.com");
        rabbitSender.sendDelayAddressList(addresslist);
    }

}
