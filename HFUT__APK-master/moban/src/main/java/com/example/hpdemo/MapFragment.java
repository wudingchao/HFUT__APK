package com.example.hpdemo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapView;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.MarkerSymbol;
import com.esri.core.symbol.PictureMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.example.hpdemo.activity.RangingActivity;
import com.example.hpdemo.audioMedia.AudioRecordButtonMap;
import com.example.hpdemo.audioMedia.MediaManager;
import com.example.hpdemo.service.HPAndroidSdkJniEx;
import com.example.hpdemo.utils.DataParse;
import com.example.hpdemo.utils.DistanceCount;
import com.example.hpdemo.utils.LocationUtils;
import com.example.hpdemo.utils.MapUtils;
import com.example.hpdemo.utils.normalTools;
import com.helpsoft.HPAndroidCommandCallback;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.Variant;
import com.ytxt.logger.Logg;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressLint({"NewApi"})
public class MapFragment extends ContentFragment implements View.OnClickListener, MapUtils.getRealGpsListener, HPAndroidCommandCallback, AudioRecordButtonMap.AudioFinishRecorderListener {
    private static final int CENTERPOINT = 2;

    private static final int REALGPS = 1;

    private static final int SHOWDEFAULTLOCATION = 3;

    private static final String TAG = MapFragment.class.getSimpleName();

    private static String filePath = "";

    private static boolean isShowDefaultView = false;

    private int battery;

    private ExecutorService cachedThreadPool;

    private LinearLayout clear;

    private boolean clickable = true;

    private ConnectivityManager cm;

    DataParse dataParse;

    private ImageButton dingwei;

    private ImageButton fangda;

    private GsmCellLocation gcl = null;

    Graphic graphic;

    private GraphicsLayer graphicsLayer;

    private Handler handler = new Handler() {
        public void handleMessage(Message param1Message) {
            List<Double> list;
            switch (param1Message.what) {
                default:
                    return;
                case 1:
                    list = (List)param1Message.obj;
                    MapFragment.this.showLocation(((Double)list.get(0)).doubleValue(), ((Double)list.get(1)).doubleValue(), ((Double)list.get(2)).doubleValue());
                    return;
                case 4:
                    MapFragment.this.showLocationAnim(0);
                    return;
                case 5:
                    MapFragment.this.showLocationAnim(1);
                    return;
                case 6:
                    MapFragment.access$202(MapFragment.this, true);
                    return;
                case 2:
                    if (MapFragment.this.mapView.isLoaded()) {
                        Point point = MapFragment.this.mapView.getCenter();
                        point = MapFragment.this.mapView.toScreenPoint(point);
                        point = (Point)GeometryEngine.project((Geometry)MapFragment.this.mapView.toMapPoint(point), MapFragment.this.mapView.getSpatialReference(), SpatialReference.create(4326));
                        MapFragment.this.mapUtils.showCenterAt(point.getX() + 1.5D, point.getY() - 1.5D, MapFragment.this.mapView);
                        return;
                    }
                case 3:
                    break;
            }
            String str1 = MapFragment.this.spf.getString("DEFAULT_LONGITUDE", "109.0");
            String str2 = MapFragment.this.spf.getString("DEFAULT_LATITUDE", "23.0");
            double d1 = normalTools.str2Double(str1);
            double d2 = normalTools.str2Double(str2);
            MapFragment.this.showLocation(d1, d2, 0.0D);
        }
    };

    private boolean isNetWork;

    private boolean isSendGps = false;

    private volatile boolean isThreadRun;

    private boolean isfirst = true;

    public int lastvideid = -1;

    private double latitudeLocation;

    private LinearLayout lin_btn;

    private LocationManager locationManager;

    Point location_auto;

    private double longitudeLocation;

