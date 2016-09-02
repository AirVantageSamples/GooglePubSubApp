package net.airvantage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * This class loads properties from the properties file to enable the pub sub
 * client configuration
 *
 */

public class GooglePubSubConfiguration {

	private static final Logger logger = Logger.getLogger(GooglePubSubConfiguration.class);

	// the project id
	private String projectId;
	// the subscription name
	private String subsName;
	// maximum number of messages returned by one call
	private int size;
	// All received messages
	private String subsciptionURL;

	/**
	 * This constructor call loadProperties method to load properties from the
	 * properties file and initialize the URL from which messages are pulled
	 */

	public GooglePubSubConfiguration() {
		loadProperties();
		this.subsciptionURL = "projects/" + projectId + "/subscriptions/" + subsName;
	}

	/**
	 * Initialization of parameters from conf.properties
	 * 
	 * @return true if successful
	 */
	private boolean loadProperties() {
		boolean res = false;
		boolean error = true;
		Properties prop = null;
		// loading properties
		try {
			prop = PropertyLoader.load("conf.properties");
			error = false;
		} catch (Exception e0) {

			logger.error(e0);

			try {
				prop = PropertyLoader.load("src/main/resources/conf.properties");
				error = false;
			} catch (FileNotFoundException e) {
				logger.error("Could not find config.properties in the developement directory", e);
			} catch (IOException e) {
				logger.error("I/O Exception", e);
			}
		}
		if (!error) {
			projectId = prop.getProperty("pubsub.projectID");
			subsName = prop.getProperty("pubsub.subsName");
			size = Integer.parseInt(prop.getProperty("pubsub.size"));
			res = true;
			logger.info("Configuration file for PubSubConfiguration has been successfully loaded.");
		}
		return res;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getSubsName() {
		return subsName;
	}

	public void setSubsName(String subsName) {
		this.subsName = subsName;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getSubsciptionURL() {
		return subsciptionURL;
	}

	public void setSubsciptionURL(String subsciptionURL) {
		this.subsciptionURL = subsciptionURL;
	}
}
