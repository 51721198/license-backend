package com.vico.license.controller;

import com.vico.license.kafka.KafkaProducer;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liudun on 2017/5/9.
 */

@RestController
@RequestMapping("mq")
public class Mqcontroller {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mqcontroller.class);

    @RequestMapping("produce")
    public String produce(){
        KafkaProducer producer = new KafkaProducer();
        producer.produce();
        return null;
    }

//    @RequestMapping("consume")
//    public String consume(){
//        kafkaConsumer.consume();
//        return null;
//    }

}
