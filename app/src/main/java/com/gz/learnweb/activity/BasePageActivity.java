package com.gz.learnweb.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.gz.learnweb.Utils.ActivityManagerUtils;

/**
 * Created by host on 2015/8/5.
 */
public abstract class BasePageActivity extends FragmentActivity {
    Context mContext;
    Intent mIntent;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManagerUtils.getInstance().addActivity(this);
        mContext=this;
        mIntent=getIntent();
        mBundle=mIntent.getExtras();
        initData();
        initLayoutView();
        initView();
        setListener();
    }

    protected abstract void initData();

    protected abstract void initLayoutView();
    protected abstract void initView();
    protected abstract void setListener();
    public void ShowToast(String string){
        Toast.makeText(this, string,Toast.LENGTH_SHORT).show();
    }

}
