package com.xianyun.game;

import android.view.View;
import android.widget.TextView;

public class ViewStar_Count {
	private static ViewStar_Count v = null;
	
	private TextView txt_cur_star;
	private TextView txt_sec_star;
	private TextView txt_max_star;
	private TextView txt_total_star;
	private TextView txt_click_yield;
	private TextView txt_click_num;
	private TextView txt_click_star;
	private TextView txt_up_build;
	private TextView txt_achieve;

	private ViewStar_Count(View v)
	{
		txt_cur_star = (TextView) v.findViewById(R.id.count_txt_cur_star);
		txt_sec_star = (TextView) v.findViewById(R.id.count_txt_sec_star);
		txt_max_star = (TextView) v.findViewById(R.id.count_txt_max_star);
		txt_total_star = (TextView) v.findViewById(R.id.count_txt_total_star);
		txt_click_yield = (TextView) v.findViewById(R.id.count_txt_click_yield);
		txt_click_num = (TextView) v.findViewById(R.id.count_txt_click_num);
		txt_click_star = (TextView) v.findViewById(R.id.count_txt_click_star);
		txt_up_build = (TextView) v.findViewById(R.id.count_txt_up_build);
		txt_achieve = (TextView) v.findViewById(R.id.count_txt_achieve);
	}
	public static ViewStar_Count Get_View(View fv)
	{
		if (v == null)
		{
			v = new ViewStar_Count(fv);
		}
		return v;
	}
	public void UIupdata()
	{
		txt_cur_star.setText(GameData.GetData().get_star());
		txt_sec_star.setText(GameData.GetData().get_star_sec());
		txt_max_star.setText(GameData.GetData().get_star_history_max());
		txt_total_star.setText(GameData.GetData().get_star_history_total());
		txt_click_yield.setText(GameData.GetData().get_click_yield());
		txt_click_num.setText(GameData.GetData().get_click_num());
		txt_click_star.setText(GameData.GetData().get_click_total());
		txt_up_build.setText(GameData.GetData().get_updata_num());
        txt_achieve.setText(GameData.GetData().get_achieve_num());
	}
}
