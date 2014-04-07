package br.com.camiloporto.cloudfinance.android.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;
import br.com.camiloporto.cloudfinance.android.service.CloudfinanceResultReceiver.Receiver;

public abstract class AbstractCloudFinanceRestfullTask<Params, Progress, Result> extends
		AsyncTask<Params, Progress, Result> {
	
	private Receiver receiver;

	public AbstractCloudFinanceRestfullTask(Receiver receiver) {
		this.receiver = receiver;
	}

	protected String getRestfullServiceHost() {
		return "http://10.0.2.2:8080";
	}
	

	protected Receiver getReceiver() {
		return receiver;
	}
	
	@Override
	protected Result doInBackground(Params... params) {
		HttpClient httpClient = createHttpClient();
		try {
			HttpUriRequest request = prepareHttpRequest(params);
			HttpResponse response = httpClient.execute(request);
			return parseRequestResponse(response);
		} catch (ClientProtocolException e) {
			catchRequestException(e);
		} catch (IOException e) {
			catchRequestException(e);
		}
		return null;
	}

	protected abstract Result parseRequestResponse(HttpResponse response);

	protected void catchRequestException(Exception e) {
		Log.e(AbstractCloudFinanceRestfullTask.class.getSimpleName(), "Error sending request", e);
	}

	protected abstract HttpUriRequest prepareHttpRequest(Params[] params) throws UnsupportedEncodingException;

	protected HttpClient createHttpClient() {
		return new DefaultHttpClient();
	}
	
}
