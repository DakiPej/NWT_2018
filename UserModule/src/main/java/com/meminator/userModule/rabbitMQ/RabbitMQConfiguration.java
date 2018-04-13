package com.meminator.userModule.rabbitMQ;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

@Configuration
@EnableRabbit
public class RabbitMQConfiguration {
	final static String userCreateQueue = "userCreateQueue";
	final static String userDeleteQueue = "userDeleteQueue";
	final static String followQueue = "followQueue";
	
	@Bean
    Queue UserCreateQueue() {
        return new Queue(userCreateQueue, false);
    }
    
    @Bean
    Queue UserDeleteQueue() {
        return new Queue(userDeleteQueue, false);
    }
    
    @Bean
    Queue FollowQueue() {
    	return new Queue(followQueue, false);
    }
    
    @Bean
    TopicExchange UserExchange() {
        return new TopicExchange("user-queue-exchange");
    }

    @Bean
    Binding userCreateBinding() {
        return BindingBuilder.bind(UserCreateQueue()).to(UserExchange()).with("user.create");
    }
    
    @Bean
    Binding userDeleteBinding() {
        return BindingBuilder.bind(UserDeleteQueue()).to(UserExchange()).with("user.delete");
    }
    
    @Bean
    Binding followBinding() {
    	return BindingBuilder.bind(FollowQueue()).to(UserExchange()).with("user.follow");
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
	/*
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
	}*/
		
}