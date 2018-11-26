package com.example.duynguyen.sample.utils;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duynguyen.sample.model.MessageChannel;
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



    public ChatChannelAdapter(ItemListener itemListener) {
        mItemListener = itemListener;
    }

    public interface ItemListener {
        void onChannelClick(String key, int profileImageRes, String receiverName,String channelInfo);
    }
    public void setmData(User mCurrentUser,ArrayList<MessageChannel> data ){
        mData = data;
        this.mCurrentUser = mCurrentUser;
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
        String mReceiverName;
        String mChannelInfo;
        int mProfileImageRes;

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
            ChatChannelAdapter.mItemListener.onChannelClick(mKey,mProfileImageRes,mReceiverName,mChannelInfo);
        }

        public void setData(MessageChannel messageChannel) {
            mKey = messageChannel.getId();

            if (mKey.equals(Utils.ANNOUNCEMENT_CHILD )&&mUserType.equals(Utils.PARENT)){
                mReceiverName = "Annoucement";
                mChannelInfo = "Announcement from teacher";
                mProfileImageRes = R.drawable.announcement;

            }else if (mKey.equals(mCurrentUserId)&&mUserType.equals(Utils.PARENT)) {
                mReceiverName = messageChannel.getTeacherName();
                mChannelInfo = "Teacher";
                mProfileImageRes = R.drawable.teacher;

            }
            else if (mKey.equals(Utils.ANNOUNCEMENT_CHILD ) && mUserType.equals(Utils.TEACHER)){
                mReceiverName = "Annoucement";
                mChannelInfo = "Announcement to all parents";
                mProfileImageRes = R.drawable.announcement;

            }
            else if (!mKey.equals(Utils.ANNOUNCEMENT_CHILD ) && mUserType.equals(Utils.TEACHER)) {
                mReceiverName = messageChannel.getParentName();
                mChannelInfo = messageChannel.getStudentName()+"'s parent";
                mProfileImageRes = R.drawable.parent;
            }
            else {
                chatChannelLl.setVisibility(LinearLayout.GONE);
                chatChannelLl.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            }

            nameTv.setText(mReceiverName);
            infoTv.setText(mChannelInfo);
            civ.setImageResource(mProfileImageRes);
        }
    }
}
