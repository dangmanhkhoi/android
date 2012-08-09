package com.example.dmpadmin2;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainMenu extends Activity
{
	public static final int logout_menu = Menu.FIRST + 1;
	private ListView listViewMain; 
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
		
		//Main Menu
	    final MyMenu mainMenu[] = new MyMenu[]
	    {
	    		new MyMenu(R.drawable.settings, "Settings"),
	    		new MyMenu(R.drawable.display, "Display Actions"),
	    		new MyMenu(R.drawable.admin, "Administration"),
	    		new MyMenu(R.drawable.about, "About DMP")        		        		
	    };
	    
		listViewMain = (ListView) findViewById(R.id.main_category);
		listViewMain.setAdapter(new DmpMenuAdapter(this, mainMenu));
		
		listViewMain.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent(getBaseContext(), SubMenu.class);
				
				String menuSelected = mainMenu[position].title;
				intent.putExtra("menuSelected", menuSelected);				
				startActivity(intent);
				overridePendingTransition( R.anim.slide_in_left, R.anim.slide_out_left );
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
