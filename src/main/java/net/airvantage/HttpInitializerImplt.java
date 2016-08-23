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

/**
 * This class implements the HttpRequestInitializer class
 *
 */
public class HttpInitializerImplt implements HttpRequestInitializer {

	private final Credential credentials;
	private final Sleeper sleeper;

	/**
	 * 
	 * @param credentials
	 *            credentials needed to be identified
	 */

	public HttpInitializerImplt(Credential credentials) {
		this.credentials = credentials;
		this.sleeper = Sleeper.DEFAULT;

	}

	/**
	 * This method initializes the request parameters : read timeout,
	 * credentials and retries attempts whenRPC failures
	 * 
	 * @param request
	 */
	public void initialize(HttpRequest request) {
		// 2 minutes
		request.setReadTimeout(2 * 60000);
		// filling request authorization field
		request.setInterceptor(credentials);

		// retry attempts when RPC failures
		final HttpUnsuccessfulResponseHandler backoffHandler = new HttpBackOffUnsuccessfulResponseHandler(
				new ExponentialBackOff()).setSleeper(sleeper);
		request.setUnsuccessfulResponseHandler(new HttpUnsuccessfulResponseHandler() {
			public boolean handleResponse(HttpRequest request, HttpResponse response, boolean retrySupported)
					throws IOException {
				// credential or back off handler can handle it
				return credentials.handleResponse(request, response, retrySupported)
						|| backoffHandler.handleResponse(request, response, retrySupported);
			}
		});
		request.setIOExceptionHandler(new HttpBackOffIOExceptionHandler(new ExponentialBackOff())).setSleeper(sleeper);
	}
}