package net.airvantage;

public class GooglePubSubConnector {

	private Worker worker;
	private GooglePubSubConfiguration config;
	private MessagesProcessor messagesProcessor;
	public void launch() {
		messagesProcessor = new MessagesProcessor();
		config = new GooglePubSubConfiguration();
		PubsubClient client = new PubsubClient(config);
		worker = new Worker(client, messagesProcessor);
		worker.run();

	}

}
