package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DirectConfig {

  @Bean
  public Queue directQueue() {
    return new Queue("DirectQueue", true);
  }

  @Bean
  public DirectExchange directExchange() {
    return new DirectExchange("DirectExchange", true, false);
  }

  @Bean
  public DirectExchange noExistExchange() {
    return new DirectExchange("noExistExchange");
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(directQueue()).to(directExchange()).with("directRouting");
  }

}
