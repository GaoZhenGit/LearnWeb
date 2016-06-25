package com.gz.learnweb.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.net.Uri;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.gz.learnweb.Adapter.TabPagerAdapter;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.Constant;
import com.gz.learnweb.R;
import com.gz.learnweb.Utils.DensityUtil;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.entire.Course;
import com.gz.learnweb.fragment.FirstPageChildFgm.ChapterFgm;
import com.gz.learnweb.fragment.FirstPageChildFgm.DetailFgm;
import com.gz.learnweb.fragment.FirstPageChildFgm.NoteFgm;
import com.gz.learnweb.listener.OnTabSelectedListener;
import com.gz.learnweb.myview.FullScreenMediaController;
import com.gz.learnweb.myview.FullScreenVideoView;
import com.gz.learnweb.myview.PagerTabWidget;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseActivity extends BasePageActivity implements MediaPlayer.OnErrorListener {
    private AQuery aq;
    private FullScreenVideoView videoView;
    private FullScreenMediaController mediaController;

    private Course course;
    private PagerTabWidget mTabWidget;
    private ViewPager mViewPager;
    private TabPagerAdapter mPagerAdapter;

    private List<Fragment> fragments;


    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initData() {

        if (mBundle != null && mBundle.getSerializable(Constant.DataKey.COURSE) != null) {
            course = (Course) mBundle.getSerializable(Constant.DataKey.COURSE);
        }
        fragments = new ArrayList<>();
        fragments.add(new DetailFgm());
        fragments.add(new NoteFgm());
        ChapterFgm chapterFgm = new ChapterFgm();
        chapterFgm.setVideoList(course.getCourse_videos());
        fragments.add(chapterFgm);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_course);
        aq = new AQuery(this);
        gson = new GsonBuilder().disableHtmlEscaping().create();

        //获得组件
        videoView = (FullScreenVideoView) findViewById(R.id.video_view);
        mediaController = new FullScreenMediaController(this);
        mTabWidget = (PagerTabWidget) findViewById(R.id.video_tabwidget);
        mViewPager = (ViewPager) findViewById(R.id.video_viewpager);

        //设置tab相关组件
        mTabWidget.setDividerInvisible();
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_detail, null));
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_note, null));
        mTabWidget.addTab(LayoutInflater.from(this).inflate(R.layout.tab_chapter, null));

        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mPagerAdapter);

        mTabWidget.setmViewPager(mViewPager);

        mTabWidget.setmOnTabSelectedListener(new OnTabSelectedListener() {
            @Override
            public void onSelected(List<View> tabViews, int position) {
                switch (position) {
                    case 0:
                        aq.id(R.id.tab_detail_v).visible();
                        aq.id(R.id.tab_detail_tv).textColor(getResources().getColor(R.color.light_green));

                        aq.id(R.id.tab_note_v).invisible();
                        aq.id(R.id.tab_note_tv).textColor(getResources().getColor(R.color.b));
                        aq.id(R.id.tab_chapter_v).invisible();
                        aq.id(R.id.tab_chapter_tv).textColor(getResources().getColor(R.color.b));
                        break;
                    case 1:
                        aq.id(R.id.tab_note_v).visible();
                        aq.id(R.id.tab_note_tv).textColor(getResources().getColor(R.color.light_green));

                        aq.id(R.id.tab_detail_v).invisible();
                        aq.id(R.id.tab_detail_tv).textColor(getResources().getColor(R.color.b));
                        aq.id(R.id.tab_chapter_v).invisible();
                        aq.id(R.id.tab_chapter_tv).textColor(getResources().getColor(R.color.b));
                        break;
                    case 2:
                        aq.id(R.id.tab_chapter_v).visible();
                        aq.id(R.id.tab_chapter_tv).textColor(getResources().getColor(R.color.light_green));

                        aq.id(R.id.tab_detail_v).invisible();
                        aq.id(R.id.tab_detail_tv).textColor(getResources().getColor(R.color.b));
                        aq.id(R.id.tab_note_v).invisible();
                        aq.id(R.id.tab_note_tv).textColor(getResources().getColor(R.color.b));
                        break;
                }
            }
        });

        //设置视频相关组件
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        mediaController.setAnchorView(videoView);
        mediaController.setBringView(aq.id(R.id.btn_fullscreen).getView());

        if (course != null) {
            videoView.setVideoURI(Uri.parse(course.getCourse_videos().get(0).getUri()));
            LogUtil.i("----video uri------", course.getCourse_videos().get(0).getUri());
        }
        videoView.setOnErrorListener(this);
        videoView.setOnVideoStartListener(new FullScreenVideoView.onVideoStartListener() {
            @Override
            public void onStart() {
                //设置历史记录，在播放视频后
                saveHistroy();
            }
        });
    }

    public void reSetVideoUri(String uri) {
        videoView.stopPlayback();
        videoView.setVideoURI(Uri.parse(uri));
    }

    @Override
    protected void initView() {
        aq.id(R.id.title_mid_text).text(course.getCourse_name());
        aq.id(R.id.title_left_img).visible();
        aq.id(R.id.title_right_img).visible().image(R.drawable.note);
        checkCollection();
    }

    @Override
    protected void setListener() {
        aq.id(R.id.btn_fullscreen).clicked(this, "aq_landscape");
        aq.id(R.id.title_left_btn).clicked(this, "finish");
        aq.id(R.id.title_right_btn).clicked(this, "aq_note");

    }

    //全屏按钮
    public void aq_landscape() {
        switch (getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setPortraitView();
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                setLandscapeView();
                break;
        }
    }

    //添加收藏
    public void aq_note() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.DataKey.COURSE,course);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    //添加收藏
    public void aq_collection(){
        aq.id(R.id.title_second_right_btn).clickable(false);
        LogUtil.i("---------","collection");
        Map<String,String> param =new HashMap<>();
        param.put("course_id",course.getCourse_id()+"");
        VolleyUtils.post(Constant.URL.AddCollection, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String state = jsonObject.optString("state");
                    if (state.equals("success")){
                        Toast.makeText(CourseActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        aq.id(R.id.title_second_right_btn).clicked(CourseActivity.this, "aq_unCollection");
                        aq.id(R.id.title_second_right_img).image(R.drawable.heart_full);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aq.id(R.id.title_second_right_btn).clickable(true);
            }

            @Override
            public void onFail(String error) {
                aq.id(R.id.title_second_right_btn).clickable(true);
            }
        });
    }
    //取消收藏
    public void aq_unCollection(){
        aq.id(R.id.title_second_right_btn).clickable(false);
        LogUtil.i("---------","unCollection");
        Map<String,String> param =new HashMap<>();
        param.put("course_id",course.getCourse_id()+"");
        VolleyUtils.post(Constant.URL.DellectCollection, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String state = jsonObject.optString("state");
                    if (state.equals("success")){
                        Toast.makeText(CourseActivity.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                        aq.id(R.id.title_second_right_btn).clicked(CourseActivity.this, "aq_collection");
                        aq.id(R.id.title_second_right_img).image(R.drawable.heart_empty);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                aq.id(R.id.title_second_right_btn).clickable(true);
            }

            @Override
            public void onFail(String error) {
                aq.id(R.id.title_second_right_btn).clickable(true);
            }
        });
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        ShowToast(what + "");
        return false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            LogUtil.i("现在是横屏");
            setLandscapeView();
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            LogUtil.i("现在是竖屏");
            setPortraitView();
        }
    }

    //竖屏界面转换
    private void setPortraitView() {
//        aq.id(R.id.btn_start).visible();
//        aq.id(R.id.btn_stop).visible();
//        aq.id(R.id.btn_pause).visible();
//        aq.id(R.id.btn_landscape).visible();
        aq.id(R.id.title_bar).visible();
        aq.id(R.id.video_tabwidget).visible();
        aq.id(R.id.video_viewpager).visible();
        //框架参数调整
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById(R.id.video_layout).getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.title_bar);
        layoutParams.width = DensityUtil.getWindowWidth(this);
        layoutParams.height = DensityUtil.dip2px(this, 200);
        //显示状态栏
        DensityUtil.showTitle(this);
        //视频大小适配调整
        videoView.setVideoHeight(DensityUtil.dip2px(this, 200));
        videoView.setVideoWidth(DensityUtil.getWindowWidth(this));
    }

    //横屏界面设置
    private void setLandscapeView() {
//        aq.id(R.id.btn_start).gone();
//        aq.id(R.id.btn_stop).gone();
//        aq.id(R.id.btn_pause).gone();
//        aq.id(R.id.btn_landscape).gone();
        aq.id(R.id.title_bar).gone();
        aq.id(R.id.video_tabwidget).gone();
        aq.id(R.id.video_viewpager).gone();
        //框架参数调整
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) findViewById(R.id.video_layout).getLayoutParams();
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        layoutParams.height = RelativeLayout.LayoutParams.MATCH_PARENT;
        layoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        //隐藏状态栏
        DensityUtil.hideTitle(this);
        //视频大小适配调整
        videoView.setVideoWidth(DensityUtil.getWindowWidth(this));
        videoView.setVideoHeight(DensityUtil.getWindowHeight(this));
    }

    @Override
    public void onBackPressed() {
        switch (getRequestedOrientation()) {
            case ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE://如果是横屏，返回键表示退出横屏
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                setPortraitView();
                break;
            case ActivityInfo.SCREEN_ORIENTATION_PORTRAIT://如果是竖屏，返回键表示退出acitvity
                if (videoView.isPlaying()) {
                    videoView.stopPlayback();
                }
                finish();
                break;
        }
    }

    public Course getCourse() {
        return course;
    }

    //播放后添加历史记录
    public void saveHistroy() {
        Map<String, String> param = new HashMap<>();
        param.put("course_id", course.getCourse_id() + "");
        VolleyUtils.post(Constant.URL.AddHistory, param, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                LogUtil.i("history course id:", course.getCourse_id() + "");
            }

            @Override
            public void onFail(String error) {
                LogUtil.i("history fail", error + "");
            }
        });
    }

    private void checkCollection(){
        VolleyUtils.post(Constant.URL.GetCollection, null, new VolleyUtils.NetworkListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    List<Course> courses =gson.fromJson(jsonArray.toString(),new TypeToken<List<Course>>() {
                    }.getType());
                    if(courses.contains(CourseActivity.this.course)){
                        aq.id(R.id.title_second_right_btn).visible();//收藏按钮可见
                        aq.id(R.id.title_second_right_btn).clicked(CourseActivity.this,"aq_unCollection");
                        aq.id(R.id.title_second_right_img).image(R.drawable.heart_full);
                    }else {
                        aq.id(R.id.title_second_right_btn).visible();//收藏按钮可见
                        aq.id(R.id.title_second_right_btn).clicked(CourseActivity.this,"aq_collection");
                        aq.id(R.id.title_second_right_img).image(R.drawable.heart_empty);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String error) {

            }
        });
    }
}
