package com.example.minitown.chat.ui.data;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.minitown.R;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private ArrayList<ChatModel> mChatList;

    public ChatAdapter(ArrayList<ChatModel> mChatList) {
        this.mChatList = mChatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_list, viewGroup,false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder chatViewHolder, int i) {
        switch (mChatList.get(i).getSender()){
            case 1:
                chatViewHolder.mTextViewRight.setText(mChatList.get(i).getMessage());
                chatViewHolder.mTextViewRight.setVisibility(View.VISIBLE);
                break;
            case 2:
                chatViewHolder.mTextViewLeft.setText(mChatList.get(i).getMessage());
                chatViewHolder.mTextViewLeft.setVisibility(View.VISIBLE);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewLeft, mTextViewRight;
        @SuppressLint("ResourceType")
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewLeft = (TextView) itemView.findViewById(R.id.txt_receive_message);
            mTextViewRight = (TextView) itemView.findViewById(R.id.txt_sent_message);
        }
    }
}
