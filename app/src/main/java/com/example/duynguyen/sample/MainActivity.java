package com.example.duynguyen.sample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth= FirebaseAuth.getInstance();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new AccountFragment()).commit();

            navigationView.setCheckedItem(R.id.nav_account);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {

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
                        new ChatFragment()).commit();
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
                mAuth.signOut();
                finish();


        }

        mDrawerLayout.closeDrawer(GravityCompat.START);

        return true;

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
        {

            mDrawerLayout.closeDrawer(GravityCompat.START);

        }
        else
        {

            super.onBackPressed();

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mToggle.onOptionsItemSelected(item))
        {

            return true;

        }

        return super.onOptionsItemSelected(item);

    }

}
