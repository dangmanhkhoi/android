package com.example.dmpadmin2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DmpMenuAdapter extends BaseAdapter
{
	private Context mContext;
	private MyMenu[] data;
	private int mResource = R.layout.menu_main_row;
	
	public DmpMenuAdapter(Context c, MyMenu[] data)
	{
		this.mContext 	= c;
		this.data		= data;
	}

	public int getCount()
	{
		// TODO Auto-generated method stub
		return data.length;
	}

	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return data[position];
	}

	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
			convertView = inflater.inflate(mResource, parent, false);
			convertView.setTag(convertView);
		}
		else
			convertView = (View) convertView.getTag();
		
		
		MyMenu myMenu = data[position];
		
		if(myMenu.icon != 0)
		{
			ImageView icon	= (ImageView) convertView.findViewById(R.id.icon);
			icon.setImageResource(myMenu.icon);
		}
		
		TextView  title = (TextView) convertView.findViewById(R.id.title);
		
		
		
		title.setText(myMenu.title);
		
		
		return convertView;
	}
	
	

}
