package com.example.config;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopicConfig {

  public final static String man = "topic.man";

  public final static String woman = "topic.woman";

  @Bean
  public Queue topicQueue1() {
    return new Queue(man, true);
  }

  @Bean
  public Queue topicQueue2() {
    return new Queue(woman, true);
  }

  @Bean
  public TopicExchange topicExchange() {
    return new TopicExchange("TopicExchange", true, false);
  }

  @Bean
  public Binding binding1() {
    return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(man);
  }

  @Bean
  public Binding binding2() {
    return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("topic.#");
  }

}
