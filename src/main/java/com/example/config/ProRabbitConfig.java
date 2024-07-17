package com.example.config;

import lombok.NonNull;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProRabbitConfig {

  @Bean
  public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);

    rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
      @Override
      public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("ConfirmCallback: " + "相关情况: " + correlationData);
        System.out.println("ConfirmCallback: " + "确认情况: " + ack);
        System.out.println("ConfirmCallback: " + "原因: " + cause);
      }
    });

    rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
      @Override
      public void returnedMessage(@NonNull ReturnedMessage returnedMessage) {
        System.out.println("ReturnsCallback: " + returnedMessage);
      }
    });
    return rabbitTemplate;
  }
}
