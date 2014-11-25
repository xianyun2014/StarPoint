package com.example.game;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ViewStar_Updata{
	private static ViewStar_Updata v = null;
	private boolean is_show;
	
	private RelativeLayout view_layout;
	private TextView txt_title;
	private TextView txt_cur_level;
	private TextView txt_yield_sec;
	private TextView txt_yield_total;
	private TextView txt_next_add;
	private TextView txt_build_info;
	private TextView txt_cost_updata;
	
	private GameData.building cur_select = GameData.building.MZ;
	
	private ViewStar_Updata(View view)
	{
		view_layout = (RelativeLayout) view;
		txt_title = (TextView) view.findViewById(R.id.build_name);
		txt_cur_level = (TextView) view.findViewById(R.id.up_txt_Level);
		txt_yield_sec = (TextView) view.findViewById(R.id.up_txt_yield_sec);
		txt_yield_total = (TextView) view.findViewById(R.id.up_txt_yield_total);
		txt_next_add = (TextView) view.findViewById(R.id.up_txt_next_add);
		txt_build_info = (TextView) view.findViewById(R.id.txt_info);
		txt_cost_updata = (TextView) view.findViewById(R.id.up_txt_cost);
		view_layout.setVisibility(View.GONE); //Òþ²Ø
		is_show = false;
	}
	public static ViewStar_Updata GetView(View view)
	{
		if (null == v && null != view)
		{
			v = new ViewStar_Updata(view);
		}
		return v;
	}

	public void setBuild(GameData.building b)
	{
		cur_select = b;
		UIupdata();
		view_layout.setVisibility(View.VISIBLE);//show
		is_show = true;
	}
	
	public void UIupdata()
	{
		txt_title.setText(GameData.GetData().get_build_name(cur_select));
		txt_cur_level.setText(GameData.GetData().get_build_level(cur_select));
		txt_yield_sec.setText(GameData.GetData().get_build_yield_sec(cur_select));
		txt_yield_total.setText(GameData.GetData().get_build_yield_total(cur_select));
		txt_build_info.setText(GameData.GetData().get_build_info(cur_select));
		txt_next_add.setText(GameData.GetData().get_build_next_add(cur_select));
		txt_cost_updata.setText("Éý¼¶»¨·Ñ:" + GameData.GetData().get_build_updata_cost(cur_select));
	}
	public GameData.building getCurrentSelect()
	{
		return cur_select;
	}
	public boolean isShow()
	{
		return is_show;
	}
	public void hide()
	{
		view_layout.setVisibility(View.GONE);
		is_show = false;
	}
}
