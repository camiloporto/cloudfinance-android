package br.com.camiloporto.cloudfinance.android;

import org.apache.http.HttpStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import br.com.camiloporto.cloudfinance.android.service.CloudfinanceResultReceiver;
import br.com.camiloporto.cloudfinance.android.service.CloudfinanceResultReceiver.Receiver;
import br.com.camiloporto.cloudfinance.android.task.LoginTask;

public class HomeActivity extends Activity implements Receiver {
	
	private CloudfinanceResultReceiver resultReceiver;

	private OnClickListener signupClickLstener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(getApplicationContext(), SignupActivity.class);
			startActivity(i);
		}
	};
	
	private OnClickListener loginListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText emailInput = (EditText) findViewById(R.id.home_login);
			EditText passInput = (EditText) findViewById(R.id.home_pass);
			LoginTask loginTask = new LoginTask(HomeActivity.this);
			loginTask.execute(
					emailInput.getText().toString(), 
					passInput.getText().toString());
			
		}
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		registEventListeners();
		resultReceiver = new CloudfinanceResultReceiver(new Handler());
		resultReceiver.setReceiver(this);
	}
	

	private void registEventListeners() {
		registSignupEventHandler();
		registerLoginEventHandler();
	}


	private void registerLoginEventHandler() {
		Button loginButton = (Button) findViewById(R.id.home_login_button);
		loginButton.setOnClickListener(loginListener);
	}


	private void registSignupEventHandler() {
		TextView textView = (TextView) findViewById(R.id.home_newuser_link);
		textView.setOnClickListener(signupClickLstener);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}


	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		Integer httpStatusReturned = resultData.getInt(LoginTask.LOGIN_HTTP_STATUS_CODE_TAG);
		Toast toast = Toast.makeText(this, "Falha ao realizar login", Toast.LENGTH_LONG);
		if(HttpStatus.SC_UNAUTHORIZED == httpStatusReturned) {
			toast = Toast.makeText(this, "Usuário/Senha não autorizado", Toast.LENGTH_LONG);
		} else if(HttpStatus.SC_OK== httpStatusReturned) {
			toast = Toast.makeText(this, "Login Sucesso!", Toast.LENGTH_LONG);
			Intent i = new Intent(this, AccountSystemActivity.class);
			startActivity(i);
			//FIXME send account system list to activity to display
		}
		toast.show();
	}

}
