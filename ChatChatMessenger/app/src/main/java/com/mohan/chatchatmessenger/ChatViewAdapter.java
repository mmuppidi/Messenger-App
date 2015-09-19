package com.mohan.chatchatmessenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by robo-ace on 7/27/15.
 */
public class ChatViewAdapter extends BaseAdapter {

    private ArrayList<String> listData;
    private LayoutInflater layoutInflater;



    public ChatViewAdapter(Context context, ArrayList<String> listData) {
        layoutInflater =  LayoutInflater.from(context);
        this.listData = listData;

    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.single_chat_text, null);
            holder = (TextView) convertView.findViewById(R.id.single_chat_textView);
            convertView.setTag(holder);}

        else{
                holder = (TextView) convertView.getTag();
            }

            holder.setText(listData.get(position));
        return convertView;

    }
}













