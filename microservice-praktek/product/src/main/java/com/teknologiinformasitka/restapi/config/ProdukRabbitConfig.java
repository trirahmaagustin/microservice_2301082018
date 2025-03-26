package com.teknologiinformasitka.restapi.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.springframework.amqp.rabbit.connection.ConnectionFactory;
@Configuration
public class ProdukRabbitConfig {


   public static final String ORDER_EXCHANGE = "order.exchange";
   public static final String ORDER_QUEUE = "order.created.queue";
   public static final String ORDER_ROUTING_KEY = "order.created";


   @Bean
   public DirectExchange orderExchange() {
       return new DirectExchange(ORDER_EXCHANGE);
   }
  
   @Bean
   public Queue orderQueue() {
       return new Queue(ORDER_QUEUE);
   }
  
   @Bean
   public Binding orderBinding(Queue orderQueue, DirectExchange orderExchange) {
       return BindingBuilder.bind(orderQueue).to(orderExchange).with(ORDER_ROUTING_KEY);
   }


   @Bean
   public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
       return new Jackson2JsonMessageConverter();
   }
  
}

