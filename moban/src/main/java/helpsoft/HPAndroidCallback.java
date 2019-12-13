package com.helpsoft;

import java.util.ArrayList;
import java.util.List;

public class HPAndroidCallback {
    protected static List<HPAndroidCommandCallback> m_listCommandCallbacks;

    protected static HPAndroidConnectionCallback m_pConnectionCallback = null;

    protected static List<HPAndroidStreamDataCallback> m_pStreamDataCallbacks = null;

    static  {
        m_listCommandCallbacks = null;
    }

    public static boolean addCommandCallback(HPAndroidCommandCallback paramHPAndroidCommandCallback) {
        if (m_listCommandCallbacks == null)
            m_listCommandCallbacks = new ArrayList<HPAndroidCommandCallback>();
        return (m_listCommandCallbacks != null && !m_listCommandCallbacks.contains(paramHPAndroidCommandCallback)) ? m_listCommandCallbacks.add(paramHPAndroidCommandCallback) : false;
    }

    public static boolean addStreamDataCallback(HPAndroidStreamDataCallback paramHPAndroidStreamDataCallback) {
        if (m_pStreamDataCallbacks == null)
            m_pStreamDataCallbacks = new ArrayList<HPAndroidStreamDataCallback>();
        return (m_pStreamDataCallbacks != null && !m_pStreamDataCallbacks.contains(paramHPAndroidStreamDataCallback)) ? m_pStreamDataCallbacks.add(paramHPAndroidStreamDataCallback) : false;
    }

    public static HPAndroidConnectionCallback getConnectionCallback() { return m_pConnectionCallback; }

    public static boolean hasCommandCallback(HPAndroidCommandCallback paramHPAndroidCommandCallback) { return (m_listCommandCallbacks == null) ? false : m_listCommandCallbacks.contains(paramHPAndroidCommandCallback); }

    public static void removeCommandCallback(HPAndroidCommandCallback paramHPAndroidCommandCallback) {
        if (m_listCommandCallbacks == null)
            return;
        m_listCommandCallbacks.remove(paramHPAndroidCommandCallback);
    }

    public static void removeStreamDataCallback(HPAndroidStreamDataCallback paramHPAndroidStreamDataCallback) {
        if (m_pStreamDataCallbacks == null)
            return;
        m_pStreamDataCallbacks.remove(paramHPAndroidStreamDataCallback);
    }

    public static void setConnectionCallback(HPAndroidConnectionCallback paramHPAndroidConnectionCallback) { m_pConnectionCallback = paramHPAndroidConnectionCallback; }
}
