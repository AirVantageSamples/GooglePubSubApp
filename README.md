Google Pub Sub App
==========================

Description
-------
This application connects to Google Pub Sub topic via a subscription. For each received message, its content is displayed on the console

Configuration
-------------
The configuration file `conf.properties` contains :
* Google Developers Console Project ID
* Subscription name
* Maximum number of messages returned by one call

 
Running
--------------------
 
Set your credentials using the  command line  "gcloud auth login"  that enables a web-based authorization (you can revoke your credentials for a specific account using: gcloud auth revoke 'account')
 
The configuration file `config.properties` has to be adapted,

A log file `PubSubApp.log` will be created.
