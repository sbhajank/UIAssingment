package com.afkl.exercises.spring.locations;

import com.afkl.exercises.spring.matrics.MyAsyncHandlerInterceptor;
import com.afkl.exercises.spring.paging.PageableHandlerMethodArgumentResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.RelProvider;
import org.springframework.hateoas.core.EvoInflectorRelProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@Configuration
public class LocationsApiWebConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(myAsyncHandlerInterceptor());
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(WRITE_DATES_AS_TIMESTAMPS);
        builder.failOnUnknownProperties(false);
        builder.serializationInclusion(NON_NULL);
        builder.modules(new JacksonLocationsModule(), new Jackson2HalModule());
        return builder;
    }

    @Bean
    public RelProvider jsonRelProvider() {
        return new EvoInflectorRelProvider();
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false).favorParameter(true).defaultContentType(HAL_JSON);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }


    @Bean
    public MyAsyncHandlerInterceptor myAsyncHandlerInterceptor(){
        return new MyAsyncHandlerInterceptor();
    }
}
