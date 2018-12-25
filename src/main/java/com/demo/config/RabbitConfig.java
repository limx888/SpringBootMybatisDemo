package com.demo.config;


import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue addressListQueuq() {
        // 第一个是 QUEUE 的名字,第二个是消息是否需要持久化处理
        return new Queue("AddressList", true);
    }

    @Bean
    public Queue addressListAckQueuq() {
        // 第一个是 QUEUE 的名字,第二个是消息是否需要持久化处理
        return new Queue("ackAddressList", true);
    }

    @Bean
    public Queue addressListAutoQueuq() {
        // 第一个是 QUEUE 的名字,第二个是消息是否需要持久化处理
        return new Queue("autoAddressList", true);
    }
}
