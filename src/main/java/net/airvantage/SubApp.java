package net.airvantage;

/**
 * 
 * This class is the main class it creates an instance of the dataHandler and
 * the GooglePubSubConnector and it connects the application to the Google Pub
 * Sub platform
 */

public class SubApp {

	/**
	 * This method connects to the Pub Sub platform in order to retrieve
	 * messages from a google topic via a subscription and acknowledge them
	 * 
	 */

	public static void main(String args[]) {
                DataHandler dataHandler = new DataHandler();
		GooglePubSubConnector connector = new GooglePubSubConnector();
		connector.launch(dataHandler);

	}

}
