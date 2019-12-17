package com.example.hpdemo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.hpdemo.activity.AlarmPictureActivity;
import com.example.hpdemo.activity.AudioTalkbackActivity;
import com.example.hpdemo.activity.HandlerHotportActivity;
import com.example.hpdemo.activity.InfoPositionActivity;
import com.example.hpdemo.activity.VideoBrowsingActivity;
import com.example.hpdemo.activity.WeatherAlarmDetailActivity;
import com.example.hpdemo.adapter.CameraListAdapter;
import com.example.hpdemo.adapter.SwipeAdapter;
import com.example.hpdemo.db.NotifcationDB;
import com.example.hpdemo.db.SiteInformation;
import com.example.hpdemo.dialog.CameraAlarmChooseDialog;
import com.example.hpdemo.dialog.HotspotAlarmChooseDialog;
import com.example.hpdemo.dialog.PositionChooseDialog;
import com.example.hpdemo.dialog.SOSSaveDialog;
import com.example.hpdemo.dialog.StationAlarmChooseDialog;
import com.example.hpdemo.service.HPAndroidSdkJniEx;
import com.example.hpdemo.utils.BroadcastWindowUtils;
import com.example.hpdemo.utils.normalTools;
import com.example.hpdemo.view.LoadingDialog;
import com.helpsoft.HPAndroidCommandCallback;
import com.helpsoft.HPAndroidSdkJni;
import com.helpsoft.Variant;
import com.ytxt.logger.Logg;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.litepal.crud.DataSupport;

@SuppressLint({"HandlerLeak"})
public class InformationFragment extends ContentFragment implements HPAndroidCommandCallback, View.OnClickListener, SwipeAdapter.DeleteChooseCheckBoxCallback {
    private static final int CHECKBOX_CLICK_CALLBACK = 2;

    private static final int DELETE_MSG_COMPLETED = 5;

    private static final int DISMISS_DIALOG = 4;

    private static final int HANDLER_ADD_ADAPTER_ITEM = 1;

    private static final int HANDLER_DELETE_ADAPTER_ITEM = 0;

    private static final int HANDLER_LIST_REFRUESH = 3;

