package com.example.duynguyen.sample.RewardComp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duynguyen.sample.R;
import com.example.duynguyen.sample.model.CloudImage;
import com.example.duynguyen.sample.model.Student;
import com.example.duynguyen.sample.utils.AccountEvalAdapter;
import com.example.duynguyen.sample.utils.CustomToast;
import com.example.duynguyen.sample.utils.FirebaseUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardItemsAdapter extends RecyclerView.Adapter<RewardItemsAdapter.RewardItemVh> {
    List<CloudImage> mRewardItems = new ArrayList<>();
    List<CloudImage> mProfilePics = new ArrayList<>();
    private Student mStudent;
    private Context mContext;

    public RewardItemsAdapter(Context context) {
        mContext = context;
    }

    public void setmRewardItems(List<CloudImage> mRewardItems,Student mStudent) {
        this.mRewardItems = mRewardItems;
        this.mStudent = mStudent;
        mProfilePics = mStudent.getProfilePics();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RewardItemVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        return new RewardItemVh(inflater.inflate(R.layout.reward_item_vh, viewGroup, false));

    }

    @Override
    public void onBindViewHolder(@NonNull RewardItemVh rewardItemVh, int i) {
        rewardItemVh.setData(mRewardItems.get(i),i);
    }

    @Override
    public int getItemCount() {
        return mRewardItems.size();
    }

    public class RewardItemVh extends RecyclerView.ViewHolder {
        @BindView(R.id.reward_iv)
        ImageView rewardIv;
        @BindView(R.id.reward_name_tv)
        TextView rewardNameTv;
        @BindView(R.id.pts_tv)
        TextView ptsTv;
        @BindView(R.id.redeem_btn)
        Button redeemBtn;

        private View view;

        public RewardItemVh(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            view = itemView;
        }

        void setData (final CloudImage rewardItem, final int pos){
            Picasso.get().load(rewardItem.getUrl()).into(rewardIv);
            rewardNameTv.setText(rewardItem.getName());
            ptsTv.setText(rewardItem.getPts()+" pts");
            //Check if student has enough reward pts
            final int rewardPts = rewardItem.getPts();
            final int studentCurrentPts = mStudent.getRewardPts();
            if (rewardPts>studentCurrentPts){
                redeemBtn.setBackgroundColor(ContextCompat.getColor(mContext,R.color.grey));
                redeemBtn.setEnabled(false);
            }
            //Notified user that it is already redeemed.
            if (!rewardItem.getEnable()){
                redeemBtn.setBackgroundColor(ContextCompat.getColor(mContext,R.color.grey));
                redeemBtn.setText("Redeemed");
                redeemBtn.setEnabled(false);
            }

            redeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("RewardItemAdapter","redeem is clicked");
                    //Update current rewards pts for students
                    int remainingPts = studentCurrentPts - rewardPts;
                    mStudent.setRewardPts(remainingPts);

                    //add Profile Pic to student database
                        //set all cloudImage to false
                    for (CloudImage profilePic: mProfilePics){
                        profilePic.setEnable(false);
                    }
                    CloudImage newProfilePic = rewardItem;
                    newProfilePic.setEnable(true);
                    mProfilePics.add(newProfilePic);
                    mStudent.setProfilePics(mProfilePics);

                    //update reward Items
                    mRewardItems.get(pos).setEnable(false);
                    mStudent.setRewardPics(mRewardItems);

                    FirebaseUtils.updateStudentInfo(mStudent);
                }
            });

        }
    }
}
