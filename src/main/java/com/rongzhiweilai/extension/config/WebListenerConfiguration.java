package com.rongzhiweilai.extension.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rongzhiweilai.extension.annotation.resolver.JSONParamAnnotationResolver;

import java.util.List;

/**
 * @author : championjing
 * @ClassName: WebListenerConfiguration
 * @Description: TODO
 * @Date: 3/5/2019 5:58 PM
 */
@Configuration
public class WebListenerConfiguration extends WebMvcConfigurerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(WebListenerConfiguration.class);
    
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        LOGGER.debug("添加json解析器");
        argumentResolvers.add(new JSONParamAnnotationResolver());
    }
}
