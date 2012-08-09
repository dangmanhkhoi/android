package com.example.dmpadmin2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FormFailover extends Activity
{
	public static final int logout_menu = Menu.FIRST + 1;
	private RadioGroup rEnableFailover;
	private RadioButton rEnableFailoverSelected;
	
	private EditText etFailoverTimeout;
	private EditText etFailoverFlashVariable;
	private EditText etFailoverURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.failover_form);
		
		getLayoutInflater().inflate(R.layout.form_header, null);
		TextView titleBar = (TextView) findViewById(R.id.t_form_title_bar);
 
		Bundle extras = getIntent().getExtras();

		if (extras != null)
		{
			String value = extras.getString("formSelected");			 
			titleBar.setText(value);
		}		

		 
		rEnableFailover = (RadioGroup) findViewById(R.id.r_enable_failover);
		etFailoverURL 	= (EditText) findViewById(R.id.et_failover_url);
		etFailoverTimeout		= (EditText) findViewById(R.id.et_failover_timeout);	
		etFailoverFlashVariable	= (EditText) findViewById(R.id.et_failover_flash_variable);
		
		 
		
		rEnableFailover.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub

				TableLayout tLayout = (TableLayout) findViewById(R.id.table_layout_enable_failover);

				if (checkedId == R.id.r_enable_failover_yes)
					tLayout.setVisibility(View.VISIBLE);
				else
					tLayout.setVisibility(View.GONE);
			}
		});
		
		
		// Click Save
		((Button) findViewById(R.id.b_save))
				.setOnClickListener(new OnClickListener()
				{

					public void onClick(View v)
					{
						boolean validation = true;

						int enableSelectedId 	= rEnableFailover.getCheckedRadioButtonId();
						rEnableFailoverSelected = (RadioButton) findViewById(enableSelectedId);

				 
						String failoverURL 		= etFailoverURL.getText().toString().trim();
						String failoverFlashVar = etFailoverFlashVariable.getText().toString().trim();
						String failoverTimeout 	= etFailoverTimeout.getText().toString().trim();
						
						

						
						if (rEnableFailoverSelected == null)
						{
							validation = false;
							showValidationError("Enable Failover");
						}
						else
						{
							if (rEnableFailoverSelected.getText().equals("Yes"))
							{									
								if (failoverURL == null || failoverURL.equals(""))
								{
									validation = false;
									showValidationError("Failover URL");
								}
								else if (failoverFlashVar == null || failoverFlashVar.equals(""))
								{
									validation = false;
									showValidationError("Failover Flash Variables");
									etFailoverFlashVariable.requestFocus();
								}
								else if (failoverTimeout == null || failoverTimeout.equals(""))
								{
									validation = false;
									showValidationError("Failover Timeout");
									etFailoverTimeout.requestFocus();
								}
								 
							}
						}

						 
						// Toast.makeText(DmpForm.this,
						// rVideoLoopSelected.getText(),
						// Toast.LENGTH_SHORT).show();

						// System.out.println("FULLSCREEN: " +
						// rFullscreenSelected.getText().toString());
						// System.out.println("VideoLoop: " +
						// rFullscreenSelected.getText());
						
						
						if(validation == true)
						{							
							Toast.makeText(FormFailover.this, "Saving ....", Toast.LENGTH_SHORT).show();
							finish();
							overridePendingTransition(R.anim.slide_in_down,R.anim.slide_out_down);
						}

					}

				});		
		
		// Click Cancel
		((Button) findViewById(R.id.b_cancel))
				.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v)
					{
						goBack();
					}
				});
		
	}
	
	
	private void showValidationError(String fieldName)
	{
		String msg = "\"" + fieldName + "\" is a required field";
		Toast.makeText(FormFailover.this, msg, Toast.LENGTH_SHORT).show();
	}
	private void goBack()
	{
		finish();
		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
	}
	
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event)
	{
		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			goBack();
			return true;
		}
		 
		
		return super.onKeyUp(keyCode, event);
	}
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		
		
		
		SharedPreferences settings = getSharedPreferences("DMP_LOGED_IN", 0);
		
		boolean isLogedIn = settings.getBoolean("dmp_loged_in", false);
		
		System.out.println("COME HERE: " + isLogedIn);
		
		//has not loged in
		if(!isLogedIn)
			doLogout();


	}
 
	
	private void doLogout()
	{
		// Logout logic here...

		// Return to the login activity
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
	
	 

	public boolean onOptionsItemSelected(MenuItem item)
	{
		doLogout();
		
		
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, logout_menu, 0, "Logout");

		return result;

	}
}