    private float mBear;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context param1Context, Intent param1Intent) {
            String str = param1Intent.getAction();
            if (str.equals("android.intent.action.BATTERY_CHANGED")) {
                MapFragment.access$902(MapFragment.this, param1Intent.getIntExtra("level", 0));
                return;
            }
            if (str.equals(Constants.NOTIFACTION_TYPE.GPS_UPLOAD_CHANGED)) {
                MapFragment.access$1002(MapFragment.this, param1Intent.getBooleanExtra("checkstatus", false));
                return;
            }
        }
    };

    private double mLatitude;

    private double mLongtitude;

    private MapUtils mapUtils;

    private MapView mapView;

    private SimpleMarkerSymbol markerSymbol;

    private PictureMarkerSymbol pictureAnim;

    private PictureMarkerSymbol pictureMarkerSymbol;

    private LinearLayout ranging;

    private LinearLayout ranging_gon;

    private LinearLayout ranging_line;

    private LinearLayout ranging_point;

    private int sendGpsCount;

    private int sendGpsIntervalTime = 5;

    private SharedPreferences spf;

    private String state = "";

    private ImageButton suoxiao;

    private TelephonyManager tm = null;

    private int upFileStreamID;

    private String userName = "";

    private View view;

    private void clearGraphicslayer() { this.graphicsLayer.removeAll(); }

    private void drawRealWeather() {
        int j = this.dataParse.getDataCount();
        ArrayList<Point> arrayList = new ArrayList();
        for (int i = 0; i < j; i++) {
            Log.d("guo", "count:" + j);
            float f1 = this.dataParse.getLongitude(i);
            float f2 = this.dataParse.getLatitude(i);
            arrayList.add(new Point(f1, f2));
            if (Integer.valueOf(this.dataParse.getFireDangerRating(i)).intValue() >= 4) {
                this.markerSymbol = new SimpleMarkerSymbol(-65536, 10, SimpleMarkerSymbol.STYLE.CIRCLE);
            } else {
                this.markerSymbol = new SimpleMarkerSymbol(-16711936, 10, SimpleMarkerSymbol.STYLE.CIRCLE);
                this.pictureMarkerSymbol = new PictureMarkerSymbol(getResources().getDrawable(2130837885));
            }
            DistanceCount.drawPoint(Double.valueOf(f1).doubleValue(), Double.valueOf(f2).doubleValue(), (MarkerSymbol)this.pictureMarkerSymbol, this.mapView, this.graphicsLayer);
        }
        DistanceCount.drawPolygon(arrayList, this.mapView, this.graphicsLayer);
        DistanceCount.drawLineOfTwoPointWithDistance(new Point(Double.valueOf(this.dataParse.getLongitude(0)).doubleValue(), Double.valueOf(this.dataParse.getLatitude(0)).doubleValue()), new Point(Double.valueOf(this.dataParse.getLongitude(1)).doubleValue(), Double.valueOf(this.dataParse.getLatitude(1)).doubleValue()), this.mapView, this.graphicsLayer);
    }

    private void getLocation(int paramInt) {}

    private void hidDefaultView() {
        this.ranging_point.setVisibility(4);
        this.ranging_line.setVisibility(4);
    }

    private void initView() {
        this.ranging = (LinearLayout)this.view.findViewById(2131624590);
        this.ranging_point = (LinearLayout)this.view.findViewById(2131624587);
        this.ranging_line = (LinearLayout)this.view.findViewById(2131624588);
        this.ranging_gon = (LinearLayout)this.view.findViewById(2131624589);
        this.clear = (LinearLayout)this.view.findViewById(2131624591);
        this.lin_btn = (LinearLayout)this.view.findViewById(2131624592);
        this.mapView = (MapView)this.view.findViewById(2131624585);
        this.fangda = (ImageButton)this.view.findViewById(2131624774);
        this.suoxiao = (ImageButton)this.view.findViewById(2131624775);
        this.dingwei = (ImageButton)this.view.findViewById(2131624776);
        this.state = this.mapView.retainState();
        this.ranging.setOnClickListener(this);
        this.ranging_point.setOnClickListener(this);
        this.ranging_line.setOnClickListener(this);
        this.ranging_gon.setOnClickListener(this);
        this.clear.setOnClickListener(this);
        this.fangda.setOnClickListener(this);
        this.suoxiao.setOnClickListener(this);
        this.dingwei.setOnClickListener(this);
    }

    private void loadOffLineMap() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("activity.hpdemo.example.com", 0);
        sharedPreferences.getString("off_x", "0");
        sharedPreferences.getString("off_y", "0");
        if (!this.mapUtils.loadOffLineMap(this.mapView))
            normalTools.showToast((Context)getActivity(), "������������");
    }

    private void sendRequestGpsUpload(double paramDouble1, double paramDouble2) {
        String str = normalTools.getCurrentTime();
        ByteBuffer byteBuffer = ByteBuffer.allocate(524288);
        Variant variant = new Variant();
        variant.addValue("TIME", str);
        variant.addValue("VOLTAGE_GRADE", this.battery);
        variant.addValue("LONGITUDE", paramDouble1);
        variant.addValue("LATITUDE", paramDouble2);
        if (!Variant.VariantToBuffer(variant, byteBuffer))
            return;
        byteBuffer.flip();
        HPAndroidSdkJni.sendCommand(10293, "TID", byteBuffer.array(), byteBuffer.remaining());
    }

    private void sendResponseCloseGpsUpload(int paramInt1, int paramInt2) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(524288);
        Variant variant = new Variant();
        variant.addValue("RESULT", paramInt1);
        variant.addValue("USER_ID", paramInt2);
        if (!Variant.VariantToBuffer(variant, byteBuffer))
            return;
        byteBuffer.flip();
        HPAndroidSdkJni.sendCommand(10303, "TID", byteBuffer.array(), byteBuffer.remaining());
    }

    private void sendResponseOpenGpsUpload(int paramInt1, int paramInt2) {
        if (paramInt1 == 1) {
            this.isThreadRun = false;
            getLocation(this.sendGpsIntervalTime * 1000);
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(524288);
        Variant variant = new Variant();
        variant.addValue("RESULT", paramInt1);
        variant.addValue("USER_ID", paramInt2);
        if (!Variant.VariantToBuffer(variant, byteBuffer))
            return;
        byteBuffer.flip();
        HPAndroidSdkJni.sendCommand(10302, "TID", byteBuffer.array(), byteBuffer.remaining());
    }

    private void setTimerTask() {
        (new Timer()).schedule(new TimerTask() {
            public void run() { MapFragment.this.handler.sendEmptyMessage(4); }
        },  1000L, 2000L);
        (new Timer()).schedule(new TimerTask() {
            public void run() { MapFragment.this.handler.sendEmptyMessage(5); }
        },  0L, 2000L);
    }

    private void showDefaultView() {
        this.ranging_point.setVisibility(0);
        this.ranging_line.setVisibility(0);
    }

    private void showLocation(double paramDouble1, double paramDouble2, double paramDouble3) {
        this.sendGpsCount++;
        Log.d("guo", "��������" + paramDouble1 + "_" + paramDouble2 + "_sendGpsCount" + this.sendGpsCount);
        if (this.sendGpsCount > 500)
            this.sendGpsCount = 0;
        if (this.isSendGps);
    }

    private void showLocationAnim(int paramInt) {
        clearGraphicslayer();
        if (paramInt == 0) {
            this.pictureAnim = new PictureMarkerSymbol(getResources().getDrawable(2130837746));
        } else if (paramInt == 1) {
            this.pictureAnim = new PictureMarkerSymbol(getResources().getDrawable(2130837850));
        }
        DistanceCount.drawPoint(this.mLongtitude, this.mLatitude, (MarkerSymbol)this.pictureAnim, this.mapView, this.graphicsLayer);
    }

    private int str2Int(String paramString) {
        try {
            return Integer.valueOf(paramString).intValue();
        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
            return -2;
        }
    }

    public void getRealGps(double paramDouble1, double paramDouble2, float paramFloat) {
        ArrayList<Double> arrayList = new ArrayList();
        Log.d("guo", "����������" + paramDouble1 + "__" + paramDouble2);
        this.mLongtitude = paramDouble1;
        this.mLatitude = paramDouble2;
        arrayList.add(Double.valueOf(paramDouble1));
        arrayList.add(Double.valueOf(paramDouble2));
        this.mBear = paramFloat;
        Logg.d(TAG, "bear = " + this.mBear);
        arrayList.add(Double.valueOf(paramFloat));
        Message message = this.handler.obtainMessage();
        message.what = 1;
        message.obj = arrayList;
        this.handler.sendMessage(message);
    }

    public void initMapView() {
        this.mapView.setMapBackground(16777215, 16777215, 30.0F, 1.0F);
        this.mapView.setResolution(80.0D);
        this.isNetWork = this.mapUtils.isNetworkAvailable();
        Log.d("guo", this.isNetWork + "");
        this.isNetWork = this.mapUtils.isNetworkAvailable();
        if (MyApplication.getInstance().isLoadOnLineMap()) {
            if (!this.isNetWork)
                normalTools.showToast((Context)getActivity(), "������");
            this.mapUtils.loadOnLineMap(this.mapView);
        } else {
            loadOffLineMap();
        }
        Log.d("guo", "Scale" + this.mapView.getScale());
        this.graphicsLayer = this.mapUtils.getGraphicsLayer();
        this.markerSymbol = new SimpleMarkerSymbol(-65536, 10, SimpleMarkerSymbol.STYLE.CIRCLE);
    }

    public void onClick(View paramView) {
        Bundle bundle;
        switch (paramView.getId()) {
            default:
                checkClick(1000, paramView.getId());
                bundle = new Bundle();
                switch (paramView.getId()) {
                    default:
                        return;
                    case 2131624590:
                        if (isShowDefaultView) {
                            hidDefaultView();
                            isShowDefaultView = false;
                            return;
                        }
                        if (!isShowDefaultView) {
                            showDefaultView();
                            isShowDefaultView = true;
                            return;
                        }
                    case 2131624587:
                        bundle.putInt("rangStyle", 0);
                        intent = new Intent((Context)getActivity(), RangingActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return;
                    case 2131624588:
                        bundle.putInt("rangStyle", 1);
                        intent = new Intent((Context)getActivity(), RangingActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        return;
                    case 2131624589:
                        break;
                }
                break;
            case 2131624591:
                clearGraphicslayer();
                return;
            case 2131624774:
                this.mapView.zoomin();
                return;
            case 2131624775:
                this.mapView.zoomout();
                return;
            case 2131624776:
                LocationUtils.center();
                return;
        }
        bundle.putInt("rangStyle", 2);
        Intent intent = new Intent((Context)getActivity(), RangingActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void onCommand(int paramInt, String paramString, Variant paramVariant) { // Byte code:
        //   0: iload_1
        //   1: lookupswitch default -> 52, 10116 -> 152, 10294 -> 117, 10298 -> 360, 10302 -> 53, 10304 -> 99
        //   52: return
        //   53: aload_0
        //   54: iconst_1
        //   55: putfield isSendGps : Z
        //   58: aload_0
        //   59: aload_3
        //   60: ldc_w 'INTERVAL_TIME'
        //   63: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   66: putfield sendGpsIntervalTime : I
        //   69: aload_3
        //   70: ldc_w 'USER_ID'
        //   73: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   76: pop
        //   77: aload_0
        //   78: getfield mapUtils : Lcom/example/hpdemo/utils/MapUtils;
        //   81: invokevirtual isGpsEnable : ()Z
        //   84: ifne -> 87
        //   87: aload_0
        //   88: getfield mapUtils : Lcom/example/hpdemo/utils/MapUtils;
        //   91: invokevirtual isNetworkAvailable : ()Z
        //   94: ifne -> 98
        //   97: return
        //   98: return
        //   99: aload_0
        //   100: iconst_0
        //   101: putfield isSendGps : Z
        //   104: aload_0
        //   105: iconst_1
        //   106: aload_3
        //   107: ldc_w 'USER_ID'
        //   110: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   113: invokespecial sendResponseCloseGpsUpload : (II)V
        //   116: return
        //   117: aload_3
        //   118: ldc_w 'RESULT'
        //   121: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   124: istore_1
        //   125: ldc 'guo'
        //   127: new java/lang/StringBuilder
        //   130: dup
        //   131: invokespecial <init> : ()V
        //   134: ldc_w 'GPS�����������

        public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
            this.view = paramLayoutInflater.inflate(2130968730, paramViewGroup, false);
            filePath = MapUtils.getDefaultFilePath();
            this.spf = getActivity().getApplicationContext().getSharedPreferences("hpdemo.example.com", 0);
            this.userName = this.spf.getString("username", "");
            this.sendGpsIntervalTime = this.spf.getInt("GPS_TIME", 5);
            this.isSendGps = this.spf.getBoolean("checkstatus", false);
            this.longitudeLocation = normalTools.str2Double(this.spf.getString("longitudeLocation", "107.00"));
            this.latitudeLocation = normalTools.str2Double(this.spf.getString("latitudeLocation", "30.00"));
            this.cachedThreadPool = Executors.newSingleThreadExecutor();
            this.locationManager = (LocationManager)getActivity().getSystemService("location");
            this.cm = (ConnectivityManager)getActivity().getSystemService("connectivity");
            this.tm = (TelephonyManager)getActivity().getSystemService("phone");
            this.mapUtils = new MapUtils((Context)getActivity(), this.locationManager, this.cm);
            initView();
            this.dataParse = new DataParse(filePath + "/txt1.txt");
            return this.view;
        }

        public void onDestroyView() {
            super.onDestroyView();
            Log.d("guo", "onDestroyView");
            this.isThreadRun = false;
            this.mapUtils.closeGps();
        }

        public void onFinished(float paramFloat, String paramString) {
            int i = (int)paramFloat;
            String[] arrayOfString = paramString.split("/");
            String str1 = arrayOfString[arrayOfString.length - 1];
            long l = (new Date()).getTime();
            int[] arrayOfInt = normalTools.randomArray(1000, 10000, 8);
            int j = (int)(Math.random() * 7.0D);
            String str2 = this.userName + arrayOfInt[j] + l;
            Variant variant = new Variant();
            variant.addValue("FILE_EXT_NAME", "");
            variant.addValue("UPLOAD_TYPE", "BroadcastAudioFile");
            variant.addValue("FILE_NAME", str2 + str1);
            HPAndroidSdkJni.sendCommand(10115, paramString + "/" + i, variant);
        }

        public void onHiddenChanged(boolean paramBoolean) {
            super.onHiddenChanged(paramBoolean);
            Log.d("guo", "BayonetFragmentonHiddenChanged" + paramBoolean);
            if (paramBoolean) {
                HPAndroidSdkJniEx.removeCommandCallbackEx(this);
                this.isThreadRun = false;
                MediaManager.release();
                LocationUtils.BDstop();
                return;
            }
            if (!paramBoolean) {
                HPAndroidSdkJniEx.addCommandCallbackEx(this);
                Log.d(TAG, "isSendGps--" + this.isSendGps);
                MediaManager.resume();
                this.sendGpsCount = 0;
                getLocation(this.sendGpsIntervalTime * 5000);
                LocationUtils.initMap(this.mapView, this.mapUtils.getLocationLayer(), null, this.handler, false, false);
                return;
            }
        }

        public void onPause() {
            MediaManager.pause();
            HPAndroidSdkJniEx.removeCommandCallbackEx(this);
            MediaManager.release();
            this.isThreadRun = false;
            this.lin_btn.removeAllViews();
            this.mapView.removeAll();
            LocationUtils.BDstop();
            super.onPause();
        }

        public void onResume() {
            MediaManager.resume();
            HPAndroidSdkJniEx.addCommandCallbackEx(this);
            super.onResume();
            initMapView();
            if (MyApplication.loginOnline) {
                AudioRecordButtonMap audioRecordButtonMap = new AudioRecordButtonMap((Context)getActivity());
                audioRecordButtonMap.setImageResource(2130837804);
                if (Build.VERSION.SDK_INT >= 17) {
                    audioRecordButtonMap.setBackground(null);
                } else {
                    audioRecordButtonMap.setBackgroundDrawable(null);
                }
                this.lin_btn.addView((View)audioRecordButtonMap);
                audioRecordButtonMap.setAudioFinishRecorderListener(this);
            }
            Log.d(TAG, "onResume");
            this.isThreadRun = true;
            getLocation(this.sendGpsIntervalTime * 1000);
            if (!isHidden()) {
                if (this.isfirst) {
                    LocationUtils.initMap(this.mapView, this.mapUtils.getLocationLayer(), null, this.handler);
                    this.isfirst = false;
                    return;
                }
            } else {
                return;
            }
            LocationUtils.initMap(this.mapView, this.mapUtils.getLocationLayer(), null, this.handler, false, false);
        }

        public void onStop() {
            Log.d(TAG, "onStop");
            super.onStop();
        }
    }
