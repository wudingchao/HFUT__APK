package com.example.hpdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.hpdemo.activity.BaseActivity;


public class ServerSettingActivity extends BaseActivity  {
    private Button m_btnSave;
    private SharedPreferences m_spf;
    private EditText m_editTextIp;

    private EditText m_editTextPort = (EditText)findViewById(R.id.et_server_port);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_setting);
        this.m_editTextIp = (EditText)findViewById(R.id.tv_server_ip);
        m_btnSave = (Button) findViewById(R.id.btn_server_save);
        m_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServerSettingActivity.this, LoginActivity.class);
                startActivity(intent);
                SharedPreferences.Editor editor = m_spf.edit();
                editor.putString("HELPMT_SERVER_IP", m_editTextIp.getText().toString());
                editor.putString("HELPMT_SERVER_PORT", m_editTextPort.getText().toString());
                editor.commit();
            }
        });
    }

    protected void onDestroy () {
        super.onDestroy();

        SharedPreferences.Editor editor = this.m_spf.edit();
        editor.putString("HELPMT_SERVER_IP", this.m_editTextIp.getText().toString());
        editor.putString("HELPMT_SERVER_PORT", this.m_editTextPort.getText().toString());
        editor.commit();
        //MyApplication.getInstance().getServerIP();
        finish();
    }

    //protected native void onCreate(Bundle paramBundle);


}
