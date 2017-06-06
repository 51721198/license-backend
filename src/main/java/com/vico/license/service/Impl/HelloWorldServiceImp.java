package com.vico.license.service.Impl;

import com.vico.license.pojo.Person;
import com.vico.license.service.HelloWorldService;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

/**
 * Created by liudun on 2017/6/3.
 */
public class HelloWorldServiceImp implements HelloWorldService.Iface {

    private static final Logger LOGGER = Logger.getLogger(HelloWorldService.class);

    @Override
    public Person sayHello(String name) throws TException {
        LOGGER.info("thrift 接口被调用!!!!");
        Person person = new Person();
        person.setUserName(name);
        person.setUserPhone(738217);
        return person;
    }
}
