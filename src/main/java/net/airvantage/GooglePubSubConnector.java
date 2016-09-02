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
	 * 
	 * @param dataHandler
	 */

	public void launch(DataHandler dataHandler) {
		messagesProcessor = new MessagesProcessor(dataHandler);
		config = new GooglePubSubConfiguration();
		PubsubClient client = new PubsubClient(config);
		worker = new Worker(client, messagesProcessor);
		worker.run();

	}

}
