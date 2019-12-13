package com.example.hpdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.hpdemo.activity.BaseActivity;
import com.example.hpdemo.service.MTResponse;
import com.helpsoft.HPAndroidCommandCallback;
import com.helpsoft.HPAndroidConnectionCallback;
import com.helpsoft.Variant;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.helpsoft.HPAndroidSdkJni.connectServer;
import static com.helpsoft.HPAndroidSdkJni.sendCommand;

public class LoginActivity extends BaseActivity implements View.OnClickListener , HPAndroidCommandCallback, HPAndroidConnectionCallback {

    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message param1Message) {
            Intent intent;
            switch (param1Message.what) {
                default:
                    return;
                case 3:
                    Log.i(TAG, LoginActivity.this + "LOGIN_SUCCESS");
                    removeMessages(3);
                    intent = new Intent((Context)LoginActivity.this, MTResponse.class);
                    LoginActivity.this.startService(intent);
                   /* if (LoginActivity.this.mLoadingDialog != null)
                        LoginActivity.this.mLoadingDialog.dismiss();
                    LoginActivity.this.getUserInfo(1);*/
                    intent = new Intent((Context)LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    return;
                case 5:
                    /*if (LoginActivity.this.mLoadingDialog != null) {
                        LoginActivity.this.mLoadingDialog.dismiss();
                        return;
                    }*/
                case 4:
                    //LoginActivity.this.img.startAnimation(LoginActivity.this.reverse);
                    return;
                case 6:
                    LoginActivity.this.finish();
                    return;
                case 7:
                    /*if (!LoginActivity.this.isNetworkAvailable()) {
                        LoginActivity.this.showOffLoginDialog();
                        return;
                    }*/
                case 100:
                    break;
            }
            //Toast((Context)LoginActivity.this, "��������");
            Toast.makeText(LoginActivity.this,"login success",Toast.LENGTH_SHORT).show();
        }
    };
    private static final String CMDTAG = "AA";


    private String username2;
    private String userpassword2;

    private CheckBox m_rememberPasswordCheckBox;


    private SharedPreferences m_spf;

    private String port;

    private TextView register;

    private Animation reverse;

    //private LoginInfo selectLogininfo;
    private Button login;

    private Button server;

    private Button login_offline;


    private String ip;

    private String userName;

    private String userPassword;

    private ImageView user_image;

    private EditText user_name;

    private EditText user_password;
    private TextView forgetPassword;
    private LinearLayout linear_password;


    public static native boolean sendCommand ( int paramInt1, String paramString,
                                               byte[] paramArrayOfbyte, int paramInt2);

    public static boolean sendCommand ( int paramInt, String paramString, Variant paramVariant){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);
        if (!Variant.VariantToBuffer(paramVariant, byteBuffer))
            return false;
        byteBuffer.flip();
        return sendCommand(paramInt, paramString, byteBuffer.array(), byteBuffer.remaining());
    }


    private boolean m_b = false;

    public boolean getBoolValue () {
        return this.m_b;
    }

    public byte getType () {
        return this.m_type;
    }

    private byte m_type = 1;



    private void initUI() {


        this.user_image =  findViewById(R.id.image_user);
        this.user_name =  findViewById(R.id.edt_user);
        this.user_password = findViewById(R.id.edt_password);
        this.forgetPassword =  findViewById(R.id.forget_password);
        this.linear_password =  findViewById(R.id.linear_password);
        this.login_offline =  findViewById(R.id.login_offline);
        this.register =  findViewById(R.id.register);

        this.m_rememberPasswordCheckBox =  findViewById(R.id.checkbox_remember_password);
        this.server =  findViewById(R.id.btn_server);
        server.setOnClickListener(this);
        this.login =  findViewById(R.id.login);
        login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_server:
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(),ServerSettingActivity.class);
                startActivity(intent);
                return;
            case R.id.login:

                Bundle bundle = getIntent().getExtras();

                this.ip = bundle.getString("HELPMT_SERVER_IP", null);
                this.port = bundle.getString("HELPMT_SERVER_PORT", null);
                this.username2 = this.user_name.getText().toString();
                this.userpassword2 = this.user_password.getText().toString();
                if (this.m_rememberPasswordCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = this.m_spf.edit();
                    m_spf = getApplicationContext().getSharedPreferences("hpdemo.example.com", 0);
                    editor.putString("loginusername", this.username2);
                    editor.putString("loginuserpassword", this.userpassword2);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = this.m_spf.edit();
                    editor.putString("loginusername", "");
                    editor.putString("loginuserpassword", "");
                    editor.commit();
                }
                connectServer(this.ip, Integer.valueOf(this.port).intValue());
                return;
            case R.id.login_offline:
                break;
        }

    }

    public void onConnect() {
        Variant variant = new Variant();
        variant.addValue("USER_NAME", this.username2);
        variant.addValue("PASSWORD", this.userpassword2);
        variant.addValue("TYPE", "PHONE");
        sendCommand(10001, CMDTAG, variant);
    }

    @Override
    public void onConnectError() {

    }

    @Override
    public void onDisconnect() {

    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, this + "onStop");
    }

    @Override
    public void onCommand(int paramInt, String paramString, Variant paramVariant) {

    }
}
