package com.helpsoft;

import android.hardware.Camera;
import android.view.SurfaceHolder;
import java.io.IOException;

public class HPAndroidVideoCollect implements Camera.PreviewCallback {
    private long m_beginTime = 0L;

    private Camera m_camera;

    private HPAndroidVideoEncoder m_h264Encoder = null;

    private long m_hHandlerVideo = 0L;

    private boolean m_isEncoderVideo = false;

    private HPAndroidVideoCollectCallback m_pVideoCollectCallback = null;

    private byte[] rotateYUV420Degree90(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
        byte[] arrayOfByte = new byte[paramInt1 * paramInt2 * 3 / 2];
        int j = 0;
        int i;
        for (i = 0; i < paramInt1; i++) {
            int m;
            for (m = paramInt2 - 1; m >= 0; m--) {
                arrayOfByte[j] = paramArrayOfbyte[m * paramInt1 + i];
                j++;
            }
        }
        int k = paramInt1 * paramInt2 * 3 / 2 - 1;
        for (i = paramInt1 - 1; i > 0; i -= 2) {
            for (j = 0; j < paramInt2 / 2; j++) {
                arrayOfByte[k] = paramArrayOfbyte[paramInt1 * paramInt2 + j * paramInt1 + i];
                arrayOfByte[--k] = paramArrayOfbyte[paramInt1 * paramInt2 + j * paramInt1 + i - 1];
                k--;
            }
        }
        return arrayOfByte;
    }

    public byte[] cropYUV420(byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) {
        byte[] arrayOfByte = new byte[paramInt1 * paramInt3 * 3 / 2];
        int m = (paramInt2 - paramInt3) / 2;
        int i = 0;
        int j;
        for (j = m; j < m + paramInt3; j++) {
            int n = 0;
            while (n < paramInt1) {
                arrayOfByte[i] = paramArrayOfbyte[j * paramInt1 + n];
                n++;
                i++;
            }
        }
        int k = paramInt2 + m / 2;
        j = k;
        while (j < paramInt3 / 2 + k) {
            m = 0;
            paramInt2 = i;
            i = m;
            while (i < paramInt1) {
                arrayOfByte[paramInt2] = paramArrayOfbyte[j * paramInt1 + i];
                i++;
                paramInt2++;
            }
            j++;
            i = paramInt2;
        }
        return arrayOfByte;
    }

    public Camera getCamera() { return this.m_camera; }

    public void onPreviewFrame(byte[] paramArrayOfbyte, Camera paramCamera) {
        this.m_isEncoderVideo = true;
        if (this.m_beginTime != 0L)
            this.m_beginTime = System.currentTimeMillis();
        byte[] arrayOfByte = rotateYUV420Degree90(paramArrayOfbyte, 320, 240);
        int i = this.m_h264Encoder.Encoder(this.m_hHandlerVideo, arrayOfByte, 90, 320, 240, 320, 240);
        if (i > 4 && this.m_pVideoCollectCallback != null)
            this.m_pVideoCollectCallback.onCollectVideo(arrayOfByte, i, (int)(System.currentTimeMillis() - this.m_beginTime));
        this.m_isEncoderVideo = false;
        this.m_camera.addCallbackBuffer(paramArrayOfbyte);
    }

