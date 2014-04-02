package br.com.camiloporto.cloudfinance.android.service;

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

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

public class CloudfinanceService extends IntentService {

	public CloudfinanceService() {
		super("CloudFinanceService");
	}

	@Override
	protected void onHandleIntent(Intent i) {
		ResultReceiver receiver = i.getParcelableExtra("receiver");
		String command = i.getStringExtra("command");
		String userName = i.getStringExtra("userName");
		String pass = i.getStringExtra("pass");
		
		String jsonResult = sendLoginRequest(userName, pass);
		
		Log.d("CloudFinanceService", "requesting rest login... " + userName + "#" +pass);
		
		Bundle resultData = new Bundle();
		resultData.putString("json", jsonResult);
		receiver.send(0, resultData);
	}

	private String sendLoginRequest(String userName, String pass) {
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
			return Integer.toString(response.getStatusLine().getStatusCode());
			
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
