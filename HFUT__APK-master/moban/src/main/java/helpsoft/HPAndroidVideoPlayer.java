package com.helpsoft;

import android.media.MediaCodec;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.IOException;
import java.nio.ByteBuffer;

public class HPAndroidVideoPlayer implements SurfaceHolder.Callback {
    private int h;

    private PlayerThread mPlayer = null;

    private SurfaceView m_sv = null;

    private Surface surface;

    private int w;

    public void closePlayer() {
        try {
            if (this.mPlayer != null)
                this.mPlayer.closePlayer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.m_sv = null;
        if (this.mPlayer != null) {
            this.mPlayer.m_bExitThread = true;
            this.mPlayer.interrupt();
            this.mPlayer = null;
        }
    }

    public boolean isColosed() { return (this.m_sv == null); }

    public boolean playFrame(byte[] paramArrayOfbyte, int paramInt1, int paramInt2) {
        if (this.mPlayer != null)
            try {
                this.mPlayer.playFrame(paramArrayOfbyte, paramInt1, paramInt2);
                return this.mPlayer.m_playsuccessed;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        return false;
    }

    public void reStart() {
        try {
            Log.d("FragmentLivePreview", "restart");
            if (this.mPlayer == null && this.surface != null && this.w != 0 && this.h != 0) {
                this.mPlayer = new PlayerThread();
                this.mPlayer.initPlayer(this.surface, this.w, this.h);
                this.mPlayer.start();
            }
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void setCacheTime(int paramInt) {
        if (this.mPlayer != null)
            this.mPlayer.setCacheTime(paramInt);
    }

    public void setSurfaceView(SurfaceView paramSurfaceView) {
        try {
            this.m_sv = paramSurfaceView;
            this.m_sv.getHolder().addCallback(this);
            return;
        } catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {
        try {
            Log.d("FragmentLivePreview", "surfaceChanged");
            if (this.mPlayer == null) {
                this.mPlayer = new PlayerThread();
                if (!this.mPlayer.initPlayer(paramSurfaceHolder.getSurface(), paramInt2, paramInt3))
                    return;
                this.surface = paramSurfaceHolder.getSurface();
                this.w = paramInt2;
                this.h = paramInt3;
                this.mPlayer.start();
                return;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void surfaceCreated(SurfaceHolder paramSurfaceHolder) {}

    public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder) {
        try {
            if (this.mPlayer != null)
                this.mPlayer.closePlayer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (this.mPlayer != null) {
            this.mPlayer.m_bExitThread = true;
            this.mPlayer.interrupt();
            this.mPlayer = null;
        }
    }

    private class PlayerThread extends Thread {
        ByteBuffer[] inputBuffers = null;

        public boolean m_bExitThread = false;

        private final int m_bufferCount = 60;

        private int m_cacheTime = 200;

        private MediaCodec m_decoder = null;

        private ByteBuffer[] m_playerBuffers = new ByteBuffer[60];

        private int[] m_playerTimes = new int[60];

        public boolean m_playsuccessed = true;

        private int m_rindex = 0;

        private long m_startMs = 0L;

        private int m_startMs2 = 0;

        private int m_windex = 0;

        private PlayerThread() {}

        public void closePlayer() {
            synchronized (this.m_playerBuffers) {
                if (this.m_decoder != null) {
                    this.m_decoder.stop();
                    this.m_decoder.release();
                }
                this.m_bExitThread = true;
                interrupt();
                this.m_playerBuffers = null;
                this.m_playerTimes = null;
                this.m_decoder = null;
                return;
            }
        }

        public boolean initPlayer(Surface param1Surface, int param1Int1, int param1Int2) {
            int i;
            for (i = 0; i < 60; i++)
                this.m_playerBuffers[i] = ByteBuffer.allocate(131072);
            try {
                this.m_decoder = MediaCodec.createDecoderByType("video/avc");
                if (this.m_decoder == null)
                    return false;
            } catch (IOException iOException) {
                iOException.printStackTrace();
                if (this.m_decoder == null)
                    return false;
            }
            MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", param1Int1, param1Int2);
            this.m_decoder.configure(mediaFormat, param1Surface, null, 0);
            this.m_decoder.start();
            return true;
        }

        public void playFrame(byte[] param1ArrayOfbyte, int param1Int1, int param1Int2) {
            synchronized (this.m_playerBuffers) {
                ByteBuffer byteBuffer = this.m_playerBuffers[this.m_windex];
                if (byteBuffer.position() > 0)
                    return;
                this.m_playerTimes[this.m_windex] = param1Int2;
                byteBuffer.put(param1ArrayOfbyte);
                this.m_windex++;
                if (this.m_windex >= 60)
                    this.m_windex = 0;
                return;
            }
        }

        public void run() { // Byte code:
            //   0: invokestatic interrupted : ()Z
            //   3: ifne -> 329
            //   6: aload_0
            //   7: getfield m_bExitThread : Z
            //   10: ifne -> 329
            //   13: lconst_1
            //   14: invokestatic sleep : (J)V
            //   17: aload_0
            //   18: getfield m_playerBuffers : [Ljava/nio/ByteBuffer;
            //   21: astore_3
            //   22: aload_3
            //   23: monitorenter
            //   24: aload_0
            //   25: getfield m_playerBuffers : [Ljava/nio/ByteBuffer;
            //   28: aload_0
            //   29: getfield m_rindex : I
            //   32: aaload
            //   33: astore #4
            //   35: aload #4
            //   37: invokevirtual position : ()I
            //   40: ifgt -> 76
            //   43: aload_3
            //   44: monitorexit
            //   45: goto -> 0
            //   48: astore #4
            //   50: aload_3
            //   51: monitorexit
            //   52: aload #4
            //   54: athrow
            //   55: astore_3
            //   56: aload_0
            //   57: iconst_0
            //   58: putfield m_playsuccessed : Z
            //   61: aload_3
            //   62: invokevirtual printStackTrace : ()V
            //   65: goto -> 0
            //   68: astore_3
            //   69: aload_3
            //   70: invokevirtual printStackTrace : ()V
            //   73: goto -> 17
            //   76: aload_0
            //   77: getfield m_playerTimes : [I
            //   80: aload_0
            //   81: getfield m_rindex : I
            //   84: iaload
            //   85: istore_1
            //   86: aload_0
            //   87: getfield m_startMs : J
            //   90: lconst_0
            //   91: lcmp
            //   92: ifne -> 113
            //   95: aload_0
            //   96: invokestatic currentTimeMillis : ()J
            //   99: aload_0
            //   100: getfield m_cacheTime : I
            //   103: i2l
            //   104: ladd
            //   105: putfield m_startMs : J
            //   108: aload_0
            //   109: iload_1
            //   110: putfield m_startMs2 : I
            //   113: iload_1
            //   114: aload_0
            //   115: getfield m_startMs2 : I
            //   118: isub
            //   119: i2l
            //   120: invokestatic currentTimeMillis : ()J
            //   123: aload_0
            //   124: getfield m_startMs : J
            //   127: lsub
            //   128: lsub
            //   129: invokestatic abs : (J)J
            //   132: ldc2_w 3000
            //   135: lcmp
            //   136: iflt -> 157
            //   139: aload_0
            //   140: invokestatic currentTimeMillis : ()J
            //   143: aload_0
            //   144: getfield m_cacheTime : I
            //   147: i2l
            //   148: ladd
            //   149: putfield m_startMs : J
            //   152: aload_0
            //   153: iload_1
            //   154: putfield m_startMs2 : I
            //   157: invokestatic currentTimeMillis : ()J
            //   160: aload_0
            //   161: getfield m_startMs : J
            //   164: lsub
            //   165: iload_1
            //   166: aload_0
            //   167: getfield m_startMs2 : I
            //   170: isub
            //   171: i2l
            //   172: lcmp
            //   173: ifge -> 181
            //   176: aload_3
            //   177: monitorexit
            //   178: goto -> 0
            //   181: aload_0
            //   182: aload_0
            //   183: getfield m_rindex : I
            //   186: iconst_1
            //   187: iadd
            //   188: putfield m_rindex : I
            //   191: aload_0
            //   192: getfield m_rindex : I
            //   195: bipush #60
            //   197: if_icmplt -> 205
            //   200: aload_0
            //   201: iconst_0
            //   202: putfield m_rindex : I
            //   205: aload_0
            //   206: getfield m_decoder : Landroid/media/MediaCodec;
            //   209: invokevirtual getInputBuffers : ()[Ljava/nio/ByteBuffer;
            //   212: astore #5
            //   214: aload_0
            //   215: getfield m_decoder : Landroid/media/MediaCodec;
            //   218: ldc2_w -1
            //   221: invokevirtual dequeueInputBuffer : (J)I
            //   224: istore_2
            //   225: iload_2
            //   226: iflt -> 279
            //   229: aload #5
            //   231: iload_2
            //   232: aaload
            //   233: astore #5
            //   235: aload #5
            //   237: invokevirtual clear : ()Ljava/nio/Buffer;
            //   240: pop
            //   241: aload #5
            //   243: aload #4
            //   245: invokevirtual array : ()[B
            //   248: iconst_0
            //   249: aload #4
            //   251: invokevirtual position : ()I
            //   254: invokevirtual put : ([BII)Ljava/nio/ByteBuffer;
            //   257: pop
            //   258: aload_0
            //   259: getfield m_decoder : Landroid/media/MediaCodec;
            //   262: iload_2
            //   263: iconst_0
            //   264: aload #4
            //   266: invokevirtual position : ()I
            //   269: iload_1
            //   270: sipush #1000
            //   273: imul
            //   274: i2l
            //   275: iconst_0
            //   276: invokevirtual queueInputBuffer : (IIIJI)V
            //   279: aload #4
            //   281: iconst_0
            //   282: invokevirtual position : (I)Ljava/nio/Buffer;
            //   285: pop
            //   286: new android/media/MediaCodec$BufferInfo
            //   289: dup
            //   290: invokespecial <init> : ()V
            //   293: astore #4
            //   295: aload_0
            //   296: getfield m_decoder : Landroid/media/MediaCodec;
            //   299: aload #4
            //   301: lconst_0
            //   302: invokevirtual dequeueOutputBuffer : (Landroid/media/MediaCodec$BufferInfo;J)I
            //   305: istore_1
            //   306: iload_1
            //   307: iflt -> 319
            //   310: aload_0
            //   311: getfield m_decoder : Landroid/media/MediaCodec;
            //   314: iload_1
            //   315: iconst_1
            //   316: invokevirtual releaseOutputBuffer : (IZ)V
            //   319: aload_3
            //   320: monitorexit
            //   321: aload_0
            //   322: iconst_1
            //   323: putfield m_playsuccessed : Z
            //   326: goto -> 0
            //   329: return
            // Exception table:
            //   from	to	target	type
            //   13	17	68	java/lang/InterruptedException
            //   17	24	55	java/lang/Exception
            //   24	45	48	finally
            //   50	52	48	finally
            //   52	55	55	java/lang/Exception
            //   76	113	48	finally
            //   113	157	48	finally
            //   157	178	48	finally
            //   181	205	48	finally
            //   205	225	48	finally
            //   235	279	48	finally
            //   279	306	48	finally
            //   310	319	48	finally
            //   319	321	48	finally
            //   321	326	55	java/lang/Exception }

            public void setCacheTime(int param1Int) { this.m_cacheTime = param1Int; }
        }
    }
