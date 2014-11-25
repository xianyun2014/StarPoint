package com.xianyun.game;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class ViewControl {
	private static ViewControl vc = null;
	private SlidingLayout view_sliding;
	private ViewStar_Frame view_star;
	private ViewStar_Count view_count;
	private ViewStar_Updata view_updata;
	
	private ViewAchieve_Show view_achieve;
	
	private View vgs;

    private boolean is_Show_Achieve = false;
    private String achieve_name, achieve_info;

	private ViewControl(View v)
	{
		View va = (View) v.findViewById(R.id.layout_game_achieve_show);
		view_achieve = ViewAchieve_Show.Get_ViewAchieve_Show(va);
		
		view_sliding = (SlidingLayout) v;
		vgs = (View) v.findViewById(R.id.layout_game_star);
        view_star = ViewStar_Frame.GetView(vgs);
        View vup = (View) vgs.findViewById(R.id.view_build_updata);
        view_updata = ViewStar_Updata.GetView(vup);
        
        View vgsc = (View) v.findViewById(R.id.layout_game_star_count);
        view_count = ViewStar_Count.Get_View(vgsc);
	}
	public static ViewControl GetViewControl(View v)
	{
		if (null == vc)
		{
			vc = new ViewControl(v);
		}
		return vc;
	}
	public void UIALLupdata()
	{
		view_count.UIupdata();
		view_star.UIupdata();
		view_updata.UIupdata();
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
				view_updata.UIupdata();
			}
		}
        if (is_Show_Achieve){
            view_achieve.set_achieve(achieve_name, achieve_info);
        }
	}
	public void StartViewAchieve(String name, String info)
	{
        achieve_name = name;
        achieve_info = info;
        is_Show_Achieve = true;
	}
}
