package com.demo.rabbit;

import com.demo.config.RabbitConfig;
import com.demo.model.Addresslist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RabbitSender {
    private static final Logger log = LoggerFactory.getLogger(RabbitSender.class);
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendAddressList(Addresslist addresslist) {
        rabbitTemplate.convertAndSend(addresslist);
        rabbitTemplate.convertAndSend("autoAddressList", addresslist);
        rabbitTemplate.convertAndSend("ackAddressList", addresslist);
    }

    public void sendDelayAddressList(Addresslist addresslist) {
        rabbitTemplate.convertAndSend(RabbitConfig.DELAY_EXCHANGE_ADDRESSLIST, RabbitConfig.DELAY_ROUTING_KEY, addresslist, message -> {
            // TODO 第一句是可要可不要,根据自己需要自行处理
            message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Addresslist.class.getName());
            // TODO 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            message.getMessageProperties().setExpiration(5 * 1000 + "");
            return message;
        });
        log.info("[发送时间] - [{}]-[{}]", LocalDateTime.now(), addresslist.toString());
    }
}
