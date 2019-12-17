package com.example.hpdemo;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.hpdemo.activity.BaseActivity;
import com.example.hpdemo.activity.PatrolFoundActivity;
import com.example.hpdemo.activity.SupervisoryControlActivity;
import com.example.hpdemo.db.NotifcationDB;
import com.example.hpdemo.service.HPAndroidSdkJniEx;
import com.example.hpdemo.service.MTResponse;
import com.example.hpdemo.utils.LocationUtils;
import com.example.hpdemo.utils.normalTools;
import com.helpsoft.HPAndroidCommandCallback;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.Variant;
import com.stub.StubApp;
import com.ytxt.logger.Logg;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;

@SuppressLint({"HandlerLeak"})
public class MainActivity extends BaseActivity implements View.OnClickListener, HPAndroidCommandCallback, InformationFragment.OnDeleteLayoutCallback {
    private static final int HANDLER_UPDATE_NEW_IMAGEVIEW = 1;

    private static final String TAG = "MainActivity";

    static FragmentManager fm;

    private static InformationFragment fragment1;

    private static PatrolFoundActivity fragment2;

    private static MapFragment fragment3;

    private static MineFragment fragment5;

    private ImageButton bayonet;

    private Button bt_clear;

    private ExecutorService cachedThreadPool;

    private ImageButton contacts;

    private ImageButton control;

    private int countGetSiteInfo = 0;

    private int currentpage = 0;

    private ArrayList<ContentValues> cvs;

    private long exitTime = 0L;

    private ImageButton information;

    private Button mBt_delete;

    private int mDeleteLayoutHeight = -1;

    private boolean mIsShowDeleteLayout = false;

    private NotifcationDB mNotifcationDB;

    private RelativeLayout mRl_delete;

    private TextView mTv_delete_count;

    private Handler mhandler = new Handler() {
        public void handleMessage(Message param1Message) {
            switch (param1Message.what) {
                default:
                    return;
                case 1:
                    break;
            }
            if (param1Message.arg1 > 0) {
                MainActivity.this.news.setVisibility(0);
                return;
            }
            MainActivity.this.news.setVisibility(8);
        }
    };

    private ImageButton mine;

    private ImageView news;

    private boolean premaploadtype = true;

    private SharedPreferences spf;

    long starttime;

    private LinearLayout tab_bayonet;

    private LinearLayout tab_contacks;

    private LinearLayout tab_control;

    private LinearLayout tab_information;

    private LinearLayout tab_mine;

    private String userName;

    static  {
        StubApp.interface11(2);
        fragment3 = null;
    }

    private void get() {
        Display display = getWindowManager().getDefaultDisplay();
        Logg.i("tag", "width-display :" + display.getWidth());
        Logg.i("tag", "heigth-display :" + display.getHeight());
        int i = (getResources().getConfiguration()).smallestScreenWidthDp;
        Logg.i("tag", "smallest width : " + i);
    }

    private void getSiteServer(int paramInt) {
        if (paramInt <= 0)
            return;
        Variant variant = new Variant();
        variant.addValue("PAGE_INDEX", paramInt);
        variant.addValue("PAGE_SIZE", 10);
        HPAndroidSdkJni.sendCommand(10175, "tid", variant);
    }

    public static void hideFragments(FragmentTransaction paramFragmentTransaction) {
        if (fragment1 != null)
            paramFragmentTransaction.hide(fragment1);
        if (fragment3 != null)
            paramFragmentTransaction.hide(fragment3);
        if (fragment5 != null)
            paramFragmentTransaction.hide(fragment5);
    }

