/*
 * @author:  闲云野鹤
 * @qq:      836663997
 * @blog:    blog.csdn.net/xianyun2009
 * @create:  2014-11-21
 */

package com.xianyun.game;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SlidingLayout extends RelativeLayout {
	private View leftLayout, contentLayout;
	private int screenWidth, leftWidth = 400;
	
	private MarginLayoutParams leftLayoutParams;
	private MarginLayoutParams contentLayoutParams;
    //标记状态
    private final int SHOW_LEFT = 0, HIDE_LEFT = 1, NULL_STATE = -1;
    //状态
    private int STATE;
    //当前显示状态
    private boolean isLeftShow = false, isContentShow = true, isSliding = false;
    
    private float x_down, x_move, x_up;
    
    //计算速度
    private VelocityTracker velocity = null;
    //滑动最小速度
    private final int VELOCITY_MIN = 200;
    
	@SuppressWarnings("deprecation")
	public SlidingLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		screenWidth = wm.getDefaultDisplay().getWidth();
	}
	@Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            leftLayout = getChildAt(0);
            leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();
            leftLayoutParams.width = leftWidth;
            leftLayoutParams.leftMargin = -leftWidth;
            leftLayout.setLayoutParams(leftLayoutParams);
            
            contentLayout = getChildAt(1);
            contentLayoutParams = (MarginLayoutParams) contentLayout.getLayoutParams();
            contentLayoutParams.width = screenWidth;
            contentLayout.setLayoutParams(contentLayoutParams);
        }
    }
	public boolean isShowLeft()
	{
		return isLeftShow;
	}
	public boolean isShowContent()
	{
		return isContentShow;
	}
	
	public boolean isSlidingToLeft()
	{
		return isSliding && SHOW_LEFT == getState((int)(x_move - x_down));
	}

	//根据手势趋势获取状态
	private int getState(int d)
	{
		if (d > 0)
		{
			if (isContentShow)
				return SHOW_LEFT;
		}
		else if (d < 0)
		{
			if (isLeftShow)
				return HIDE_LEFT;
		}
		return NULL_STATE;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		createTracker(e);
		switch (e.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			x_down = (int)e.getRawX();
			isSliding = true;
			break;
		case MotionEvent.ACTION_MOVE:
			x_move = (int) e.getRawX();
			int DisX = (int)(x_move - x_down);
			STATE = getState(DisX);
			switch (STATE)
			{
			case SHOW_LEFT:
			case HIDE_LEFT:
				setLeftWidth(DisX);
				fixContent();

				break;
			}
			
			break;
		case MotionEvent.ACTION_UP:
			x_up = e.getRawX();
			STATE = getState((int)(x_up - x_down));
			switch (STATE)
			{
			case SHOW_LEFT:
				if (shouldShowLeft())
					showLeft();
				else
					hideLeft();
				break;
			case HIDE_LEFT:
				if (shouldHideLeft())
					hideLeft();
				else
					showLeft();
				break;
			}
			recycleTracker();
			isSliding = false;
			break;
		}
		return true;
	}
	//计算加速度
	private void createTracker(MotionEvent e)
	{
		if (velocity == null)
			velocity = VelocityTracker.obtain();
		velocity.addMovement(e);
	}
	//获取加速度（像素/秒）
	private int getVelocity()
	{
		velocity.computeCurrentVelocity(1000);
		int v = (int) velocity.getXVelocity();
		return Math.abs(v);
	}
	//回收
	private void recycleTracker()
	{
		velocity.recycle();
		velocity = null;
	}
	
	private boolean shouldShowLeft()
	{
		return getVelocity() > VELOCITY_MIN || x_up - x_down > leftWidth / 2;
	}
	private boolean shouldHideLeft()
	{
		return getVelocity() > VELOCITY_MIN || x_down - x_up > leftWidth / 2;
	}

	//设置左侧边应该显示的长度，正负表示拉出与收缩
	private boolean setLeftWidth(int w)
	{
		if (w > 0)
			leftLayoutParams.leftMargin = w - leftWidth;
		else
			leftLayoutParams.leftMargin = w;
		
		if (leftLayoutParams.leftMargin > 0)
			leftLayoutParams.leftMargin = 0;
		if (leftLayoutParams.leftMargin < -leftWidth)
			leftLayoutParams.leftMargin = -leftWidth;
		leftLayout.setLayoutParams(leftLayoutParams);
		
		return leftLayoutParams.leftMargin == 0 || leftLayoutParams.leftMargin == leftWidth;
	}
	//修正内容界面的宽度
	private void fixContent()
	{
		contentLayoutParams.leftMargin = leftLayoutParams.leftMargin + leftWidth;
        contentLayoutParams.rightMargin = -contentLayoutParams.leftMargin;
    	contentLayout.setLayoutParams(contentLayoutParams);
	}
	//显示内容面板
	public void showContent()
	{
		new ScrollTaskLeft().execute(-1000);
		isContentShow = true;
		isLeftShow = false;
	}
	public void showLeft()
	{
		new ScrollTaskLeft().execute(30);
		isLeftShow = true;
		isContentShow = false;
	}
	public void hideLeft()
	{
		new ScrollTaskLeft().execute(-30);
		isLeftShow = false;
		isContentShow = true;
	}

	//左侧边滑动动画
	class ScrollTaskLeft extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = leftLayoutParams.leftMargin;
            do {
            	leftMargin += speed[0];
            	if (speed[0] > 0)
            	{
            		if (leftMargin > 0)
            		{
            			leftMargin = 0;
            			break;
            		}
            	}
            	else {
            		if (leftMargin < - leftWidth)
            		{
            			leftMargin = -leftWidth;
            			break;
            		}
				}
                publishProgress(leftMargin);
                
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) { }
            }while (!(leftLayoutParams.leftMargin == 0 || leftLayoutParams.leftMargin == leftWidth));
            publishProgress(leftMargin);
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... leftMargin) {
        	leftLayoutParams.leftMargin = leftMargin[0];
        	leftLayout.setLayoutParams(leftLayoutParams);
        	fixContent();
        }
    }
}
