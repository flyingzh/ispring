package com.zf.annotation;

import java.lang.annotation.*;

/**
 * @author admin
 * @date 2020/3/4 16:30
 * @description
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface Autowired {
}
