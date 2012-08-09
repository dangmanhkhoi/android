package com.example.dmpadmin2;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
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

public class FormSetting extends Activity
{
	private RadioGroup rFullscreen;
	private RadioGroup rVideoLoop;
	private RadioButton rFullscreenSelected;
	private RadioButton rVideoLoopSelected;
	private EditText etXcordinate;
	private EditText etYcordinate;
	private EditText etWidth;
	private EditText etHeight;
	private EditText etMediaURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.setting_form);
		
		getLayoutInflater().inflate(R.layout.form_header, null);
		TextView titleBar = (TextView) findViewById(R.id.t_form_title_bar);
 
		Bundle extras = getIntent().getExtras();

		if (extras != null)
		{
			String value = extras.getString("formSelected");			 
			titleBar.setText(value);
		}		

		rVideoLoop 		= (RadioGroup) findViewById(R.id.r_video_loop);
		rFullscreen 	= (RadioGroup) findViewById(R.id.r_fullscreen);
		etMediaURL 		= (EditText) findViewById(R.id.e_media_url);
		etXcordinate	= (EditText) findViewById(R.id.et_x_coordinate);
		etYcordinate	= (EditText) findViewById(R.id.et_y_coordinate);
		etWidth 		= (EditText) findViewById(R.id.et_width);
		etHeight 		= (EditText) findViewById(R.id.et_height);
		
		rFullscreen.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				// TODO Auto-generated method stub

				TableLayout tLayout = (TableLayout) findViewById(R.id.table_layout_fullscreen);

				if (checkedId == R.id.r_fullscreen_yes)
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

						int fullScreenSelectedId = rFullscreen.getCheckedRadioButtonId();
						rFullscreenSelected 	 = (RadioButton) findViewById(fullScreenSelectedId);

						int videoLoopSelectedId = rVideoLoop.getCheckedRadioButtonId();
						rVideoLoopSelected 		= (RadioButton) findViewById(videoLoopSelectedId);

						String mediaURL = etMediaURL.getText().toString().trim();
						String xCoord 	= etXcordinate.getText().toString().trim();
						String yCoord 	= etYcordinate.getText().toString().trim();
						String width 	= etWidth.getText().toString().trim();
						String height 	= etHeight.getText().toString().trim();

						if (mediaURL == null || mediaURL.equals(""))
						{
							validation = false;
							showValidationError("Media URL");
						}
						else if (rVideoLoopSelected == null)
						{
							validation = false;
							showValidationError("Video Loop");
						}
						else if (rFullscreenSelected == null)
						{
							validation = false;
							showValidationError("Fullscreen");
						}
						else
						{
							if (rFullscreenSelected.getText().equals("Yes"))
							{									
								if (xCoord == null || xCoord.equals(""))
								{
									validation = false;
									showValidationError("X Coordinate");
									etXcordinate.requestFocus();
								}
								else if (yCoord == null || yCoord.equals(""))
								{
									validation = false;
									showValidationError("Y Coordinate");
									etYcordinate.requestFocus();
								}
								else if (width == null || width.equals(""))
								{
									validation = false;
									showValidationError("Width");
									etWidth.requestFocus();
								}
								else if (height == null || height.isEmpty())
								{
									validation = false;
									showValidationError("Height");
									etHeight.requestFocus();
								}
							}
						}

						System.out.println("rVideoLoopSelected: "
								+ rVideoLoopSelected);
						// Toast.makeText(DmpForm.this,
						// rVideoLoopSelected.getText(),
						// Toast.LENGTH_SHORT).show();

						// System.out.println("FULLSCREEN: " +
						// rFullscreenSelected.getText().toString());
						// System.out.println("VideoLoop: " +
						// rFullscreenSelected.getText());
						
						
						if(validation == true)
						{							
							Toast.makeText(FormSetting.this, "Saving ....", Toast.LENGTH_SHORT).show();
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
		Toast.makeText(FormSetting.this, msg, Toast.LENGTH_SHORT).show();
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

}
