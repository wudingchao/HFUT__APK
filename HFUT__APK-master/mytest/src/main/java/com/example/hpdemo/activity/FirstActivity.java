package com.example.hpdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hpdemo.R;
import com.example.hpdemo.WelcomeActivity;

public  class FirstActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Intent intent = new Intent(FirstActivity.this, WelcomeActivity.class);
        startActivity(intent);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {

    }
}
