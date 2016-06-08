package com.gz.learnweb.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.ibm.gz.learnweb.R;
import com.gz.learnweb.entire.Discuss;
import com.gz.learnweb.entire.User;

import java.util.List;

/**
 * Created by host on 2015/11/15.
 */
public class DiscussAdapter extends BaseAdapter {
    private List<Discuss> discussList;

    private AQuery aQuery;

    public DiscussAdapter(List<Discuss> discusses) {
        this.discussList = discusses;
    }

    @Override
    public int getCount() {
        return discussList == null ? 0 : discussList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.adapter_discuss, parent, false);
        }

        Discuss discuss = discussList.get(position);
        User user = discuss.getUser();
        if(null!=user) {
            aQuery = new AQuery(convertView);
            aQuery.id(R.id.avatar).image(user.getAvater());
            aQuery.id(R.id.tv_username).text(user.getUsername());
            aQuery.id(R.id.tv_discuss).text(discuss.getContent());
        }

        return convertView;
    }
}
