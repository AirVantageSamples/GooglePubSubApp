package net.airvantage;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.services.pubsub.model.AcknowledgeRequest;
import com.google.api.services.pubsub.model.PubsubMessage;

/**
 * This class implementing IMessagesProcessor process data fetched from google
 * Pub/Sub platform
 *
 */

public class MessagesProcessor {

	private static final Logger logger = Logger.getLogger(MessagesProcessor.class);

	private DataHandler dataHandler;

	/**
	 * This is the MessagesProcessor constructor, it creates an instance of
	 * MessagesProcessor
	 * 
	 * @param dataHandler
	 *            is the object that handles received data
	 */

	public MessagesProcessor(DataHandler dataHandler) {
		this.dataHandler = dataHandler;
	}

	/**
	 * this method process the messages by calling the DataHandler methods
	 * acknowledges messages after processing data
	 * 
	 * @param messages
	 *            List of received messages
	 * @param ackIds
	 *            List of ackIds of received messages
	 * @param client
	 *            the pub sub client to call in order to retrieve data
	 */

	public void processMessages(List<PubsubMessage> messages, List<String> ackIds, PubsubClient client) {

		this.dataHandler.processMyMessages(messages);
		this.acknowledgeMessages(ackIds, client);

	}

	/**
	 * This method acknowledges messages
	 * 
	 * @param ackIds
	 * @param client
	 */
	public void acknowledgeMessages(List<String> ackIds, PubsubClient client) {

		try {
			client.getClient().projects().subscriptions()
					.acknowledge(client.getConfig().getSubsciptionURL(), new AcknowledgeRequest().setAckIds(ackIds))
					.execute();
		} catch (IOException e) {
			logger.error(e);
		}

	}

}

