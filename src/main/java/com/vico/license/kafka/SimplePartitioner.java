package com.vico.license.kafka;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

/**
 * Created by liudun on 2017/5/14.
 */
public class SimplePartitioner implements Partitioner{

    public SimplePartitioner(VerifiableProperties properties){}

    @Override
    public int partition(Object key, int a_numPartitions) {
        int partition = 0;
        String stringKey = (String)key;
        int offset = stringKey.lastIndexOf('.');
        if (offset > 0){
            partition = Integer.parseInt(stringKey.substring(offset + 1)) % a_numPartitions;
        }

        return partition;
    }
}
