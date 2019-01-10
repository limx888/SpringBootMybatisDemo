package com.demo.interceptor;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * lock拦截器（AOP）
 * Guava Cache
 */
@Aspect
@Configuration
public class LockMethodInterceptor {
    private static final Cache<String, Object> CACHES = CacheBuilder.newBuilder()
            //最大缓存数
            .maximumSize(1000)
            //设置写缓存后过期时间
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();

    @Around("execution(public * *(..)) && @annotation(com.demo.interceptor.LocalLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        LocalLock localLock = method.getAnnotation(LocalLock.class);
        String key = getKey(localLock.key(), pjp.getArgs());
        if (!StringUtils.isEmpty(key)) {
            if (null != CACHES.getIfPresent(key)) {
                throw new RuntimeException("请勿重复请求");
            }
            // 如果是第一次请求，就将key当前对象压入缓存中
            CACHES.put(key, key);
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new RuntimeException("服务器异常");
        } finally {
            // TODO 为了演示效果,这里就不调用 CACHES.invalidate(key); 代码了
        }
    }

    /**
     * key 的生成策略
     * @param keyExpress 表达式
     * @param args 参数
     * @return 生成的key
     */
    private String getKey(String keyExpress, Object[] args) {
        for (int i = 0; i < args.length; i++) {
            keyExpress = keyExpress.replace("arg[" + i + "]", args[i].toString());
        }
        return keyExpress;
    }
}
