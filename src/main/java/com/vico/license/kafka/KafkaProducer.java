package com.vico.license.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Created by liudun on 2017/5/9.
 */

//kafka生产者
@Component
@EnableKafka
public class KafkaProducer  {

    public static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String message) {
        // the KafkaTemplate provides asynchronous send methods returning a Future
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);

        // register a callback with the listener to receive the result of the send asynchronously
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("sent message='{}' with offset={}"+message+
                        result.getRecordMetadata().offset());
                LOGGER.info("sent message='{}' with offset={}", message,
                        result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                System.out.println("unable to send message='{}'"+message+ex);
                LOGGER.error("unable to send message='{}'", message, ex);
            }
        });

        // or, to block the sending thread to await the result, invoke the future's get() method
    }

//    private final Producer<String,String> producer;
//
//    public final static String TOPIC = "page_visits";
//
//    public KafkaProducer(){
//        Properties props = new Properties();
//        //配置kafka的broker,每一个broker代表一台开了kafka的服务器,这里不能连接zk!!!!!
//        props.put("metadata.broker.list","10.4.226.212:9092");
//        //producer对发送的消息的序列化方式,这里就是string格式,也可以格式化成二进制
//        props.put("serializer.class","kafka.serializer.StringEncoder");
//        //始终没有搞懂这个Partitioner
//        props.put("partitioner.class","com.vico.license.kafka.SimplePartitioner");
//        //是否需要应答,这个配置关系到消息便宜量offset
//        props.put("request.required.acks","-1");
//        props.put("group.id","0");
//        ProducerConfig producerConfig = new ProducerConfig(props);
//        producer = new Producer<>(producerConfig);
//    }
//
//    public void produce() throws InterruptedException {
//        long events = Long.parseLong("1000");
//        for (long nEvents = 0;nEvents < events; nEvents++) {
//            Random rnd = new Random();
//            long runtime = new Date().getTime();
//            String ip = "192.168.2." + rnd.nextInt(255);
//            String msg = runtime + ",www.example.com," + ip;
//            System.out.println("发送消息: " + msg);
//            KeyedMessage<String, String> data = new KeyedMessage<>(TOPIC, ip, msg);
//            producer.send(data);
//            Thread.sleep(1000);
//        }
//        producer.close();
//    }

}
