package com.meminator.demo.configurations;


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
public class AsyncConfiguration implements RabbitListenerConfigurer	{
	
    public static final String EXCHANGE_VOTE_NAME = "vote-queue-exchange";
    public static final String ROUTING_KEY_VOTE = "*.vote";
public static final String QUEUE_NAME_VOTE = "voteQueue";
	
	@Bean
	public TopicExchange usersExchange	()	{
		return new TopicExchange(""); 
	}
	
	@Bean
    public TopicExchange voteExchange(){
        return new TopicExchange(EXCHANGE_VOTE_NAME);
	}
	
	@Bean
    public Queue voteQueue(){
        return new Queue(QUEUE_NAME_VOTE);
	}
	
	 @Bean
	    public Binding declareBindingVote(){
	        return BindingBuilder.bind(voteQueue()).to(voteExchange()).with(ROUTING_KEY_VOTE);
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