    public boolean openCollect(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5) throws Exception { // Byte code:
        //   0: aload_0
        //   1: new com/helpsoft/HPAndroidVideoEncoder
        //   4: dup
        //   5: invokespecial <init> : ()V
        //   8: putfield m_h264Encoder : Lcom/helpsoft/HPAndroidVideoEncoder;
        //   11: aload_0
        //   12: aload_0
        //   13: getfield m_h264Encoder : Lcom/helpsoft/HPAndroidVideoEncoder;
        //   16: iload_3
        //   17: iload #6
        //   19: iload #5
        //   21: iload #4
        //   23: invokevirtual OpenEncoder : (IIII)J
        //   26: putfield m_hHandlerVideo : J
        //   29: aload_0
        //   30: getfield m_hHandlerVideo : J
        //   33: lconst_0
        //   34: lcmp
        //   35: ifne -> 40
        //   38: iconst_0
        //   39: ireturn
        //   40: aload_0
        //   41: iload_2
        //   42: invokestatic open : (I)Landroid/hardware/Camera;
        //   45: putfield m_camera : Landroid/hardware/Camera;
        //   48: aload_0
        //   49: getfield m_camera : Landroid/hardware/Camera;
        //   52: aload_1
        //   53: invokevirtual setPreviewDisplay : (Landroid/view/SurfaceHolder;)V
        //   56: new android/graphics/PixelFormat
        //   59: dup
        //   60: invokespecial <init> : ()V
        //   63: astore #7
        //   65: bipush #17
        //   67: aload #7
        //   69: invokestatic getPixelFormatInfo : (ILandroid/graphics/PixelFormat;)V
        //   72: aload_0
        //   73: getfield m_camera : Landroid/hardware/Camera;
        //   76: invokevirtual getParameters : ()Landroid/hardware/Camera$Parameters;
        //   79: astore_1
        //   80: aload_1
        //   81: iload #4
        //   83: iload #5
        //   85: invokevirtual setPreviewSize : (II)V
        //   88: aload_1
        //   89: bipush #17
        //   91: invokevirtual setPreviewFormat : (I)V
        //   94: iload #4
        //   96: iload #5
        //   98: imul
        //   99: aload #7
        //   101: getfield bitsPerPixel : I
        //   104: imul
        //   105: bipush #8
        //   107: idiv
        //   108: istore #4
        //   110: iconst_0
        //   111: istore_2
        //   112: iload_2
        //   113: iconst_3
        //   114: if_icmpge -> 147
        //   117: iload #4
        //   119: newarray byte
        //   121: astore #7
        //   123: aload_0
        //   124: getfield m_camera : Landroid/hardware/Camera;
        //   127: aload #7
        //   129: invokevirtual addCallbackBuffer : ([B)V
        //   132: iload_2
        //   133: iconst_1
        //   134: iadd
        //   135: istore_2
        //   136: goto -> 112
        //   139: astore_1
        //   140: aload_1
        //   141: invokevirtual printStackTrace : ()V
        //   144: goto -> 56
        //   147: aload_0
        //   148: getfield m_camera : Landroid/hardware/Camera;
        //   151: aload_0
        //   152: invokevirtual setPreviewCallbackWithBuffer : (Landroid/hardware/Camera$PreviewCallback;)V
        //   155: aload_0
        //   156: getfield m_camera : Landroid/hardware/Camera;
        //   159: bipush #90
        //   161: invokevirtual setDisplayOrientation : (I)V
        //   164: aload_0
        //   165: getfield m_camera : Landroid/hardware/Camera;
        //   168: aload_1
        //   169: invokevirtual setParameters : (Landroid/hardware/Camera$Parameters;)V
        //   172: aload_1
        //   173: iload_3
        //   174: sipush #1000
        //   177: imul
        //   178: iload_3
        //   179: sipush #1000
        //   182: imul
        //   183: invokevirtual setPreviewFpsRange : (II)V
        //   186: aload_0
        //   187: getfield m_camera : Landroid/hardware/Camera;
        //   190: aload_1
        //   191: invokevirtual setParameters : (Landroid/hardware/Camera$Parameters;)V
        //   194: aload_0
        //   195: getfield m_camera : Landroid/hardware/Camera;
        //   198: aload_0
        //   199: invokevirtual setPreviewCallback : (Landroid/hardware/Camera$PreviewCallback;)V
        //   202: aload_0
        //   203: getfield m_camera : Landroid/hardware/Camera;
        //   206: invokevirtual startPreview : ()V
        //   209: iconst_1
        //   210: ireturn
        //   211: astore #7
        //   213: aload #7
        //   215: invokevirtual printStackTrace : ()V
        //   218: goto -> 172
        //   221: astore_1
        //   222: aload_1
        //   223: invokevirtual printStackTrace : ()V
        //   226: aload_0
        //   227: invokevirtual stopCollect : ()V
        //   230: aload_0
        //   231: getfield m_hHandlerVideo : J
        //   234: lconst_0
        //   235: lcmp
        //   236: ifeq -> 250
        //   239: aload_0
        //   240: getfield m_h264Encoder : Lcom/helpsoft/HPAndroidVideoEncoder;
        //   243: aload_0
        //   244: getfield m_hHandlerVideo : J
        //   247: invokevirtual CloseEncoder : (J)V
        //   250: aload_0
        //   251: lconst_0
        //   252: putfield m_hHandlerVideo : J
        //   255: iconst_0
        //   256: ireturn
        //   257: astore_1
        //   258: aload_1
        //   259: invokevirtual printStackTrace : ()V
        //   262: goto -> 194
        // Exception table:
        //   from	to	target	type
        //   48	56	139	java/io/IOException
        //   80	110	221	java/lang/Exception
        //   117	132	221	java/lang/Exception
        //   147	164	221	java/lang/Exception
        //   164	172	211	java/lang/Exception
        //   172	194	257	java/lang/Exception
        //   194	209	221	java/lang/Exception
        //   213	218	221	java/lang/Exception
        //   258	262	221	java/lang/Exception }

        public void setVideoCollectCallback(HPAndroidVideoCollectCallback paramHPAndroidVideoCollectCallback) { this.m_pVideoCollectCallback = paramHPAndroidVideoCollectCallback; }

        public void stopCollect() {
            if (this.m_camera != null) {
                try {
                    this.m_camera.setPreviewDisplay(null);
                } catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                this.m_camera.setPreviewCallbackWithBuffer(null);
                this.m_camera.stopPreview();
                this.m_camera.release();
                this.m_camera = null;
            } else {
                try {
                    while (this.m_isEncoderVideo == true)
                        Thread.sleep(10L);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                if (this.m_hHandlerVideo != 0L)
                    this.m_h264Encoder.CloseEncoder(this.m_hHandlerVideo);
                this.m_hHandlerVideo = 0L;
                return;
            }
            while (this.m_isEncoderVideo == true)
                Thread.sleep(10L);
        }
    }
