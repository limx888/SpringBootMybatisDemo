package com.demo.rabbit;

import com.demo.model.Addresslist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "AddressList")
public class RabbitReceiver {
    private static final Logger log = LoggerFactory.getLogger(RabbitReceiver.class);
    @RabbitHandler
    public void process(Addresslist addresslist) {
        log.info("[listener 监听的消息] - [{}]", addresslist.toString());
    }
}
