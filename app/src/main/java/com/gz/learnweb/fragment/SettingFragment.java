package com.gz.learnweb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gz.learnweb.Constant;
import com.gz.learnweb.Utils.ActivityManagerUtils;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.Utils.SpUtils;
import com.gz.learnweb.Utils.VolleyUtils;
import com.gz.learnweb.activity.LoginActivity;
import com.gz.learnweb.activity.MainActivity;
import com.gz.learnweb.entire.User;
import com.gz.learnweb.listener.LeftHideShow;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.activity.UserModifyActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * information of user
 * Created by host on 2015/9/4.
 */
public class SettingFragment extends Fragment implements LeftHideShow {
    View convertView;
    User user;
    AQuery aq;

    SpUtils sp;
    Gson gson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        convertView = inflater.inflate(R.layout.setting_fragment, container, false);
        aq = new AQuery(getActivity());
        fetchUser();
        initView();
        setListener();
        leftOn();
        return convertView;
    }

    private void fetchUser() {
        sp = new SpUtils(getActivity());
        String userJson = sp.getValue(Constant.DataKey.USER, "{}");
        gson = new GsonBuilder().disableHtmlEscaping().create();
        user = gson.fromJson(userJson, User.class);
    }

    private void initView() {
        AQuery inaq = new AQuery(convertView);
        inaq.id(R.id.setting_name).text(user.getUsername());
        inaq.id(R.id.setting_detail).text(user.getDetail());
        if (user.getUser_tel() != null) {
            inaq.id(R.id.setting_phone).text(user.getUser_tel());
            inaq.id(R.id.btn_setting_phone).visible();
            inaq.id(R.id.sp_setting_phone).visible();
        }
        if (user.getUser_mail() != null) {
            inaq.id(R.id.setting_mail).text(user.getUser_mail());
            inaq.id(R.id.btn_setting_mail).visible();
        }
    }

    private void setListener() {
        AQuery inaq = new AQuery(convertView);
        inaq.id(R.id.btn_setting_logoff).clicked(this, "aq_logoff");
        inaq.id(R.id.btn_setting_name).clicked(this, "aq_settingName");
        inaq.id(R.id.btn_setting_detail).clicked(this, "aq_settingDetail");
    }

    public void aq_logoff() {
        SpUtils sp = new SpUtils(getActivity());
        sp.setValue(Constant.DataKey.FIRSTSTART, true);
        sp.setValue(Constant.DataKey.USER, null);
        sp.setValue(Constant.DataKey.SESS, null);
        sp.clear();
        ActivityManagerUtils.getInstance().removeAllActivity();
        getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    //请求名字更改
    public void aq_settingName() {
        Intent intent = new Intent(getActivity(), UserModifyActivity.class);
        Bundle bundle = new Bundle();
        //设置请求码和类型都使用同一个int常量Constant.CODE.NameModfiy
        bundle.putInt(Constant.DataKey.MODIFYTYPE, Constant.CODE.NameModfiy);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.CODE.NameModfiy);
    }

    //启动个性签名修改
    public void aq_settingDetail() {
        Intent intent = new Intent(getActivity(), UserModifyActivity.class);
        Bundle bundle = new Bundle();
        //设置请求码和类型都使用同一个int常量Constant.CODE.DetailModify
        bundle.putInt(Constant.DataKey.MODIFYTYPE, Constant.CODE.DetailModify);
        intent.putExtras(bundle);
        startActivityForResult(intent, Constant.CODE.DetailModify);
    }

    //从修改页面返回的“用户”结果处理，先发起post请求，待返回成功信息后，修改本地信息，存储信息
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            final String result = data.getStringExtra(Constant.DataKey.MODIFYRESULT);
            switch (requestCode) {
                //名字修改部分
                case Constant.CODE.NameModfiy:
                    //写入post参数
                    Map<String, String> nameParam = new HashMap<>();
                    nameParam.put("username", result);
                    //网络请求
                    VolleyUtils.post(Constant.URL.ModifyUser, nameParam, new VolleyUtils.NetworkListener() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.optString("state");
                                if (state.equals("success")) {
                                    //写入本地文件
                                    user.setUsername(result);
                                    sp.setValue(Constant.DataKey.USER,gson.toJson(user));
                                    //更改设置页页面
                                    aq.id(R.id.setting_name).text(result);
                                    //更改左拉菜单页面
                                    ((MainActivity)getActivity()).refreshUserView();
                                    Toast.makeText(getActivity(), "姓名修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    LogUtil.i("modify fail",response);
                                    Toast.makeText(getActivity(), "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(String error) {
                            Toast.makeText(getActivity(), "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
                //个性签名修改部分
                case Constant.CODE.DetailModify:
                    //写入post参数
                    Map<String, String> deatailParam = new HashMap<>();
                    deatailParam.put("detail", result);
                    //网络请求
                    VolleyUtils.post(Constant.URL.ModifyUser, deatailParam, new VolleyUtils.NetworkListener() {
                        @Override
                        public void onSuccess(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String state = jsonObject.optString("state");
                                if (state.equals("success")) {
                                    aq.id(R.id.setting_detail).text(result);
                                    //写入本地文件
                                    user.setDetail(result);
                                    sp.setValue(Constant.DataKey.USER, gson.toJson(user));
                                    Toast.makeText(getActivity(), "个性签名修改成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    LogUtil.i("modify fail",response);
                                    Toast.makeText(getActivity(), "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFail(String error) {
                            Toast.makeText(getActivity(), "修改失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;
            }
        }
    }

    @Override
    public void leftOn() {
        LogUtil.i("left", "first page on");
        aq.id(R.id.btn_setting).background(R.color.light_grey);
        aq.id(R.id.img_setting).image(R.drawable.setting_red);
        aq.id(R.id.text_setting).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("个人设置");
    }

    @Override
    public void leftOff() {
        aq.id(R.id.btn_setting).background(R.color.white);//背景色
        aq.id(R.id.img_setting).image(R.drawable.setting_gray);//图标
        aq.id(R.id.text_setting).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }
}
