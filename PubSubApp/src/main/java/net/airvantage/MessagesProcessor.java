package net.airvantage;

import java.io.IOException;
import java.util.List;

import com.google.api.services.pubsub.model.AcknowledgeRequest;
import com.google.api.services.pubsub.model.PubsubMessage;

public class MessagesProcessor implements IMessagesProcessor {

	private DataHandler dataHandler;

	public void initialize() {
		dataHandler = new DataHandler();
	}

	public void processMessages(List<PubsubMessage> messages, List<String> ackIds, PubsubClient client) {

		this.dataHandler.processMyMessages(messages);
		this.acknowledgeMessages(ackIds, client);

	}

	public void acknowledgeMessages(List<String> ackIds, PubsubClient client) {

		try {
			client.getClient().projects().subscriptions()
					.acknowledge(client.getConfig().getSubsciptionURL(), new AcknowledgeRequest().setAckIds(ackIds))
					.execute();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

	}

}
