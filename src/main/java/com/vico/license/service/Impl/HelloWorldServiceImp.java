package com.vico.license.service.Impl;

import com.vico.license.pojo.Person;
import com.vico.license.service.HelloWorldService;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liudun on 2017/6/3.
 */
public class HelloWorldServiceImp implements HelloWorldService.Iface {

    public static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldService.class);

    @Override
    public Person sayHello(String name) throws TException {
        LOGGER.info("thrift 接口被调用!!!!");
        Person person = new Person();
        person.setUserName(name);
        person.setUserPhone(738217);
        return person;
    }
}
