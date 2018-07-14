package com.iflytek.assist;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.assist.Model.ChatItemModel;
import com.iflytek.voicedemo.R;

import java.util.ArrayList;

/**
 * Created by qxb-810 on 2018/7/11.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {


    private Context context;
    private OnItemClickListener mItemClickListener;

    private ArrayList<ChatItemModel> arrayList = new ArrayList<ChatItemModel>();

    public ChatAdapter(Context context,OnItemClickListener mItemClickListener){
        this.context = context;
        this.mItemClickListener = mItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textView.setText(arrayList.get(position).getVoiceText());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //TODO
        public ArrayList<ChatItemModel> getArrayList() {
        return arrayList;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private LinearLayout linearLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_question);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.ll_chat);
        }
    }


}
