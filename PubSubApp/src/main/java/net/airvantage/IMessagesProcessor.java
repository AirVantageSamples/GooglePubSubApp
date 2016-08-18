package net.airvantage;

import java.util.List;

import com.google.api.services.pubsub.model.PubsubMessage;

public interface IMessagesProcessor {

	public void initialize();

	public void processMessages(List<PubsubMessage> messages, List<String> ackIds, PubsubClient client);

	public void acknowledgeMessages(List<String> ackIds, PubsubClient client);

}
