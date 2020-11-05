package com.finance.webfetcher.rpc;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("intuit.userdata");
    }

    @Bean
    public Queue queue() {
        return new Queue("webQueue");
    }

    @Bean
    public Binding binding(DirectExchange directExchange,
                           Queue queue) {
        return BindingBuilder.bind(queue)
                .to(directExchange)
                .with("web");
    }

    @Bean
    public MessageConverter jackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
