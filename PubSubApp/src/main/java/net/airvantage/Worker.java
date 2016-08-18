package net.airvantage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.api.services.pubsub.model.PullRequest;
import com.google.api.services.pubsub.model.PullResponse;
import com.google.api.services.pubsub.model.ReceivedMessage;

public class Worker {

	private PubsubClient client;
	private MessagesProcessor messagesProcessor;

	public Worker(PubsubClient client, MessagesProcessor messagesProcessor) {
		this.client = client;
		this.messagesProcessor = messagesProcessor;
		this.messagesProcessor.initialize();
	}

	public void run() {

		PullRequest MyPullReq = new PullRequest().setMaxMessages(client.getConfig().getSize());
		while (true) {
			// pulling messages
			pullMessages(MyPullReq);
		}
	}

	private void pullMessages(PullRequest myPullReq) {

		List<ReceivedMessage> receivedMessages = new ArrayList<ReceivedMessage>();
		List<PubsubMessage> pubsubMessages = new ArrayList<PubsubMessage>();

		List<String> ackIDs = new ArrayList<String>();

		PullResponse MyResponse = null;
		try {
			MyResponse = client.getClient().projects().subscriptions()
					.pull(client.getConfig().getSubsciptionURL(), myPullReq).execute();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		if (MyResponse != null) {
			// retrieving messages
			receivedMessages = MyResponse.getReceivedMessages();
			if (receivedMessages == null || receivedMessages.isEmpty()) {
				System.out.println("Waiting for messages !");
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
