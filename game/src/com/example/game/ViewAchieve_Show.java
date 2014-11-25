package com.example.game;

import android.view.View;
import android.widget.TextView;

public class ViewAchieve_Show {
	private static ViewAchieve_Show vs = null;
	
	private TextView txt_achieve_name;
	private TextView txt_achieve_info;
	private ViewAchieve_Show(View v)
	{
		txt_achieve_name = (TextView) v.findViewById(R.id.txt_achieve_name);
		txt_achieve_info = (TextView) v.findViewById(R.id.txt_achieve_info);
	}
	public static ViewAchieve_Show Get_ViewAchieve_Show(View v)
	{
		if (null == vs)
		{
			vs = new ViewAchieve_Show(v);
		}
		return vs;
	}
}
