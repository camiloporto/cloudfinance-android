package br.com.camiloporto.cloudfinance.android;

import br.com.camiloporto.cloudfinance.android.service.CloudfinanceResultReceiver.Receiver;
import br.com.camiloporto.cloudfinance.android.task.LoginTask;
import br.com.camiloporto.cloudfinance.android.task.SignupTask;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends Activity implements Receiver {
	
	
	private OnClickListener submitEventHandler = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			EditText emailInput = (EditText) findViewById(R.id.signup_login_input);
			EditText passInput = (EditText) findViewById(R.id.signup_pass_input);
			EditText confirmPassInput = (EditText) findViewById(R.id.signup_confirm_pass_input);
			SignupTask signupTask = new SignupTask(SignupActivity.this);
			signupTask.execute(
					emailInput.getText().toString(),
					passInput.getText().toString(),
					confirmPassInput.getText().toString()
				);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		Button signup = (Button) findViewById(R.id.signup_button);
		signup.setOnClickListener(submitEventHandler);
		// Show the Up button in the action bar.
		setupActionBar();
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onReceiveResult(int resultCode, Bundle resultData) {
		String json = resultData.getString("json");
		Log.d("SignupActivity", "json:\n" + json);
		Toast toast = Toast.makeText(this, "SignupTask concluida", Toast.LENGTH_LONG);
		toast.show();
		//FIXME navigate to AccountSystems screen if success
	}

}
