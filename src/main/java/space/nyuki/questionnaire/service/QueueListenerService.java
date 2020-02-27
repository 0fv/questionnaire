package space.nyuki.questionnaire.service;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.nyuki.questionnaire.listener.QueueMessageListener;
import space.nyuki.questionnaire.pojo.QuestionnaireEntity;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class QueueListenerService {
	@Autowired
	private RabbitAdmin rabbitAdmin;
	@Autowired
	private Exchange exchange;
	@Autowired
	private DirectMessageListenerContainer container;
	@Autowired
	private QueueMessageListener queueMessageListener;
	@Autowired
	private QuestionnaireEntityService questionnaireEntityService;

	public void registry(List<String> queues) {
		if (!queues.isEmpty()) {
			queues.forEach(this::declareBinding);
			container.addQueueNames(queues.toArray(new String[]{}));
			container.start();
			System.out.println(String.format("%s is running！", container));
		}

	}

	public void init() {
		container.setMessageListener(queueMessageListener);
		List<QuestionnaireEntity> data = questionnaireEntityService.getData(0);
		registry(data.stream().map(QuestionnaireEntity::getId).collect(Collectors.toList()));
	}

	public void addQueue(String id) {
		this.declareBinding(id);
		container.addQueueNames(id);
	}


	public void deleteQueue(String id) {
		rabbitAdmin.deleteQueue(id);
		container.removeQueueNames(id);
	}

	public void declareBinding(String queueName) {
		if (rabbitAdmin.getQueueProperties(queueName) == null) {
			Queue queue = new Queue(queueName, true, false, false, null);
			rabbitAdmin.declareQueue(queue);
			Binding binding = BindingBuilder.bind(queue).to(this.exchange).with(queueName).noargs();
			rabbitAdmin.declareBinding(binding);
		}
	}
}

