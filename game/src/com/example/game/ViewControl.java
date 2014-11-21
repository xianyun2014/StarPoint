package com.example.game;

import android.view.View;

public class ViewControl {
	private SlidingLayout view_sliding;
	private ViewStar_Frame view_star;
	private ViewStar_Count view_count;
	private ViewStar_Updata view_updata;
	
	public ViewControl(View v)
	{
		view_sliding = (SlidingLayout) v;
		View vgs = (View) v.findViewById(R.id.layout_game_star);
        view_star = ViewStar_Frame.GetView(vgs);
        View vup = (View) vgs.findViewById(R.id.view_build_updata);
        view_updata = ViewStar_Updata.GetView(vup);
        
        View vgsc = (View) v.findViewById(R.id.layout_game_star_count);
        view_count = ViewStar_Count.Get_View(vgsc);
	}
	
	public void UIupdata()
	{
		if (view_sliding.isShowLeft() || view_sliding.isSlidingToLeft())
		{
			view_count.UIupdata();
		}
		if (view_sliding.isShowContent())
		{
			view_star.UIupdata();
			if (view_updata.isShow())
			{
				view_updata.UIupdata(view_updata.getCurrentSelect());
			}
		}
	}
}
