package com.smart_healtcare_system.appointment_service.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.appointment.requests.name}")
    private String appointmentRequestsQueue;

    @Value("${rabbitmq.queue.appointment.responses.name}")
    private String appointmentResponsesQueue;

    @Value("${rabbitmq.exchange.name}")
    private String appointmentExchange;

    @Value("${rabbitmq.binding.appointment.requests.routing.key}")
    private String appointmentRequestsRoutingKey;

    @Value("${rabbitmq.binding.appointment.responses.routing.key}")
    private String appointmentReponsesRoutingKey;

    @Bean
    public Queue appointmentRequestsQueue() {
        return QueueBuilder.durable(appointmentRequestsQueue).build();
    }

    @Bean
    public Queue appointmentResponsesQueue() {
        return QueueBuilder.durable(appointmentResponsesQueue).build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(appointmentExchange);
    }

    @Bean
    public Binding appointmentRequestsBinding() {
        return BindingBuilder
                .bind(appointmentRequestsQueue())
                .to(exchange())
                .with(appointmentRequestsRoutingKey);
    }

    @Bean
    public Binding appointmentResponsesBinding() {
        return BindingBuilder
                .bind(appointmentResponsesQueue())
                .to(exchange())
                .with(appointmentReponsesRoutingKey);
    }


    //Message converter
    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    //Configurare RabbitTemplate
    @Bean
    public RabbitTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationRunner runner(RabbitAdmin rabbitAdmin) {
        return args -> {
            rabbitAdmin.declareQueue(appointmentRequestsQueue());
            rabbitAdmin.declareQueue(appointmentResponsesQueue());
            rabbitAdmin.declareExchange(exchange());
            rabbitAdmin.declareBinding(appointmentRequestsBinding());
            rabbitAdmin.declareBinding(appointmentResponsesBinding());
        };
    }
}
