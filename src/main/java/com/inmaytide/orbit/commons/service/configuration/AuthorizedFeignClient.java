package com.inmaytide.orbit.commons.service.configuration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author inmaytide
 * @since 2023/5/11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@FeignClient
public @interface AuthorizedFeignClient {

    @AliasFor(annotation = FeignClient.class, attribute = "name")
    String name() default "";

    @AliasFor(annotation = FeignClient.class, attribute = "qualifiers")
    String[] qualifiers() default {};

    @AliasFor(annotation = FeignClient.class, attribute = "configuration")
    Class<?>[] configuration() default {AuthorizedFeignClientConfiguration.class};

    @AliasFor(annotation = FeignClient.class, attribute = "contextId")
    String contextId() default "";

    @AliasFor(annotation = FeignClient.class, attribute = "url")
    String url() default "";

    @AliasFor(annotation = FeignClient.class, attribute = "dismiss404")
    boolean dismiss404() default false;

    @AliasFor(annotation = FeignClient.class, attribute = "fallback")
    Class<?> fallback() default void.class;

    @AliasFor(annotation = FeignClient.class, attribute = "path")
    String path() default "";

}
