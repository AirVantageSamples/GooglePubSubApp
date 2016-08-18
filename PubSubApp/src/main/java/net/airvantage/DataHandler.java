package net.airvantage;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.google.api.services.pubsub.model.PubsubMessage;

public class DataHandler {

	public void processMyMessages(List<PubsubMessage> messages) {

		for (PubsubMessage msg : messages) {
			String message;
			try {
				message = new String(msg.decodeData(), "UTF-8");
				System.out.println("received message: " + message);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}
