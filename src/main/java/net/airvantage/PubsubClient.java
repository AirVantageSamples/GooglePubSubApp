package net.airvantage;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;

/**
 * This class instantiates a google Pub/Sub client
 *
 */

public class PubsubClient {

	private static final Logger logger = Logger.getLogger(PubsubClient.class);

	private GooglePubSubConfiguration config;
	private Pubsub client;

	/**
	 * This constructor creates an instance of a Pub Sub client
	 * 
	 * @param config
	 *            contains the parameters needed to communicate with the google
	 *            Pub/Sub platform
	 */

	public PubsubClient(GooglePubSubConfiguration config) {
		this.client = createMyPubSubClient();
		this.config = config;
	}

	/**
	 * create a pub sub client using your own HttpTransport and JsonFactory
	 * 
	 * @return an instance of Pubsub client
	 */

	private Pubsub createMyPubSubClient() {
		HttpTransport transport = Utils.getDefaultTransport();
		JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
		HttpRequestInitializer initializer = null;
		GoogleCredential credential = null;

		if (transport != null && jsonFactory != null) {
			try {
				credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);
			} catch (IOException e) {
				logger.error(e);
			}
			if (credential != null && credential.createScopedRequired()) {
				credential = credential.createScoped(PubsubScopes.all());
			}
			initializer = new HttpInitializerImplt(credential);
		}
		return new Pubsub.Builder(transport, jsonFactory, initializer).setApplicationName("pubSubApp2").build();
	}

	public GooglePubSubConfiguration getConfig() {
		return config;
	}

	public Pubsub getClient() {
		return client;
	}

}
