package com.example.duynguyen.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.RecyclerViewApdater;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<User> mUsers = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);

        populateUsers();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        RecyclerViewApdater recyclerViewApdater = new RecyclerViewApdater(mUsers);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerViewApdater);


    }

    private void populateUsers() {
        User user0 = new User("name0","","123");
        User user1 = new User("name1","","234");
        mUsers.add(user0);
        mUsers.add(user1);

    }


}
