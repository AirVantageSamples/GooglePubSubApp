package net.airvantage;

import java.io.FileNotFoundException;
import java.util.Properties;

public class GooglePubSubConfiguration {

	    // the project id
		private String project_id;
		// the subscription name
	    private String subs_name;
	    // maximum number of messages returned by one call
	 	private int size;
	    // All received messages 
	    private String subsciptionURL;
	 	
	 	
	 	public GooglePubSubConfiguration(){
	 		loadProperties();
	 		this.subsciptionURL = "projects/" + project_id + "/subscriptions/" + subs_name;
	 	}
	 	/**
		 * Initialization of static parameters from conf.properties
		 * 
		 *   @return true if successful
	    */
		private boolean loadProperties() {
			boolean res = false; 
			boolean error = true;
			Properties prop = null;
			// loading properties
			try {
	            prop = PropertyLoader.load("conf.properties");
	            error = false;
			}catch (Exception e0) {    
			    try {
				  prop = PropertyLoader.load("src/main/java/net/airvantage/resources/conf.properties");
				  error = false;
			    } catch (FileNotFoundException e) {
				      System.out.println("Could not find conf.properties in the developement directory" + e.getMessage());
			    } catch (Exception e) {
				      System.out.println("Could not find any conf.properties." + e.getMessage());
			    }
	        }
			if (!error){
				project_id = prop.getProperty("pubsub.projectID");
				subs_name =  prop.getProperty("pubsub.subsName");
				size = Integer.parseInt(prop.getProperty("pubsub.size"));
				res=true;
				System.out.println("Configuration file for PubSubConfiguration has been successfully loaded.");
			}
			return res;
		}
		public String getProject_id() {
			return project_id;
		}
		public void setProject_id(String project_id) {
			this.project_id = project_id;
		}
		public String getSubs_name() {
			return subs_name;
		}
		public void setSubs_name(String subs_name) {
			this.subs_name = subs_name;
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
