package com.meminator.imageModule.configurations;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;


@Configuration
@EnableRabbit
public class RabbitConfig implements RabbitListenerConfigurer {

    public static final String EXCHANGE_USERS_NAME = "user-queue-exchange";
    public static final String ROUTING_KEY_USERS = "user.create";
    public static final String QUEUE_NAME_USERS = "usersQueue";
    public static final String ROUTING_KEY_USERS_DELETE = "user.delete";
    public static final String QUEUE_NAME_USERS_DELETE = "usersQueueDelete";
    public static final String EXCHANGE_POST_NAME = "posts-queue-exchange";
    public static final String ROUTING_KEY_POST = "post.create";
    public static final String QUEUE_NAME_POST = "postQueue";
    public static final String ROUTING_KEY_POST_DELETE = "post.delete";
    public static final String QUEUE_NAME_POST_DELETE = "postQueueDelete";

    @Bean
    public TopicExchange usersExchange() {
        return new TopicExchange(EXCHANGE_USERS_NAME);
    }

    @Bean
    public TopicExchange postExchange(){
        return new TopicExchange(EXCHANGE_POST_NAME);
    }

    @Bean
    public Queue usersQueue(){
        return  new Queue(QUEUE_NAME_USERS);
    }

    @Bean
    public Queue postQueue(){
        return new Queue(QUEUE_NAME_POST);
    }
    
    @Bean
    public Queue usersDeleteQueue(){
        return  new Queue(QUEUE_NAME_USERS_DELETE);
    }

    @Bean
    public Queue postDeleteQueue(){
        return new Queue(QUEUE_NAME_POST_DELETE);
    }

    @Bean
    public Binding declareBindingUsers(){
        return BindingBuilder.bind(usersQueue()).to(usersExchange()).with(ROUTING_KEY_USERS);
    }

    @Bean
    public Binding declareBindingPost(){
        return BindingBuilder.bind(postQueue()).to(postExchange()).with(ROUTING_KEY_POST);
    }
    
    @Bean
    public Binding declareBindingUsersDelete(){
        return BindingBuilder.bind(usersDeleteQueue()).to(usersExchange()).with(ROUTING_KEY_USERS_DELETE);
    }

    @Bean
    public Binding declareBindingPostDelete(){
        return BindingBuilder.bind(postDeleteQueue()).to(postExchange()).with(ROUTING_KEY_POST_DELETE);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }

    @Bean
    public DefaultMessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
        factory.setMessageConverter(consumerJackson2MessageConverter());
        return factory;
    }

    @Override
    public void configureRabbitListeners(final RabbitListenerEndpointRegistrar registrar) {
        registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
}