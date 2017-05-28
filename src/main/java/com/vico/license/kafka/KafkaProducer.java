package com.vico.license.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Created by liudun on 2017/5/9.
 */

//kafka生产者
public class KafkaProducer {

    private final Producer<String,String> producer;

    public final static String TOPIC = "page_visits";

    public KafkaProducer(){
        Properties props = new Properties();
        //配置kafka的broker,每一个broker代表一台开了kafka的服务器,这里不能连接zk!!!!!
        props.put("metadata.broker.list","10.4.226.212:9092");
        //producer对发送的消息的序列化方式,这里就是string格式,也可以格式化成二进制
        props.put("serializer.class","kafka.serializer.StringEncoder");
        //始终没有搞懂这个Partitioner
        props.put("partitioner.class","com.vico.license.kafka.SimplePartitioner");
        //是否需要应答,这个配置关系到消息便宜量offset
        props.put("request.required.acks","-1");
        ProducerConfig producerConfig = new ProducerConfig(props);
        producer = new Producer<>(producerConfig);
    }

    public void produce(){
        long events = Long.parseLong("1000");
        for (long nEvents = 0;nEvents < events; nEvents++) {
            Random rnd = new Random();
            long runtime = new Date().getTime();
            String ip = "192.168.2." + rnd.nextInt(255);
            String msg = runtime + ",www.example.com," + ip;
            KeyedMessage<String, String> data = new KeyedMessage<>(TOPIC, ip, msg);
            producer.send(data);
        }
        producer.close();
    }
}
