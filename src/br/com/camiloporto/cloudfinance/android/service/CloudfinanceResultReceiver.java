package br.com.camiloporto.cloudfinance.android.service;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

public class CloudfinanceResultReceiver extends ResultReceiver {
	
	private Receiver receiver;

	public CloudfinanceResultReceiver(Handler handler) {
		super(handler);
	}
	
	public interface Receiver {
		public void onReceiveResult(int resultCode, Bundle resultData);
	}
	
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}
	
	@Override
	protected void onReceiveResult(int resultCode, Bundle resultData) {
		if(receiver != null) {
			receiver.onReceiveResult(resultCode, resultData);
		}
	}

}
