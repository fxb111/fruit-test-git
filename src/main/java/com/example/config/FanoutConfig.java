package com.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FanoutConfig {

  @Bean
  public Queue fanoutQueue1() {
    return new Queue("fanoutQueue1", true);
  }

  @Bean
  public Queue fanoutQueue2() {
    return new Queue("fanoutQueue2", true);
  }

  @Bean
  public Queue fanoutQueue3() {
    return new Queue("fanoutQueue3", true);
  }

  @Bean
  public FanoutExchange fanoutExchange() {
    return new FanoutExchange("fanoutExchange", true, false);
  }

  @Bean
  public Binding bindingA() {
    return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
  }

  @Bean
  public Binding bindingB() {
    return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
  }

  @Bean
  public Binding bindingC() {
    return BindingBuilder.bind(fanoutQueue3()).to(fanoutExchange());
  }

}
