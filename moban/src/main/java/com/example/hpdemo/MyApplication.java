package com.example.hpdemo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import com.example.hpdemo.chat.SaveGroupMsgContent;
import com.example.hpdemo.db.CheckVersionUpdateResponse;
import com.example.hpdemo.db.NotifcationDB;
import com.example.hpdemo.db.PatrolGpsinfo;
import com.example.hpdemo.db.SaveUserMsgContent;
import com.example.hpdemo.db.UserInfo;
import com.example.hpdemo.exception.CrashHandler;
import com.example.hpdemo.utils.LocationUtils;
import com.example.hpdemo.utils.normalTools;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.Variant;
import com.ytxt.logger.Logg;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.litepal.LitePalApplication;

@SuppressLint({"UseSparseArrays"})
public class MyApplication extends Application {
    public static String Api_Port;

    public static String Api_Url;

    public static String FILE_Port;

    public static String FILE_Url;

    public static String MapUrl;

    public static String SERVER_IP;

    private static final String TAG = "MyApplication";

    public static final String UID = "no";

    public static boolean autologin;

    public static int battery;

    public static List<Map<String, Integer>> bayonetStatus;

    private static ExecutorService cachedThreadPool;

    public static boolean callStatus;

    public static List<Map<String, Integer>> cameraStatus;

    public static String defaultDownloadMapListUrl;

    public static boolean isHaveAddingNotifcation;

    private static boolean isHaveNetConnect;

    public static boolean isHaveSaveingNotifcation;

    public static boolean isHaveWifi;

    public static boolean islogin;

    public static boolean isstarting;

    public static Map<String, Map<String, Integer>> listCameraStatus;

    public static boolean loginOnline;

    public static boolean loginout;

    public static Context mAppContext;

    private static HashMap<Integer, String> mCameraName;

    public static String mHeadPortrait;

    public static MyApplication mInstance;

    public static String mLoginName;

    public static UserInfo mLoginUserInfo;

    public static String mLoginUserName;

    public static String mLoginUserPwd;

    private static NotifcationDB mNotifcationDB;

    private static ArrayList<NotifcationInfoDataChangeCallback> mNotifcationInfDataChangeListeners;

    public static BlockingQueue<ContentValues> mSaveNotifcationQueue;

    private static HashMap<String, String> mStationAddr;

    private static HashMap<String, String> mStationName;

    public static BlockingQueue<ContentValues> mUntreatedNotifcationQueue;

    public static int mUserId;

    private static HashMap<Integer, String> mUserName;

    public static CheckVersionUpdateResponse mVersionUpdateResponse;

    private static List<ContentValues> saveNotifcationInfoDataList = Collections.synchronizedList(new ArrayList<ContentValues>());

    public static long startTime;

    public static int time;

    public static boolean videoStatus;

    public static boolean videoWinStatus;

    private boolean isLoadOnLineMap = false;

    private List<Map<Integer, Boolean>> listGroupGetOfflineMsg = new ArrayList<Map<Integer, Boolean>>();

    private List<Map<Integer, Integer>> listGroupNotifiMsg = new ArrayList<Map<Integer, Integer>>();

    private List<SaveGroupMsgContent> listSaveGroupMsgContent = new ArrayList<SaveGroupMsgContent>();

    private List<SaveUserMsgContent> listSaveUserMsgContent = new ArrayList<SaveUserMsgContent>();

