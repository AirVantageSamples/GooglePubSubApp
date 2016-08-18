package net.airvantage;

import java.io.IOException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.HttpBackOffIOExceptionHandler;
import com.google.api.client.http.HttpBackOffUnsuccessfulResponseHandler;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpUnsuccessfulResponseHandler;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.client.util.Sleeper;


public class HttpInitializerImplt implements HttpRequestInitializer {
    
	private final Credential _Credential;
	private final Sleeper sleeper;
   
    public HttpInitializerImplt(Credential _credential) {
          this._Credential = _credential;
          this.sleeper = Sleeper.DEFAULT;
          
    }
    public void initialize(HttpRequest request) {
       // 2 minutes 	
       request.setReadTimeout(2*60000);  
       // filling request authorization field
       request.setInterceptor(_Credential);
       // retry attempts when RPC failures
       final HttpUnsuccessfulResponseHandler backoffHandler = new HttpBackOffUnsuccessfulResponseHandler(new ExponentialBackOff()).setSleeper(sleeper);
       request.setUnsuccessfulResponseHandler(new HttpUnsuccessfulResponseHandler() {
		public boolean handleResponse(HttpRequest request, HttpResponse response, boolean retrySupported) throws IOException {
			// credential or  back off handler can handle it 
			return _Credential.handleResponse(request, response, retrySupported)||backoffHandler.handleResponse(request, response, retrySupported);
		}
       });
       request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff())).setSleeper(sleeper);
    }
}