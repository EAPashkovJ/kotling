package com.eapashkov.downloadexecutor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class XmlValidation {

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        return  new LocalValidatorFactoryBean();
    }
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validatorFactoryBean().getValidator());
        return processor;
    }
}
