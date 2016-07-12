package net.airvantage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import com.google.api.services.pubsub.Pubsub;
import com.google.api.services.pubsub.model.AcknowledgeRequest;
import com.google.api.services.pubsub.model.PubsubMessage;
import com.google.api.services.pubsub.model.PullRequest;
import com.google.api.services.pubsub.model.PullResponse;
import com.google.api.services.pubsub.model.ReceivedMessage;

public class SubApp {

 // the project id
  static public String project_id;
 // the subscription name
  static public String subs_name;
 // maximum number of messages returned by one call
  static public int size;
 // All received messages 
  static public List<ReceivedMessage> AllReceivedMessages = new ArrayList<ReceivedMessage>();
 // List of received messages by one call
  static public List<ReceivedMessage> receivedMessages;
 // ackIDs of received messages by one call     		
  static public List<String> ackIDs = new ArrayList<String>();
 // instance of pubsub client
  static public Pubsub pubsub;
 // complete subscription name
  static public String subscriptionName;
 // instance of pull request
  static PullRequest MyPullReq;
	
 public static void main(String args[]) {
  // loading properties
   loadProperties();  
  // instantiate a Pub/Sub client
   pubsub = PubsubClient.createMyPubSubClient();
   subscriptionName = "projects/" + project_id + "/subscriptions/" + subs_name;
  // creating a pull request
   MyPullReq = new PullRequest().setMaxMessages(size);
   while (true) {
   // pulling messages
    pullMessages();
    if (!ackIDs.isEmpty()){
    // acknowledging messages	
     acknowledgeMessages();
    }
   }
 }

 /**
  * acknowledge messages
  */
 private static void acknowledgeMessages() {
  try {
   pubsub.projects().subscriptions().acknowledge(subscriptionName, new AcknowledgeRequest().setAckIds(ackIDs)).execute();
   ackIDs.clear();
  } catch (IOException e) {
   System.out.println(e.getMessage());
  }
 }
	
 /**
  * pull messages
  */
 private static void pullMessages() {
  PullResponse MyResponse = null;
  try {
   MyResponse = pubsub.projects().subscriptions().pull(subscriptionName, MyPullReq).execute();
  } catch (IOException e) {
   System.out.println(e.getMessage());
  }
  if (MyResponse != null) {
  // retrieving messages
   receivedMessages = MyResponse.getReceivedMessages();
  if (receivedMessages == null||receivedMessages.isEmpty()) {
   System.out.println("Waiting for messages !");
  } else {	
   for (ReceivedMessage rcvmsg : receivedMessages) {
   // saving messages
    AllReceivedMessages.add(rcvmsg);
    PubsubMessage pubsubmsg = rcvmsg.getMessage();
    try {
     String message = new String(pubsubmsg.decodeData(), "UTF-8");
     System.out.println(message);
    } catch (UnsupportedEncodingException e) {
     System.out.println(e.getMessage());
    }
     ackIDs.add(rcvmsg.getAckId());
    }
   }	
  }
 }
 
 /**
  * Initialization of static parameters from conf.properties
  *   @return true if successful
  */
 private static boolean loadProperties() {
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
   subs_name = prop.getProperty("pubsub.subsName");
   size = Integer.parseInt(prop.getProperty("pubsub.size"));
   res=true;
   System.out.println("Configuration file for PubSubConfiguration has been successfully loaded.");
  }
  return res;
 }
}

