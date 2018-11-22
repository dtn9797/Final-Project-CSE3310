package com.example.duynguyen.sample.RewardComp;

import android.support.annotation.NonNull;
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
import com.example.duynguyen.sample.utils.AccountEvalAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RewardItemsAdapter extends RecyclerView.Adapter<RewardItemsAdapter.RewardItemVh> {
    List<CloudImage> mRewardItems = new ArrayList<>();

    public RewardItemsAdapter() {
    }

    public void setmRewardItems(List<CloudImage> mRewardItems) {
        this.mRewardItems = mRewardItems;
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
        rewardItemVh.setData(mRewardItems.get(i));
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

        public RewardItemVh(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        void setData (CloudImage rewardItem){
            Picasso.get().load(rewardItem.getUrl()).into(rewardIv);
            rewardNameTv.setText(rewardItem.getName());
            ptsTv.setText(rewardItem.getPts()+" pts");
            redeemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("RewardItemAdapter","redeem is clicked");
                }
            });

        }
    }
}
