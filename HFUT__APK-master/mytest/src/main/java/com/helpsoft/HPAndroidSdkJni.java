package com.helpsoft;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.Iterator;

public class HPAndroidSdkJni extends com.helpsoft.HPAndroidCallback {
    private static Handler g_onCommandHandler;

    private static Handler g_onConnectErrorHandler;

    private static Handler g_onConnectHandler = new Handler() {
        public void handleMessage(Message param1Message) {
            if (com.helpsoft.HPAndroidCallback.m_pConnectionCallback != null)
                com.helpsoft.HPAndroidCallback.m_pConnectionCallback.onConnect();
        }
    };

    private static Handler g_onDisconnectHandler = new Handler() {
        public void handleMessage(Message param1Message) {
            if (com.helpsoft.HPAndroidCallback.m_pConnectionCallback != null)
                com.helpsoft.HPAndroidCallback.m_pConnectionCallback.onDisconnect();
        }
    };

    static  {
        g_onConnectErrorHandler = new Handler() {
            public void handleMessage(Message param1Message) {
                if (com.helpsoft.HPAndroidCallback.m_pConnectionCallback != null)
                    com.helpsoft.HPAndroidCallback.m_pConnectionCallback.onConnectError();
            }
        };
        g_onCommandHandler = new Handler() {
            public void handleMessage(Message param1Message) {
                Bundle bundle = param1Message.getData();
                if (com.helpsoft.HPAndroidCallback.m_listCommandCallbacks != null) {
                    int i = bundle.getInt("CMD");
                    String str = bundle.getString("TID");
                    byte[] arrayOfByte = bundle.getByteArray("DATA");
                    bundle.getInt("SIZE");
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);
                    byteBuffer.put(arrayOfByte);
                    byteBuffer.flip();
                    com.helpsoft.Variant variant = new com.helpsoft.Variant();
                    /*if (com.helpsoft.Variant.BufferToVariant(byteBuffer, variant)) {
                        Iterator<com.helpsoft.HPAndroidCommandCallback> iterator = com.helpsoft.HPAndroidCallback.m_listCommandCallbacks.iterator();
                        while (true) {
                            if (iterator.hasNext()) {
                                ((com.helpsoft.HPAndroidCommandCallback)iterator.next()).onCommand(i, str, variant);
                                continue;
                            }
                            return;
                        }
                    }*/
                }
            }
        };
        System.loadLibrary("gnustl_shared");
        System.loadLibrary("Qt5Core");
        System.loadLibrary("HPAndroidSdk");
    }

    public static native void connectServer(String paramString, int paramInt);

    public static native int createRtp();

    public static native boolean disconnect();

    public static native int getRtpPort(int paramInt);

    public static native boolean initAndroidSdk();

    public static void onCommand(int paramInt1, String paramString, byte[] paramArrayOfbyte, int paramInt2) {
        Log.d("************", "onCommand " + paramInt1 + " ");
        Message message = g_onCommandHandler.obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putInt("CMD", paramInt1);
        bundle.putString("TID", paramString);
        bundle.putByteArray("DATA", paramArrayOfbyte);
        bundle.putInt("SIZE", paramInt2);
        message.setData(bundle);
        g_onCommandHandler.sendMessage(message);
    }

    public static void onConnect() {
        Log.d("************", "onConnect");
        Message message = g_onConnectHandler.obtainMessage();
        g_onConnectHandler.sendMessage(message);
    }

    public static void onConnectError(int paramInt) {
        Log.d("************", "onConnectError");
        Message message = g_onConnectErrorHandler.obtainMessage();
        g_onConnectErrorHandler.sendMessage(message);
    }

    public static void onDisconnect() {
        Log.d("************", "onDisconnect");
        Message message = g_onDisconnectHandler.obtainMessage();
        g_onDisconnectHandler.sendMessage(message);
    }

    public static void onStreamData(int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfbyte, int paramInt5) {
        if (m_pStreamDataCallbacks != null) {
            int i;
            for (i = m_pStreamDataCallbacks.size() - 1; i >= 0; i--)
                ((com.helpsoft.HPAndroidStreamDataCallback)m_pStreamDataCallbacks.get(i)).onStreamData(paramInt1, paramInt2, paramInt3, paramInt4, paramArrayOfbyte, paramInt5);
        }
    }

    public static native void releaseAndroidSdk();

    public static native void releaseRtp(int paramInt);

    public static boolean sendCommand(int paramInt, String paramString, com.helpsoft.Variant paramVariant) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1048576);
        /*if (!com.helpsoft.Variant.VariantToBuffer(paramVariant, byteBuffer))
            return false;
        byteBuffer.flip();*/
        return sendCommand(paramInt, paramString, byteBuffer.array(), byteBuffer.remaining());
    }

    public static native boolean sendCommand(int paramInt1, String paramString, byte[] paramArrayOfbyte, int paramInt2);

    public static native boolean sendRtpStreamData(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4);

    public static native boolean sendStreamData(int paramInt1, byte[] paramArrayOfbyte, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

    public static native void setAudioCodec(int paramInt);

    public static native void setRtpDst(int paramInt1, int paramInt2, String paramString, int paramInt3);
}
