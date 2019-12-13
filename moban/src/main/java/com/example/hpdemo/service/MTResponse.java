package com.example.hpdemo.service;

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
import com.example.hpdemo.MyApplication;
import com.example.hpdemo.audioMedia.MediaManager;
import com.example.hpdemo.db.BayonetInfo;
import com.example.hpdemo.db.CameraInfo;
import com.example.hpdemo.db.UserInfo;
import com.example.hpdemo.dialog.DeleteSureDialog;
import com.example.hpdemo.utils.WindowUtils;
import com.example.hpdemo.utils.normalTools;
import com.helpsoft.HPAndroidCommandCallback;
import com.helpsoft.HPAndroidConnectionCallback;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.HPAndroidStreamDataCallback;
import com.helpsoft.Variant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MTResponse extends Service implements HPAndroidCommandCallback, HPAndroidConnectionCallback, HPAndroidStreamDataCallback, Runnable {
    public static final String ACTION = "MTResponseBroadCast";

    public static final String ACTION_CONNECTIONED = "ACTION_CONNECTIONED";

    public static final String ACTION_NO_CONNECTION = "ACTION_NO_CONNECTION";

    public static final int BACKGROUND = 102;

    public static final String CMDTAG = "mtresponse";

    public static final String CONNECTACTION = "MTResponseConnectBroadCast";

    public static final int FOREGROUND = 101;

    public static final String SITEDEALEDACTION = "MTResponseBroadCast";

    private static MTResponse sm_instance;

    private int bayonetCountFromServer = 0;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context param1Context, Intent param1Intent) {
            if (param1Intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                NetworkInfo networkInfo = ((ConnectivityManager) MTResponse.this.getSystemService("connectivity")).getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() && !MyApplication.loginOnline && MTResponse.this.isForeground) {
                    if (MyApplication.autologin) {
                        MTResponse.this.reConnect(100);
                        return;
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
           /* (new DeleteSureDialog((Context)MTResponse.this, "������������������������������?", new DeleteSureDialog.DeleteSureDialogCallback() {
                public void onCancel() {}

                public void onSure() { MTResponse.this.reConnect(100); }
            })).show();*/
        }
    };

    private ExecutorService cachedThreadPool;

    private List<Variant> cameraList = new ArrayList<Variant>();

    private int countGetOfflineMsg = 0;

    private int countGetSiteInfo = 0;

    private int countGetUserInfo = 0;

    private Queue<DownloadInfo> downloadQueue;

    public Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            super.handleMessage(param1Message);
            if (!MyApplication.loginout) {
                switch (param1Message.what) {
                    default:
                        return;
                    case 1:
                        if (MTResponse.this.isForeground && MyApplication.loginOnline) {
                            normalTools.showToast(MTResponse.this.getApplicationContext(), "��������������");
                            return;
                        }
                        return;
                    case 2:
                        if (MTResponse.this.isForeground && MyApplication.loginOnline) {
                            normalTools.showToast(MTResponse.this.getApplicationContext(), "��������,��������...");
                            return;
                        }
                        return;
                    case 3:
                        if (MTResponse.this.isForeground && MyApplication.loginOnline) {
                            normalTools.showToast(MTResponse.this.getApplicationContext(), "��������,��������...");
                            return;
                        }
                        return;
                    case 4:
                        removeMessages(4);
                        if (!MTResponse.this.isConnect) {
                            HPAndroidSdkJni.connectServer(MTResponse.this.ip, Integer.valueOf(MTResponse.this.port).intValue());
                            return;
                        }
                        return;
                    case 101:
                        MTResponse.access$002(MTResponse.this, true);
                        return;
                    case 102:
                        break;
                }
                MTResponse.access$002(MTResponse.this, false);
                return;
            }
        }
    };

    private boolean hasChecked = false;

    private String ip;

    private boolean isConnect = false;

    private boolean isForeground = true;

    String last_recv_chat_time;

    private long lasttryconnect = 0L;

    private DownloadInfo m_currentDownloadInfo;

    private UploadInfo m_currentUploadInfo;

    private int m_nThreadSleepCount = 0;

    private SharedPreferences m_spf;

    private Thread m_thread;

    private ObjectListen mtResponse;

    private String port;

    private Queue<UploadInfo> uploadQueue;

    private String userName;

    private String userPassword;

    private void getBayonetList(int paramInt) {
        if (paramInt <= 0)
            return;
        if (paramInt == 1)
            this.bayonetCountFromServer = 0;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10013, "mtresponse", variant);
    }

    public static MTResponse getInstance() {
        return sm_instance;
    }

    private void getSiteServer(int paramInt) {
        if (paramInt <= 0)
            return;
        if (paramInt == 1) {
            this.cameraList.clear();
            this.countGetSiteInfo = 0;
        }
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10175, "mtresponse", variant);
    }

    private void getUserInfo(int paramInt) {
        if (paramInt <= 0)
            return;
        if (paramInt == 1)
            this.countGetUserInfo = 0;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10003, "mtresponse", variant);
    }

    private void initSiteStatusMap() {
        for (int i = 0; i < this.cameraList.size(); i++) {
            Variant variant = this.cameraList.get(i);
            String str1 = variant.getStringValue("DEVICE_ID");
            String str2 = variant.getStringValue("PARENT_DEVICE_ID");
            int j = variant.getInt32Value("STATUS");
            if (MyApplication.listCameraStatus.containsKey(str2)) {
                ((Map<String, Integer>) MyApplication.listCameraStatus.get(str2)).put(str1, Integer.valueOf(j));
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
        if (paramInt == 1)
            this.countGetOfflineMsg = 0;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        variant.addValue("TYPE", 0);
        variant.addValue("TARGET_ID", MyApplication.mUserId);
        variant.addValue("TIME", this.last_recv_chat_time);
        HPAndroidSdkJni.sendCommand(10241, "mtresponse", variant);
    }

    private void playAudio(String paramString) {
        MediaManager.playSound(paramString, new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer param1MediaPlayer) {
            }
        });
    }

    private void reConnect(int paramInt) {
        this.handler.sendEmptyMessageDelayed(4, paramInt);
    }

    private void sendDownloadFile(String paramString) {
        sm_instance.addDownload(paramString, new DownloadResult() {
            public void onDownloadProcess(String param1String, long param1Long) {
            }

            public void onDownloadResult(String param1String, boolean param1Boolean) {
            }
        });
    }

    private void sendDownloadFileChannel(String paramString) {
        sm_instance.addDownload(paramString, new DownloadResult() {
            public void onDownloadProcess(String param1String, long param1Long) {
            }

            public void onDownloadResult(String param1String, boolean param1Boolean) {
                if (param1Boolean)
                    MTResponse.this.playAudio(param1String);
            }
        });
    }

    public boolean DownloadStep(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
        Log.d("guo", "m_currentDownloadInfo.m_nStreamId:" + this.m_currentDownloadInfo.m_nStreamId);
        if (this.m_currentDownloadInfo != null && this.m_currentDownloadInfo.m_nStreamId != 0) {
            if (this.m_currentDownloadInfo.Update(paramArrayOfbyte, paramInt1, paramInt2) == true) {
                this.m_currentDownloadInfo = null;
                return false;
            }
        } else if (this.m_currentDownloadInfo == null) {
            this.m_currentDownloadInfo = this.downloadQueue.poll();
            if (this.m_currentDownloadInfo != null) {
                Variant variant = new Variant();
                variant.addValue("FILE_PATH", this.m_currentDownloadInfo.m_serverFileName);
                HPAndroidSdkJni.sendCommand(10113, "MTresponse", variant);
                return true;
            }
        }
        return false;
    }

    public boolean UploadSetp() {
        if (this.m_currentUploadInfo != null && this.m_currentUploadInfo.m_nStreamId != 0) {
            if (this.m_currentUploadInfo.Update() == true) {
                this.m_currentUploadInfo = null;
                return false;
            }
            return true;
        }
        if (this.m_currentUploadInfo == null) {
            this.m_currentUploadInfo = this.uploadQueue.poll();
            if (this.m_currentUploadInfo != null) {
                Variant variant = new Variant();
                String[] arrayOfString = this.m_currentUploadInfo.m_fileName.split("/");
                long l = (new Date()).getTime();
                int[] arrayOfInt = normalTools.randomArray(1000, 10000, 8);
                int i = (int) (Math.random() * 7.0D);
                String str = this.userName + arrayOfInt[i] + l;
                variant.addValue("UPLOAD_TYPE", this.m_currentUploadInfo.m_type);
                variant.addValue("FILE_NAME", str + arrayOfString[arrayOfString.length - 1]);
                HPAndroidSdkJni.sendCommand(10115, "TID", variant);
                return true;
            }
        }
        return false;
    }

    public void addDownload(String paramString, DownloadResult paramDownloadResult) {
        DownloadInfo downloadInfo = new DownloadInfo();
        downloadInfo.m_serverFileName = paramString;
        downloadInfo.m_downloadResult = paramDownloadResult;
        this.downloadQueue.add(downloadInfo);
        this.m_currentDownloadInfo = this.downloadQueue.poll();
        if (this.m_currentDownloadInfo != null) {
            Variant variant = new Variant();
            variant.addValue("FILE_PATH", this.m_currentDownloadInfo.m_serverFileName);
            HPAndroidSdkJni.sendCommand(10113, "MTresponse", variant);
        }
    }

    public void addUpload(String paramString1, String paramString2, UploadResult paramUploadResult) {
        UploadInfo uploadInfo = new UploadInfo();
        uploadInfo.m_fileName = paramString1;
        uploadInfo.m_type = paramString2;
        uploadInfo.m_uploadResult = paramUploadResult;
        this.uploadQueue.add(uploadInfo);
    }

    public IBinder onBind(Intent paramIntent) {
        return null;
    }

    public void onCommand(int paramInt, String paramString, Variant paramVariant) { // Byte code:


        public void onConnect () {
            this.isConnect = true;
            this.handler.sendEmptyMessage(1);
            String str = normalTools.getCurrentTime();
            Log.d("guo", "����������������������" + str);
            Variant variant = new Variant();
            variant.addValue("USER_NAME", this.userName);
            variant.addValue("PASSWORD", this.userPassword);
            variant.addValue("RELOGIN", true);
            HPAndroidSdkJni.sendCommand(10001, "mtresponse", variant);
        }

        public void onConnectError () {
            this.handler.sendEmptyMessage(2);
            this.isConnect = false;
            MyApplication.islogin = false;
            String str = normalTools.getCurrentTime();
            Log.d("guo", "����������������" + str);
            reConnect(5000);
        }

        public void onCreate () {
            sm_instance = this;
            super.onCreate();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            registerReceiver(this.broadcastReceiver, intentFilter);
            Log.i("LoginActivity", "serveronCreate");
        }

        public void onDestroy () {
            super.onDestroy();
            unregisterReceiver(this.broadcastReceiver);
            HPAndroidSdkJniEx.removeCommandCallbackEx(this);
            HPAndroidSdkJni.removeStreamDataCallback(this);
            HPAndroidSdkJni.disconnect();
            Log.i("LoginActivity", "ServeronDestroy");
        }

        public void onDisconnect () {
            this.handler.sendEmptyMessage(3);
            MyApplication.islogin = false;
            Intent intent = new Intent("MTResponseConnectBroadCast");
            intent.putExtra("IsConnect", false);
            sendBroadcast(intent);
            WindowUtils.getInstance().hidePopupWindow();
            this.isConnect = false;
            String str = normalTools.getCurrentTime();
            Log.d("guo", "����������������" + str);
            reConnect(5000);
        }

        public int onStartCommand (Intent paramIntent,int paramInt1, int paramInt2){
            HPAndroidSdkJni.setConnectionCallback(this);
            HPAndroidSdkJniEx.addCommandCallbackEx(this);
            HPAndroidSdkJni.addStreamDataCallback(this);
            this.cachedThreadPool = Executors.newFixedThreadPool(10);
            this.uploadQueue = new ArrayBlockingQueue<UploadInfo>(100);
            this.downloadQueue = new ArrayBlockingQueue<DownloadInfo>(100);
            this.m_thread = new Thread(this);
            this.m_thread.start();
            this.m_spf = getApplicationContext().getSharedPreferences("hpdemo.example.com", 0);
            this.ip = this.m_spf.getString("HELPMT_SERVER_IP", "60.173.167.105");
            this.port = this.m_spf.getString("HELPMT_SERVER_PORT", "8066");
            this.userName = MyApplication.mLoginUserName;
            this.userPassword = MyApplication.mLoginUserPwd;
            (new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(5000L);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        label15:
                        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) MTResponse.this.getSystemService("activity")).getRunningAppProcesses()) {
                            if (runningAppProcessInfo.processName.equals(MTResponse.this.getPackageName())) {
                                if (runningAppProcessInfo.importance != 100) {
                                    if (runningAppProcessInfo.importance == 200)
                                        break label15;
                                } else {
                                    MTResponse.this.handler.sendEmptyMessage(101);
                                    continue;
                                }
                                MTResponse.this.handler.sendEmptyMessage(102);
                            }
                        }
                    }
                }
            })).start();
            return super.onStartCommand(paramIntent, paramInt1, paramInt2);
        }

        public void onStreamData ( int paramInt1, int paramInt2, int paramInt3, int paramInt4,
        byte[] paramArrayOfbyte, int paramInt5){
            if (this.m_currentDownloadInfo != null && paramInt1 == 7 && this.m_currentDownloadInfo.m_nStreamId == paramInt2)
                DownloadStep(paramArrayOfbyte, paramInt5, paramInt4);
        }

        public void run () {
            while (true) {
                boolean bool = false;
                if (UploadSetp() == true)
                    bool = true;
                if (!bool) {
                    this.m_nThreadSleepCount--;
                    if (this.m_nThreadSleepCount <= 0) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        this.m_nThreadSleepCount = 10;
                    }
                }
            }
        }
    }
}
