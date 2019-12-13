package com.example.hpdemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hpdemo.activity.BaseActivity;
import com.example.hpdemo.db.BayonetInfo;
import com.example.hpdemo.db.CameraInfo;
import com.example.hpdemo.db.LoginInfo;
import com.example.hpdemo.db.UserInfo;
import com.example.hpdemo.dialog.DeleteSureDialog;
import com.example.hpdemo.dialog.LoginUsersDialog;
import com.example.hpdemo.service.DownloadResult;
import com.example.hpdemo.service.HPAndroidSdkJniEx;
import com.example.hpdemo.service.MTResponse;
import com.example.hpdemo.utils.normalTools;
import com.example.hpdemo.view.LoadingDialog;
import com.helpsoft.HPAndroidCommandCallback;
import com.helpsoft.HPAndroidConnectionCallback;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.Variant;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.litepal.crud.DataSupport;

public class LoginActivity extends BaseActivity implements View.OnClickListener, HPAndroidCommandCallback, HPAndroidConnectionCallback {
    private static final String CMDTAG;

    private static final int LOGIN_FAILED = 5;

    private static final int LOGIN_SUCCESS = 3;

    public static final int PERMISSION_AUDIO = 4;

    public static final int PERMISSION_CAMERA = 2;

    public static final int PERMISSION_LOCATION = 1;

    public static final int PERMISSION_WTITESD = 3;

    private static final int STARTINTENT = 6;

    private static final String TAG;

    private static boolean m_init;

    private Animation anim;

    private int bayonetCountFromServer = 0;

    private ExecutorService cachedThreadPool;

    private List<Variant> cameraList = new ArrayList<Variant>();

    private int countGetOfflineMsg = 0;

    private int countGetSiteInfo = 0;

    private int countGetUserInfo = 0;

    private long exitTime = 0L;

    private TextView forgetPassword;

    private View gab;

    private boolean hashecked = false;

    private ImageView img;

    private String ip;

    private String last_recv_chat_time;

    private LinearLayout linear_password;

    private boolean loaded = false;

    private Button login;

    private Button login_offline;

