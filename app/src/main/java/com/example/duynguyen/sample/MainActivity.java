package com.example.duynguyen.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    EditText passInput;
    Button btn;

    final public String nameExtra = "a";
    final public String passExtra = "b";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameInput = findViewById(R.id.name_et);
        passInput = findViewById(R.id.pass_et);
        btn = findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Popular Movies", Toast.LENGTH_LONG).show();
                String userName = String.valueOf(nameInput.getText());
                String pass = String.valueOf(passInput.getText());

                Intent intent = new Intent(getApplicationContext(),DetailedActivity.class);
                intent.putExtra(nameExtra, userName);
                intent.putExtra(passExtra, pass);

                startActivity(intent);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);

    }
}

