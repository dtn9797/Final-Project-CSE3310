package com.example.duynguyen.sample;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.duynguyen.sample.BehaviorEvaluationComp.EvaluationFragment;
import com.example.duynguyen.sample.ChatComp.ChatMenuFragment;
import com.example.duynguyen.sample.RewardComp.RewardsFragment;
import com.example.duynguyen.sample.model.ClassRoom;
import com.example.duynguyen.sample.model.User;
import com.example.duynguyen.sample.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String mCurrentUserId;
    String mClassId;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCurrentUserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        setUpView(savedInstanceState);
        storeInfoLocally();



    }


    private void storeInfoLocally() {
        final SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
        final SharedPreferences.Editor prefsEditor = mPrefs.edit();
        final Gson gson = new Gson();
        //get classRoom and currentUser and store them locally in SharedPreferences
        mDatabase.child(Utils.USERS_CHILD).child(mCurrentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUser = dataSnapshot.getValue(User.class);
                String json = gson.toJson(mUser);
                prefsEditor.putString(Utils.CURRENT_USER_KEY, json);
                prefsEditor.apply();

                mClassId = mUser.getClassId();
                //subscribe to announcement topic
                if(mUser.getUserType().equals(Utils.PARENT)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Utils.ANNOUNCEMENT_CHILD + mClassId);
                    FirebaseMessaging.getInstance().subscribeToTopic(Utils.EVALUATION_CHILD+ mClassId);
                }

                DatabaseReference classRef = FirebaseDatabase.getInstance().getReference().child(Utils.CLASSES_CHILD).
                        child(mUser.getClassId());
                classRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //get class info & store in in shared preferences
                        ClassRoom classRoom = dataSnapshot.getValue(ClassRoom.class);
                        String json = gson.toJson(classRoom);
                        prefsEditor.putString(Utils.CLASS_ROOM_KEY, json);
                        prefsEditor.apply();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setUpView(Bundle savedInstanceState) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AccountFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_account);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_account:
                //getActionBar().setTitle("Account settings");
                getSupportActionBar().setTitle("Account Settings");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AccountFragment()).commit();
                break;

            case R.id.nav_chat:
                //getActionBar().setTitle("Chat");
                getSupportActionBar().setTitle("Chat");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChatMenuFragment()).commit();
                break;

            case R.id.nav_evaluation:
                //getActionBar().setTitle("Behavior evaluation");
                getSupportActionBar().setTitle("Behavior evaluation");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new EvaluationFragment()).commit();
                break;

            case R.id.nav_rewards:
                //getActionBar().setTitle("Rewards");
                getSupportActionBar().setTitle("Rewards");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new RewardsFragment()).commit();
                break;

            case R.id.nav_settings:
                //getActionBar().setTitle("Settings");
                getSupportActionBar().setTitle("Settings");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingFragment()).commit();
                break;

            case R.id.nav_logout:
                //Unsubscribe to topic
                if(mUser.getUserType().equals(Utils.PARENT)) {
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Utils.ANNOUNCEMENT_CHILD + mClassId);
                    FirebaseMessaging.getInstance().unsubscribeFromTopic(Utils.EVALUATION_CHILD + mClassId);
                }
                mAuth.signOut();
                finish();


        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {

            mDrawerLayout.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item)) {

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

}
