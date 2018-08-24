package com.example.demo;

import com.example.demo.service.UserService;
import com.example.demo.service.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.lang.reflect.Method;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        ApplicationContext ac = new FileSystemXmlApplicationContext("src/main/java/com/example/demo/config.xml");
        UserService us = (UserServiceImpl) ac.getBean("userService");
        us.addUser();


        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(DemoApplication.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });
        DemoApplication sample = (DemoApplication) enhancer.create();
        sample.test();
    }

    public void test(){
        System.out.println("hello world");
    }
}
