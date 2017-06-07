package com.vico.license.controller;

import com.alibaba.fastjson.JSON;
import com.vico.license.kafka.KafkaProducer;
import com.vico.license.pojo.ProcessResult;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liudun on 2017/5/9.
 */

@RestController
@RequestMapping("mq")
public class Mqcontroller {
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Mqcontroller.class);

    @Autowired
    private KafkaProducer kafkaProducer;

    @Value("${kafka.topic}")
    private String topic;

    @RequestMapping("produce")
    public String produce(){
        ProcessResult result = new ProcessResult();
        System.out.println("kafak发送消息:...");
        for (int i = 0; i < 20; i++) {
            kafkaProducer.send(topic, "测试发送数据:" + i);
            System.out.println("现在发送第 {} 条信息."+i);
            LOGGER.info("现在发送第 {} 条信息.", i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("数据发送完毕.");
        LOGGER.info("数据发送完毕.");
        result.setResultdesc("队列数据已经发送完毕");
        return JSON.toJSONString(result);
    }

//    @RequestMapping("consume")
//    public String consume(){
//        kafkaConsumer.consume();
//        return null;
//    }

}
