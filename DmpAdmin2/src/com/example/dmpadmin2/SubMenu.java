package com.example.dmpadmin2;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class SubMenu extends Activity
{
	private MyMenu[] listSubMenu;
	public static final int logout_menu = Menu.FIRST + 1;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_menu_main);

		// Depend on menu selected, get XML menu
		Bundle extras = getIntent().getExtras();

		if (extras != null)
		{
			String menuSelected = extras.getString("menuSelected");

			TextView titleBar = (TextView) findViewById(R.id.title_bar_text);
			titleBar.setText(menuSelected);

			listSubMenu = getListSubMenu(menuSelected);

			ListView listView = (ListView) findViewById(R.id.sub_category);

			listView.setAdapter(new DmpMenuAdapter(this, listSubMenu));

			listView.setOnItemClickListener(new OnItemClickListener()
			{
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id)
				{

					String formSelected = listSubMenu[position].title;

					Intent intent = selectForm(formSelected);

					intent.putExtra("formSelected", formSelected);

					startActivity(intent);

					overridePendingTransition(R.anim.slide_in_up,
							R.anim.slide_out_up);

				}
			});

		}

		((Button) findViewById(R.id.back_to_main))
				.setOnClickListener(new OnClickListener()
				{
					public void onClick(View v)
					{
						goBack();
					}
				});

	}

	
	
	@Override
	protected void onStart()
	{
		// TODO Auto-generated method stub
		super.onStart();
		
		SharedPreferences settings = getSharedPreferences("DMP_LOGED_IN", 0);
		
		boolean isLogedIn = settings.getBoolean("dmp_loged_in", false);
		
		//has not loged in
		if(!isLogedIn)
			doLogout();


	}



	private Intent selectForm(String formName)
	{
		Intent intent = new Intent(getBaseContext(), FormSetting.class); // default

		if (formName.equals("Settings"))
			intent = new Intent(getBaseContext(), FormSetting.class);
		else if (formName.equals("Failover"))
			intent = new Intent(getBaseContext(), FormFailover.class);

		return intent;
	}

	private MyMenu[] getListSubMenu(String menuSelected)
	{

		int source = R.xml.menu_setting;

		if (menuSelected.equals("Settings"))
			source = R.xml.menu_setting;
		else if (menuSelected.equals("Display Actions"))
			source = R.xml.menu_display_action;
		else if (menuSelected.equals("Administration"))
			source = R.xml.menu_administration;
		else if (menuSelected.equals("About DMP"))
			source = R.xml.menu_about_dmp;

		ArrayList<String> listSubMenu = prepareListFromXml(source);

		MyMenu myMenu[] = new MyMenu[listSubMenu.size()];

		for (int i = 0; i < listSubMenu.size(); i++)
			myMenu[i] = new MyMenu(0, listSubMenu.get(i));

		return myMenu;

	}

	private ArrayList<String> prepareListFromXml(int source)
	{
		ArrayList<String> subMenus = new ArrayList<String>();

		XmlResourceParser subMenuXml = getResources().getXml(source);

		int eventType = -1;

		while (eventType != XmlResourceParser.END_DOCUMENT)
		{
			if (eventType == XmlResourceParser.START_TAG)
			{
				String strNode = subMenuXml.getName();

				if (strNode.equals("item"))
				{
					subMenus.add(subMenuXml.getAttributeValue(null, "title"));
				}

			}

			try
			{
				eventType = subMenuXml.next();
			}
			catch (XmlPullParserException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}

		return subMenus;

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

	private void doLogout()
	{
		// Logout logic here...

		// Return to the login activity
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		
		
	}

	public boolean onOptionsItemSelected(MenuItem item)
	{
		doLogout();
		finish();
		
		return super.onOptionsItemSelected(item);
	}

	public boolean onCreateOptionsMenu(Menu menu)
	{
		boolean result = super.onCreateOptionsMenu(menu);
		menu.add(0, logout_menu, 0, "Logout");

		return result;

	}
}
