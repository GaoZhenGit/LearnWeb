package com.gz.learnweb.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.androidquery.AQuery;
import com.gz.learnweb.entire.Note;
import com.ibm.gz.learnweb.R;

import java.util.List;

/**
 * Created by gz on 15/9/2.
 */
public class NoteAdapter extends BaseAdapter {
    private Context context;
    private List<Note> noteList;
    private LayoutInflater listContainer;
    private AQuery aq;
    public NoteAdapter(Context context,List<Note> list){
        noteList=list;
        this.context=context;
        listContainer=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return noteList.size();
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
        if(convertView==null){
            convertView=listContainer.inflate(R.layout.note_adapter,parent,false);
        }
        aq=new AQuery(convertView);
        Note note=noteList.get(position);
        aq.id(R.id.note_date).text(note.getTime());
        aq.id(R.id.note_course_name).text(note.getCourseName());
        aq.id(R.id.note_text).text(note.getText());
        return convertView;
    }
}
