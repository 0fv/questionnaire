package space.nyuki.questionnaire.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import space.nyuki.questionnaire.exception.AckFailException;
import space.nyuki.questionnaire.listener.QueueMessageListener;

@Configuration
public class RabbitMQConfig {
	@Autowired
	private QueueMessageListener queueMessageListener;

	@Bean
	public RabbitAdmin rabbitAdmin(RabbitTemplate rabbitTemplate) {

		rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
			if (!ack) {
				throw new AckFailException(cause);
			}
		});
		return new RabbitAdmin(rabbitTemplate);
	}

	@Bean
	@Primary
	public RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry() {
		return new RabbitListenerEndpointRegistry();
	}

	@Bean
	public Exchange exchange(RabbitAdmin rabbitAdmin) {
		Exchange exchange = new DirectExchange("result");
		rabbitAdmin.declareExchange(exchange);
		return exchange;
	}

	@Bean
	public DirectMessageListenerContainer directMessageListenerContainer(RabbitTemplate rabbitTemplate) {
		ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
		DirectMessageListenerContainer container = new DirectMessageListenerContainer(connectionFactory);
		container.setMessageListener(queueMessageListener);
		container.setAutoStartup(true);
		container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
		return container;
	}

}
