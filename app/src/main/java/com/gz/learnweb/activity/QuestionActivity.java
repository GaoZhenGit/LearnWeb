package com.gz.learnweb.activity;

import android.widget.EditText;
import android.widget.ListView;

import com.androidquery.AQuery;
import com.gz.learnweb.Adapter.DiscussAdapter;
import com.gz.learnweb.entire.Question;
import com.gz.learnweb.entire.User;
import com.gz.learnweb.Adapter.QuestionAdapter;
import com.gz.learnweb.R;
import com.gz.learnweb.entire.Discuss;

import java.util.ArrayList;
import java.util.List;

/**
 * ask question to teacher and discuss
 * Created by host on 2015/11/14.
 */
public class QuestionActivity extends BasePageActivity {
    private AQuery aQuery;
    private boolean isTeacher = true;

    private ListView listView;
    private QuestionAdapter questionAdapter;
    private DiscussAdapter discussAdapter;
    private EditText send;

    private List<Question> questionList;
    private List<Discuss> discussList;


    @Override
    protected void initData() {

    }

    @Override
    protected void initLayoutView() {
        setContentView(R.layout.activity_question);
        aQuery = new AQuery(this);
    }

    @Override
    protected void initView() {
        listView = (ListView) findViewById(R.id.listview);
        send = (EditText) findViewById(R.id.et_send);

        questionList = new ArrayList<>();
        discussList = new ArrayList<>();

        setStaticData();//静态数据

        questionAdapter = new QuestionAdapter(questionList);
        discussAdapter = new DiscussAdapter(discussList);
        listView.setAdapter(questionAdapter);
        listView.setDivider(null);
        listView.setClickable(false);
    }

    @Override
    protected void setListener() {
        aQuery.id(R.id.title_left_btn).clicked(this, "onBackPressed");
        aQuery.id(R.id.title_left).clicked(this, "changeToTeacher");
        aQuery.id(R.id.title_right).clicked(this, "changeToStudent");
        aQuery.id(R.id.btn_send).clicked(this, "send");
    }

    public void changeToTeacher() {
        if (isTeacher) {
            return;
        }
        aQuery.id(R.id.title_left).backgroundColorId(R.color.white);
        aQuery.id(R.id.title_right).background(R.drawable.title_stock);
        isTeacher = true;

        listView.setAdapter(questionAdapter);
    }

    public void changeToStudent() {
        if (!isTeacher) {
            return;
        }
        aQuery.id(R.id.title_right).backgroundColorId(R.color.white);
        aQuery.id(R.id.title_left).background(R.drawable.title_stock);
        isTeacher = false;

        listView.setAdapter(discussAdapter);
    }

    private void setStaticData() {
        String[] comment = {
                "好，看了视频一下就懂了",
                "昨天上课走神了，没想到内容全在视频这里",
                "谢谢老师分享",
                "有人看懂那个是怎么运行起来的吗？",
                "我们班好多同学在看这个视频",
                "顶！！！！！",
                "感觉还不错，就是有些地方讲得有点快"
        };
        String[] names = {
                "小星",
                "Geek",
                "karry",
                "爱德华",
                "米其林",
                "哆啦A梦",
                "Jackson"
        };
        String[] avaters = {
                "http://139.129.18.117/images/u=2160420705,2533030665&fm=23&gp=0.jpg",
                "http://139.129.18.117/images/u=2584355946,4148531126&fm=23&gp=0.jpg",
                "http://139.129.18.117/images/u=4131486482,3650592575&fm=23&gp=0.jpg",
                "http://139.129.18.117/images/u=82957784,1293591806&fm=23&gp=0.jpg",
                "http://139.129.18.117/images/u=2486134525,1545291788&fm=23&gp=0.jpg",
                "http://139.129.18.117/images/u=926885119,1486850880&fm=23&gp=0.jpg",
                "http://139.129.18.117/images/u=3845937323,3814335822&fm=23&gp=0.jpg"
        };
        for (int i = 0; i < comment.length; i++) {
            Discuss discuss = new Discuss();
            discuss.setContent(comment[i]);
            User user = new User();
            user.setAvater(avaters[i]);
            user.setUsername(names[i]);
            discuss.setUser(user);
            discussList.add(discuss);
        }

        String[] content = {
                "请问DB2中CURSOR如果带有Sensitive参数是什么意思？",
                "不敏感的游标意味着在游标打开后，不能看到其他应用对基础表中的更改(包括数据插入，更新和删除)，不敏感的游标是只读的，结果表中行的数量和内容在游标打开的整个过程中不变。不敏感的游标不能用于数据更新和删除。\n" +
                        "敏感的游标意味着在游标打开后，能够看到其他应用对基础表所作的更改，结果表中行的数量(其他应用插入或删除)在游标打开的整个过程中不变，但是行的内容可变 。敏感的游标能够用于数据更新和删除。",
                "居然这么快就回复了！谢谢老师，我明白了",};
        for (int i = 0; i < 3; i++) {
            Question question = new Question();
            question.setIsFromTeacher(i % 2 == 1);
            question.setContent(content[i]);
            questionList.add(question);
        }
    }

    public void send() {
        String sendString = send.getText().toString();
        if (sendString.length() == 0) {
            return;
        }
        if (isTeacher) {
            Question question = new Question();
            question.setContent(sendString);
            question.setIsFromTeacher(false);
            questionList.add(question);
            questionAdapter.notifyDataSetChanged();
            listView.setSelection(questionList.size() - 1);
        } else {

        }
        send.setText("");
    }
}
