package io.fundrequest.tokensale.progress.infrastructure;

import io.fundrequest.tokensale.progress.AzraelMessageListener;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JmsConfig {
    @Bean
    SimpleMessageListenerContainer paidContainer(ConnectionFactory connectionFactory,
                                                 MessageListenerAdapter paidListenerAdapter,
                                                 @Value("${io.fundrequest.azrael.queue.paid}") final String queueName) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(paidListenerAdapter);
        return container;
    }

    @Bean
    SimpleMessageListenerContainer transferContainer(ConnectionFactory connectionFactory,
                                                     MessageListenerAdapter transferListenerAdapter,
                                                     @Value("${io.fundrequest.azrael.queue.transfer}") final String queueName) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(transferListenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter paidListenerAdapter(AzraelMessageListener listener) {
        return new MessageListenerAdapter(listener, "receivePaidMessage");
    }

    @Bean
    MessageListenerAdapter transferListenerAdapter(AzraelMessageListener listener) {
        return new MessageListenerAdapter(listener, "receiveTransferMessage");
    }


    @Bean
    TopicExchange exchange() {
        return new TopicExchange("azrael-exchange");
    }

    @Bean
    Queue paidQueue(@Value("${io.fundrequest.azrael.queue.paid}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding paidBinding(Queue paidQueue, TopicExchange exchange, @Value("${io.fundrequest.azrael.queue.paid}") final String queueName) {
        return BindingBuilder.bind(paidQueue).to(exchange).with(queueName);
    }

    @Bean
    Queue transferQueue(@Value("${io.fundrequest.azrael.queue.transfer}") final String queueName) {
        return new Queue(queueName, true);
    }

    @Bean
    Binding transferBinding(Queue transferQueue, TopicExchange exchange, @Value("${io.fundrequest.azrael.queue.transfer}") final String queueName) {
        return BindingBuilder.bind(transferQueue).to(exchange).with(queueName);
    }

}
