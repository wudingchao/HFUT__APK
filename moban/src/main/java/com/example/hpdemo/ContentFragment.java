package com.example.hpdemo;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresPermission;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.hpdemo.activity.BaseActivity;
import com.example.hpdemo.utils.normalTools;
import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    public List<Integer> clickedIds = new ArrayList<Integer>();

    public Handler clickhandler = new Handler() {
        public void handleMessage(Message param1Message) {
            switch (param1Message.what) {
                default:
                    super.handleMessage(param1Message);
                    return;
                case 16:
                    ContentFragment.this.clickedIds.remove(Integer.valueOf(param1Message.arg1));
                case 17:
                    break;
            }
            ContentFragment.access$002(ContentFragment.this, false);
        }
    };

    private boolean isStarting = false;

    public boolean checkClick(int paramInt1, int paramInt2) {
        if (BaseActivity.ingoreIds.contains(Integer.valueOf(paramInt2)))
            return true;
        if (this.clickedIds.contains(Integer.valueOf(paramInt2)))
            return false;
        this.clickedIds.add(Integer.valueOf(paramInt2));
        Message message = this.clickhandler.obtainMessage(16, paramInt2, 0);
        this.clickhandler.sendMessageDelayed(message, 1000L);
        return true;
    }

    public boolean checkIntent(Intent paramIntent) {
        if (!MyApplication.loginOnline) {
            if (paramIntent.getComponent() != null) {
                String str = paramIntent.getComponent().getShortClassName();
                return !(!str.contains("PatrolFoundActivity") && !str.contains("PatrolFoundListActivity") && !str.contains("ResourceCollectActivity") && !str.contains("ResourceCollectListActivity") && !str.contains("CollectRoadActivity") && !str.contains("TraceShowActivity"));
            }
            return false;
        }
        return true;
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) { return paramLayoutInflater.inflate(2130968735, paramViewGroup, false); }

    public void startActivity(Intent paramIntent) {
        if (!this.isStarting) {
            this.isStarting = true;
            this.clickhandler.sendEmptyMessageDelayed(17, 1000L);
            if (checkIntent(paramIntent)) {
                super.startActivity(paramIntent);
                return;
            }
        } else {
            return;
        }
        normalTools.showToast((Context)getActivity(), "����������������");
    }

    public void startActivityForResult(@RequiresPermission Intent paramIntent, int paramInt) {
        if (!this.isStarting) {
            this.isStarting = true;
            this.clickhandler.sendEmptyMessageDelayed(17, 1000L);
            if (checkIntent(paramIntent)) {
                super.startActivityForResult(paramIntent, paramInt);
                return;
            }
        } else {
            return;
        }
        normalTools.showToast((Context)getActivity(), "����������������");
    }
}
