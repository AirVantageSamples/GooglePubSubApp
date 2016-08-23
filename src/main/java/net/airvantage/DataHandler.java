package net.airvantage;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.log4j.Logger;

import com.google.api.services.pubsub.model.PubsubMessage;

/**
 * This class handles the received messages, it displays the messages but it is
 * possible to link this application to a database for example.
 *
 */

public class DataHandler {

	private static final Logger logger = Logger.getLogger(DataHandler.class);

	/**
	 * This method process the received records.
	 * 
	 * @param messages
	 *            the list of messages received from google Pub/Sub
	 */

	public void processMyMessages(List<PubsubMessage> messages) {

		for (PubsubMessage msg : messages) {
			String message;
			try {
				message = new String(msg.decodeData(), "UTF-8");
				logger.debug("received message: " + message);
			} catch (UnsupportedEncodingException e) {

				logger.error(e);
			}

		}

	}
}
