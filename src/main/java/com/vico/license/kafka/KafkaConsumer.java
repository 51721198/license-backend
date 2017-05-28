package com.vico.license.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

/**
 * Created by liudun on 2017/5/9.
 */
//kafka消费者
public class KafkaConsumer implements MessageListener<String,String> {

    //不和spring集成时你需要手动设置consumer的配置并且进行加载
//    private final kafka.javaapi.consumer.ConsumerConnector consumer;
//    public KafkaConsumer(){
//        Properties props = new Properties();
//
//        //consumer连接的是zookeeper而不是kafka,意思是连到zk获取broker然后再自动连接到kafka的broker?
//        props.put("zookeeper.connect","10.4.226.212:2181");
//
//        //消费者所属组,一个组内部只能有一个消费者能够消费指定主题
//        props.put("group.id","jd_group");s
//
//        //zk连接超时
//        props.put("zookeeper.session.timeout.ms","4000");
//        props.put("zookeeper.sync.time.ms","200");
//        //consumer对消息的解码方式,这个必须和producer一致.
//        props.put("serializer.class","kafka.serializer.StringEncoder");
//
//        ConsumerConfig config = new ConsumerConfig(props);
//        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(config);
//    }

    //不和spring集成时你需要调用这个方法才能消费队列消息
//     private void consume() {
//        Map<String, Integer> topicCountMap = new HashMap<>();
//        topicCountMap.put(KafkaProducer.TOPIC, new Integer(1));
//
//        StringDecoder keyDecoder = new StringDecoder(new VerifiableProperties());
//        StringDecoder valueDecoder = new StringDecoder(new VerifiableProperties());
//
//        Map<String, List<KafkaStream<String, String>>> consumerMap = consumer.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
//        KafkaStream<String, String> stream = consumerMap.get(KafkaProducer.TOPIC).get(0);
//        ConsumerIterator<String, String> it = stream.iterator();
//        while (it.hasNext()) {
//            System.out.println(it.next().message());
//        }
//    }

    @Override
    public void onMessage(ConsumerRecord<String, String> stringStringConsumerRecord) {
        System.out.println("kafka consumer收到的消息: "+stringStringConsumerRecord.value());
    }
}
