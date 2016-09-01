package net.airvantage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.api.services.pubsub.model.PullRequest;
import com.google.api.services.pubsub.model.PullResponse;
import com.google.api.services.pubsub.model.ReceivedMessage;

/**
 * This class pulls and acknowledges received messages
 *
 */

public class Worker {

	private static final Logger logger = Logger.getLogger(Worker.class);

	private PubsubClient client;
	private MessagesProcessor messagesProcessor;

	/**
	 * This constructor initializes the messages processor
	 * 
	 * @param client
	 *            the google Pub Sub Client
	 * @param messagesProcessor
	 *            the messages processor
	 */

	public Worker(PubsubClient client, MessagesProcessor messagesProcessor) {
		this.client = client;
		this.messagesProcessor = messagesProcessor;

	}

	/**
	 * This method starts the Worker
	 */
	public void run() {

		PullRequest myPullReq = new PullRequest().setMaxMessages(client.getConfig().getSize());
		while (true) {
			// pulling messages
			pullMessages(myPullReq);
		}
	}

	private void pullMessages(PullRequest myPullReq) {

		List<ReceivedMessage> receivedMessages;
		List<PubsubMessage> pubsubMessages = new ArrayList<PubsubMessage>();

		List<String> ackIDs = new ArrayList<String>();

		PullResponse myResponse = null;
		try {
			myResponse = client.getClient().projects().subscriptions()
					.pull(client.getConfig().getSubsciptionURL(), myPullReq).execute();
		} catch (IOException e) {
			logger.error(e);
		}
		if (myResponse != null) {
			// retrieving messages
			receivedMessages = myResponse.getReceivedMessages();
			if (receivedMessages == null || receivedMessages.isEmpty()) {
				logger.info("Waiting for messages !");
			} else {
				for (ReceivedMessage rcvmsg : receivedMessages) {
					PubsubMessage pubsubmsg = rcvmsg.getMessage();
					pubsubMessages.add(pubsubmsg);
					ackIDs.add(rcvmsg.getAckId());
				}
			}
		}
		this.messagesProcessor.processMessages(pubsubMessages, ackIDs, client);
	}
}

