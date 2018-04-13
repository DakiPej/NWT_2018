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
	
    public static final String EXCHANGE_UPDATE_POSTS= "post-queue-exchange";
    
    public static final String QUEUE_POST_VOTE = "postVoteQueue";
    public static final String ROUTING_KEY_POST_VOTE = "post.vote";
    
    public static final String QUEUE_POSTS_TO_BE_ADDED = "postQueue";
    public static final String ROUTING_KEY_POSTS_TO_BE_ADDED = "post.create"; 
    
    public static final String QUEUE_POSTS_TO_BE_DELETED = "postQueueDelete"; 
    public static final String ROUTING_KEY_POSTS_TO_BE_DELETED = "post.delete"; 
    
    
    public static final String EXCHANGE_UPDATE_USERS = "user-queue-exchange";
    
    public static final String QUEUE_USERS_TO_BE_ADDED = "usersQueue";
    public static final String ROUTING_KEY_USERS_TO_BE_ADDED = "user.create"; 
    
    public static final String QUEUE_USERS_TO_BE_DELETED = "usersQueueDelete";
    public static final String ROUTING_KEY_USERS_TO_BE_DELETED = "user-delete";
    
	
	@Bean
	public TopicExchange usersExchange	()	{
		return new TopicExchange(EXCHANGE_UPDATE_USERS); 
	}
	@Bean
    public TopicExchange postsExchange(){
        return new TopicExchange(EXCHANGE_UPDATE_POSTS);
	}
	
	@Bean
    public Queue postVoteQueue(){
        return new Queue(QUEUE_POST_VOTE);
	}
	@Bean
	public Queue addPostsQueue()	{
		return new Queue(QUEUE_POSTS_TO_BE_ADDED); 
	}
	@Bean
	public Queue deletePostsQueue()	{
		return new Queue(QUEUE_POSTS_TO_BE_DELETED);
	}
	@Bean
	public Queue addUsersQueue()	{
		return new Queue(QUEUE_USERS_TO_BE_ADDED);
	}
	@Bean
	public Queue deleteUsersQueue()	{
		return new Queue(QUEUE_USERS_TO_BE_DELETED);
	}
	
	
	@Bean
	public Binding declarePostVoteBinding()	{
	    return BindingBuilder.bind(postVoteQueue()).to(postsExchange()).with(ROUTING_KEY_POST_VOTE);
	}
	@Bean
	public Binding declareCreatePostBinding()	{
		return BindingBuilder.bind(addPostsQueue()).to(postsExchange()).with(ROUTING_KEY_POSTS_TO_BE_ADDED);
	}
	@Bean
	public Binding declareDeletePostBinding()	{
		return BindingBuilder.bind(deletePostsQueue()).to(postsExchange()).with(ROUTING_KEY_POSTS_TO_BE_DELETED);
	}
	
	@Bean
	public Binding declareCreateUserBinding()	{
		return BindingBuilder.bind(addUsersQueue()).to(usersExchange()).with(ROUTING_KEY_USERS_TO_BE_ADDED);
	}
	@Bean
	public Binding declareDeleteUserBinding()	{
		return BindingBuilder.bind(deleteUsersQueue()).to(usersExchange()).with(ROUTING_KEY_USERS_TO_BE_DELETED);
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
