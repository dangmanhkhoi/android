package com.example.dmpadmin2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity
{
	private EditText etUsername;
	private EditText etPassword;
	private HttpProgressLogin mHttpProgressLogin;
	private TextView tvMsg;
	private SharedPreferences settings;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		etUsername = (EditText) findViewById(R.id.et_username);
		etPassword = (EditText) findViewById(R.id.et_password);
		tvMsg = (TextView) findViewById(R.id.tv_msg);

		buttonLogin();
		
		
		 settings = getSharedPreferences("DMP_LOGED_IN", 0);
		 SharedPreferences.Editor editor = settings.edit();
		 editor.putBoolean("dmp_loged_in", false);
		 editor.commit();
	}
	 
	private void buttonLogin()
	{
		((Button) findViewById(R.id.b_login))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						String username = etUsername.getText().toString();
						String password = etPassword.getText().toString();

						if (username.trim().equals(""))
							tvMsg.setText("Username is a required field");
						else if (password.trim().equals(""))
							tvMsg.setText("Password is a required field");
						else
						{

							String url = "http://khoidang.comasdfdasf/android/check_username.php?username="
									+ username + "&password=" + password;
							// System.out.println("URL: " + url);
							mHttpProgressLogin = new HttpProgressLogin();

							mHttpProgressLogin.execute(url);
						}

					}

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	protected class HttpProgressLogin extends
			AsyncTask<String, Integer, String>
	{
		ProgressDialog pd = null;

		@Override
		protected void onPreExecute()
		{
			pd = ProgressDialog.show(MainActivity.this, "", "Loading...");
			tvMsg.setText("");
		}

		@Override
		protected String doInBackground(String... params)
		{
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(params[0]);
			try
			{
				HttpResponse response = client.execute(request);
				return HttpHelper.request(response).toString();

			}
			catch (Exception ex)
			{
				return "Failed!" + ex.toString();
			}

		}

		protected void onPostExecute(String result)
		{
			super.onPostExecute(result);

			if (result.trim().equals("true"))
			{
				
				SharedPreferences.Editor editor = settings.edit();
				editor.putBoolean("dmp_loged_in", true);
				editor.commit();
				
				Intent intent = new Intent(getBaseContext(), MainMenu.class);
				startActivity(intent);
				overridePendingTransition(R.anim.slide_in_up,
						R.anim.slide_out_up);
				finish();
			}
			else
				tvMsg.setText("Invalid Username/Password");

			pd.cancel();

		}

	}
}
