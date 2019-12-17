package com.example.hpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ContentFragment2 extends Fragment {
    public Handler clickhandler = new Handler() {
        public void handleMessage(Message param1Message) {
            switch (param1Message.what) {
                default:
                    super.handleMessage(param1Message);
                    return;
                case 17:
                    break;
            }
            ContentFragment2.access$002(ContentFragment2.this, false);
        }
    };

    private boolean isStarting = false;

    public boolean checkIntent(Intent paramIntent) {
        if (!MyApplication.loginOnline) {
            String str = paramIntent.getComponent().getShortClassName();
            if (!str.contains("PatrolFoundActivity") && !str.contains("PatrolFoundListActivity") && !str.contains("ResourceCollectActivity") && !str.contains("ResourceCollectListActivity") && !str.contains("CollectRoadActivity") && !str.contains("TraceShowActivity"))
                return false;
        }
        return true;
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) { return paramLayoutInflater.inflate(2130968735, paramViewGroup, false); }

    public void startActivity(Intent paramIntent) {
        if (!this.isStarting) {
            this.isStarting = true;
            this.clickhandler.sendEmptyMessageDelayed(17, 1000L);
            if (checkIntent(paramIntent))
                super.startActivity(paramIntent);
        }
    }

    public void startActivityForResult(@RequiresPermission Intent paramIntent, int paramInt) {
        if (!this.isStarting) {
            this.isStarting = true;
            this.clickhandler.sendEmptyMessageDelayed(17, 1000L);
            if (checkIntent(paramIntent))
                super.startActivityForResult(paramIntent, paramInt);
        }
    }
}
