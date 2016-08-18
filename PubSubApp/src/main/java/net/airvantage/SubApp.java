package net.airvantage;

public class SubApp {

	static private GooglePubSubConnector connector;

	public static void main(String args[]) {

		connector = new GooglePubSubConnector();
		connector.launch();

	}

}