    private static final String TAG = "InformationFragment";

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        public void onReceive(Context param1Context, Intent param1Intent) {
            String str = param1Intent.getStringExtra("siteid");
            InformationFragment.this.mAdapter.dealFireAlarm(str);
        }
    };

    private Button bt_clear;

    Bundle bundle;

    private List<SiteInformation> cameraList;

    private int countTotal = 0;

    private CharSequence cs_content = "����";

    String current_alarm_type;

    private ArrayList<String> deleteNotifcationIdLists = new ArrayList<String>();

    private String hotportNO = "";

    private ListView inforList;

    Intent intent;

    private boolean isAllChoose = true;

    private boolean isCreateView = false;

    private ImageView iv_right;

    private SwipeAdapter mAdapter;

    private Button mBt_delete;

    private OnDeleteLayoutCallback mDeleteLayoutStatusListener;

    private LoadingDialog mLoadingDialog = null;

    private NotifcationDB mNotifcationDB;

    private Handler mhandler = new Handler() {
        public void handleMessage(Message param1Message) {
            String str;
            ArrayList arrayList;
            switch (param1Message.what) {
                default:
                    return;
                case 0:
                    InformationFragment.this.mAdapter.removeItem(InformationFragment.this.pos);
                    InformationFragment.this.mAdapter.notifyDataSetChanged();
                    return;
                case 1:
                    arrayList = (ArrayList)param1Message.obj;
                    if (InformationFragment.this.mAdapter != null) {
                        InformationFragment.this.mAdapter.addItems2(arrayList);
                        InformationFragment.this.mAdapter.notifyDataSetChanged();
                        MyApplication.isHaveAddingNotifcation = false;
                        MyApplication.dealNotifcationAndCallback();
                        return;
                    }
                case 3:
                    if (InformationFragment.this.mAdapter != null) {
                        InformationFragment.this.mAdapter.reAddItems(MyApplication.getsaveNotifcationInfoDataList());
                        InformationFragment.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                case 2:
                    str = String.format(InformationFragment.this.getString(2131230792), new Object[] { Integer.valueOf(arrayList.arg1) });
                    if (InformationFragment.this.mDeleteLayoutStatusListener != null)
                        InformationFragment.this.mDeleteLayoutStatusListener.onDeleteLayoutStatusChange(true, arrayList.arg1);
                    InformationFragment.this.iv_right.setImageResource(2130837880);
                    InformationFragment.this.tv_delete_count.setText(str);
                    return;
                case 4:
                    if (InformationFragment.this.mLoadingDialog != null) {
                        InformationFragment.this.mLoadingDialog.cancel();
                        return;
                    }
                case 5:
                    break;
            }
            InformationFragment.this.mAdapter.reAddItems(MyApplication.getsaveNotifcationInfoDataList());
            if (InformationFragment.this.mDeleteLayoutStatusListener != null)
                InformationFragment.this.mDeleteLayoutStatusListener.onDeleteLayoutStatusChange(false, 0);
            InformationFragment.this.mAdapter.setDisplayDelete(false);
            if (InformationFragment.this.title_left != null)
                InformationFragment.this.title_left.setVisibility(4);
            if (InformationFragment.this.mLoadingDialog != null) {
                InformationFragment.this.mLoadingDialog.cancel();
                return;
            }
        }
    };

    private int pID;

    private String pID_20;

    int pos = 0;

    PopupWindow selestWindow;

    private TextView title_content;

    private LinearLayout title_left;

    private TextView tv_delete_count;

    ContentValues ucv;

    private View view;

    private void dealSiteAlarm(String paramString1, int paramInt, String paramString2) {
        Variant variant = new Variant();
        variant.addValue("DEVICE_ID", paramString1);
        variant.addValue("STATUS", paramInt);
        paramString1 = paramString2;
        if (paramString2 == null)
            paramString1 = "notifcationID;" + paramInt;
        HPAndroidSdkJni.sendCommand(10249, paramString1 + ";" + paramInt, variant);
    }

    private void deleteAllNotifcationInfo() { (new Thread() {
        public void run() {
            MyApplication.getsaveNotifcationInfoDataList().clear();
            InformationFragment.this.mNotifcationDB.clearAll();
            InformationFragment.this.mhandler.sendEmptyMessage(5);
        }
    }).start(); }

    private void deleteNotifcationInfo() { (new Thread() {
        public void run() {
            for (String str : InformationFragment.this.deleteNotifcationIdLists) {
                for (ContentValues contentValues : MyApplication.getsaveNotifcationInfoDataList()) {
                    String str1 = contentValues.getAsString(Constants.NOTIFCATION_ID);
                    if (!TextUtils.isEmpty(str1) && str1.equals(str))
                        MyApplication.removeNotifcationInfo(contentValues);
                }
            }
            int i = 0;
            for (String str : InformationFragment.this.deleteNotifcationIdLists) {
                if (InformationFragment.this.mNotifcationDB.delete(str) > 0)
                    i++;
            }
            Logg.i("InformationFragment", "������������: " + i);
            InformationFragment.this.mhandler.sendEmptyMessage(5);
        }
    }).start(); }

    private void flagOperationed() {
        if (this.ucv != null) {
            this.ucv.put("isClick", Boolean.valueOf(true));
            if (this.mAdapter != null) {
                this.mAdapter.notifyDataSetChanged();
                return;
            }
        }
    }

    private int getChooseDeleteCount(HashMap<Integer, Boolean> paramHashMap) {
        if (paramHashMap == null)
            return 0;
        this.deleteNotifcationIdLists.clear();
        int i = 0;
        for (Map.Entry<Integer, Boolean> entry : paramHashMap.entrySet()) {
            Integer integer = (Integer)entry.getKey();
            if (((Boolean)entry.getValue()).booleanValue()) {
                ContentValues contentValues = (ContentValues)this.mAdapter.getItem(integer.intValue());
                if (contentValues != null)
                    this.deleteNotifcationIdLists.add(contentValues.getAsString(Constants.NOTIFCATION_ID));
                i++;
            }
        }
        int j = i;
        return (i != this.deleteNotifcationIdLists.size()) ? -1 : j;
    }

    private void initView() {
        this.tv_delete_count = (TextView)this.view.findViewById(2131624116);
        this.iv_right = (ImageView)this.view.findViewById(2131624738);
        this.iv_right.setImageResource(2130837880);
        this.inforList = (ListView)this.view.findViewById(2131624598);
        this.title_left = (LinearLayout)this.view.findViewById(2131624736);
        this.title_content = (TextView)this.view.findViewById(2131624737);
        this.mBt_delete = (Button)this.view.findViewById(2131624117);
        this.bt_clear = (Button)this.view.findViewById(2131624357);
        this.title_left.setVisibility(4);
        this.title_content.setText(this.cs_content);
        this.inforList.setAdapter((ListAdapter)this.mAdapter);
        this.mBt_delete.setOnClickListener(this);
        this.bt_clear.setOnClickListener(this);
        this.title_left.setOnClickListener(this);
        this.iv_right.setVisibility(0);
        this.iv_right.setOnClickListener(this);
        this.inforList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> param1AdapterView, View param1View, final int camera_id, long param1Long) {
                InformationFragment.this.bundle = new Bundle();
                InformationFragment.this.pos = camera_id;
                InformationFragment.this.ucv = (ContentValues)InformationFragment.this.mAdapter.getItem(camera_id);
                if (InformationFragment.this.ucv == null) {
                    InformationFragment.this.ucv = new ContentValues();
                    return;
                }
                final Object intent = InformationFragment.this.ucv.getAsString("NAME");
                InformationFragment.this.current_alarm_type = (String)object;
                if (object.equals("����������")) {
                    object = InformationFragment.this.ucv.getAsString("FILEPATH");
                    if (!object.equals("")) {
                        InformationFragment.this.bundle.putString("PATH", (String)object);
                        InformationFragment.this.bundle.putInt("TYPE", 0);
                        InformationFragment.this.bundle.putString("NAME", "");
                        camera_id = InformationFragment.this.ucv.getAsInteger("ID").intValue();
                        final int bayont_id = InformationFragment.this.ucv.getAsInteger("BAYONET_ID").intValue();
                        InformationFragment.this.bundle.putInt("ID", camera_id);
                        InformationFragment.this.bundle.putInt("BAYONET_ID", i);
                        object = MyApplication.getCameraName(InformationFragment.this.ucv.getAsInteger("ID").intValue());
                        (new CameraAlarmChooseDialog((Context)InformationFragment.this.getActivity(), new CameraAlarmChooseDialog.CameraAlarmChooseDialogCallback() {
                            public void onCancel() {}

                            public void onChooseAudio() {
                                InformationFragment.this.flagOperationed();
                                InformationFragment.this.intent = new Intent((Context)InformationFragment.this.getActivity(), AudioTalkbackActivity.class);
                                InformationFragment.this.bundle.putInt("DEVICE_ID", bayont_id);
                                InformationFragment.this.bundle.putString("NAME", bayonetName);
                                if ("��������".equals(InformationFragment.this.current_alarm_type)) {
                                    InformationFragment.this.bundle.putInt("TYPE", 2);
                                } else if ("����������".equals(InformationFragment.this.current_alarm_type)) {
                                    InformationFragment.this.bundle.putInt("TYPE", 0);
                                }
                                InformationFragment.this.intent.putExtras(InformationFragment.this.bundle);
                                InformationFragment.this.startActivity(InformationFragment.this.intent);
                            }

                            public void onChooseBroadcast() {
                                InformationFragment.this.flagOperationed();
                                (new BroadcastWindowUtils(InformationFragment.this.getActivity(), bayont_id, (View)InformationFragment.this.title_content)).showWindow();
                            }

                            public void onChooseImage() {
                                InformationFragment.this.flagOperationed();
                                Log.d("InformationFragment", "onChooseImage()");
                                InformationFragment.this.intent = new Intent((Context)InformationFragment.this.getActivity(), AlarmPictureActivity.class);
                                InformationFragment.this.bundle.putString("PATH", InformationFragment.this.ucv.getAsString("FILEPATH"));
                                InformationFragment.this.intent.putExtras(InformationFragment.this.bundle);
                                InformationFragment.this.startActivity(InformationFragment.this.intent);
                            }

                            public void onChoosePosition() {
                                InformationFragment.this.flagOperationed();
                                Intent intent = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                                intent.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                                intent.putExtras(new Bundle());
                                intent.putExtra("ID", camera_id);
                                intent.putExtra("BAYONET_ID", bayont_id);
                                InformationFragment.this.startActivity(intent);
                            }

                            public void onChooseVideo() {
                                InformationFragment.this.flagOperationed();
                                Intent intent = new Intent((Context)InformationFragment.this.getActivity(), VideoBrowsingActivity.class);
                                InformationFragment.this.bundle.putInt("ID", bayont_id);
                                InformationFragment.this.bundle.putString("NAME", bayonetName);
                                InformationFragment.this.bundle.putInt("TYPE", 0);
                                intent.putExtras(InformationFragment.this.bundle);
                                InformationFragment.this.startActivity(intent);
                            }

                            public void onStopAlarm() { InformationFragment.this.flagOperationed(); }
                        })).show();
                        return;
                    }
                    return;
                }
                if (object.equals("��������")) {
                    object = InformationFragment.this.ucv.getAsString("FILEPATH");
                    if (!object.equals("")) {
                        InformationFragment.this.bundle.putString("PATH", (String)object);
                        InformationFragment.this.bundle.putInt("TYPE", 2);
                        InformationFragment.this.bundle.putInt("ID", InformationFragment.this.ucv.getAsInteger("ID").intValue());
                        InformationFragment.this.bundle.putString("DEVICE_ID", InformationFragment.this.ucv.getAsString("DEVICE_ID"));
                        final boolean isDeal = InformationFragment.this.ucv.getAsBoolean("isDeal").booleanValue();
                        object = InformationFragment.this.ucv.getAsString(Constants.NOTIFCATION_ID);
                        InformationFragment.access$902(InformationFragment.this, InformationFragment.this.ucv.getAsInteger("ID").intValue());
                        InformationFragment.access$1002(InformationFragment.this, InformationFragment.this.ucv.getAsString("DEVICE_ID"));
                        (new StationAlarmChooseDialog((Context)InformationFragment.this.getActivity(), bool, new StationAlarmChooseDialog.StationAlarmChooseDialogCallback() {
                            public void onCancel() {}

                            public void onChooseImage() {
                                InformationFragment.this.flagOperationed();
                                Log.d("InformationFragment", "onChooseImage()");
                                InformationFragment.this.intent = new Intent((Context)InformationFragment.this.getActivity(), AlarmPictureActivity.class);
                                InformationFragment.this.bundle.putString("PATH", InformationFragment.this.ucv.getAsString("FILEPATH"));
                                InformationFragment.this.intent.putExtras(InformationFragment.this.bundle);
                                InformationFragment.this.startActivity(InformationFragment.this.intent);
                            }

                            public void onChoosePosition() {
                                InformationFragment.this.flagOperationed();
                                ArrayList<SiteInformation> arrayList = (ArrayList)DataSupport.where(new String[] { "deviceId = ?", InformationFragment.access$1000(this.this$1.this$0) }).find(SiteInformation.class);
                                if (arrayList == null || arrayList.size() == 0) {
                                    normalTools.showToast((Context)InformationFragment.this.getActivity(), "������������");
                                    return;
                                }
                                SiteInformation siteInformation = arrayList.get(0);
                                if (siteInformation != null) {
                                    Intent intent = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                                    intent.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("SiteInformation", (Serializable)siteInformation);
                                    intent.putExtras(bundle);
                                    intent.putExtra("PID", InformationFragment.this.pID);
                                    intent.putExtra("ID_20", InformationFragment.this.pID_20);
                                    InformationFragment.this.startActivity(intent);
                                    return;
                                }
                            }

                            public void onChooseVideo() {
                                InformationFragment.this.flagOperationed();
                                InformationFragment.access$1202(InformationFragment.this, DataSupport.where(new String[] { "parentDeviceId = ?", InformationFragment.access$1000(this.this$1.this$0) }).find(SiteInformation.class));
                                if (InformationFragment.this.cameraList != null && InformationFragment.this.cameraList.size() != 0) {
                                    InformationFragment.this.showSelectPop(InformationFragment.this.pID);
                                    return;
                                }
                                normalTools.showToast((Context)InformationFragment.this.getActivity(), InformationFragment.this.getString(2131230840));
                            }

                            public void onErrorAlarm() {
                                InformationFragment.this.flagOperationed();
                                String str = InformationFragment.this.ucv.getAsString("DEVICE_ID");
                                Log.d("InformationFragment", "onStopAlarm() devced_id = " + str);
                                InformationFragment.this.dealSiteAlarm(str, 2, notifactionId);
                            }

                            public void onTrueAlarm() {
                                InformationFragment.this.flagOperationed();
                                String str = InformationFragment.this.ucv.getAsString("DEVICE_ID");
                                Log.d("InformationFragment", "onConfirmAlarm() devced_id = " + str);
                                InformationFragment.this.dealSiteAlarm(str, 3, notifactionId);
                            }
                        })).show();
                        return;
                    }
                    return;
                }
                if (object.equals("��������")) {
                    object = InformationFragment.this.ucv.getAsString("HOTSPOT_NO");
                    final double x = InformationFragment.this.ucv.getAsDouble("LONGITUDE").doubleValue();
                    final double y = InformationFragment.this.ucv.getAsDouble("LATITUDE").doubleValue();
                    final boolean isDeal = InformationFragment.this.ucv.getAsBoolean("isDeal").booleanValue();
                    (new HotspotAlarmChooseDialog((Context)InformationFragment.this.getActivity(), bool, new HotspotAlarmChooseDialog.HotSpotAlarmChooseDialogCallback() {
                        public void onCancel() {}

                        public void onChooseFeedback() {
                            InformationFragment.this.flagOperationed();
                            Intent intent = new Intent((Context)InformationFragment.this.getActivity(), HandlerHotportActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("HOTSPOT_NO", hotsportNO);
                            bundle.putString("REPORT_TIME", InformationFragment.this.ucv.getAsString("REPORT_TIME"));
                            bundle.putDouble("LONGITUDE", longitude);
                            bundle.putDouble("LATITUDE", latitude);
                            intent.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                            intent.putExtras(bundle);
                            InformationFragment.this.startActivity(intent);
                        }

                        public void onChoosePosition() {
                            if (InformationFragment.this.hotportNO != null && InformationFragment.this.hotportNO.equals(hotsportNO)) {
                                normalTools.showToast((Context)InformationFragment.this.getActivity(), "����������������");
                                return;
                            }
                            InformationFragment.this.flagOperationed();
                            Intent intent = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("HOTSPOT_NO", hotsportNO);
                            bundle.putString("REPORT_TIME", InformationFragment.this.ucv.getAsString("REPORT_TIME"));
                            bundle.putString("NAME", InformationFragment.this.ucv.getAsString("NAME"));
                            bundle.putBoolean("isDeal", isDeal);
                            bundle.putDouble("LONGITUDE", longitude);
                            bundle.putDouble("LATITUDE", latitude);
                            intent.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                            intent.putExtras(bundle);
                            InformationFragment.this.startActivity(intent);
                        }
                    })).show();
                    return;
                }
                if (object.equals("��������")) {
                    object = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                    object.putExtra("X", InformationFragment.this.ucv.getAsDouble("X"));
                    object.putExtra("Y", InformationFragment.this.ucv.getAsDouble("Y"));
                    object.putExtra("REPORT_TIME", InformationFragment.this.ucv.getAsString("REPORT_TIME"));
                    object.putExtra("FIRE_ADDRESS", InformationFragment.this.ucv.getAsString("FIRE_ADDRESS"));
                    object.putExtra("REMARK", InformationFragment.this.ucv.getAsString("REMARK"));
                    object.putExtra("NAME", InformationFragment.this.ucv.getAsString("NAME"));
                    object.putExtra("FIRE_ID", InformationFragment.this.ucv.getAsString("FIRE_ID"));
                    object.putExtra("ALARM_PEOPLE", InformationFragment.this.ucv.getAsString("ALARM_PEOPLE"));
                    object.putExtra("FIRE_TYPE", InformationFragment.this.ucv.getAsInteger("FIRE_TYPE"));
                    object.putExtra("ALARM_CALL", InformationFragment.this.ucv.getAsString("ALARM_CALL"));
                    object.putExtra("NOTIFACTION_TYPE", Constants.NOTIFACTION_TYPE.MANUAL_ALARAM_NOTIFCATION);
                    (new PositionChooseDialog((Context)InformationFragment.this.getActivity(), new PositionChooseDialog.PositionChooseDialogCallback() {
                        public void onCancel() {}

                        public void onChoosePosition() {
                            InformationFragment.this.flagOperationed();
                            InformationFragment.this.startActivity(intent_manual);
                        }
                    })).show();
                    return;
                }
                if (object.equals("��������")) {
                    object = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                    object.putExtra("X", InformationFragment.this.ucv.getAsDouble("X"));
                    object.putExtra("Y", InformationFragment.this.ucv.getAsDouble("Y"));
                    object.putExtra("REPORT_TIME", InformationFragment.this.ucv.getAsString("REPORT_TIME"));
                    object.putExtra("FIRE_ADDRESS", InformationFragment.this.ucv.getAsString("FIRE_ADDRESS"));
                    object.putExtra("REMARK", InformationFragment.this.ucv.getAsString("REMARK"));
                    object.putExtra("NAME", InformationFragment.this.ucv.getAsString("NAME"));
                    object.putExtra("PID", InformationFragment.this.ucv.getAsString("FIRE_ID"));
                    object.putExtra("ALARM_PEOPLE", InformationFragment.this.ucv.getAsString("ALARM_PEOPLE"));
                    object.putExtra("FIRE_TYPE", InformationFragment.this.ucv.getAsInteger("FIRE_TYPE"));
                    InformationFragment.access$1002(InformationFragment.this, InformationFragment.this.ucv.getAsString("FIRE_ID"));
                    (new PositionChooseDialog((Context)InformationFragment.this.getActivity(), new PositionChooseDialog.PositionChooseDialogCallback() {
                        public void onCancel() {}

                        public void onChoosePosition() {
                            ArrayList<SiteInformation> arrayList = (ArrayList)DataSupport.where(new String[] { "deviceId = ?", InformationFragment.access$1000(this.this$1.this$0) }).find(SiteInformation.class);
                            if (arrayList == null || arrayList.size() == 0) {
                                normalTools.showToast((Context)InformationFragment.this.getActivity(), "������������");
                                return;
                            }
                            SiteInformation siteInformation = arrayList.get(0);
                            if (siteInformation != null) {
                                InformationFragment.this.flagOperationed();
                                Bundle bundle = new Bundle();
                                intent_fire.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                                bundle.putSerializable("SiteInformation", (Serializable)siteInformation);
                                intent_fire.putExtras(bundle);
                                InformationFragment.this.startActivity(intent_fire);
                                return;
                            }
                        }
                    })).show();
                    return;
                }
                if (object.equals("��������")) {
                    InformationFragment.this.flagOperationed();
                    object = new Intent((Context)InformationFragment.this.getActivity(), WeatherAlarmDetailActivity.class);
                    InformationFragment.this.bundle.putParcelable("weather_alarm_value", (Parcelable)InformationFragment.this.ucv);
                    object.putExtras(InformationFragment.this.bundle);
                    InformationFragment.this.startActivity((Intent)object);
                    return;
                }
                if (!object.equals("������������")) {
                    if (object.equals("��������")) {
                        object = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                        object.putExtra("X", InformationFragment.this.ucv.getAsDouble("X"));
                        object.putExtra("Y", InformationFragment.this.ucv.getAsDouble("Y"));
                        object.putExtra("TransactionId", InformationFragment.this.ucv.getAsString("TransactionId"));
                        object.putExtra("ELEVATION", InformationFragment.this.ucv.getAsDouble("ELEVATION"));
                        object.putExtra("NAME", InformationFragment.this.ucv.getAsString("NAME"));
                        object.putExtra("DEVICE_ID", InformationFragment.this.ucv.getAsString("DEVICE_ID"));
                        InformationFragment.access$1002(InformationFragment.this, InformationFragment.this.ucv.getAsString("DEVICE_ID"));
                        (new PositionChooseDialog((Context)InformationFragment.this.getActivity(), new PositionChooseDialog.PositionChooseDialogCallback() {
                            public void onCancel() {}

                            public void onChoosePosition() {
                                ArrayList<SiteInformation> arrayList = (ArrayList)DataSupport.where(new String[] { "deviceId = ?", InformationFragment.access$1000(this.this$1.this$0) }).find(SiteInformation.class);
                                if (arrayList == null || arrayList.size() == 0) {
                                    normalTools.showToast((Context)InformationFragment.this.getActivity(), "������������");
                                    return;
                                }
                                SiteInformation siteInformation = arrayList.get(0);
                                if (siteInformation != null) {
                                    InformationFragment.this.flagOperationed();
                                    Bundle bundle = new Bundle();
                                    intent.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                                    bundle.putSerializable("SiteInformation", (Serializable)siteInformation);
                                    intent.putExtras(bundle);
                                    InformationFragment.this.startActivity(intent);
                                    return;
                                }
                            }
                        })).show();
                        return;
                    }
                    if (object.equals("SOS����")) {
                        InformationFragment.this.ucv.getAsInteger("ID").intValue();
                        object = InformationFragment.this.ucv.getAsString("TransactionId");
                        InformationFragment.this.ucv.getAsString("CONTENT");
                        InformationFragment.this.ucv.getAsString("FIRE_ADDRESS");
                        final double x = InformationFragment.this.ucv.getAsDouble("X").doubleValue();
                        final double y = InformationFragment.this.ucv.getAsDouble("Y").doubleValue();
                        String[] arrayOfString = InformationFragment.this.ucv.getAsString("FILEPATH").split(";");
                        final String backimg = arrayOfString[0];
                        final String frontimg = arrayOfString[1];
                        (new SOSSaveDialog((Context)InformationFragment.this.getActivity(), new SOSSaveDialog.SOSSaveDialogCallback() {
                            public void onCancel() {}

                            public void onChoosePosition() {
                                InformationFragment.this.flagOperationed();
                                Intent intent = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                                intent.putExtra("X", x);
                                intent.putExtra("Y", y);
                                intent.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                                InformationFragment.this.startActivity(intent);
                            }

                            public void onSave() {
                                InformationFragment.this.flagOperationed();
                                ByteBuffer byteBuffer = ByteBuffer.allocate(65536);
                                Variant variant = new Variant();
                                variant.addValue("TransactionId", transactionid);
                                variant.addValue("X", x);
                                variant.addValue("Y", y);
                                variant.addValue("SAVE_TIME", normalTools.getCurrentTime());
                                if (!Variant.VariantToBuffer(variant, byteBuffer))
                                    return;
                                byteBuffer.flip();
                                HPAndroidSdkJni.sendCommand(10387, transactionid, byteBuffer.array(), byteBuffer.remaining());
                            }

                            public void onViewImg1() {
                                InformationFragment.this.flagOperationed();
                                Log.d("InformationFragment", "onChooseImage()");
                                InformationFragment.this.intent = new Intent((Context)InformationFragment.this.getActivity(), AlarmPictureActivity.class);
                                InformationFragment.this.bundle.putString("PATH", backimg);
                                InformationFragment.this.intent.putExtras(InformationFragment.this.bundle);
                                InformationFragment.this.startActivity(InformationFragment.this.intent);
                            }

                            public void onViewImg2() {
                                InformationFragment.this.flagOperationed();
                                Log.d("InformationFragment", "onChooseImage()");
                                InformationFragment.this.intent = new Intent((Context)InformationFragment.this.getActivity(), AlarmPictureActivity.class);
                                InformationFragment.this.bundle.putString("PATH", frontimg);
                                InformationFragment.this.intent.putExtras(InformationFragment.this.bundle);
                                InformationFragment.this.startActivity(InformationFragment.this.intent);
                            }
                        })).show();
                        return;
                    }
                    if (object.equals("��������")) {
                        InformationFragment.this.ucv.getAsInteger("ID").intValue();
                        InformationFragment.this.ucv.getAsString("TransactionId");
                        final double x = InformationFragment.this.ucv.getAsDouble("X").doubleValue();
                        final double y = InformationFragment.this.ucv.getAsDouble("Y").doubleValue();
                        object = new Intent((Context)InformationFragment.this.getActivity(), InfoPositionActivity.class);
                        object.putExtra("X", d1);
                        object.putExtra("Y", d2);
                        object.putExtra("NOTIFACTION_TYPE", InformationFragment.this.ucv.getAsInteger("NOTIFACTION_TYPE"));
                        (new PositionChooseDialog((Context)InformationFragment.this.getActivity(), new PositionChooseDialog.PositionChooseDialogCallback() {
                            public void onCancel() {}

                            public void onChoosePosition() {
                                InformationFragment.this.flagOperationed();
                                InformationFragment.this.startActivity(intent);
                            }
                        })).show();
                        return;
                    }
                }
            }
        });
    }

    public void onAllDelet() {
        this.mLoadingDialog = new LoadingDialog((Context)getActivity());
        this.iv_right.setImageResource(2130837880);
        deleteAllNotifcationInfo();
    }

    public void onAttach(Activity paramActivity) {
        super.onAttach(paramActivity);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("MTResponseBroadCast");
        paramActivity.registerReceiver(this.broadcastReceiver, intentFilter);
    }

    public void onClick() {
        if (this.mAdapter == null)
            return;
        List list = this.mAdapter.getValues();
        this.deleteNotifcationIdLists.clear();
        int i = 0;
        for (ContentValues contentValues : list) {
            if (contentValues != null && contentValues.getAsBoolean("isFlagDelete").booleanValue()) {
                i++;
                this.deleteNotifcationIdLists.add(contentValues.getAsString(Constants.NOTIFCATION_ID));
            }
        }
        this.countTotal = i;
        Message message = Message.obtain();
        message.what = 2;
        message.arg1 = this.countTotal;
        this.mhandler.sendMessage(message);
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case 2131624736:
                this.title_left.setVisibility(4);
                if (this.mAdapter.getDisplayDelete()) {
                    if (this.mDeleteLayoutStatusListener != null)
                        this.mDeleteLayoutStatusListener.onDeleteLayoutStatusChange(false, 0);
                    this.iv_right.setImageResource(2130837880);
                    this.isAllChoose = true;
                } else if (this.mDeleteLayoutStatusListener != null) {
                    this.mDeleteLayoutStatusListener.onDeleteLayoutStatusChange(true, -1);
                }
                this.mAdapter.setDisplayDelete(false);
                this.mAdapter.notifyDataSetChanged();
                return;
            case 2131624117:
                this.mLoadingDialog = new LoadingDialog((Context)getActivity());
                deleteNotifcationInfo();
                if (this.mDeleteLayoutStatusListener != null)
                    this.mDeleteLayoutStatusListener.onDeleteLayoutStatusChange(false, 0);
                this.mAdapter.setDisplayDelete(false);
                this.mhandler.sendEmptyMessage(4);
                return;
            case 2131624357:
                deleteAllNotifcationInfo();
                return;
            case 2131624738:
                break;
        }
        this.title_left.setVisibility(0);
        if (this.mAdapter != null && !this.mAdapter.getDisplayDelete()) {
            if (this.mDeleteLayoutStatusListener != null)
                this.mDeleteLayoutStatusListener.onDeleteLayoutStatusChange(true, -1);
            this.mAdapter.setDisplayDelete(true);
            this.mAdapter.notifyDataSetChanged();
            return;
        }
    }

    public void onCommand(int paramInt, String paramString, Variant paramVariant) {
        (new ContentValues()).put("TIME", normalTools.getCurrentTime());
        switch (paramInt) {
            default:
                return;
            case 10250:
                paramInt = paramVariant.getInt32Value("RESULT");
                if (paramInt == 1) {
                    String[] arrayOfString = paramString.split(";");
                    if (arrayOfString.length == 2) {
                        Logg.i("InformationFragment", "onCommand() alarm_deal_success");
                        MyApplication.updateNotifactionInfoByNotifcationID(arrayOfString[0], arrayOfString[1]);
                        normalTools.showToast((Context)getActivity(), getString(2131230766));
                        getActivity().sendBroadcast(new Intent("com.example.hpdemo.activity.MyInfoActivity.SENDBROADCAST"));
                        return;
                    }
                }
                normalTools.showToast((Context)getActivity(), "��������, ����:" + paramInt);
                return;
            case 10388:
                break;
        }
        paramInt = paramVariant.getInt32Value("RESULT");
        if (paramInt == 1) {
            normalTools.showToast((Context)getActivity(), "����������������");
            return;
        }
        normalTools.showToast((Context)getActivity(), "��������, ����:" + paramInt);
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        this.view = paramLayoutInflater.inflate(2130968741, paramViewGroup, false);
        this.mNotifcationDB = new NotifcationDB((Context)getActivity());
        this.mAdapter = new SwipeAdapter((Context)getActivity());
        this.mAdapter.setOnDeleteChooseCheckBoxCallback(this);
        HPAndroidSdkJniEx.addCommandCallbackEx(this);
        initView();
        MyApplication.setNotifcationInfoDataChangeCallbackListener(new MyApplication.NotifcationInfoDataChangeCallback() {
            public void onAddDataChange(ArrayList<ContentValues> param1ArrayList) {
                Message message = Message.obtain();
                message.what = 1;
                message.obj = param1ArrayList;
                InformationFragment.this.mhandler.sendMessage(message);
            }

            public void onDataSize(int param1Int) {}

            public void onDataUpdate() { InformationFragment.this.mhandler.sendEmptyMessage(3); }
        });
        this.isCreateView = true;
        if (MyApplication.getsaveNotifcationInfoDataList() != null) {
            int i = MyApplication.getsaveNotifcationInfoDataList().size();
            if (i != 0) {
                int j;
                for (j = 0; j < i; j++) {
                    ContentValues contentValues = MyApplication.getsaveNotifcationInfoDataList().get(j);
                    this.mAdapter.addItem(contentValues);
                }
            }
        }
        return this.view;
    }

    public void onDelet() {
        if (this.deleteNotifcationIdLists.size() == 0)
            return;
        this.mLoadingDialog = new LoadingDialog((Context)getActivity());
        deleteNotifcationInfo();
    }

    public void onDestroyView() {
        if (this.mLoadingDialog != null)
            this.mLoadingDialog.dismiss();
        super.onDestroyView();
    }

    public void setOnDeleteLayoutStatusListener(OnDeleteLayoutCallback paramOnDeleteLayoutCallback) { this.mDeleteLayoutStatusListener = paramOnDeleteLayoutCallback; }

    protected void showSelectPop(int paramInt) {
        View view1 = View.inflate((Context)getActivity(), 2130968834, null);
        this.selestWindow = new PopupWindow(view1, -2, -2);
        this.selestWindow.setBackgroundDrawable((Drawable)new ColorDrawable(0));
        this.selestWindow.setOutsideTouchable(true);
        this.selestWindow.setFocusable(true);
        this.selestWindow.showAtLocation((View)this.title_content, 17, 0, 0);
        ListView listView = (ListView)view1.findViewById(2131624858);
        CameraListAdapter cameraListAdapter = new CameraListAdapter((Context)getActivity(), new CameraListAdapter.ListViewItemClickCallback() {
            public void onClick() { InformationFragment.this.selestWindow.dismiss(); }
        });
        listView.setAdapter((ListAdapter)cameraListAdapter);
        for (int i = 0; i < this.cameraList.size(); i++) {
            String str1 = ((SiteInformation)this.cameraList.get(i)).getSiteName();
            int j = ((SiteInformation)this.cameraList.get(i)).getSiteId();
            String str2 = ((SiteInformation)this.cameraList.get(i)).getParentDeviceId();
            int k = ((SiteInformation)this.cameraList.get(i)).getPtzType();
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", str1);
            contentValues.put("TYPE", Integer.valueOf(2));
            contentValues.put("ID", Integer.valueOf(j));
            contentValues.put("ID_20", str2);
            contentValues.put("pID", Integer.valueOf(paramInt));
            contentValues.put("ptz_type", Integer.valueOf(k));
            cameraListAdapter.addItems(contentValues);
        }
    }

    public void update(int paramInt, Variant paramVariant) { // Byte code:
        //   0: new android/content/ContentValues
        //   3: dup
        //   4: invokespecial <init> : ()V
        //   7: astore #11
        //   9: iload_1
        //   10: lookupswitch default -> 84, 10002 -> 84, 10055 -> 85, 10059 -> 84, 10245 -> 1087, 10269 -> 914, 10285 -> 324, 10365 -> 1343, 10369 -> 1532
        //   84: return
        //   85: aload #11
        //   87: ldc_w 'TIME'
        //   90: invokestatic getCurrentTime : ()Ljava/lang/String;
        //   93: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   96: aload_2
        //   97: ldc_w 'ID'
        //   100: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   103: istore_1
        //   104: aload_2
        //   105: ldc_w 'TYPE'
        //   108: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   111: istore #7
        //   113: iload #7
        //   115: iconst_m1
        //   116: if_icmpeq -> 84
        //   119: iload #7
        //   121: bipush #-3
        //   123: if_icmpeq -> 84
        //   126: ldc ''
        //   128: astore #10
        //   130: aload_2
        //   131: ldc_w 'IMAGE_PATH'
        //   134: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   137: astore #12
        //   139: aload #10
        //   141: astore_2
        //   142: iload #7
        //   144: iconst_1
        //   145: if_icmpne -> 169
        //   148: new java/lang/StringBuilder
        //   151: dup
        //   152: invokespecial <init> : ()V
        //   155: ldc_w '��������:����������������,ID'
        //   158: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   161: iload_1
        //   162: invokevirtual append : (I)Ljava/lang/StringBuilder;
        //   165: invokevirtual toString : ()Ljava/lang/String;
        //   168: astore_2
        //   169: iload #7
        //   171: bipush #-2
        //   173: if_icmpne -> 197
        //   176: new java/lang/StringBuilder
        //   179: dup
        //   180: invokespecial <init> : ()V
        //   183: ldc_w '��������:��������,ID'
        //   186: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: iload_1
        //   190: invokevirtual append : (I)Ljava/lang/StringBuilder;
        //   193: invokevirtual toString : ()Ljava/lang/String;
        //   196: astore_2
        //   197: iload #7
        //   199: bipush #-4
        //   201: if_icmpne -> 225
        //   204: new java/lang/StringBuilder
        //   207: dup
        //   208: invokespecial <init> : ()V
        //   211: ldc_w '��������:������������ID'
        //   214: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   217: iload_1
        //   218: invokevirtual append : (I)Ljava/lang/StringBuilder;
        //   221: invokevirtual toString : ()Ljava/lang/String;
        //   224: astore_2
        //   225: aload #11
        //   227: ldc_w 'NAME'
        //   230: ldc_w '����������'
        //   233: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   236: aload #11
        //   238: ldc_w 'CONTENT'
        //   241: aload_2
        //   242: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   245: aload #11
        //   247: ldc_w 'ID'
        //   250: iload_1
        //   251: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   254: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   257: aload #11
        //   259: ldc_w 'isClick'
        //   262: iconst_0
        //   263: invokestatic valueOf : (Z)Ljava/lang/Boolean;
        //   266: invokevirtual put : (Ljava/lang/String;Ljava/lang/Boolean;)V
        //   269: aload #11
        //   271: ldc_w 'FILEPATH'
        //   274: aload #12
        //   276: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   279: aload #11
        //   281: ldc_w 'NOTIFACTION_TYPE'
        //   284: getstatic com/example/hpdemo/Constants$NOTIFACTION_TYPE.CAMERA_ALARM_NOTIFCATION : I
        //   287: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   290: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   293: aload_0
        //   294: getfield isCreateView : Z
        //   297: ifeq -> 310
        //   300: aload_0
        //   301: getfield mAdapter : Lcom/example/hpdemo/adapter/SwipeAdapter;
        //   304: aload #11
        //   306: invokevirtual addItem : (Landroid/content/ContentValues;)V
        //   309: return
        //   310: ldc 'InformationFragment'
        //   312: ldc_w 'PhoneNotifyCameraAlarm MyApplication.getSaveDataList().add()'
        //   315: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
        //   318: aload #11
        //   320: invokestatic addSaveData : (Landroid/content/ContentValues;)V
        //   323: return
        //   324: aload_2
        //   325: ldc_w 'FIRE_TYPE'
        //   328: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   331: istore_1
        //   332: aload_2
        //   333: ldc_w 'FIRE_ADDRESS'
        //   336: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   339: astore #10
        //   341: aload_2
        //   342: ldc_w 'REMARK'
        //   345: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   348: astore #12
        //   350: aload_2
        //   351: ldc_w 'X'
        //   354: invokevirtual getDoubleValue : (Ljava/lang/String;)D
        //   357: dstore_3
        //   358: aload_2
        //   359: ldc_w 'Y'
        //   362: invokevirtual getDoubleValue : (Ljava/lang/String;)D
        //   365: dstore #5
        //   367: aload_2
        //   368: ldc_w 'REPORT_TIME'
        //   371: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   374: astore #13
        //   376: aload_0
        //   377: getfield ucv : Landroid/content/ContentValues;
        //   380: ldc_w 'X'
        //   383: dload_3
        //   384: invokestatic valueOf : (D)Ljava/lang/Double;
        //   387: invokevirtual put : (Ljava/lang/String;Ljava/lang/Double;)V
        //   390: aload_0
        //   391: getfield ucv : Landroid/content/ContentValues;
        //   394: ldc_w 'Y'
        //   397: dload #5
        //   399: invokestatic valueOf : (D)Ljava/lang/Double;
        //   402: invokevirtual put : (Ljava/lang/String;Ljava/lang/Double;)V
        //   405: aload_0
        //   406: getfield ucv : Landroid/content/ContentValues;
        //   409: ldc_w 'REPORT_TIME'
        //   412: aload #13
        //   414: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   417: aload_0
        //   418: getfield ucv : Landroid/content/ContentValues;
        //   421: ldc_w 'FIRE_ADDRESS'
        //   424: aload #10
        //   426: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   429: aload_0
        //   430: getfield ucv : Landroid/content/ContentValues;
        //   433: ldc_w 'REMARK'
        //   436: aload #12
        //   438: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   441: ldc 'InformationFragment'
        //   443: new java/lang/StringBuilder
        //   446: dup
        //   447: invokespecial <init> : ()V
        //   450: ldc_w 'PhoneRequestFireReport-----------------x = '
        //   453: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   456: dload_3
        //   457: invokevirtual append : (D)Ljava/lang/StringBuilder;
        //   460: ldc_w ', y = '
        //   463: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   466: dload #5
        //   468: invokevirtual append : (D)Ljava/lang/StringBuilder;
        //   471: ldc_w ', fire_type = '
        //   474: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   477: iload_1
        //   478: invokevirtual append : (I)Ljava/lang/StringBuilder;
        //   481: invokevirtual toString : ()Ljava/lang/String;
        //   484: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
        //   487: iload_1
        //   488: ifne -> 582
        //   491: aload_2
        //   492: ldc_w 'FIRE_ID'
        //   495: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   498: astore_2
        //   499: aload_0
        //   500: getfield ucv : Landroid/content/ContentValues;
        //   503: ldc_w 'NAME'
        //   506: ldc_w '��������'
        //   509: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   512: aload_0
        //   513: getfield ucv : Landroid/content/ContentValues;
        //   516: ldc_w 'CONTENT'
        //   519: aload #10
        //   521: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   524: aload_0
        //   525: getfield ucv : Landroid/content/ContentValues;
        //   528: ldc_w 'FIRE_ID'
        //   531: aload_2
        //   532: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   535: aload_0
        //   536: getfield ucv : Landroid/content/ContentValues;
        //   539: ldc_w 'FIRE_TYPE'
        //   542: iload_1
        //   543: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   546: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   549: aload_0
        //   550: getfield ucv : Landroid/content/ContentValues;
        //   553: ldc_w 'NOTIFACTION_TYPE'
        //   556: getstatic com/example/hpdemo/Constants$NOTIFACTION_TYPE.FIRE_ALARAM_NOTIFCATION : I
        //   559: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   562: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   565: aload_0
        //   566: getfield isCreateView : Z
        //   569: ifeq -> 908
        //   572: aload_0
        //   573: getfield mAdapter : Lcom/example/hpdemo/adapter/SwipeAdapter;
        //   576: aload #11
        //   578: invokevirtual addItem : (Landroid/content/ContentValues;)V
        //   581: return
        //   582: iload_1
        //   583: iconst_1
        //   584: if_icmpne -> 771
        //   587: aload_2
        //   588: ldc_w 'FIRE_ID'
        //   591: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   594: astore_2
        //   595: iconst_1
        //   596: anewarray java/lang/String
        //   599: dup
        //   600: iconst_0
        //   601: ldc_w 'userID'
        //   604: aastore
        //   605: invokestatic select : ([Ljava/lang/String;)Lorg/litepal/crud/ClusterQuery;
        //   608: iconst_2
        //   609: anewarray java/lang/String
        //   612: dup
        //   613: iconst_0
        //   614: ldc_w 'userID = ?'
        //   617: aastore
        //   618: dup
        //   619: iconst_1
        //   620: aload_2
        //   621: invokestatic valueOf : (Ljava/lang/Object;)Ljava/lang/String;
        //   624: aastore
        //   625: invokevirtual where : ([Ljava/lang/String;)Lorg/litepal/crud/ClusterQuery;
        //   628: ldc_w com/example/hpdemo/db/UserInfo
        //   631: invokevirtual find : (Ljava/lang/Class;)Ljava/util/List;
        //   634: astore_2
        //   635: aload_2
        //   636: ifnull -> 84
        //   639: aload_2
        //   640: invokeinterface size : ()I
        //   645: ifeq -> 84
        //   648: aload_2
        //   649: iconst_0
        //   650: invokeinterface get : (I)Ljava/lang/Object;
        //   655: checkcast com/example/hpdemo/db/UserInfo
        //   658: astore_2
        //   659: aload_2
        //   660: ifnull -> 84
        //   663: aload_0
        //   664: getfield ucv : Landroid/content/ContentValues;
        //   667: ldc_w 'NAME'
        //   670: ldc_w '��������'
        //   673: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   676: new java/lang/StringBuilder
        //   679: dup
        //   680: invokespecial <init> : ()V
        //   683: ldc_w '������: '
        //   686: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   689: aload_2
        //   690: invokevirtual getName : ()Ljava/lang/String;
        //   693: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   696: ldc_w ' '
        //   699: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   702: aload #10
        //   704: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   707: invokevirtual toString : ()Ljava/lang/String;
        //   710: astore #10
        //   712: aload_0
        //   713: getfield ucv : Landroid/content/ContentValues;
        //   716: ldc_w 'CONTENT'
        //   719: aload #10
        //   721: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   724: aload_0
        //   725: getfield ucv : Landroid/content/ContentValues;
        //   728: ldc_w 'FIRE_TYPE'
        //   731: iload_1
        //   732: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   735: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   738: aload_0
        //   739: getfield ucv : Landroid/content/ContentValues;
        //   742: ldc_w 'ALARM_PEOPLE'
        //   745: aload_2
        //   746: invokevirtual getName : ()Ljava/lang/String;
        //   749: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   752: aload_0
        //   753: getfield ucv : Landroid/content/ContentValues;
        //   756: ldc_w 'NOTIFACTION_TYPE'
        //   759: getstatic com/example/hpdemo/Constants$NOTIFACTION_TYPE.MANUAL_ALARAM_NOTIFCATION : I
        //   762: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   765: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   768: goto -> 565
        //   771: iload_1
        //   772: iconst_2
        //   773: if_icmpne -> 565
        //   776: aload_2
        //   777: ldc_w 'ALARM_PEOPLE'
        //   780: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   783: astore #12
        //   785: aload_2
        //   786: ldc_w 'ALARM_CALL'
        //   789: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   792: astore_2
        //   793: aload_0
        //   794: getfield ucv : Landroid/content/ContentValues;
        //   797: ldc_w 'NAME'
        //   800: ldc_w '��������'
        //   803: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   806: new java/lang/StringBuilder
        //   809: dup
        //   810: invokespecial <init> : ()V
        //   813: ldc_w '������: '
        //   816: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   819: aload #12
        //   821: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   824: ldc_w ' '
        //   827: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   830: aload #10
        //   832: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   835: invokevirtual toString : ()Ljava/lang/String;
        //   838: astore #10
        //   840: aload_0
        //   841: getfield ucv : Landroid/content/ContentValues;
        //   844: ldc_w 'CONTENT'
        //   847: aload #10
        //   849: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   852: aload_0
        //   853: getfield ucv : Landroid/content/ContentValues;
        //   856: ldc_w 'FIRE_TYPE'
        //   859: iload_1
        //   860: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   863: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   866: aload_0
        //   867: getfield ucv : Landroid/content/ContentValues;
        //   870: ldc_w 'ALARM_CALL'
        //   873: aload_2
        //   874: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   877: aload_0
        //   878: getfield ucv : Landroid/content/ContentValues;
        //   881: ldc_w 'ALARM_PEOPLE'
        //   884: aload #12
        //   886: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   889: aload_0
        //   890: getfield ucv : Landroid/content/ContentValues;
        //   893: ldc_w 'NOTIFACTION_TYPE'
        //   896: getstatic com/example/hpdemo/Constants$NOTIFACTION_TYPE.MANUAL_ALARAM_NOTIFCATION : I
        //   899: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   902: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   905: goto -> 565
        //   908: aload #11
        //   910: invokestatic addSaveData : (Landroid/content/ContentValues;)V
        //   913: return
        //   914: aload_2
        //   915: ldc_w 'ID'
        //   918: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   921: pop
        //   922: aload_2
        //   923: ldc_w 'DEVICE_TYPE'
        //   926: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   929: istore_1
        //   930: aload_2
        //   931: ldc_w 'ALARM_TYPE'
        //   934: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   937: istore #7
        //   939: aload_2
        //   940: ldc_w 'ALARM_VALUE'
        //   943: invokevirtual getDoubleValue : (Ljava/lang/String;)D
        //   946: pop2
        //   947: aload_2
        //   948: ldc_w 'ALARM_TRIGGER_VALUE'
        //   951: invokevirtual getDoubleValue : (Ljava/lang/String;)D
        //   954: pop2
        //   955: ldc ''
        //   957: astore_2
        //   958: iload #7
        //   960: iconst_1
        //   961: if_icmpne -> 968
        //   964: ldc_w '������������'
        //   967: astore_2
        //   968: iload #7
        //   970: iconst_2
        //   971: if_icmpne -> 978
        //   974: ldc_w '������������'
        //   977: astore_2
        //   978: iload #7
        //   980: iconst_3
        //   981: if_icmpne -> 988
        //   984: ldc_w '��������������'
        //   987: astore_2
        //   988: iload #7
        //   990: iconst_4
        //   991: if_icmpne -> 998
        //   994: ldc_w '������������'
        //   997: astore_2
        //   998: iload #7
        //   1000: iconst_5
        //   1001: if_icmpne -> 1008
        //   1004: ldc_w '������������'
        //   1007: astore_2
        //   1008: iload #7
        //   1010: bipush #6
        //   1012: if_icmpne -> 1019
        //   1015: ldc_w '������������������'
        //   1018: astore_2
        //   1019: iload_1
        //   1020: ifne -> 1034
        //   1023: aload #11
        //   1025: ldc_w 'NAME'
        //   1028: ldc_w '������������'
        //   1031: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1034: iload_1
        //   1035: iconst_2
        //   1036: if_icmpne -> 1050
        //   1039: aload #11
        //   1041: ldc_w 'NAME'
        //   1044: ldc_w '������������'
        //   1047: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1050: aload #11
        //   1052: ldc_w 'isClick'
        //   1055: iconst_0
        //   1056: invokestatic valueOf : (Z)Ljava/lang/Boolean;
        //   1059: invokevirtual put : (Ljava/lang/String;Ljava/lang/Boolean;)V
        //   1062: aload #11
        //   1064: ldc_w 'CONTENT'
        //   1067: aload_2
        //   1068: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1071: aload_0
        //   1072: getfield mAdapter : Lcom/example/hpdemo/adapter/SwipeAdapter;
        //   1075: aload #11
        //   1077: invokevirtual addItem : (Landroid/content/ContentValues;)V
        //   1080: return
        //   1081: astore_2
        //   1082: aload_2
        //   1083: invokevirtual printStackTrace : ()V
        //   1086: return
        //   1087: aload_2
        //   1088: ldc_w 'ID'
        //   1091: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   1094: istore_1
        //   1095: aload_2
        //   1096: ldc 'DEVICE_ID'
        //   1098: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   1101: astore #10
        //   1103: aload_2
        //   1104: ldc_w 'ALARM_STATUS'
        //   1107: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   1110: istore #7
        //   1112: aload_2
        //   1113: ldc_w 'LIGHT_STATUS'
        //   1116: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   1119: istore #8
        //   1121: aload_2
        //   1122: ldc_w 'THERMAL_STATUS'
        //   1125: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   1128: istore #9
        //   1130: aload_2
        //   1131: ldc_w 'ALARM_TIME'
        //   1134: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   1137: astore #12
        //   1139: aload_2
        //   1140: ldc_w 'IMAGE_PATH'
        //   1143: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   1146: astore_2
        //   1147: aload #11
        //   1149: ldc_w 'TIME'
        //   1152: aload #12
        //   1154: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1157: aload #11
        //   1159: ldc_w 'NAME'
        //   1162: ldc_w '��������'
        //   1165: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1168: aload #11
        //   1170: ldc_w 'ID'
        //   1173: iload_1
        //   1174: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   1177: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   1180: aload #11
        //   1182: ldc_w 'isClick'
        //   1185: iconst_0
        //   1186: invokestatic valueOf : (Z)Ljava/lang/Boolean;
        //   1189: invokevirtual put : (Ljava/lang/String;Ljava/lang/Boolean;)V
        //   1192: aload #11
        //   1194: ldc_w 'FILEPATH'
        //   1197: aload_2
        //   1198: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1201: aload #11
        //   1203: ldc_w 'NOTIFACTION_TYPE'
        //   1206: getstatic com/example/hpdemo/Constants$NOTIFACTION_TYPE.STATION_ALARM_NOTIFCATION : I
        //   1209: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   1212: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   1215: iload #7
        //   1217: ifne -> 1221
        //   1220: return
        //   1221: iload #7
        //   1223: iconst_1
        //   1224: if_icmpne -> 1227
        //   1227: iload #7
        //   1229: iconst_2
        //   1230: if_icmpne -> 1234
        //   1233: return
        //   1234: iload #8
        //   1236: ifne -> 1239
        //   1239: iload #8
        //   1241: iconst_1
        //   1242: if_icmpne -> 1245
        //   1245: iload #9
        //   1247: ifne -> 1250
        //   1250: iload #9
        //   1252: iconst_1
        //   1253: if_icmpne -> 1256
        //   1256: aload_0
        //   1257: ldc_w 2131230800
        //   1260: invokevirtual getString : (I)Ljava/lang/String;
        //   1263: iconst_2
        //   1264: anewarray java/lang/Object
        //   1267: dup
        //   1268: iconst_0
        //   1269: iload_1
        //   1270: invokestatic valueOf : (I)Ljava/lang/String;
        //   1273: aastore
        //   1274: dup
        //   1275: iconst_1
        //   1276: aload #10
        //   1278: invokestatic getStationAddr : (Ljava/lang/String;)Ljava/lang/String;
        //   1281: aastore
        //   1282: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
        //   1285: astore_2
        //   1286: ldc 'InformationFragment'
        //   1288: new java/lang/StringBuilder
        //   1291: dup
        //   1292: invokespecial <init> : ()V
        //   1295: ldc_w '��������: content = '
        //   1298: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1301: aload_2
        //   1302: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   1305: invokevirtual toString : ()Ljava/lang/String;
        //   1308: invokestatic i : (Ljava/lang/String;Ljava/lang/String;)V
        //   1311: aload #11
        //   1313: ldc_w 'CONTENT'
        //   1316: aload_2
        //   1317: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1320: aload_0
        //   1321: getfield isCreateView : Z
        //   1324: ifeq -> 1337
        //   1327: aload_0
        //   1328: getfield mAdapter : Lcom/example/hpdemo/adapter/SwipeAdapter;
        //   1331: aload #11
        //   1333: invokevirtual addItem : (Landroid/content/ContentValues;)V
        //   1336: return
        //   1337: aload #11
        //   1339: invokestatic addSaveData : (Landroid/content/ContentValues;)V
        //   1342: return
        //   1343: ldc_w 'guo'
        //   1346: ldc_w '��������������'
        //   1349: invokestatic e : (Ljava/lang/String;Ljava/lang/String;)I
        //   1352: pop
        //   1353: aload_2
        //   1354: ldc_w 'HOTSPOT_NO'
        //   1357: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   1360: astore #10
        //   1362: aload_2
        //   1363: ldc_w 'LONGITUDE'
        //   1366: invokevirtual getDoubleValue : (Ljava/lang/String;)D
        //   1369: dstore_3
        //   1370: aload_2
        //   1371: ldc_w 'LATITUDE'
        //   1374: invokevirtual getDoubleValue : (Ljava/lang/String;)D
        //   1377: dstore #5
        //   1379: aload_2
        //   1380: ldc_w 'VALUE'
        //   1383: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   1386: pop
        //   1387: aload_2
        //   1388: ldc_w 'LAND_TYPE'
        //   1391: invokevirtual getInt32Value : (Ljava/lang/String;)I
        //   1394: pop
        //   1395: aload_2
        //   1396: ldc_w 'IS_CLOUB'
        //   1399: invokevirtual getBoolValue : (Ljava/lang/String;)Z
        //   1402: pop
        //   1403: aload_2
        //   1404: ldc_w 'IS_CONTINUATION'
        //   1407: invokevirtual getBoolValue : (Ljava/lang/String;)Z
        //   1410: pop
        //   1411: aload #11
        //   1413: ldc_w 'TIME'
        //   1416: aload_2
        //   1417: ldc_w 'REPORT_TIME'
        //   1420: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   1423: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1426: aload #11
        //   1428: ldc_w 'NAME'
        //   1431: ldc_w '��������'
        //   1434: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1437: aload #11
        //   1439: ldc_w 'CONTENT'
        //   1442: ldc_w '������������'
        //   1445: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1448: aload #11
        //   1450: ldc_w 'isClick'
        //   1453: iconst_0
        //   1454: invokestatic valueOf : (Z)Ljava/lang/Boolean;
        //   1457: invokevirtual put : (Ljava/lang/String;Ljava/lang/Boolean;)V
        //   1460: aload #11
        //   1462: ldc_w 'HOTSPOTNO'
        //   1465: aload #10
        //   1467: invokevirtual put : (Ljava/lang/String;Ljava/lang/String;)V
        //   1470: aload #11
        //   1472: ldc_w 'LONGITUDE'
        //   1475: dload_3
        //   1476: invokestatic valueOf : (D)Ljava/lang/Double;
        //   1479: invokevirtual put : (Ljava/lang/String;Ljava/lang/Double;)V
        //   1482: aload #11
        //   1484: ldc_w 'LATITUDE'
        //   1487: dload #5
        //   1489: invokestatic valueOf : (D)Ljava/lang/Double;
        //   1492: invokevirtual put : (Ljava/lang/String;Ljava/lang/Double;)V
        //   1495: aload #11
        //   1497: ldc_w 'NOTIFACTION_TYPE'
        //   1500: getstatic com/example/hpdemo/Constants$NOTIFACTION_TYPE.HOT_POINT_NOTIFCATION : I
        //   1503: invokestatic valueOf : (I)Ljava/lang/Integer;
        //   1506: invokevirtual put : (Ljava/lang/String;Ljava/lang/Integer;)V
        //   1509: aload_0
        //   1510: getfield isCreateView : Z
        //   1513: ifeq -> 1526
        //   1516: aload_0
        //   1517: getfield mAdapter : Lcom/example/hpdemo/adapter/SwipeAdapter;
        //   1520: aload #11
        //   1522: invokevirtual addItem : (Landroid/content/ContentValues;)V
        //   1525: return
        //   1526: aload #11
        //   1528: invokestatic addSaveData : (Landroid/content/ContentValues;)V
        //   1531: return
        //   1532: aload_0
        //   1533: aload_2
        //   1534: ldc_w 'HOTPORTNO'
        //   1537: invokevirtual getStringValue : (Ljava/lang/String;)Ljava/lang/String;
        //   1540: putfield hotportNO : Ljava/lang/String;
        //   1543: return
        // Exception table:
        //   from	to	target	type
        //   1071	1080	1081	java/lang/Exception }

        public static interface OnDeleteLayoutCallback {
            void onDeleteLayoutStatusChange(boolean param1Boolean, int param1Int);
        }
    }
