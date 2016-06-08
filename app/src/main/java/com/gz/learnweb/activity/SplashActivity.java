package com.gz.learnweb.activity;

import android.content.Intent;

import com.ibm.gz.learnweb.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by host on 2015/10/18.
 */
public class SplashActivity extends BasePageActivity{
    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {

    }

    @Override
    protected void initView() {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void setListener() {
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        };
        Timer timer=new Timer();
        timer.schedule(timerTask,2000);
    }
}