    private void initView() {
        this.mTv_delete_count = (TextView)findViewById(2131624116);
        this.mBt_delete = (Button)findViewById(2131624117);
        this.bt_clear = (Button)findViewById(2131624357);
        this.mRl_delete = (RelativeLayout)findViewById(2131624115);
        this.information = (ImageButton)findViewById(2131624619);
        this.contacts = (ImageButton)findViewById(2131624622);
        this.bayonet = (ImageButton)findViewById(2131624356);
        this.control = (ImageButton)findViewById(2131624624);
        this.mine = (ImageButton)findViewById(2131624626);
        this.tab_information = (LinearLayout)findViewById(2131624618);
        this.tab_contacks = (LinearLayout)findViewById(2131624621);
        this.tab_bayonet = (LinearLayout)findViewById(2131624355);
        this.tab_control = (LinearLayout)findViewById(2131624623);
        this.tab_mine = (LinearLayout)findViewById(2131624625);
        this.news = (ImageView)findViewById(2131624620);
        this.tab_information.setOnClickListener(this);
        this.tab_contacks.setOnClickListener(this);
        this.tab_bayonet.setOnClickListener(this);
        this.tab_control.setOnClickListener(this);
        this.tab_mine.setOnClickListener(this);
        this.mBt_delete.setOnClickListener(this);
        this.bt_clear.setOnClickListener(this);
        this.tab_information.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                MainActivity.this.tab_information.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                MainActivity.access$502(MainActivity.this, MainActivity.this.tab_information.getHeight());
                (MainActivity.this.mRl_delete.getLayoutParams()).height = MainActivity.this.mDeleteLayoutHeight;
            }
        });
    }

    private void repalceMapfragment() {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.remove(fragment3);
        fragmentTransaction.commit();
        fragmentTransaction = fm.beginTransaction();
        fragment3 = new MapFragment();
        fragmentTransaction.add(2131624354, fragment3);
        fragmentTransaction.commit();
    }

    private void resetImg() {
        this.information.setImageResource(2130837808);
        this.contacts.setImageResource(2130837806);
        this.control.setImageResource(2130837753);
        this.mine.setImageResource(2130837980);
    }

    private void setDefaultSegment() { showFragment(3); }

    public void onClick(View paramView) {
        if (checkClick(500, paramView.getId())) {
            String str;
            switch (paramView.getId()) {
                default:
                    return;
                case 2131624117:
                    if (fragment1 != null)
                        fragment1.onDelet();
                    str = String.format(getString(2131230792), new Object[] { Integer.valueOf(0) });
                    this.mTv_delete_count.setText(str);
                    return;
                case 2131624357:
                    if (fragment1 != null)
                        fragment1.onAllDelet();
                    str = String.format(getString(2131230792), new Object[] { Integer.valueOf(0) });
                    this.mTv_delete_count.setText(str);
                    return;
                case 2131624618:
                    if (!MyApplication.loginOnline) {
                        normalTools.showToast((Context)this, "����������������");
                        return;
                    }
                    showFragment(1);
                    resetImg();
                    this.information.setImageResource(2130837809);
                    return;
                case 2131624621:
                    startActivity(new Intent((Context)this, PatrolFoundActivity.class));
                    return;
                case 2131624355:
                    if (!this.mIsShowDeleteLayout) {
                        showFragment(3);
                        resetImg();
                        return;
                    }
                    return;
                case 2131624623:
                    startActivity(new Intent((Context)this, SupervisoryControlActivity.class));
                    return;
                case 2131624625:
                    break;
            }
            showFragment(5);
            resetImg();
            this.mine.setImageResource(2130837994);
            return;
        }
    }

    public void onCommand(int paramInt, String paramString, Variant paramVariant) {
        switch (paramInt) {
            default:
                return;
            case 10066:
                break;
        }
        paramInt = paramVariant.getInt32Value("RESULT");
        Log.d("guo", "����������������:" + paramInt);
    }

    protected native void onCreate(Bundle paramBundle);

    public void onDeleteLayoutStatusChange(boolean paramBoolean, int paramInt) {
        this.mIsShowDeleteLayout = paramBoolean;
        if (this.mRl_delete != null) {
            if (paramBoolean) {
                this.mRl_delete.setVisibility(0);
                if (this.mTv_delete_count != null && paramInt != -1) {
                    String str = String.format(getString(2131230792), new Object[] { Integer.valueOf(paramInt) });
                    this.mTv_delete_count.setText(str);
                }
                return;
            }
        } else {
            return;
        }
        this.mRl_delete.setVisibility(8);
    }

    protected void onDestroy() {
        Log.i("MainActivity", "onDestroy");
        super.onDestroy();
        LocationUtils.BDTruestop();
    }

    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
        if (paramInt == 4 && paramKeyEvent.getAction() == 0) {
            if (System.currentTimeMillis() - this.exitTime > 2000L) {
                normalTools.showToast(this.context, "����������������!");
                this.exitTime = System.currentTimeMillis();
                return true;
            }
            MyApplication.loginout = true;
            stopService(new Intent((Context)this, MTResponse.class));
            HPAndroidSdkJni.releaseAndroidSdk();
            finish();
            System.exit(0);
            return true;
        }
        return super.onKeyDown(paramInt, paramKeyEvent);
    }

    protected void onResume() {
        Log.i("MainActivity", "onResume");
        super.onResume();
        boolean bool = MyApplication.getInstance().isLoadOnLineMap();
        if (!this.premaploadtype && bool)
            repalceMapfragment();
        this.premaploadtype = bool;
        if (MyApplication.getsaveNotifcationInfoDataList().size() > 0) {
            this.news.setVisibility(0);
            return;
        }
        this.news.setVisibility(8);
    }

    protected void onStop() {
        Log.i("MainActivity", "onStop");
        super.onStop();
        HPAndroidSdkJniEx.removeCommandCallbackEx(this);
    }

    public void showFragment(int paramInt) {
        if (this.currentpage == paramInt)
            return;
        this.currentpage = paramInt;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        hideFragments(fragmentTransaction);
        switch (paramInt) {
            default:
                fragmentTransaction.commit();
                return;
            case 1:
                if (fragment1 != null) {
                    fragmentTransaction.show(fragment1);
                } else {
                    fragment1 = new InformationFragment();
                    fragment1.setOnDeleteLayoutStatusListener(this);
                    fragmentTransaction.add(2131624354, fragment1);
                }
            case 3:
                if (fragment3 != null) {
                    fragmentTransaction.show(fragment3);
                } else {
                    fragment3 = new MapFragment();
                    fragmentTransaction.add(2131624354, fragment3);
                }
            case 5:
                break;
        }
        if (fragment5 != null)
            fragmentTransaction.show(fragment5);
        fragment5 = new MineFragment();
        fragmentTransaction.add(2131624354, fragment5);
    }
}
