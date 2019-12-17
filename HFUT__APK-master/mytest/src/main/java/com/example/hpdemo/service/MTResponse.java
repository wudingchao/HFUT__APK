package com.example.hpdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.helpsoft.HPAndroidConnectionCallback;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.HPAndroidStreamDataCallback;
import com.helpsoft.Variant;

public class MTResponse extends Service implements HPAndroidConnectionCallback,HPAndroidStreamDataCallback, Runnable{
    private boolean hasChecked = false;

    private String ip;

    private boolean isConnect = false;

    private boolean isForeground = true;

    String last_recv_chat_time;

    private long lasttryconnect = 0L;

    //private DownloadInfo m_currentDownloadInfo;

    //private UploadInfo m_currentUploadInfo;

    private int m_nThreadSleepCount = 0;

   // private SharedPreferences m_spf;

    private Thread m_thread;

    //private ObjectListen mtResponse;

    private String port;

   // private Queue<UploadInfo> uploadQueue;

    private String userName;

    private String userPassword;

    public IBinder onBind(Intent paramIntent) { return null; }

    public Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            super.handleMessage(param1Message);
        }
    };

    @Override
    public void onConnect() {
        this.isConnect = true;
        this.handler.sendEmptyMessage(1);

        Variant variant = new Variant();
        variant.addValue("USER_NAME", this.userName);
        variant.addValue("PASSWORD", this.userPassword);
        variant.addValue("RELOGIN", true);
        HPAndroidSdkJni.sendCommand(10001, "mtresponse", variant);

    }

    @Override
    public void onConnectError() {

    }

    @Override
    public void onDisconnect() {

    }

    @Override
    public void onStreamData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, int paramInt5) {

    }

    @Override
    public void run() {

    }
}
