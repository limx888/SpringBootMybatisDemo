package com.demo.rabbit;

import com.demo.config.RabbitConfig;
import com.demo.model.Addresslist;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class RabbitHandler {
    private static final Logger log = LoggerFactory.getLogger(RabbitHandler.class);

    @RabbitListener(queues = {RabbitConfig.REGISTER_QUEUE_NAME})
    public void listenerDelayQueue(Addresslist addresslist, Message message, Channel channel) {
        log.info("[listenerDelayQueue 监听的消息] - [消费时间] - [{}]-[{}]", LocalDateTime.now(), addresslist.toString());
        try {
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
            e.printStackTrace();
        }
    }

    /**
     * <p>TODO 该方案是 spring-boot-data-amqp 默认的方式,不太推荐。具体推荐使用  listenerManualAck()</p>
     * 默认情况下,如果没有配置手动ACK, 那么Spring Data AMQP 会在消息消费完毕后自动帮我们去ACK
     * 存在问题：如果报错了,消息不会丢失,但是会无限循环消费,一直报错,如果开启了错误日志很容易就吧磁盘空间耗完
     * 解决方案：手动ACK,或者try-catch 然后在 catch 里面讲错误的消息转移到其它的系列中去
     * spring.rabbitmq.listener.simple.acknowledge-mode=manual
     * <p>
     *
     * @param addresslist 监听的内容
     */
    @RabbitListener(queues = {"autoAddressList"})
    public void listenerAutoAck(Addresslist addresslist, Message message, Channel channel) {
        // TODO 如果手动ACK,消息会被监听消费,但是消息在队列中依旧存在,如果 未配置 acknowledge-mode 默认是会在消费完毕后自动ACK掉
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[listenerAutoAck 监听的消息] - [{}]", addresslist.toString());
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.info("[listenerAutoAck IOException 监听的消息] - [{}]", addresslist.toString());
            try {
                // TODO 处理失败,重新压入MQ
                channel.basicRecover();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = {"ackAddressList"})
    public void listenerManualAck(Addresslist addresslist, Message message, Channel channel) {
        log.info("[listenerManualAck 监听的消息] - [{}]", addresslist.toString());
        try {
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
            e.printStackTrace();
        }
    }
}
