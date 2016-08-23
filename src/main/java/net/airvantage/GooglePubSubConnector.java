package net.airvantage;

/**
 * This class creates the Worker that will process data, configures and launches
 * it
 *
 */

public class GooglePubSubConnector {

	private Worker worker;
	private GooglePubSubConfiguration config;
	private MessagesProcessor messagesProcessor;

	/**
	 * This method creates the Worker that will process data, configures and
	 * launches it
	 */

	public void launch() {
		messagesProcessor = new MessagesProcessor();
		config = new GooglePubSubConfiguration();
		PubsubClient client = new PubsubClient(config);
		worker = new Worker(client, messagesProcessor);
		worker.run();

	}

}
