/*
 * ˫��˵�����
 * @author:  ����Ұ��
 * @qq:      836663997
 * @blog:    blog.csdn.net/xianyun2009
 * @create:  2014-11-21
 * @�ο�                       ����csdnר��
 */

package com.example.game;

import android.content.Context;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class SlidingLayout extends RelativeLayout {
	private View leftLayout, contentLayout, rightLayout;
	private int screenWidth, rightWidth = 120, leftWidth = 320;
	
	private MarginLayoutParams leftLayoutParams;
	private MarginLayoutParams contentLayoutParams;
    private MarginLayoutParams rightLayoutParams;  
    //ת��״̬
    private final int SHOW_LEFT = 0, HIDE_LEFT = 1, SHOW_RIGHT = 2, HIDE_RIGHT = 3, NULL_STATE = -1;
    //״̬���
    private int STATE;
    //��ǰ��ʾ״��
    private boolean isLeftShow = false, isRightShow = false, isContentShow = true;
    
    private float x_down, x_move, x_up;
    
    //���ڼ��������ٶ�
    private VelocityTracker velocity = null;
    //��ҳ����С�ٶ�
    private final int VELOCITY_MIN = 200;
    
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
            
            rightLayout = getChildAt(2);
            rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();
            rightLayoutParams.width = rightWidth;
            rightLayoutParams.rightMargin = -rightWidth;
            rightLayout.setLayoutParams(rightLayoutParams);
        }
    }
	//��õ�ǰ״̬
	private int getState(int d)
	{
		if (d > 0)
		{
			if (isContentShow)
				return SHOW_LEFT;
			else if (isRightShow)
				return HIDE_RIGHT;
		}
		else if (d < 0)
		{
			if (isContentShow)
				return SHOW_RIGHT;
			else if (isLeftShow)
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
				fixContent(true);

				break;
			case SHOW_RIGHT:
			case HIDE_RIGHT:
				setRightWidth(DisX);
				fixContent(false);
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
			case SHOW_RIGHT:
				if (shouldShowRight())
					showRight();
				else
					hideRight();
				break;
			case HIDE_RIGHT:
				if (shouldHideRight())
					hideRight();
				else
					showRight();
				break;
			}
			recycleTracker();
			break;
		}
		return true;
	}
	//��¼ÿ�λ���
	private void createTracker(MotionEvent e)
	{
		if (velocity == null)
			velocity = VelocityTracker.obtain();
		velocity.addMovement(e);
	}
	//��û����ٶ�(����/s)
	private int getVelocity()
	{
		velocity.computeCurrentVelocity(1000);
		int v = (int) velocity.getXVelocity();
		return Math.abs(v);
	}
	//����
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
	private boolean shouldShowRight()
	{
		return getVelocity() > VELOCITY_MIN || x_down - x_up > rightWidth / 2;
	}
	private boolean shouldHideRight()
	{
		return getVelocity() > VELOCITY_MIN || x_up - x_down > rightWidth / 2;
	}
	
	//�����Ҳ�layout��ʾ���ȣ� ������ʾ���������ջ�
	private boolean setRightWidth(int w)
	{
		if (w > 0)
			rightLayoutParams.rightMargin = -w;
		else
			rightLayoutParams.rightMargin = -(rightWidth + w);
		
		if (rightLayoutParams.rightMargin < -rightWidth)
			rightLayoutParams.rightMargin = -rightWidth;
		else if (rightLayoutParams.rightMargin > 0)
			rightLayoutParams.rightMargin = 0;
		rightLayout.setLayoutParams(rightLayoutParams);
		
		return rightLayoutParams.rightMargin == 0 || rightLayoutParams.rightMargin == rightWidth;
	}
	//�������layout��ʾ���ȣ�������ʾ���������ջ�
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
	//�������ݲ㣬 ����isleft ��ʾ�Ƿ���������������
	private void fixContent(boolean isLeft)
	{
		if (isLeft){
			contentLayoutParams.leftMargin = leftLayoutParams.leftMargin + leftWidth;
			contentLayoutParams.rightMargin = -contentLayoutParams.leftMargin;
		}
		else {
			contentLayoutParams.rightMargin = rightLayoutParams.rightMargin + rightWidth;
        	contentLayoutParams.leftMargin = -contentLayoutParams.rightMargin;
		}
    	contentLayout.setLayoutParams(contentLayoutParams);
	}
	//������ʾ״̬�л�
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
	public void showRight()
	{
		new ScrollTaskRight().execute(30);
		isRightShow = true;
		isContentShow = false;
	}
	public void hideRight()
	{
		new ScrollTaskRight().execute(-30);
		isRightShow = false;
		isContentShow = true;
	}
	//���������Զ���ʾ������
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
        	fixContent(true);
        }
    }
	//�����Ҳ���Զ���ʾ������
	class ScrollTaskRight extends AsyncTask<Integer, Integer, Integer> {
        @Override  
        protected Integer doInBackground(Integer... speed) {
            int rightMargin = rightLayoutParams.rightMargin;
            
            while (true) {
            	rightMargin += speed[0];
            	if (speed[0] > 0){
            		if (rightMargin > 0){
            			rightMargin = 0;
            			break;
            		}
            	}
            	else {
            		if (rightMargin < -rightWidth){
	                	rightMargin = -rightWidth;
	                    break;
            		}
				}
            	publishProgress(rightMargin);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) { }
            }
            publishProgress(rightMargin);
            return rightMargin;
        }
        @Override
        protected void onProgressUpdate(Integer... rightMargin) {
        	rightLayoutParams.rightMargin = rightMargin[0];
        	rightLayout.setLayoutParams(rightLayoutParams);
        	fixContent(false);
        }
    }
}