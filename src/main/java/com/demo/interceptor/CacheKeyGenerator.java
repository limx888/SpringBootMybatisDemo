package com.demo.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * key生成器（生成策略）
 */
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}