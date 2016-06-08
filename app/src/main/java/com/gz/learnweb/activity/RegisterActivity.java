package com.gz.learnweb.activity;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.androidquery.AQuery;
import com.gz.learnweb.Adapter.TabPagerAdapter;
import com.gz.learnweb.Constant;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.Utils.SpUtils;
import com.gz.learnweb.fragment.RegisterFragment;
import com.gz.learnweb.listener.OnTabSelectedListener;
import com.gz.learnweb.myview.TwoPagerTabWidget;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends BasePageActivity {
    private AQuery aq;
    private TwoPagerTabWidget mTabWidget;
    private ViewPager mViewPager;
    SpUtils spUtils;
    List<Fragment> fragments;


    @Override
    protected void initData() {
        spUtils=new SpUtils(this);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_registe_login);
        fragments=new ArrayList<>();
        fragments.add(RegisterFragment.newInstance(Constant.FragmentType.PhoneLogin));
        fragments.add(RegisterFragment.newInstance(Constant.FragmentType.EmailLogin));
    }

    @Override
    protected void initView() {

        aq=new AQuery(this);
        mViewPager=(ViewPager)findViewById(R.id.viewPager);
        aq.id(R.id.title_mid_text).text("快速注册");
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_left_tv).visible().text("退出");
        aq.id(R.id.title_right_img).gone();
        mTabWidget=(TwoPagerTabWidget)findViewById(R.id.two_tab);
        mTabWidget.setText_first_tab("手机注册");
        mTabWidget.setText_second_tab("邮箱注册");

        mViewPager.setAdapter(new TabPagerAdapter(getSupportFragmentManager(), fragments));
        mTabWidget.setmViewPager(mViewPager);



    }

    @Override
    protected void setListener() {
        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch (position) {
                    case 0:
                        ShowToast("0");
                        break;
                    case 1:
                        ShowToast("1");
                        break;
                }
            }
        });
        aq.id(R.id.title_left_btn).clicked(this, "aq_back");
    }

    public void aq_back(){
        finish();
    }

    public void aq_login(){
        Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
