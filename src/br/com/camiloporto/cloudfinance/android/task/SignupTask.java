package br.com.camiloporto.cloudfinance.android.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import android.os.Bundle;
import android.util.Log;
import br.com.camiloporto.cloudfinance.android.service.CloudfinanceResultReceiver.Receiver;

public class SignupTask extends AbstractCloudFinanceRestfullTask<String, Void, String> {


	public SignupTask(Receiver receiver) {
		super(receiver);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		Bundle resultData = new Bundle();
		resultData.putString("json", result);
		getReceiver().onReceiveResult(0, resultData);
	}

	@Override
	protected String parseRequestResponse(HttpResponse response) {
		HttpEntity entity = response.getEntity();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String result = null;
		try {
			entity.writeTo(out);
			result = new String(out.toByteArray());
		} catch (IOException e) {
			catchRequestException(e);
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				Log.d("IO", "duh!", e);
			}
		}
		return result;
	}

	@Override
	protected HttpUriRequest prepareHttpRequest(String[] params) throws UnsupportedEncodingException {
		String userName = params[0];
		String pass = params[1];
		String confirmPass = params[2];
		
		HttpPost post = new HttpPost(getRestfullServiceHost() + "/user/signup");
		List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		postParams.add(new BasicNameValuePair("userName", userName));
		postParams.add(new BasicNameValuePair("pass", pass));
		postParams.add(new BasicNameValuePair("confirmPass", confirmPass));
		UrlEncodedFormEntity form = new UrlEncodedFormEntity(postParams);
		form.setContentEncoding(HTTP.UTF_8);
		post.setEntity(form);
		post.addHeader("Accept", "application/json");
			
		return post;
	}


	

}
