package com.gz.learnweb.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


import com.androidquery.AQuery;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gz.learnweb.listener.OnTabSelectedListener;
import com.nineoldandroids.animation.Animator;
import com.gz.learnweb.R;

import java.util.List;

/**
 * 多处用到的两个Tab的布局
 */
public class TwoPagerTabWidget extends PagerTabWidget {

    public TwoPagerTabWidget(Context context) {
        super(context);
    }

    public TwoPagerTabWidget(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TwoPagerTabWidget(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(Context context) {
        super.init(context);
        //TODO
        setDividerInvisible();
        View tab_first = LayoutInflater.from(mContext).inflate(R.layout.two_tab_content, null);
        View tab_second = LayoutInflater.from(mContext).inflate(R.layout.two_tab_content, null);
        new AQuery(tab_first).id(R.id.two_tab_bottom).visibility(VISIBLE)
                .id(R.id.two_tab_text).textColorId(R.color.light_green);
        new AQuery(tab_second).id(R.id.two_tab_bottom).visibility(GONE)
                .id(R.id.two_tab_text).textColorId(R.color.light_grey);
        addTab(tab_first);
        addTab(tab_second);
        mOnTabSelectedListener = new OnTabSelectedListener() {
            @Override
            public void onSelected(final List<View> tabViews, int position) {
                int unselected = (position == 0) ? 1 : 0;
                new AQuery(tabViews.get(position)).id(R.id.two_tab_bottom).visibility(VISIBLE)
                        .id(R.id.two_tab_text).textColorId(R.color.light_green);
                new AQuery(tabViews.get(unselected)).id(R.id.two_tab_text).textColorId(R.color.light_grey);

                Techniques firstAction = (position == 0) ? Techniques.SlideInRight : Techniques.SlideOutRight;
                Techniques secondAction = (position == 0) ? Techniques.SlideOutLeft : Techniques.SlideInLeft;

                YoYo.with(firstAction).duration(300).playOn(tabViews.get(0).findViewById(R.id.two_tab_bottom));
                YoYo.with(secondAction).duration(300).withListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        //new AQuery(tabViews.get(gone)).id(R.id.two_tab_bottom).visibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                }).playOn(tabViews.get(1).findViewById(R.id.two_tab_bottom));

            }
        };
    }

    @Override
    public void setmOnTabSelectedListener(OnTabSelectedListener mOnTabSelectedListener) {
        //do nothing
    }


    public void setText_first_tab(String text_first_tab) {
        new AQuery(tabViews.get(0)).id(R.id.two_tab_text).text(text_first_tab);
    }


    public void setText_second_tab(String text_second_tab) {
        new AQuery(tabViews.get(1)).id(R.id.two_tab_text).text(text_second_tab);
    }

}
