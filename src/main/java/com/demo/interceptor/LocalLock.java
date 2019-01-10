package com.demo.interceptor;

import java.lang.annotation.*;

/**
 * Lock锁的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface LocalLock {
    String key() default "";

    /**
     * 过期时间
     * @return
     */
    int expire() default 5;
}
