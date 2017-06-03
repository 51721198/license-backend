package com.vico.license.rpc;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by liudun on 2017/6/4.
 */

public class ThriftServiceServerFactory implements InitializingBean {


    private Integer port;

    private Integer priority = 1;

    //服务端接口
    private Class<?> serviceIface;

    //服务端接口实现类
    private Object serviceImpl;

    private String serviceSimpleName;

    private ThriftServer serverThread;

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Object getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(Object serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public Class<?> getServiceIface() {
        this.serviceIface = serviceIface;
        this.serviceSimpleName = serviceIface.getSimpleName();
//        if (isIfaceExtendFb303()) {
//            throw new IllegalArgumentException("serviceInterface‘s Sub Class of Iface must extend from com.facebook.fb303.FacebookService.Iface)");
//        }
        return serviceIface;
    }

    public void setServiceIface(Class<?> serviceIface) {
        this.serviceIface = serviceIface;
    }

    //用来注册zk地址的,暂时用不上
    private String configPath;

    public String getConfigPath() {
        return configPath;
    }

    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
         // processor = 服务端接口名 + $Processor;
        Class Processor = Class.forName(serviceIface.getName() + "$Processor");

        // 接口iface = 服务端接口名 + $Iface;
        Class Iface = Class.forName(serviceIface.getName() + "$Iface");

         // 列用iface获取构造类
        Constructor con = Processor.getConstructor(Iface);

         // 处理类 = 调用iface的构造类,把实现类作为参数传给iface的构造方法
        TProcessor processor = (TProcessor) con.newInstance(serviceImpl);

        serverThread = new ThriftServer(port,processor);
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(serverThread);
    }

    public class ThriftServer implements Runnable{

        private Integer port;
        private TProcessor processor;

        ThriftServer(Integer port,TProcessor processor){
            this.port = port;
            this.processor = processor;
        }

        @Override
        public void run() {
            TServerSocket serverTransport = null;
            try {
                serverTransport = new TServerSocket(port);
            } catch (TTransportException e) {
                e.printStackTrace();
            }
            TBinaryProtocol.Factory protFactory = new TBinaryProtocol.Factory(true, true);
            TThreadPoolServer.Args args = new TThreadPoolServer.Args(serverTransport);
            args.protocolFactory(protFactory);
            args.processor(processor);
            TServer server = new TThreadPoolServer(args);
            System.out.println("Starting server on port " + getPort() + " ...");
            server.serve();
        }


    }


}
