package com.gz.learnweb.activity;



import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.gz.learnweb.Constant;
import com.gz.learnweb.R;
import com.gz.learnweb.entire.PopularizationCourse;
import com.gz.learnweb.myview.CircleIndicators;

public class ShowPopuActivity extends BasePageActivity {
    private AQuery aq;
    private PopularizationCourse course;
    private ViewPager viewPager;
    private CircleIndicators circleIndicators;
    @Override
    protected void initData() {
        if(mBundle!=null&& mBundle.getSerializable(Constant.DataKey.POPULARIZATION)!=null){
            course=(PopularizationCourse)mBundle.getSerializable(Constant.DataKey.POPULARIZATION);
        }
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_show_popu);
        aq=new AQuery(this);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        circleIndicators=(CircleIndicators)findViewById(R.id.circle_indicators);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text(course.getTitle());
        aq.id(R.id.title_left_img).visible();
//        aq.id(R.id.title_left_tv).visible().text("退出");
        viewPager.setAdapter(new ShowPopuPagerAdapter());
        circleIndicators.setViewPager(viewPager);
    }

    @Override
    protected void setListener() {
        aq.id(R.id.title_left_btn).clicked(this,"finish");
    }




    /**作为viewpager的adapter
     *
     *
     *
     */
    class ShowPopuPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return course.getImages().size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            LayoutInflater inflatet=LayoutInflater.from(container.getContext());
            View view=inflatet.inflate(R.layout.adapter_show_pop, container, false);
            final AQuery aq=new AQuery(view);
            aq.id(R.id.show_pop_img).image(course.getImages().get(position),true,true,0,0,new BitmapAjaxCallback() {
                @Override
                protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                    super.callback(url, iv, bm, status);
                    aq.id(R.id.progressBar1).gone();
                }
            });
//            ImageView imageView = new ImageView(container.getContext());
//            RelativeLayout relativeLayout=new RelativeLayout(container.getContext());
//            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT));
//
//
//            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            imageView.setLayoutParams(new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
//                    RelativeLayout.LayoutParams.MATCH_PARENT));
//
//            new AQuery(imageView).image(course.getImages().get(position),true,true,0,0,new BitmapAjaxCallback(){
//                @Override
//                protected void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
//                    super.callback(url,iv,bm,status);
//
//                }
//            });
            container.addView(view);

            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
