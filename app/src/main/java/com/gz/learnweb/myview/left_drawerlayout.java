package com.gz.learnweb.myview;

import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

/**
 * Created by host on 2015/8/9.
 */
public class left_drawerlayout extends DrawerLayout {
    //手指划动用
    double down_x=0,down_y=0,up_x=0,up_y=0;
    public left_drawerlayout(Context context) {
        super(context);
    }

    public left_drawerlayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public left_drawerlayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            down_x = event.getX();
            down_y = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            up_x=event.getX();
            up_y=event.getY();
            if(down_x-up_x > 150) {
                down_x=0;
                down_y=0;
                up_x=0;
                up_y=0;
                this.closeDrawer(Gravity.LEFT);
            } else if(up_x - down_x > 150) {
                down_x=0;
                down_y=0;
                up_x=0;
                up_y=0;
                this.openDrawer(Gravity.LEFT);
            }
        }
        return super.onTouchEvent(event);
    }
}
