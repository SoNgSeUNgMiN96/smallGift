package com.sgwannabig.smallgift.springboot.config;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class MessageConverterConfiguration {
  @Bean
  public MappingJackson2HttpMessageConverter messageConverter() {
    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    converter.setSupportedMediaTypes(
        Arrays.asList(
            MediaType.APPLICATION_JSON,
            MediaType.APPLICATION_OCTET_STREAM
        )
    );
    return converter;
  }

}
