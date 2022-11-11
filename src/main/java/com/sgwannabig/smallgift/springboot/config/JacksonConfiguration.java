package com.sgwannabig.smallgift.springboot.config;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfiguration {

  private static final String DATE_FORMAT = "yyyy-MM-dd";
  private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
  private static final String TIMEZONE = "Asia/Seoul";

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

    return builder -> {

      // formatter
      DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(DATETIME_FORMAT);

      // deserializers
      builder.deserializers(new LocalDateDeserializer(dateFormatter));
      builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

      // serializers
      builder.serializers(new LocalDateSerializer(dateFormatter));
      builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter))
          .timeZone(TIMEZONE);
    };
  }
}
