package com.gz.learnweb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.gz.learnweb.Adapter.PopularizationAdapter;
import com.gz.learnweb.Constant;
import com.gz.learnweb.Utils.LogUtil;
import com.gz.learnweb.listener.LeftHideShow;
import com.gz.learnweb.R;
import com.gz.learnweb.activity.ShowPopuActivity;
import com.gz.learnweb.entire.PopularizationCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * show the population
 * Created by gz on 15/9/11.
 */
public class PopularizationFragment extends Fragment implements LeftHideShow {

    private View mView;
    private ListView listView;
    private AQuery aq;
    private List<PopularizationCourse> courses;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_popularization,container,false);
        initData();
        initView();
        setListener();
        leftOn();
        return mView;
    }

    private void initData() {
        courses=new ArrayList<>();
        List<String> images=new ArrayList<>();
        images.add("http://139.129.18.117/images/ss1.png");
        images.add("http://139.129.18.117/images/ss2.png");
        images.add("http://139.129.18.117/images/ss3.png");
        images.add("http://139.129.18.117/images/ss4.png");
        images.add("http://139.129.18.117/images/ss5.png");
        images.add("http://139.129.18.117/images/ss6.png");
        images.add("http://139.129.18.117/images/ss7.png");
        images.add("http://139.129.18.117/images/ss8.png");
        images.add("http://139.129.18.117/images/ss9.png");
//        images.add("http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1508/31/c11/spcgroup/12002152_1441035884238_1080x1920.jpg");
//        images.add("http://imgrt.pconline.com.cn/images/upload/upc/tx/wallpaper/1508/31/c11/spcgroup/12002130_1441035885744_1080x1920.jpg");
//        images.add("http://img.mukewang.com/55ea64dc0001197b06000338-280-160.jpg");

        PopularizationCourse course1 = new PopularizationCourse();
        course1.setTitle("servelet");
        course1.setDetail("教你使用servelet");
        course1.setPageImage("http://img.mukewang.com/549bbf110001019406000338-280-160.jpg");
        course1.setImages(images);

        PopularizationCourse course2=new PopularizationCourse();
        course2.setTitle("ipad");
        course2.setDetail("用ipad编程");
        course2.setPageImage("http://img.mukewang.com/55dfcb37000114b104000200.jpg");
        course2.setImages(images);

        PopularizationCourse course3=new PopularizationCourse();
        course3.setTitle("世界首个通用计算机S/360");
        course3.setDetail("");
        course3.setPageImage("http://1.marketonhand.sinaapp.com/ss0.png");
        course3.setImages(images);

        courses.add(course3);
        courses.add(course1);
        courses.add(course2);
        listView=(ListView)mView.findViewById(R.id.popularization_list);
    }

    private void initView() {
        listView.setAdapter(new PopularizationAdapter(getActivity(),courses));
        listView.setDivider(null);
    }

    private void setListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable(Constant.DataKey.POPULARIZATION,courses.get(position));
                Intent intent=new Intent(getActivity(), ShowPopuActivity.class);
                intent.putExtras(bundle);
                getActivity().startActivity(intent);
            }
        });
    }


    @Override
    public void leftOff() {
        LogUtil.i("left", "popularization off");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_popularization).background(R.color.white);//背景色
        aq.id(R.id.img_popularization).image(R.drawable.popularization_gray);//图标
        aq.id(R.id.text_popularization).getTextView().setTextColor(getResources().getColor(R.color.grey));
    }

    @Override
    public void leftOn() {
        LogUtil.i("left", "popularization on");
        if(aq==null){
            aq=new AQuery(getActivity());
        }
        aq.id(R.id.btn_popularization).background(R.color.light_grey);
        aq.id(R.id.img_popularization).image(R.drawable.popularization_red);
        aq.id(R.id.text_popularization).getTextView().setTextColor(getResources().getColor(R.color.text_red));
        aq.id(R.id.title_mid_text).text("科普天地园");
    }
}
