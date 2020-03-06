package com.zf.annotation;

import java.lang.annotation.*;

/**
 * @author zf
 * @date 2020/3/4 14:52
 * @description  定义Bean
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Service {
    String value() default "";
}
