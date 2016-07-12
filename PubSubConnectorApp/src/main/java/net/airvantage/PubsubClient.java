package net.airvantage;

import java.io.IOException;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.util.Utils;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.PubsubScopes;

public class PubsubClient {

	/**
	 * create a pub sub client using your own HttpTransport and JsonFactory
	 * @return an instance of Pubsub client
	 */
	
	public static Pubsub createMyPubSubClient() {
		HttpTransport transport = Utils.getDefaultTransport();
		JsonFactory jsonFactory = Utils.getDefaultJsonFactory();
		HttpRequestInitializer initializer = null;
		GoogleCredential credential = null;
		
		if (transport!=null&&jsonFactory!=null){
			try {
				credential = GoogleCredential.getApplicationDefault(transport, jsonFactory);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
			if (credential.createScopedRequired()){
				credential = credential.createScoped(PubsubScopes.all());
			}
	        initializer= new HttpInitializerImplt(credential);
	    }
		return new Pubsub.Builder(transport, jsonFactory, initializer).setApplicationName("pubSubApp").build();
	}
}
