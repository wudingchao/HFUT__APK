package com.helpsoft;

public class HPAndroidVideoEncoder {
    static  {
        System.loadLibrary("H264Encoder");
    }

    public native void CloseEncoder(long paramLong);

    public native int Encoder(long paramLong, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

    public native long OpenEncoder(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}
