package com.gz.learnweb.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.google.gson.Gson;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.Utils.SpUtils;
import com.gz.learnweb.entire.User;
import com.gz.learnweb.listener.LeftHideShow;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.entire.Interest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gz on 15/9/30.
 */
public class InterestFragment extends Fragment implements LeftHideShow {
    private static final int[] userids={
            R.id.tag1,
            R.id.tag2,
            R.id.tag3,
            R.id.tag4,
            R.id.tag5,
            R.id.tag6,
    };
    private static final int[] ids={
            R.id.choice_tag1,
            R.id.choice_tag2,
            R.id.choice_tag3,
            R.id.choice_tag4,
            R.id.choice_tag5,
            R.id.choice_tag6,
            R.id.choice_tag7,
            R.id.choice_tag8,
            R.id.choice_tag9,
            R.id.choice_tag10,
            R.id.choice_tag11,
            R.id.choice_tag12,};

    private AQuery aq;
    private View contextView;
    private SpUtils sp;
    private TagOnClickListener listener;

    private List<Interest> allInterest;
    private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aq=new AQuery(getActivity());
        Gson gson =new Gson();
        sp=new SpUtils(getActivity());
        LogUtil.i("---user----",sp.getValue("user",""));
        user=gson.fromJson(sp.getValue("user",""),User.class);
        listener=new TagOnClickListener();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contextView =inflater.inflate(R.layout.fragment_interest,container,false);
        initListView();
        leftOn();
        return contextView;
    }

    private void initListView() {
        requestNet();
        showUserTag();
        new AQuery(contextView).id(R.id.btn_tag_comfirm).clicked(this,"aq_self_tag");
    }

    //请求访问网络（获取标签）
    private void requestNet(){
        List<Interest> interests=new ArrayList<>();
        Interest interest=new Interest();
        interest.setId(1255);
        interest.setName("java");
        Interest interest2=new Interest();
        interest2.setId(12255);
        interest2.setName("css");

        interests.add(interest);
        interests.add(interest2);
        showTags(interests);
    }

    //带有刷新性质的显示下方tag
    private void showTags(List<Interest> list){
        if(allInterest==null){
            allInterest=new ArrayList<>();
        }
        allInterest.clear();
        allInterest.addAll(list);
        int interestCount=allInterest.size();
        for(int i=0;i<interestCount&&i<6;i++){
            TextView textView= (TextView) contextView.findViewById(ids[i]);
            textView.setVisibility(View.VISIBLE);
            textView.setText(allInterest.get(i).getName());
            textView.setOnClickListener(listener);
        }
    }

    //带有刷新性质的显示user标签
    public void showUserTag(){
        List<Interest> userTag=user.getInterests();
        if(userTag==null){
            return;
        }
        int userTagCount=userTag.size();
        for(int i=0;i<6;i++){
            TextView textView=(TextView)contextView.findViewById(userids[i]);
            textView.setVisibility(View.INVISIBLE);
        }
        for(int i=0;i<6&&i<userTagCount;i++){
            TextView textView=(TextView)contextView.findViewById(userids[i]);
            textView.setVisibility(View.VISIBLE);
            textView.setText(userTag.get(i).getName());
            textView.setOnLongClickListener(listener);
        }
    }

    private void addUserTag(Interest interest){
        if(user.getInterests()==null){
            user.setInterests(new ArrayList<Interest>());
        }
        if(!user.getInterests().contains(interest)) {
            user.getInterests().add(interest);
            Gson gson=new Gson();
            sp.setValue("user",gson.toJson(user,User.class));
        }
        showUserTag();
    }

    private void deleteUserTag(Interest interest){
        if(user.getInterests().contains(interest)) {
            user.getInterests().remove(interest);
            Gson gson=new Gson();
            sp.setValue("user",gson.toJson(user,User.class));
        }
        showUserTag();
    }

    private void addSelfTag(String name){
        Interest interest=new Interest();
        interest.setName(name);
        interest.setId(-1);
        addUserTag(interest);
    }

    public void aq_self_tag(){
        EditText self=new AQuery(contextView).id(R.id.et_self_tag).getEditText();
        addSelfTag(self.getText().toString());
        self.setText("");
    }





    @Override
    public void leftOn() {
        LogUtil.i("left", "interest on");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_interest).background(R.color.light_grey);
        aq.id(R.id.img_interest).image(R.drawable.collect_red);
        aq.id(R.id.text_interest).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("兴趣标签");
    }

    @Override
    public void leftOff() {
        LogUtil.i("left", "interest off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_interest).background(R.color.white);//背景色
        aq.id(R.id.img_interest).image(R.drawable.collect_gray);//图标
        aq.id(R.id.text_interest).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }


    private class TagOnClickListener implements View.OnClickListener,View.OnLongClickListener{
        
        private int idToIndex(int id){
            int index=0;
            switch (id){
                //user tag id
                case R.id.tag1:
                    index=0;
                    break;
                case R.id.tag2:
                    index=1;
                    break;
                case R.id.tag3:
                    index=2;
                    break;
                case R.id.tag4:
                    index=3;
                    break;
                case R.id.tag5:
                    index=4;
                    break;
                case R.id.tag6:
                    index=5;
                    break;
                //all tag id
                case R.id.choice_tag1:
                    index=0;
                    break;
                case R.id.choice_tag2:
                    index=1;
                    break;
                case R.id.choice_tag3:
                    index=2;
                    break;
                case R.id.choice_tag4:
                    index=3;
                    break;
                case R.id.choice_tag5:
                    index=4;
                    break;
                case R.id.choice_tag6:
                    index=5;
                    break;
                case R.id.choice_tag7:
                    index=6;
                    break;
                case R.id.choice_tag8:
                    index=7;
                    break;
                case R.id.choice_tag9:
                    index=8;
                    break;
                case R.id.choice_tag10:
                    index=9;
                    break;
                case R.id.choice_tag11:
                    index=10;
                    break;
                case R.id.choice_tag12:
                    index=11;
                    break;
                //
            }
            return index;
        }

        @Override
        public void onClick(View v) {
            addUserTag(allInterest.get(idToIndex(v.getId())));
        }

        @Override
        public boolean onLongClick(final View v) {
            AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
            String[] items={"删除"};
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteUserTag(user.getInterests().get(idToIndex(v.getId())));
                }
            });
            builder.create().show();
            return true;
        }
    }
}
