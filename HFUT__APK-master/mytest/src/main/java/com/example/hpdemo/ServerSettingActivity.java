package com.example.hpdemo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.hpdemo.activity.ActivityCollector;
import com.example.hpdemo.activity.BaseActivity;

import java.security.PrivateKey;


public class ServerSettingActivity extends BaseActivity implements View.OnClickListener{
    private Button m_btnSave;
    private SharedPreferences m_spf;
    private EditText m_editTextIp;
    private EditText m_editTextPort;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setting);
        initUI();
    }



    private void initUI() {
        m_editTextIp = (EditText) findViewById(R.id.tv_server_ip);
        m_editTextPort = (EditText) findViewById(R.id.et_server_port);
        m_btnSave = (Button) findViewById(R.id.btn_server_save);
        m_btnSave.setOnClickListener(this);



    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("HELPMT_SERVER_IP", m_editTextIp.getText().toString());
        intent.putExtra("HELPMT_SERVER_PORT", m_editTextPort.getText().toString());
        finish();

    }




}


