package com.gz.learnweb.activity;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.learnweb.Constant;
import com.gz.learnweb.Utils.SpUtils;
import com.gz.learnweb.entire.User;
import com.gz.learnweb.R;

/**
 * to modify the username or detail on server
 * Created by host on 2015/11/2.
 */
public class UserModifyActivity extends BasePageActivity {
    private AQuery aQuery;
    private int modifyType;
    private SpUtils spUtils;
    private User user;
    private Gson gson;

    private EditText editText;

    @Override
    protected void initData() {
        spUtils = new SpUtils(this);
        gson = new GsonBuilder().disableHtmlEscaping().create();
        user = gson.fromJson(spUtils.getValue(Constant.DataKey.USER, ""), User.class);

        if (mBundle == null||user == null) {
            finish();
        }
        modifyType = mBundle.getInt(Constant.DataKey.MODIFYTYPE);
    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_user_modify);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        editText = (EditText) findViewById(R.id.et_modify);
        aQuery.id(R.id.title_left_img).visible();
        aQuery.id(R.id.title_right_text).visible().text(R.string.comfirm);
        aQuery.id(R.id.title_right_btn).clicked(this,"aq_confirm");
        if (modifyType == Constant.CODE.NameModfiy) {
            aQuery.id(R.id.title_mid_text).text(R.string.namemodify);
            editText.setText(user.getUsername());
        }
        if (modifyType == Constant.CODE.DetailModify) {
            aQuery.id(R.id.title_mid_text).text(R.string.detailmodify);
            editText.setText(user.getDetail());
        }
    }

    public void aq_confirm(){
        Intent intent = new Intent();
        intent.putExtra(Constant.DataKey.MODIFYRESULT,editText.getText().toString());
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.title_left_btn).clicked(this, "onBackPressed");
    }
}
