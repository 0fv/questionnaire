package space.nyuki.questionnaire.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.nyuki.questionnaire.pojo.ResultTemplate;
import space.nyuki.questionnaire.pojo.SubmitResult;
import space.nyuki.questionnaire.service.ResultCollectionService;

import java.io.IOException;

@Component
public class QueueMessageListener implements ChannelAwareMessageListener {
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ResultCollectionService resultCollectionService;

	@Override
	public void onMessage(Message message, Channel channel) {
		try {
			SubmitResult submitResult = objectMapper.readValue(message.getBody(), SubmitResult.class);
			resultCollectionService.saveData(submitResult);
			channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
		} catch (Exception e) {
			try {
				channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		}


	}
}
