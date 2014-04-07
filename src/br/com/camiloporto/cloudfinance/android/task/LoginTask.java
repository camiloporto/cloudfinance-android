package br.com.camiloporto.cloudfinance.android.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import br.com.camiloporto.cloudfinance.android.service.CloudfinanceResultReceiver.Receiver;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

public class LoginTask extends AsyncTask<String, Void, Integer> {

	public static final String LOGIN_HTTP_STATUS_CODE_TAG = "httpStatusCode";
	private Receiver receiver;

	public LoginTask(Receiver receiver) {
		this.receiver = receiver;
	}

	@Override
	protected Integer doInBackground(String... params) {
		String userName = params[0];
		String pass = params[1];
		Integer httpStatusCode = sendLoginRequest(userName, pass);
		
		return httpStatusCode;
	}
	
	@Override
	protected void onPostExecute(Integer result) {
		super.onPostExecute(result);
		Bundle resultData = new Bundle();
		resultData.putInt(LOGIN_HTTP_STATUS_CODE_TAG, result);
		receiver.onReceiveResult(0, resultData);
	}
	
	private Integer sendLoginRequest(String userName, String pass) {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://10.0.2.2:8080/user/login");
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("userName", userName));
		postParams.add(new BasicNameValuePair("pass", pass));
		try {
			UrlEncodedFormEntity form = new UrlEncodedFormEntity(postParams);
			form.setContentEncoding(HTTP.UTF_8);
			post.setEntity(form);
			post.addHeader("Accept", "application/json");
			
			HttpResponse response = httpClient.execute(post);
			return response.getStatusLine().getStatusCode();
			
		} catch (UnsupportedEncodingException e) {
			Log.d("CloudFinanceService", "Pau", e);
		} catch (ClientProtocolException e) {
			Log.d("CloudFinanceService", "Pau", e);
		} catch (IOException e) {
			Log.d("CloudFinanceService", "Pau", e);
		}
		return null;
	}

}
