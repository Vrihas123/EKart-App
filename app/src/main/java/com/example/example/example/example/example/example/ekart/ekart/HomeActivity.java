package com.example.example.example.example.example.example.ekart.ekart;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.example.example.example.example.example.ekart.ekart.helper.SharedPrefs;
import com.example.example.example.example.example.example.ekart.ekart.signup.view.SignUpActivity;

public class HomeActivity extends AppCompatActivity {

    private Button log_out_btn;
    private SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sharedPrefs = new SharedPrefs(this);
        log_out_btn = (Button) findViewById(R.id.logOutButton);

        log_out_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefs.setLogin(false);
                Intent i = new Intent(HomeActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
