package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duynguyen.sample.Model.MessageChannel;
import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.User;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChatChannelAdapter extends RecyclerView.Adapter<ChatChannelAdapter.ChatChannelVH> {

    ArrayList<MessageChannel> mData = new ArrayList<>();
    User mCurrentUser;
    protected static ItemListener mItemListener;


    public ChatChannelAdapter(User mCurrentUser, ArrayList<MessageChannel> mData, ItemListener itemListener) {
        this.mData = mData;
        this.mCurrentUser = mCurrentUser;
        this.mItemListener = itemListener;
    }

    public interface ItemListener {
        void onChannelClick(String key);
    }
    public void setmData(ArrayList<MessageChannel> data ){
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatChannelVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new ChatChannelVH(inflater.inflate(R.layout.chat_channel_vh, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ChatChannelVH chatChannelVH, int i) {
        chatChannelVH.setData(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




    public class ChatChannelVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        String mKey;
        String mUserType;
        String mCurrentUserId;

        @BindView(R.id.chat_channel_name_tv)
        TextView nameTv;
        @BindView(R.id.chat_channel_info_tv)
        TextView infoTv;
        @BindView(R.id.chat_channel_civ)
        CircleImageView civ;
        @BindView(R.id.chat_channel_ll)
        LinearLayout chatChannelLl;

        public ChatChannelVH(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);

            mUserType = mCurrentUser.getUserType();
            mCurrentUserId = mCurrentUser.getfUserId();
        }


        @Override
        public void onClick(View v) {
            ChatChannelAdapter.mItemListener.onChannelClick(mKey);
        }

        public void setData(MessageChannel messageChannel) {
            mKey = messageChannel.getId();

            if (mKey.equals(Utils.ANNOUNCEMENT_CHILD )&&mUserType.equals(Utils.PARENT)){
                civ.setImageResource(R.drawable.announcement);
                infoTv.setText("Announcement from teacher");
            }else if (mKey.equals(mCurrentUserId)&&mUserType.equals(Utils.PARENT)) {
                civ.setImageResource(R.drawable.teacher);
                nameTv.setText(messageChannel.getTeacherName());
                infoTv.setText("Teacher");
            }
            else if (mKey.equals(Utils.ANNOUNCEMENT_CHILD ) && mUserType.equals(Utils.TEACHER)){
                civ.setImageResource(R.drawable.announcement);
                infoTv.setText("Announcement to all parents");
            }
            else if (!mKey.equals(Utils.ANNOUNCEMENT_CHILD ) && mUserType.equals(Utils.TEACHER)) {
                civ.setImageResource(R.drawable.parent);
                nameTv.setText(messageChannel.getParentName());
                infoTv.setText(messageChannel.getStudentName()+"'s parent");
            }
            else {
                chatChannelLl.setVisibility(LinearLayout.GONE);
                chatChannelLl.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }
        }
    }
}