    private boolean logined = false;

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
                    if (LoginActivity.this.mLoadingDialog != null)
                        LoginActivity.this.mLoadingDialog.dismiss();
                    LoginActivity.this.getUserInfo(1);
                    intent = new Intent((Context)LoginActivity.this, MainActivity.class);
                    LoginActivity.this.startActivity(intent);
                    return;
                case 5:
                    if (LoginActivity.this.mLoadingDialog != null) {
                        LoginActivity.this.mLoadingDialog.dismiss();
                        return;
                    }
                case 4:
                    LoginActivity.this.img.startAnimation(LoginActivity.this.reverse);
                    return;
                case 6:
                    LoginActivity.this.finish();
                    return;
                case 7:
                    if (!LoginActivity.this.isNetworkAvailable()) {
                        LoginActivity.this.showOffLoginDialog();
                        return;
                    }
                case 100:
                    break;
            }
            normalTools.showToast((Context)LoginActivity.this, "��������");
        }
    };

    private LoadingDialog mLoadingDialog;

    private CheckBox m_rememberPasswordCheckBox;

    private SharedPreferences m_spf;

    private String port;

    private TextView register;

    private Animation reverse;

    private LoginInfo selectLogininfo;

    private Button server;

    private String userName;

    private String userPassword;

    private ImageView user_image;

    private EditText user_name;

    private EditText user_password;

    private String username2;

    private String userpassword2;

    static  {
        StubApp.interface11(1);
        TAG = LoginActivity.class.getSimpleName();
        CMDTAG = TAG.toLowerCase();
        m_init = false;
    }

    private void backgroundAlpha(float paramFloat) {
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = paramFloat;
        getWindow().setAttributes(layoutParams);
    }

    private void getBayonetList(int paramInt) {
        if (paramInt <= 0)
            return;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10013, CMDTAG, variant);
    }

    private void getSiteServer(int paramInt) {
        if (paramInt <= 0)
            return;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10175, CMDTAG, variant);
    }

    private void getUserInfo(int paramInt) {
        if (paramInt <= 0)
            return;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10003, CMDTAG, variant);
    }

    private void initSiteStatusMap() {
        for (int i = 0; i < this.cameraList.size(); i++) {
            Variant variant = this.cameraList.get(i);
            String str1 = variant.getStringValue("DEVICE_ID");
            String str2 = variant.getStringValue("PARENT_DEVICE_ID");
            int j = variant.getInt32Value("STATUS");
            if (MyApplication.listCameraStatus.containsKey(str2)) {
                ((Map<String, Integer>)MyApplication.listCameraStatus.get(str2)).put(str1, Integer.valueOf(j));
            } else {
                HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
                hashMap.put(str1, Integer.valueOf(j));
                MyApplication.listCameraStatus.put(str2, hashMap);
            }
        }
    }

    private void initUserOfflineMsg(int paramInt) {
        if (paramInt <= 0)
            return;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        variant.addValue("TYPE", 0);
        variant.addValue("TARGET_ID", MyApplication.mUserId);
        variant.addValue("TIME", this.last_recv_chat_time);
        HPAndroidSdkJni.sendCommand(10241, CMDTAG, variant);
    }

    private void initView() {
        this.user_image = (ImageView)findViewById(2131624341);
        this.user_name = (EditText)findViewById(2131624343);
        this.user_password = (EditText)findViewById(2131624042);
        this.forgetPassword = (TextView)findViewById(2131624351);
        this.linear_password = (LinearLayout)findViewById(2131624344);
        this.register = (TextView)findViewById(2131624352);
        this.gab = findViewById(2131624345);
        this.login = (Button)findViewById(2131624349);
        this.login_offline = (Button)findViewById(2131624350);
        this.m_rememberPasswordCheckBox = (CheckBox)findViewById(2131624347);
        this.server = (Button)findViewById(2131624348);
        this.server.setOnClickListener(this);
        this.login.setOnClickListener(this);
        this.login_offline.setOnClickListener(this);
        this.forgetPassword.setOnClickListener(this);
        this.register.setOnClickListener(this);
        this.m_spf = getApplicationContext().getSharedPreferences("hpdemo.example.com", 0);
        this.userName = this.m_spf.getString("loginusername", "");
        this.userPassword = this.m_spf.getString("loginuserpassword", "");
        this.user_name.setText(this.userName);
        this.user_password.setText(this.userPassword);
        this.user_name.setSelection(this.user_name.length());
        this.user_password.setSelection(this.user_password.length());
        if (this.user_name.length() > 0 && this.user_password.length() > 0) {
            this.m_rememberPasswordCheckBox.setChecked(true);
            return;
        }
        this.m_rememberPasswordCheckBox.setChecked(false);
    }

    private void sendDownloadFile(String paramString) { MTResponse.getInstance().addDownload(paramString, new DownloadResult() {
        public void onDownloadProcess(String param1String, long param1Long) {}

        public void onDownloadResult(String param1String, boolean param1Boolean) {}
    }); }

    private void showOffLoginDialog() { (new DeleteSureDialog((Context)this, "��������������������������������������������", new DeleteSureDialog.DeleteSureDialogCallback() {
        public void onCancel() {}

        public void onSure() { LoginActivity.this.showSelectUser(); }
    })).show(); }

    private void showSelectUser() {
        final List loginInfos = DataSupport.findAll(LoginInfo.class, new long[0]);
        if (list.size() == 0) {
            normalTools.showToast((Context)this, "����������������");
            return;
        }
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final LoginUsersDialog dialog = new LoginUsersDialog((Context)this, list);
        if (list.size() > 5)
            loginUsersDialog.setHeight((int)(displayMetrics.density * 240.0F));
        loginUsersDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) {
                LoginActivity.access$702(LoginActivity.this, loginInfos.get(param1Int));
                MyApplication.loginOnline = false;
                MyApplication.getInstance().setLoadOnLineMap(false);
                MyApplication.mUserId = LoginActivity.this.selectLogininfo.getUserId();
                MyApplication.mLoginUserName = LoginActivity.this.selectLogininfo.getUserName();
                MyApplication.mLoginUserPwd = LoginActivity.this.selectLogininfo.getUserPwd();
                if (TextUtils.isEmpty(MyApplication.mLoginUserPwd))
                    MyApplication.mLoginUserPwd = "123456";
                MyApplication.mLoginName = LoginActivity.this.selectLogininfo.getName();
                MyApplication.mHeadPortrait = LoginActivity.this.selectLogininfo.getHeadImg();
                SharedPreferences.Editor editor = LoginActivity.this.m_spf.edit();
                editor.putString("username", MyApplication.mLoginUserName);
                editor.commit();
                Intent intent = new Intent((Context)LoginActivity.this, MTResponse.class);
                LoginActivity.this.startService(intent);
                intent = new Intent((Context)LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                LoginActivity.this.finish();
                dialog.dismiss();
            }
        });
        loginUsersDialog.show();
    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission((Context)this, "android.permission.ACCESS_FINE_LOCATION") != 0)
                ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.ACCESS_FINE_LOCATION" }, 1);
            if (ContextCompat.checkSelfPermission((Context)this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0)
                ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.WRITE_EXTERNAL_STORAGE" }, 3);
            if (ContextCompat.checkSelfPermission((Context)this, "android.permission.CAMERA") != 0)
                ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.CAMERA" }, 2);
            if (ContextCompat.checkSelfPermission((Context)this, "android.permission.RECORD_AUDIO") != 0) {
                ActivityCompat.requestPermissions((Activity)this, new String[] { "android.permission.RECORD_AUDIO" }, 4);
                return;
            }
        }
    }

    public boolean isNetworkAvailable() {
        if (this.cm != null) {
            NetworkInfo networkInfo = this.cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.getState() == NetworkInfo.State.CONNECTED)
                return true;
        }
        return false;
    }

    public void onClick(View paramView) {
        if (!checkClick(1000, paramView.getId()));
        switch (paramView.getId()) {
            case 2131624351:
            case 2131624352:
                return;
            default:
                return;
            case 2131624348:
                if (this.mLoadingDialog != null)
                    this.mLoadingDialog.dismiss();
                startActivity(new Intent((Context)this, ServerSettingActivity.class));
                return;
            case 2131624349:
                this.mLoadingDialog = new LoadingDialog((Context)this);
                this.ip = this.m_spf.getString("HELPMT_SERVER_IP", null);
                this.port = this.m_spf.getString("HELPMT_SERVER_PORT", null);
                if (this.ip == null || this.port == null) {
                    normalTools.showToast((Context)this, "��������������������");
                    this.mLoadingDialog.dismiss();
                    return;
                }
                this.username2 = this.user_name.getText().toString();
                this.userpassword2 = this.user_password.getText().toString();
                if (this.m_rememberPasswordCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = this.m_spf.edit();
                    editor.putString("loginusername", this.username2);
                    editor.putString("loginuserpassword", this.userpassword2);
                    editor.commit();
                } else {
                    SharedPreferences.Editor editor = this.m_spf.edit();
                    editor.putString("loginusername", "");
                    editor.putString("loginuserpassword", "");
                    editor.commit();
                }
                HPAndroidSdkJni.connectServer(this.ip, Integer.valueOf(this.port).intValue());
                return;
            case 2131624350:
                break;
        }
        showSelectUser();
    }

    public void onCommand(int paramInt, String paramString, Variant paramVariant) {

    }


        public void onConnect() {
            Variant variant = new Variant();
            variant.addValue("USER_NAME", this.username2);
            variant.addValue("PASSWORD", this.userpassword2);
            variant.addValue("TYPE", "PHONE");
            HPAndroidSdkJni.sendCommand(10001, CMDTAG, variant);
        }

        public void onConnectError() {
            if (MyApplication.loginOnline)
                normalTools.showToast(this.context, "��������������");
            this.login_offline.setVisibility(0);
            Message message = this.mHandler.obtainMessage();
            message.what = 5;
            this.mHandler.sendMessage(message);
        }

        protected native void onCreate(Bundle paramBundle);

        protected void onDestroy() {
            super.onDestroy();
            HPAndroidSdkJniEx.removeCommandCallbackEx(this);
            Log.i(TAG, this + "destroy");
        }

        public void onDisconnect() {
            if (MyApplication.loginOnline)
                normalTools.showToast(this.context, "��������������");
            Message message = this.mHandler.obtainMessage();
            message.what = 5;
            this.mHandler.sendMessage(message);
        }

        public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
            if (paramInt == 4 && paramKeyEvent.getAction() == 0) {
                if (System.currentTimeMillis() - this.exitTime > 2000L) {
                    Toast.makeText(getApplicationContext(), "����������������!", 0).show();
                    this.exitTime = System.currentTimeMillis();
                    return true;
                }
                HPAndroidSdkJni.releaseAndroidSdk();
                finish();
                System.exit(0);
                return true;
            }
            return super.onKeyDown(paramInt, paramKeyEvent);
        }

        protected void onResume() {
            super.onResume();
            if (!this.hashecked) {
                this.mHandler.sendEmptyMessageDelayed(7, 1000L);
                this.hashecked = true;
            }
            Log.i(TAG, this + "onResume");
        }

        protected void onStop() {
            super.onStop();
            Log.i(TAG, this + "onStop");
        }
    }
