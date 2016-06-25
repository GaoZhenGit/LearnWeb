package com.gz.learnweb.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.gz.learnweb.fragment.CollectFragment;
import com.gz.learnweb.fragment.MyNoteFragment;
import com.gz.learnweb.listener.LeftHideShow;
import com.gz.learnweb.Application.CloudApplication;
import com.gz.learnweb.R;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.Utils.SpUtils;
import com.gz.learnweb.entire.User;
import com.gz.learnweb.fragment.FirstPageFragment;
import com.gz.learnweb.fragment.HistoryFragment;
import com.gz.learnweb.fragment.InterestFragment;
import com.gz.learnweb.fragment.MessageFragment;
import com.gz.learnweb.fragment.PopularizationFragment;
import com.gz.learnweb.fragment.SettingFragment;

public class MainActivity extends BasePageActivity {
    private DrawerLayout mDrawerLayout;
    private AQuery aq;
    private FragmentManager fragmentManager;
    private User user;

    private SpUtils sp;
    private Gson gson;

    private Fragment currentFragment;
    //左侧每个fragment的引用
    private FirstPageFragment firstPageFragment;
    private HistoryFragment historyFragment;
    private CollectFragment collectFragment;
    private PopularizationFragment popularizationFragment;
    private InterestFragment interestFragment;
    private MessageFragment messageFragment;
    private MyNoteFragment myNoteFragment;
    private SettingFragment settingFragment;
    private long exitTime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initData() {
        fragmentManager=getSupportFragmentManager();
        sp=new SpUtils(this);
        gson =new Gson();
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_main);
        aq=new AQuery(this);
        //设置左拉菜单用户显示
        refreshUserView();
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text("");
        aq.id(R.id.title_right_img).visible().image(R.drawable.search);
        aq.id(R.id.title_left_img).visible().image(R.drawable.menu);
        aq.id(R.id.title_left_tv).visible().text("  ");
        aq.id(R.id.title_right_text).gone();
        //设置初始fragment
        firstPageFragment = new FirstPageFragment();
        fragmentManager.beginTransaction().add(R.id.main_framelayout, firstPageFragment).commit();
        currentFragment = firstPageFragment;
    }

    @Override
    protected void setListener() {
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        //每个左侧按设置监听器
        aq.id(R.id.btn_firstpage).clicked(this, "aq_firstpage");
        aq.id(R.id.btn_history).clicked(this, "aq_history");
        aq.id(R.id.btn_collect_course).clicked(this, "aq_collect_course");
        aq.id(R.id.btn_popularization).clicked(this, "aq_popularization");
        aq.id(R.id.btn_interest).clicked(this, "aq_interest");
        aq.id(R.id.btn_my_note).clicked(this, "aq_my_note");
        aq.id(R.id.btn_message).clicked(this, "aq_message");
        aq.id(R.id.btn_setting).clicked(this, "aq_setting");
        aq.id(R.id.title_left_btn).clicked(new View.OnClickListener() {
            //左上方按键设置开关效果
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.closeDrawer(Gravity.LEFT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }
            }
        });



        aq.id(R.id.title_right_btn).clicked(this,"aq_search");
    }

    public void refreshUserView(){
        user=gson.fromJson(sp.getValue("user",""),User.class);
        aq.id(R.id.username_tv).text(user.getUsername());
    }

    public void serv(){
        Intent intent=new Intent();
        intent.setAction("notifi");
        intent.setPackage("com.ibm.gz.learn_cloud");
        LogUtil.i("-------------start service", "start");
        startService(intent);
    }

    public void aq_search() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }


    /**
     * 切换fragment，可以不必重新加载
     * @param to 转换到的Fragment
     */
    public void switchContent(Fragment to) {
        if (currentFragment != to) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction();
            if (!to.isAdded()) { // 先判断是否被add过
                transaction.hide(currentFragment).add(R.id.main_framelayout, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                LeftHideShow current = (LeftHideShow) currentFragment;
                current.leftOff();
                currentFragment=to;
            } else {
                transaction.hide(currentFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
                //左侧菜单
                LeftHideShow current = (LeftHideShow) currentFragment;
                current.leftOff();
                currentFragment = to;
                current = (LeftHideShow) to;
                current.leftOn();
            }
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    //首页
    public void aq_firstpage(){
        if (firstPageFragment==null) {
            firstPageFragment = new FirstPageFragment();
        }
        switchContent(firstPageFragment);
    }

    //历史课程
    public void aq_history(){
        if(historyFragment==null){
            historyFragment=new HistoryFragment();
        }
        switchContent(historyFragment);
    }
    //收藏课程
    public void aq_collect_course(){
        if(collectFragment==null){
            collectFragment=new CollectFragment();
        }
        switchContent(collectFragment);
    }
    //科普
    public void aq_popularization(){
        if(popularizationFragment==null){
            popularizationFragment=new PopularizationFragment();
        }
        switchContent(popularizationFragment);
    }
    //兴趣
    public void aq_interest(){
        if(interestFragment==null){
            interestFragment=new InterestFragment();
        }
        switchContent(interestFragment);
    }
    //我的消息
    public void aq_message(){
        if(messageFragment==null){
            messageFragment=new MessageFragment();
        }
        switchContent(messageFragment);
    }
    //笔记
    public void aq_my_note(){
        if(myNoteFragment==null){
            myNoteFragment=new MyNoteFragment();
        }
        switchContent(myNoteFragment);
    }
    //个人设置
    public void aq_setting(){
        if(settingFragment==null){
            settingFragment=new SettingFragment();
        }
        switchContent(settingFragment);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 10000) {
                Toast.makeText(mContext, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                ((CloudApplication)getApplication()).exit();
            }
            return true;
        }
        if(keyCode==KeyEvent.KEYCODE_MENU){
            aq.id(R.id.title_left_btn).click();
        }
        return super.onKeyDown(keyCode, event);
    }


}
