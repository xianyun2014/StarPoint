package com.example.game;

import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BuildUpdataView{
	private GameData sd;
	
	private static BuildUpdataView v = null;
	
	private RelativeLayout view_layout;
	private TextView txt_title;
	private TextView txt_cur_level;
	private TextView txt_yield_sec;
	private TextView txt_yield_total;
	private TextView txt_next_add;
	private TextView txt_build_info;
	private TextView txt_cost_updata;

	public GameData.building cur_select = GameData.building.MZ;
	
	private BuildUpdataView(View view)
	{
		sd = GameData.CreateData();
		view_layout = (RelativeLayout) view.findViewById(R.id.build_info);
		
		txt_title = (TextView) view.findViewById(R.id.build_name);
		txt_cur_level = (TextView) view.findViewById(R.id.txt_Level);
		txt_yield_sec = (TextView) view.findViewById(R.id.txt_yield_sec);
		txt_yield_total = (TextView) view.findViewById(R.id.txt_yield_total);
		txt_next_add = (TextView) view.findViewById(R.id.txt_next_add);
		txt_build_info = (TextView) view.findViewById(R.id.txt_info);
		txt_cost_updata = (TextView) view.findViewById(R.id.txt_cost);
		
        //view_layout.setVisibility(4); //����
	}
	public static BuildUpdataView CreateView(View view)
	{
		if (null == v && null != view)
		{
			v = new BuildUpdataView(view);
		}
		return v;
	}

	public void setBuild(GameData.building b)
	{
		UIupdata(b);
		view_layout.setVisibility(0);//show
		cur_select = b;
	}
	
	public void UIupdata(GameData.building b)
	{
		txt_title.setText(sd.get_build_name(b));
		txt_cur_level.setText(sd.get_build_level(b));
		txt_yield_sec.setText(sd.StrPreOper(sd.get_yield_sec(b)));
		txt_yield_total.setText(sd.StrPreOper(sd.get_yield_total(b)));
		txt_build_info.setText(sd.get_build_info(b));
		txt_next_add.setText(sd.StrPreOper(sd.get_build_next_add(b)));
		txt_cost_updata.setText("��������:" + sd.StrPreOper(sd.get_build_updata_cost(b)));
	}
	public void hide()
	{
		view_layout.setVisibility(4);
	}
}
