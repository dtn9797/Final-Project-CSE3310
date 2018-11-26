package com.example.duynguyen.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class QRCodeFragment extends Fragment {
    private User mCurrentUser;
    private DatabaseReference mRef;

    ImageView qrIv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_qr, container, false);
        qrIv= view.findViewById(R.id.qr_iv);

        mRef = FirebaseDatabase.getInstance().getReference();
        getCurrentUser();
        return view;
    }

    public void getCurrentUser () {
        String currentUserId = FirebaseAuth.getInstance().getUid();
        mRef.child(Utils.USERS_CHILD).child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mCurrentUser = dataSnapshot.getValue(User.class);
                setUpView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpView() {
        String code="";
        switch (mCurrentUser.getUserType()){
            case Utils.TEACHER:
                code = mCurrentUser.getClassId();
                break;
            case Utils.STUDENT:
                code = mCurrentUser.getClassId()+mCurrentUser.getfUserId();
                    break;
        }
        Picasso.get().load("https://chart.googleapis.com/chart?chl="+code+"&chs=200x200&cht=qr&chld=H%7C0").into(qrIv);
    }
}
