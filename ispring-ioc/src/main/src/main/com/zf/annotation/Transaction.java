package com.zf.annotation;

import java.lang.annotation.*;

/**
 * @author zf
 * @date 2020/3/4 14:52
 * @description  定义事务
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Transaction {

}