    private List<Map<Integer, Integer>> listUserNotifiMsg = new ArrayList<Map<Integer, Integer>>();

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context param1Context, Intent param1Intent) {
            if (param1Intent.getAction().equals("android.intent.action.BATTERY_CHANGED"))
                MyApplication.battery = param1Intent.getIntExtra("level", 0);
        }
    };

    private SharedPreferences m_spf;

    private List<PatrolGpsinfo> offlineGpss = new ArrayList<PatrolGpsinfo>();

    static  {
        callStatus = false;
        videoStatus = false;
        videoWinStatus = false;
        loginout = false;
        bayonetStatus = new ArrayList<Map<String, Integer>>();
        cameraStatus = new ArrayList<Map<String, Integer>>();
        mStationAddr = new HashMap<String, String>();
        mUserName = new HashMap<Integer, String>();
        mStationName = new HashMap<String, String>();
        mCameraName = new HashMap<Integer, String>();
        mNotifcationInfDataChangeListeners = new ArrayList<NotifcationInfoDataChangeCallback>();
        mLoginUserName = "";
        mLoginUserPwd = "";
        mLoginName = "";
        mUserId = -1;
        mHeadPortrait = "";
        listCameraStatus = new HashMap<String, Map<String, Integer>>();
        isHaveAddingNotifcation = false;
        isHaveSaveingNotifcation = false;
        isHaveNetConnect = true;
        isHaveWifi = true;
        loginOnline = true;
        autologin = true;
        islogin = false;
        time = 5;
        battery = 0;
        FILE_Url = "192.168.2.110";
        FILE_Port = "53480";
        isstarting = true;
        startTime = 0L;
    }

    public static void addSaveData(ContentValues paramContentValues) {
        if (saveNotifcationInfoDataList != null) {
            if (saveNotifcationInfoDataList.add(paramContentValues)) {
                mUntreatedNotifcationQueue.add(paramContentValues);
                mSaveNotifcationQueue.add(paramContentValues);
                saveNotifcationToDB();
                dealNotifcationAndCallback();
            }
            return;
        }
        saveNotifcationInfoDataList = Collections.synchronizedList(new ArrayList<ContentValues>());
    }

    public static void dealNotifcationAndCallback() { // Byte code:
        //   0: ldc com/example/hpdemo/MyApplication
        //   2: monitorenter
        //   3: getstatic com/example/hpdemo/MyApplication.mUntreatedNotifcationQueue : Ljava/util/concurrent/BlockingQueue;
        //   6: invokeinterface isEmpty : ()Z
        //   11: ifne -> 137
        //   14: new java/util/ArrayList
        //   17: dup
        //   18: invokespecial <init> : ()V
        //   21: astore_2
        //   22: getstatic com/example/hpdemo/MyApplication.mUntreatedNotifcationQueue : Ljava/util/concurrent/BlockingQueue;
        //   25: invokeinterface size : ()I
        //   30: istore_1
        //   31: iconst_0
        //   32: istore_0
        //   33: iload_0
        //   34: iload_1
        //   35: if_icmpge -> 54
        //   38: getstatic com/example/hpdemo/MyApplication.mUntreatedNotifcationQueue : Ljava/util/concurrent/BlockingQueue;
        //   41: invokeinterface poll : ()Ljava/lang/Object;
        //   46: checkcast android/content/ContentValues
        //   49: astore_3
        //   50: aload_3
        //   51: ifnonnull -> 124
        //   54: getstatic com/example/hpdemo/MyApplication.isHaveAddingNotifcation : Z
        //   57: ifne -> 145
        //   60: getstatic com/example/hpdemo/MyApplication.mNotifcationInfDataChangeListeners : Ljava/util/ArrayList;
        //   63: invokevirtual iterator : ()Ljava/util/Iterator;
        //   66: astore_3
        //   67: aload_3
        //   68: invokeinterface hasNext : ()Z
        //   73: ifeq -> 145
        //   76: aload_3
        //   77: invokeinterface next : ()Ljava/lang/Object;
        //   82: checkcast com/example/hpdemo/MyApplication$NotifcationInfoDataChangeCallback
        //   85: astore #4
        //   87: aload #4
        //   89: ifnull -> 67
        //   92: aload #4
        //   94: aload_2
        //   95: invokeinterface onAddDataChange : (Ljava/util/ArrayList;)V
        //   100: aload #4
        //   102: getstatic com/example/hpdemo/MyApplication.saveNotifcationInfoDataList : Ljava/util/List;
        //   105: invokeinterface size : ()I
        //   110: invokeinterface onDataSize : (I)V
        //   115: goto -> 67
        //   118: astore_2
        //   119: ldc com/example/hpdemo/MyApplication
        //   121: monitorexit
        //   122: aload_2
        //   123: athrow
        //   124: aload_2
        //   125: aload_3
        //   126: invokevirtual add : (Ljava/lang/Object;)Z
        //   129: pop
        //   130: iload_0
        //   131: iconst_1
        //   132: iadd
        //   133: istore_0
        //   134: goto -> 33
        //   137: ldc 'MyApplication'
        //   139: ldc_w 'mUntreatedNotifcationQueue.isEmpty() = true'
        //   142: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
        //   145: ldc com/example/hpdemo/MyApplication
        //   147: monitorexit
        //   148: return
        // Exception table:
        //   from	to	target	type
        //   3	31	118	finally
        //   38	50	118	finally
        //   54	67	118	finally
        //   67	87	118	finally
        //   92	115	118	finally
        //   124	130	118	finally
        //   137	145	118	finally }

        public static Context getAppContext() {
            if (mAppContext == null)
                mAppContext = mInstance.getApplicationContext();
            return mAppContext;
        }

        public static String getCameraName(int paramInt) {
            String str2 = mCameraName.get(Integer.valueOf(paramInt));
            String str1 = str2;
            if (str2 == null)
                str1 = "";
            return str1;
        }

        public static MyApplication getInstance() { return mInstance; }

        public static boolean getIsHaveNetConnect() {
            Logg.i("tag", "getIsHaveNetConnect() ��������������= " + isHaveNetConnect);
            return isHaveNetConnect;
        }

        public static boolean getIsHaveWifiConnect() {
            Logg.i("tag", "getIsHaveWifiConnect() ����������wifi = " + isHaveWifi);
            return isHaveWifi;
        }

        public static String getStationAddr(String paramString) {
            String str = mStationAddr.get(paramString);
            paramString = str;
            if (str == null)
                paramString = "";
            return paramString;
        }

        public static String getStationName(String paramString) {
            String str = mStationName.get(paramString);
            paramString = str;
            if (str == null)
                paramString = "";
            return paramString;
        }

        public static List<ContentValues> getsaveNotifcationInfoDataList() { return (saveNotifcationInfoDataList == null) ? new ArrayList<ContentValues>() : saveNotifcationInfoDataList; }

        public static boolean removeNotifcationInfo(ContentValues paramContentValues) {
            boolean bool = saveNotifcationInfoDataList.remove(paramContentValues);
            if (saveNotifcationInfoDataList != null && bool)
                for (NotifcationInfoDataChangeCallback notifcationInfoDataChangeCallback : mNotifcationInfDataChangeListeners) {
                    if (notifcationInfoDataChangeCallback != null)
                        notifcationInfoDataChangeCallback.onDataSize(saveNotifcationInfoDataList.size());
                }
            return bool;
        }

        public static void saveNotifcationToDB() { // Byte code:
            //   0: ldc com/example/hpdemo/MyApplication
            //   2: monitorenter
            //   3: getstatic com/example/hpdemo/MyApplication.mSaveNotifcationQueue : Ljava/util/concurrent/BlockingQueue;
            //   6: invokeinterface isEmpty : ()Z
            //   11: ifne -> 93
            //   14: new java/util/ArrayList
            //   17: dup
            //   18: invokespecial <init> : ()V
            //   21: astore_2
            //   22: getstatic com/example/hpdemo/MyApplication.mSaveNotifcationQueue : Ljava/util/concurrent/BlockingQueue;
            //   25: invokeinterface size : ()I
            //   30: istore_1
            //   31: iconst_0
            //   32: istore_0
            //   33: iload_0
            //   34: iload_1
            //   35: if_icmpge -> 54
            //   38: getstatic com/example/hpdemo/MyApplication.mSaveNotifcationQueue : Ljava/util/concurrent/BlockingQueue;
            //   41: invokeinterface poll : ()Ljava/lang/Object;
            //   46: checkcast android/content/ContentValues
            //   49: astore_3
            //   50: aload_3
            //   51: ifnonnull -> 80
            //   54: getstatic com/example/hpdemo/MyApplication.isHaveSaveingNotifcation : Z
            //   57: ifne -> 76
            //   60: getstatic com/example/hpdemo/MyApplication.cachedThreadPool : Ljava/util/concurrent/ExecutorService;
            //   63: new com/example/hpdemo/MyApplication$1
            //   66: dup
            //   67: aload_2
            //   68: invokespecial <init> : (Ljava/util/ArrayList;)V
            //   71: invokeinterface execute : (Ljava/lang/Runnable;)V
            //   76: ldc com/example/hpdemo/MyApplication
            //   78: monitorexit
            //   79: return
            //   80: aload_2
            //   81: aload_3
            //   82: invokevirtual add : (Ljava/lang/Object;)Z
            //   85: pop
            //   86: iload_0
            //   87: iconst_1
            //   88: iadd
            //   89: istore_0
            //   90: goto -> 33
            //   93: ldc 'MyApplication'
            //   95: ldc_w 'mSaveNotifcationQueue.isEmpty() = true'
            //   98: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
            //   101: goto -> 76
            //   104: astore_2
            //   105: ldc com/example/hpdemo/MyApplication
            //   107: monitorexit
            //   108: aload_2
            //   109: athrow
            // Exception table:
            //   from	to	target	type
            //   3	31	104	finally
            //   38	50	104	finally
            //   54	76	104	finally
            //   80	86	104	finally
            //   93	101	104	finally }

            private void sendAndSavegps() {
                Date date = new Date();
                String str = normalTools.getCurrentTime(date.getTime());
                if (islogin) {
                    ByteBuffer byteBuffer = ByteBuffer.allocate(524288);
                    Variant variant = new Variant();
                    variant.addValue("TIME", str);
                    variant.addValue("VOLTAGE_GRADE", battery);
                    variant.addValue("LONGITUDE", LocationUtils.getLo_longitude());
                    variant.addValue("LATITUDE", LocationUtils.getLo_latitude());
                    if (!Variant.VariantToBuffer(variant, byteBuffer))
                        return;
                    byteBuffer.flip();
                    HPAndroidSdkJni.sendCommand(10293, "TID", byteBuffer.array(), byteBuffer.remaining());
                }
                PatrolGpsinfo patrolGpsinfo = new PatrolGpsinfo();
                patrolGpsinfo.setX(LocationUtils.getLo_longitude());
                patrolGpsinfo.setY(LocationUtils.getLo_latitude());
                patrolGpsinfo.setUserid(mUserId);
                patrolGpsinfo.setDate(date);
                if (isstarting) {
                    patrolGpsinfo.setStarting(isstarting);
                    startTime = date.getTime();
                    isstarting = false;
                }
                patrolGpsinfo.save();
                Intent intent = new Intent();
                intent.setAction("TRACE");
                intent.putExtra("traceid", patrolGpsinfo.getId());
                sendBroadcast(intent);
                if (!islogin)
                    synchronized (this.offlineGpss) {
                        this.offlineGpss.add(patrolGpsinfo);
                        return;
                    }
            }

            public static void setCameraName(int paramInt, String paramString) { mCameraName.put(Integer.valueOf(paramInt), paramString); }

            public static void setIsHaveNetConnect(boolean paramBoolean) {
                Logg.i("tag", "setIsHaveNetConnect() �������������� = " + paramBoolean);
                isHaveNetConnect = paramBoolean;
            }

            public static void setIsHaveWifiConnect(boolean paramBoolean) { Logg.i("tag", "setIsHaveWifiConnect() ����������wifi = " + paramBoolean); }

            public static void setNotifcationInfoDataChangeCallbackListener(NotifcationInfoDataChangeCallback paramNotifcationInfoDataChangeCallback) { mNotifcationInfDataChangeListeners.add(paramNotifcationInfoDataChangeCallback); }

            public static void setStationAddr(String paramString1, String paramString2) { mStationAddr.put(paramString1, paramString2); }

            public static void setStationName(String paramString1, String paramString2) { mStationName.put(paramString1, paramString2); }

            public static void setTime(int paramInt) { time = paramInt; }

            public static void setsaveNotifcationInfoDataList(List<ContentValues> paramList) { saveNotifcationInfoDataList = paramList; }

            public static void updateNotifactionInfoByNotifcationID(String paramString) { updateNotifactionInfoByNotifcationID(paramString, ""); }

            public static void updateNotifactionInfoByNotifcationID(String paramString1, String paramString2) {
                Object object;
                ContentValues contentValues = null;
                Iterator<ContentValues> iterator = saveNotifcationInfoDataList.iterator();
                while (true) {
                    object = contentValues;
                    if (iterator.hasNext()) {
                        object = iterator.next();
                        if (object != null) {
                            String str = object.getAsString(Constants.NOTIFCATION_ID);
                            if (!TextUtils.isEmpty(str) && str.equals(paramString1)) {
                                String str1 = String.valueOf(System.currentTimeMillis());
                                object.put("isDeal", Boolean.valueOf(true));
                                object.put("dealTheWay", paramString2);
                                object.put("report_time", str1);
                                contentValues = new ContentValues();
                                contentValues.put("isDeal", Boolean.valueOf(true));
                                contentValues.put("deal_the_way", paramString2);
                                contentValues.put("report_time", str1);
                                Iterator<NotifcationInfoDataChangeCallback> iterator1 = mNotifcationInfDataChangeListeners.iterator();
                                while (true) {
                                    object = contentValues;
                                    if (iterator1.hasNext()) {
                                        object = iterator1.next();
                                        if (object != null)
                                            object.onDataUpdate();
                                        continue;
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                        continue;
                    }
                    break;
                }
                if (mNotifcationDB != null && object != null)
                    mNotifcationDB.update(paramString1, (ContentValues)object);
            }

            public static void updateNotifcationINfo(String paramString) {
                String str1;
                Object object;
                ContentValues contentValues = null;
                String str2 = "";
                Iterator<ContentValues> iterator = saveNotifcationInfoDataList.iterator();
                while (true) {
                    object = contentValues;
                    str1 = str2;
                    if (iterator.hasNext()) {
                        object = iterator.next();
                        if (Constants.NOTIFACTION_TYPE.HOT_POINT_NOTIFCATION == object.getAsInteger("NOTIFACTION_TYPE").intValue()) {
                            str1 = object.getAsString("HOTSPOT_NO");
                            if (!TextUtils.isEmpty(str1) && str1.equals(paramString)) {
                                str1 = String.valueOf(System.currentTimeMillis());
                                object.put("isDeal", Boolean.valueOf(true));
                                object.put("report_time", str1);
                                ContentValues contentValues1 = new ContentValues();
                                contentValues1.put("isDeal", Boolean.valueOf(true));
                                contentValues1.put("report_time", str1);
                                String str = object.getAsString(Constants.NOTIFCATION_ID);
                                Iterator<NotifcationInfoDataChangeCallback> iterator1 = mNotifcationInfDataChangeListeners.iterator();
                                while (true) {
                                    object = contentValues1;
                                    str1 = str;
                                    if (iterator1.hasNext()) {
                                        object = iterator1.next();
                                        if (object != null)
                                            object.onDataUpdate();
                                        continue;
                                    }
                                    break;
                                }
                                break;
                            }
                        }
                        continue;
                    }
                    break;
                }
                if (mNotifcationDB != null && object != null)
                    mNotifcationDB.update(str1, (ContentValues)object);
            }

            protected void attachBaseContext(Context paramContext) {
                super.attachBaseContext(paramContext);
                MultiDex.install((Context)this);
            }

            public Map<String, Map<String, Integer>> getListCameraStatus() { return listCameraStatus; }

            public List<Map<Integer, Boolean>> getListGroupGetOfflineMsg() { return this.listGroupGetOfflineMsg; }

            public List<Map<Integer, Integer>> getListGroupNotifiMsg() { return this.listGroupNotifiMsg; }

            public List<SaveGroupMsgContent> getListSaveGroupMsgContent() { return this.listSaveGroupMsgContent; }

            public List<SaveUserMsgContent> getListSaveUserMsgContent() { return this.listSaveUserMsgContent; }

            public List<Map<Integer, Integer>> getListUserNotifiMsg() { return this.listUserNotifiMsg; }

            public void getLoginUserName() { mLoginUserName = getApplicationContext().getSharedPreferences("hpdemo.example.com", 0).getString("username", ""); }

            public String getServerIP() {
                String str = this.m_spf.getString("HELPMT_SERVER_IP", "60.173.167.105");
                SERVER_IP = str;
                return str;
            }

            public void initLogg() {
                Logg.setDebug(true);
                Logg.setPrint(true);
                Logg.setPrintFile(true);
                Logg.setMaxLogFileLength(10485760);
                Logg.setLogFilePath(Environment.getExternalStorageDirectory() + "/tpk/log.txt");
                Logg.i("MyApplication", "log init success");
            }

            public void initisloadmaponline() { this.isLoadOnLineMap = this.m_spf.getBoolean("isLoadOnLineMap", false); }

            public boolean isLoadOnLineMap() { return this.isLoadOnLineMap; }

            public void onConfigurationChanged(Configuration paramConfiguration) { super.onConfigurationChanged(paramConfiguration); }

            public void onCreate() {
                super.onCreate();
                Logg.d("MyApplication", "onCreate()");
                mInstance = this;
                LitePalApplication.initialize((Context)this);
                this.m_spf = getApplicationContext().getSharedPreferences("hpdemo.example.com", 0);
                mNotifcationDB = new NotifcationDB(getApplicationContext());
                mUntreatedNotifcationQueue = new ArrayBlockingQueue<ContentValues>(30);
                mSaveNotifcationQueue = new ArrayBlockingQueue<ContentValues>(30);
                cachedThreadPool = Executors.newFixedThreadPool(3);
                CrashHandler.getInstance().init((Context)this);
                LocationUtils.initBDManager((Context)this);
                initLogg();
                this.isLoadOnLineMap = this.m_spf.getBoolean("isLoadOnLineMap", false);
                time = this.m_spf.getInt("GPS_TIME", 5);
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
                intentFilter.addAction(Constants.NOTIFACTION_TYPE.GPS_UPLOAD_CHANGED);
                registerReceiver(this.mBroadcastReceiver, intentFilter);
                startSendgps();
            }

            public void onLowMemory() {
                Logg.d("MyApplication", "onLowMemory() ");
                super.onLowMemory();
            }

            public void onTerminate() {
                Logg.d("MyApplication", "onTerminate() ");
                super.onTerminate();
            }

            public void onTrimMemory(int paramInt) { super.onTrimMemory(paramInt); }

            public void setListCameraStatus(Map<String, Map<String, Integer>> paramMap) { listCameraStatus = paramMap; }

            public void setListGroupGetOfflineMsg(List<Map<Integer, Boolean>> paramList) { this.listGroupGetOfflineMsg = paramList; }

            public void setListGroupNotifiMsg(List<Map<Integer, Integer>> paramList) { this.listGroupNotifiMsg = paramList; }

            public void setListSaveGroupMsgContent(List<SaveGroupMsgContent> paramList) { this.listSaveGroupMsgContent = paramList; }

            public void setListSaveUserMsgContent(List<SaveUserMsgContent> paramList) { this.listSaveUserMsgContent = paramList; }

            public void setListUserNotifiMsg(List<Map<Integer, Integer>> paramList) { this.listUserNotifiMsg = paramList; }

            public void setLoadOnLineMap(boolean paramBoolean) { this.isLoadOnLineMap = paramBoolean; }

            public void startSendgps() { (new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            if (LocationUtils.isLocationByBaidu())
                                MyApplication.this.sendAndSavegps();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                        try {
                            Thread.sleep((MyApplication.time * 1000));
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                    }
                }
            })).start(); }

            public void uploadOfflineGpss() { (new Thread() {
                public void run() {
                    super.run();
                    synchronized (MyApplication.this.offlineGpss) {
                        Iterator<PatrolGpsinfo> iterator = MyApplication.this.offlineGpss.iterator();
                        while (iterator.hasNext()) {
                            if (MyApplication.islogin) {
                                PatrolGpsinfo patrolGpsinfo = iterator.next();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(524288);
                                Variant variant = new Variant();
                                variant.addValue("TIME", normalTools.getCurrentTime(patrolGpsinfo.getDate().getTime()));
                                variant.addValue("VOLTAGE_GRADE", MyApplication.battery);
                                variant.addValue("LONGITUDE", patrolGpsinfo.getX());
                                variant.addValue("LATITUDE", patrolGpsinfo.getY());
                                if (Variant.VariantToBuffer(variant, byteBuffer)) {
                                    byteBuffer.flip();
                                    HPAndroidSdkJni.sendCommand(10293, "TID", byteBuffer.array(), byteBuffer.remaining());
                                    iterator.remove();
                                }
                                continue;
                            }
                            return;
                        }
                    }
                    /* monitor exit ClassFileLocalVariableReferenceExpression{type=ObjectType{java/lang/Object}, name=SYNTHETIC_LOCAL_VARIABLE_1} */
                }
            }).start(); }

            public static interface NotifcationInfoDataChangeCallback {
                void onAddDataChange(ArrayList<ContentValues> param1ArrayList);

                void onDataSize(int param1Int);

                void onDataUpdate();
            }
        }
