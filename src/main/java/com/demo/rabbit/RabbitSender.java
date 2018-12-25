package com.demo.rabbit;

import com.demo.model.Addresslist;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendAddressList(Addresslist addresslist) {
        rabbitTemplate.convertAndSend(addresslist);
        rabbitTemplate.convertAndSend("autoAddressList",addresslist);
        rabbitTemplate.convertAndSend("ackAddressList",addresslist);
    }
}
